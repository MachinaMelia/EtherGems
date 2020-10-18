package machinamelia.ethergems.common.container;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.LazyOptional;
import machinamelia.ethergems.common.capabilities.crystals.CrystalInstance;
import machinamelia.ethergems.common.capabilities.crystals.CrystalProvider;
import machinamelia.ethergems.common.capabilities.crystals.ICrystal;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderInstance;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderProvider;
import machinamelia.ethergems.common.capabilities.cylinders.ICylinder;
import machinamelia.ethergems.common.init.ContainerInit;
import machinamelia.ethergems.common.items.crystals.Crystal;
import machinamelia.ethergems.common.items.cylinders.Cylinder;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.server.OpenCylinderConfirmContainerMessage;
import machinamelia.ethergems.common.tileentity.EtherFurnaceTileEntity;
import java.util.ArrayList;
import java.util.Objects;

public class EtherFurnaceCraftingContainer  extends EtherFurnaceContainer {
    private final IWorldPosCallable canInteractWithCallable;
    private final EtherFurnaceTileEntity tileEntity;
    private final NonNullList<ItemStack> items;

    private boolean craftingInited = false;
    private int affinityIndex;
    private int buttonCounter = 0;
    private int[] strengths;
    private String[] attributes;
    private ArrayList<String> attributeList = new ArrayList<String>();
    private ArrayList<Integer> strengthList = new ArrayList<Integer>();
    private ArrayList<String> elements = new ArrayList<>();
    private ArrayList<ItemStack> gems = null;

    public EtherFurnaceCraftingContainer(final int windowId, final PlayerInventory playerInventory, final EtherFurnaceTileEntity tileEntity) {
        super(ContainerInit.ETHER_FURNACE_CRAFTING_CONTAINER.get(), windowId, playerInventory, tileEntity);
        this.tileEntity = tileEntity;
        this.items = NonNullList.create();
        this.size = 44;
        this.attributes = new String[8];
        this.strengths = new int[8];
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
        this.initSlots();
        this.initInventory();
    }
    public EtherFurnaceTileEntity getTileEntity() {
        return tileEntity;
    }
    private static EtherFurnaceTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof EtherFurnaceTileEntity) {
            return (EtherFurnaceTileEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }
    public EtherFurnaceCraftingContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }
    @Override
    public ItemStack[] getCurrentSlotStacks() {
        for (int i = 0; i < 8; i++) {
            currentSlotStacks[i] = this.tileEntity.getItems().get(i);
        }
        return currentSlotStacks;
    }

    public void initInventory() {
        CompoundNBT compoundNBT = (CompoundNBT) this.playerInventory.player.getPersistentData().get("ether_furnace_inventory");
        ItemStack[] items = new ItemStack[44];
        if (compoundNBT != null) {
            ListNBT listNBT = (ListNBT) compoundNBT.get("Selected");
            if (listNBT != null) {
                readItemStacksFromTag(items, listNBT);
                for (int i = 0; i < 8; i++) {
                    this.items.add(items[i]);
                }
                this.tileEntity.setItems(this.items);
            }
            int shooterIndex = compoundNBT.getInt("Shooter");
            int engineerIndex = compoundNBT.getInt("Engineer");
            this.affinityIndex = compoundNBT.getInt("Affinity");
            this.tileEntity.setShooterIndex(shooterIndex);
            this.tileEntity.setEngineerIndex(engineerIndex);
            this.initCrafting();
        }
    }

    public void initCrafting() {
        this.tileEntity.resetButtonCounter();
        this.tileEntity.setShouldOpen(false);
        this.tileEntity.setCurrentTurn(0);
        this.tileEntity.rollTurns(this.affinityIndex);
        int level = 0;
        for (ItemStack item : this.tileEntity.getItems()) {
            String element = "";
            if (item.getItem() instanceof Crystal) {
                LazyOptional<ICrystal> crystalCapability = item.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
                ICrystal crystal = crystalCapability.orElse(new CrystalInstance());

                String attributeString = crystal.getAttributesCSV();
                this.attributes = attributeString.split(",");
                this.strengths = crystal.getStrengthArray();
                element = crystal.getElement();
                if (level == 0) {
                    level = crystal.getLevel();
                }
            } else if (item.getItem() instanceof Cylinder) {
                LazyOptional<ICylinder> cylinderCapability = item.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
                ICylinder cylinder = cylinderCapability.orElse(new CylinderInstance());

                this.attributes = new String[1];
                this.attributes[0] = cylinder.getAttribute();
                this.strengths = new int[1];
                this.strengths[0] = cylinder.getStrength();
                element = cylinder.getElement();
                if (level == 0) {
                    level = cylinder.getLevel();
                }
            }
            if (item.getItem() instanceof Crystal || item.getItem() instanceof Cylinder) {
                for (int j = 0; j < attributes.length; j++) {
                    int sameAttributeIndex = -1;
                    for (int k = 0; k < attributeList.size(); k++) {
                        if (attributes[j].equals(attributeList.get(k))) {
                            sameAttributeIndex = k;
                        }
                    }
                    if (sameAttributeIndex == -1) {
                        attributeList.add(attributes[j]);
                        strengthList.add(strengths[j]);
                        this.elements.add(element);
                    } else {
                        int currentStrength = strengthList.get(sameAttributeIndex);
                        strengthList.set(sameAttributeIndex, currentStrength + strengths[j]);
                    }
                }
            }
        }
        this.tileEntity.setAttributes(attributeList);
        this.tileEntity.setStrengths(strengthList);
        this.tileEntity.setElements(elements);
        this.tileEntity.setCrystalLevel(level);
        this.tileEntity.tick();
        this.craftingInited = true;
    }

    public ArrayList<String> getAttributes() {
        return this.tileEntity.getAttributes();
    }

    public ArrayList<Integer> getStrengths() {
        return this.tileEntity.getStrengths();
    }

    public ArrayList<Integer> getAmountList() {
        return this.tileEntity.getAmountList();
    }

    public ArrayList<String> getElements() {
        return this.elements;
    }

    public void setFlameIndex(int flameIndex) {
        this.tileEntity.setFlameIndex(flameIndex);
    }

    public int getFlameIndex() {
        return this.tileEntity.getFlameIndex();
    }

    public int getCylinderCounter() { return this.tileEntity.getCylinderCounter(); }
    public int getOriginalCylinderCounter() { return this.tileEntity.getOriginalCylinderCounter(); }
    public boolean getIsFever() { return this.tileEntity.getIsFever(); }
    public int getCylinderGauge() { return this.tileEntity.getCylinderGauge(); }

    public void createCylinder(int index) { this.tileEntity.createCylinder(index); }

    public int getButtonCounter() {
        return this.buttonCounter;
    }
    public void addButtonCounter() {
        this.buttonCounter++;
        this.tileEntity.addButtonCounter();
    }

    public void resetButtonCounter() {
        this.buttonCounter = 0;
        this.tileEntity.resetButtonCounter();
    }

    public void initSlots() {
        // Do nothing you fool!
    }

    @Override
    public void openGui() {
        OpenCylinderConfirmContainerMessage openCylinderConfirmContainerMessage = new OpenCylinderConfirmContainerMessage();
        NetworkHandler.simpleChannel.sendToServer(openCylinderConfirmContainerMessage);
    }

    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {
        this.getSlot(slotID).putStack(stack);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        this.resetButtonCounter();
    }

}
