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
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import machinamelia.ethergems.common.capabilities.crystals.CrystalInstance;
import machinamelia.ethergems.common.capabilities.crystals.CrystalProvider;
import machinamelia.ethergems.common.capabilities.crystals.ICrystal;
import machinamelia.ethergems.common.init.ContainerInit;
import machinamelia.ethergems.common.items.crystals.Crystal;
import machinamelia.ethergems.common.items.cylinders.Cylinder;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.server.OpenOptionsContainerMessage;
import machinamelia.ethergems.common.tileentity.EtherFurnaceTileEntity;
import java.util.ArrayList;

public class EtherFurnaceInventoryContainer extends EtherFurnaceContainer {

    private EtherFurnaceTileEntity etherFurnaceTileEntity = new EtherFurnaceTileEntity();

    public EtherFurnaceInventoryContainer(final int windowId, final PlayerInventory playerInventory, final EtherFurnaceTileEntity tileEntity) {
        super(ContainerInit.ETHER_FURNACE_INVENTORY_CONTAINER.get(), windowId, playerInventory, tileEntity);

        this.initSlots();
        this.initInventory();
    }
    public EtherFurnaceInventoryContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        super(ContainerInit.ETHER_FURNACE_INVENTORY_CONTAINER.get(), windowId, playerInventory, data);
        this.initSlots();
        this.initInventory();
    }

    public void initSlots() {
        // Crystal Inventory
        int startX = 16;
        int startY = 18 + 26;
        int slotSizePlus2 = 18;
        for (int row = 0; row < 4; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(etherFurnaceTileEntity, (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }
        // Gem Inventory
        startX = 204 + 8;
        startY = 18;
        for (int row = 0; row < 2; ++row) {
            for (int column = 0; column < 4; ++column) {
                this.addSlot(new Slot(etherFurnaceTileEntity, 36 + (row * 4) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }
    }

    protected void initInventory() {
        if (!this.tileEntity.getIsLocked()) {
            CompoundNBT compoundNBT = (CompoundNBT) this.playerInventory.player.getPersistentData().get("crystal_inventory");
            ItemStack[] items = new ItemStack[44];
            if (compoundNBT != null) {
                ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                if (listNBT != null) {
                    readItemStacksFromTag(items, listNBT);
                    for (int i = 0; i < 44; i++) {
                        this.putStackInSlot(i, items[i]);
                    }
                }
            }
        }
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        ArrayList<String> attributeList = new ArrayList<String>();
        for (int i = 0; i < 8; i++) {
            Slot currentSlot = this.getSlot(36 + i);
            if (currentSlot.getHasStack() && (currentSlot.getStack().getItem() instanceof Crystal || currentSlot.getStack().getItem() instanceof Cylinder)) {
                ItemStack stack = currentSlot.getStack();
                LazyOptional<ICrystal> crystalCapability = stack.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
                ICrystal crystal = crystalCapability.orElse(new CrystalInstance());
                String attributeString = crystal.getAttributesCSV();
                String[] attributes = attributeString.split(",");
                for (int j = 0; j < attributes.length; j++) {
                    if (attributes[j] != null) {
                        int sameAttributeIndex = -1;
                        for (int k = 0; k < attributeList.size(); k++) {
                            if (attributes[j].equals(attributeList.get(k))) {
                                sameAttributeIndex = k;
                            }
                        }
                        if (sameAttributeIndex == -1) {
                            attributeList.add(attributes[j]);
                        }
                    }
                }
            }
        }
        ItemStack stack = super.slotClick(slotId, dragType, clickTypeIn, player);
        if (slotId < 44 && slotId >= 0) {
            ItemStack placedStack = this.getSlot(slotId).getStack();
            if (placedStack.getItem() instanceof Crystal || placedStack.getItem() instanceof Cylinder) {
                LazyOptional<ICrystal> crystalCapability = placedStack.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
                ICrystal crystal = crystalCapability.orElse(new CrystalInstance());
                String attributeString = crystal.getAttributesCSV();
                String[] attributes = attributeString.split(",");
                if (attributeList.size() + attributes.length >= 9) {
                    boolean hasPlaced = false;
                    for (int i = 0; i < 36; i++) {
                        Slot slot = this.getSlot(i);
                        if (!slot.getHasStack() && !hasPlaced) {
                            this.putStackInSlot(i, placedStack);
                            hasPlaced = true;
                        }
                    }
                    this.putStackInSlot(slotId, ItemStack.EMPTY);
                }
            }
        }

        int count = 0;
        for (int i = 0; i < 8; i++) {
            Slot currentSlot = this.getSlot(36 + i);
            if (currentSlot.getHasStack() && isCrystal(currentSlot.getStack())) {
                count++;
                ItemStack currentSlotStack = currentSlot.getStack();
                currentSlotStacks[i] = currentSlotStack;
            }
        }

        if (count >= 2) {
            this.ableToConfirm = true;
        } else {
            this.ableToConfirm = false;
        }

        return stack;
    }

    @Override
    public void openGui() {
        ItemStack[] items = new ItemStack[44];
        for (int i = 0; i < items.length; i++) {
            items[i] = this.getSlot(i).getStack();
        }
        OpenOptionsContainerMessage openOptionsContainerMessage = new OpenOptionsContainerMessage(items);
        NetworkHandler.simpleChannel.sendToServer(openOptionsContainerMessage);
    }
}
