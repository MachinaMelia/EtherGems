package machinamelia.ethergems.common.tileentity;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;

import net.minecraft.inventory.container.Container;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraftforge.items.wrapper.InvWrapper;
import machinamelia.ethergems.common.container.EtherFurnaceInventoryContainer;
import machinamelia.ethergems.common.init.TileEntityInit;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.server.AddCylindersToConfirmMessage;
import machinamelia.ethergems.common.network.server.AddGemsToConfirmMessage;
import machinamelia.ethergems.common.util.CylinderHandler;
import machinamelia.ethergems.common.util.GemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;


public class EtherFurnaceTileEntity extends LockableLootTileEntity implements ITickableTileEntity {

    private final int[][] engineerAbilities = { { 25, 25, 50 }, { 80, 10, 10 }, { 15, 60, 25 }, { 60, 15, 25 }, { 10, 80, 10 }, { 10, 10, 80 }, { 5, 90, 5 } };

    public boolean hasCylinders = false;
    public boolean hasGems = false;
    protected int numPlayersUsing;
    private boolean isLocked = false;
    private boolean isSlotsDisabled = false;
    private boolean shouldOpen = false;
    private boolean hasMadeGems = false;
    private boolean hasMovedOn = false;
    private boolean isFever = false;
    private boolean hasCheckedCylinderCounter = false;
    private int shooterIndex;
    private int engineerIndex;
    private int previousFlameIndex = 4;
    private int dunbanCounter;
    private int buttonCounter = 0;
    private int crystalLevel = 0;
    private int delay = 20;
    private int gentleDelay = 10;
    private int cylinderGauge = 0;
    private int cylinderCounter = 1;
    private int originalCylinderCounter = 1;
    private int currentTurn = 0;
    private int numTurns;
    private int feverTurns = 0;
    private int flameIndex = 4;


    private ArrayList<Integer> strengthList = new ArrayList<Integer>();
    private ArrayList<Integer> previousCylinderIndexList = new ArrayList<Integer>();
    private ArrayList<Integer> amountList = new ArrayList<Integer>();
    private ArrayList<String> attributeList = new ArrayList<String>();
    private ArrayList<String> elements = new ArrayList<>();
    private ArrayList<ItemStack> cylinders;
    private ArrayList<ItemStack> gems;
    private NonNullList<ItemStack> furnaceContents = NonNullList.withSize(44, ItemStack.EMPTY);
    private IItemHandlerModifiable items = createHandler();
    private UUID playerUsing;
    private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);

    public EtherFurnaceTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
        this.flameIndex = 4;
    }
    public EtherFurnaceTileEntity() {
        this(TileEntityInit.ETHER_FURNACE_TILE_ENTITY.get());
    }

    public void checkGems() {
        if (gems != null && this.hasGems) {
            this.hasGems = false;
            AddGemsToConfirmMessage addGemsToConfirmMessage = new AddGemsToConfirmMessage(gems.toArray(new ItemStack[0]));
            NetworkHandler.simpleChannel.sendToServer(addGemsToConfirmMessage);
        }
    }

    public void checkCylinders() {
        int totalCylinders = 0;
        int numGems = 0;
        if (gems != null) {
            numGems = gems.size();
        }
        if (this.buttonCounter - numGems > this.originalCylinderCounter) {
            totalCylinders = this.originalCylinderCounter;
        } else {
            totalCylinders = this.buttonCounter - numGems;
        }
        if (cylinders != null && this.hasCylinders && cylinders.size() == totalCylinders && !this.hasMovedOn) {
            this.hasCylinders = false;
            this.shouldOpen = true;
            AddCylindersToConfirmMessage addCylindersToConfirmMessage = new AddCylindersToConfirmMessage(cylinders.toArray(new ItemStack[0]), this.shouldOpen);
            NetworkHandler.simpleChannel.sendToServer(addCylindersToConfirmMessage);
            this.shouldOpen = false;
            this.cylinders = new ArrayList<ItemStack>();
            this.hasMovedOn = true;
        } else if (this.flameIndex == 3 && this.buttonCounter == 0 && !this.hasCylinders && !this.hasMovedOn) {
            boolean shouldOpen = false;
            ArrayList<ItemStack> fakeCylinders = new ArrayList<ItemStack>();
            for (int i = 0; i < 9; i ++) {
                fakeCylinders.add(ItemStack.EMPTY);
            }
            AddCylindersToConfirmMessage addCylindersToConfirmMessage = new AddCylindersToConfirmMessage(fakeCylinders.toArray(new ItemStack[0]), this.shouldOpen);
            NetworkHandler.simpleChannel.sendToServer(addCylindersToConfirmMessage);
            this.shouldOpen = false;
        }
    }

    public void rollTurns(int affinityIndex) {
        Random randy = new Random();
        switch (affinityIndex) {
            case 0:
                this.numTurns = randy.nextInt(3) + 3;
                break;
            case 1:
                this.numTurns = randy.nextInt(3) + 5;
                break;
            case 2:
                this.numTurns = randy.nextInt(5) + 6;
                break;
            case 3:
                this.numTurns = randy.nextInt(6) + 8;
                break;
            case 4:
                this.numTurns = randy.nextInt(6) + 10;
                break;
        }

    }

    @Override
    public void tick() {
        if (this.attributeList.size() > 0) {
            this.delay();
        }
    }


    public void delay() {
        this.delay--;
        if (delay <= 0) {
            this.delay = 20;
            craft();
            checkGems();
            checkCylinders();
        }
    }

    public void gentleDelay() {
        this.gentleDelay--;
        if (gentleDelay <= 0) {
            this.gentleDelay = 10;
            gentleFlame();
        }
    }

    public void craft() {
        if (currentTurn < numTurns) {
            if (!hasCheckedCylinderCounter) {
                if (shooterIndex == 6) {
                    cylinderCounter = 3;
                    originalCylinderCounter = 3;
                } else {
                    this.cylinderCounter = 1;
                    originalCylinderCounter = 1;
                }
                this.cylinderGauge = 0;
                for (int i = 0; i < attributeList.size(); i++) {
                    amountList.add(0);
                }
                previousCylinderIndexList = new ArrayList<Integer>();
                hasCheckedCylinderCounter = true;
                hasMadeGems = false;
                hasMovedOn = false;
            }
            Random randy = new Random();
            int flameRoll = randy.nextInt(100);
            int strongPercentage = this.engineerAbilities[engineerIndex][0];
            int mediumPercentage = this.engineerAbilities[engineerIndex][1];
            int gentlePercentage = this.engineerAbilities[engineerIndex][2];
            if (flameRoll < strongPercentage) {
                flameIndex = 0;
            } else if (flameRoll < strongPercentage + mediumPercentage) {
                flameIndex = 1;
            } else if (flameRoll < strongPercentage + mediumPercentage + gentlePercentage) {
                flameIndex = 2;
            }
            switch (flameIndex) {
                case 0:
                    strongFlame();
                    if (shooterIndex == 5) {
                        strongFlame();
                    }
                    break;
                case 1:
                    mediumFlame();
                    if (shooterIndex == 5) {
                        mediumFlame();
                    }
                    break;
                case 2:
                    gentleFlame();
                    if (shooterIndex == 5) {
                        gentleDelay();
                    }
                    break;
            }
            previousFlameIndex = flameIndex;
            currentTurn++;
            int fever = randy.nextInt(100);
            if (shooterIndex != 5 && (fever == 69 || isFever || (this.shooterIndex == 0 && fever == 42))) {
                numTurns++;
                feverTurns++;
                isFever = true;
            }
            if (isFever && feverTurns >= 3) {
                int continueRoll = randy.nextInt(100);
                if (continueRoll < 30 || feverTurns == 10) {
                    isFever = false;
                }
            }
        } else if (!hasMadeGems) {
            this.flameIndex = 3;
            this.hasCheckedCylinderCounter = false;
            this.gems = new ArrayList<ItemStack>();
            for (int i = 0; i < strengthList.size(); i++) {
                if (strengthList.get(i) >= 300) {
                    // Set to Max Strength in Mega Heat
                    int truncatedStrength = 100;
                    gems.add(GemHandler.createGem(crystalLevel + 1, elements.get(i), attributeList.get(i), truncatedStrength));
                    gems.add(GemHandler.createGem(crystalLevel + 1, elements.get(i), attributeList.get(i), truncatedStrength));
                } else if (strengthList.get(i) >= 200) {
                    int truncatedStrength = strengthList.get(i) - 200;
                    gems.add(GemHandler.createGem(crystalLevel + 1, elements.get(i), attributeList.get(i), truncatedStrength));
                } else if (strengthList.get(i) >= 100) {
                    int truncatedStrength = strengthList.get(i) - 100;
                    gems.add(GemHandler.createGem(crystalLevel, elements.get(i), attributeList.get(i), truncatedStrength));
                }
            }
            if (gems.size() > 0) {
                this.hasGems = true;
            }
            this.hasMadeGems = true;
        }
    }
    public void createCylinder(int index) {
        if (this.cylinders == null) {
            this.cylinders = new ArrayList<ItemStack>();
        }
        int counter = 0;
        for (int i = 0; i < strengthList.size(); i++) {
            if (strengthList.get(i) < 99) {
                counter++;
            }
        }
        if (strengthList.get(index) < 100 && !previousCylinderIndexList.contains(index)) {
            cylinders.add(CylinderHandler.createCylinder(crystalLevel, elements.get(index), attributeList.get(index), strengthList.get(index)));
            previousCylinderIndexList.add(index);
            cylinderCounter--;
            this.hasCylinders = true;
        }
    }

    public void strongFlame() {
        Random randy = new Random();
        int attributeIndex = randy.nextInt(attributeList.size());
        int amount = 0;
        if (this.shooterIndex == 3) {
            if (this.previousFlameIndex != 0) {
                dunbanCounter = 2;
            } else {
                dunbanCounter++;
            }
            amount = dunbanCounter;
        } else {
            amount = randy.nextInt(12) + 3;
            if (shooterIndex == 1) {
                amount += randy.nextInt(6) + 1;
            }
        }
        for (int i = 0; i < attributeList.size(); i++) {
            if (i == attributeIndex) {
                amountList.set(i, amount);
            } else {
                amountList.set(i, 0);
            }
        }
        int currentStrength = this.strengthList.get(attributeIndex);
        this.strengthList.set(attributeIndex, currentStrength + amount);
    }

    public void mediumFlame() {
        int amount = 0;
        if (this.shooterIndex == 3) {
            if (this.previousFlameIndex != 1) {
                dunbanCounter = 2;
            } else {
                dunbanCounter++;
            }
            amount = dunbanCounter;
        } else {
            Random randy = new Random();
            amount = randy.nextInt(6) + 1;

            if (shooterIndex == 4) {
                amount += randy.nextInt(6) + 1;
            }
        }
        for (int i = 0; i < attributeList.size(); i++) {
            amountList.set(i, amount);
        }
        for (int i = 0; i < strengthList.size(); i++) {
            int currentStrength = this.strengthList.get(i);
            this.strengthList.set(i, currentStrength + amount);
        }
    }

    public void gentleFlame() {
        Random randy = new Random();
        int amount = randy.nextInt(30) + 30;
        if (shooterIndex == 2) {
            amount += 25;
        }
        this.cylinderGauge += amount;
        if (cylinderGauge >= 100) {

            cylinderCounter++;
            originalCylinderCounter++;
            this.cylinderGauge = cylinderGauge - 100;
        }
    }

    public void disableSlots(boolean isSlotsDisabled) {
        this.isSlotsDisabled = isSlotsDisabled;
    }

    private void playSound(SoundEvent sound) {
        double dx = (double) this.pos.getX() + 0.5d;
        double dy = (double) this.pos.getY() + 0.5d;
        double dz = (double) this.pos.getZ() + 0.5d;
        this.world.playSound((PlayerEntity) null, dx, dy, dz, sound, SoundCategory.BLOCKS, 0.5f, this.world.rand.nextFloat() * 0.1f + 0.9f);
    }

    public static void swapContents(EtherFurnaceTileEntity te, EtherFurnaceTileEntity otherTe) {
        NonNullList<ItemStack> list = te.getItems();
        te.setItems(otherTe.getItems());
        otherTe.setItems(list);
    }

    public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
        BlockState blockstate = reader.getBlockState(pos);
        if (blockstate.hasTileEntity()) {
            TileEntity tileEntity = reader.getTileEntity(pos);
            if (tileEntity instanceof EtherFurnaceTileEntity) {
                return ((EtherFurnaceTileEntity) tileEntity).numPlayersUsing;
            }
        }
        return 0;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        nbtTagCompound = write(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag)
    {
        this.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.furnaceContents);
        }
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.furnaceContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compound)) {
            ItemStackHelper.loadAllItems(compound, this.furnaceContents);
        }
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        if (id == 1) {
            this.numPlayersUsing = type;
            return true;
        } else {
            return super.receiveClientEvent(id, type);
        }
    }

    @Override
    public Container createMenu(final int windowID, final PlayerInventory playerInventory, final PlayerEntity player) {
        return new EtherFurnaceInventoryContainer(windowID, playerInventory, this);
    }


    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT nbtTagCompound = new CompoundNBT();
        write(nbtTagCompound);
        int tileEntityType = 42;  // arbitrary number; only used for vanilla TileEntities.  You can use it, or not, as you want.
        return new SUpdateTileEntityPacket(this.pos, tileEntityType, nbtTagCompound);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (this.isSlotsDisabled && index >= 36) {
            return false;
        }
        return true;
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory) {
        return new EtherFurnaceInventoryContainer(id, playerInventory, this);
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        if (this.itemHandler != null) {
            this.itemHandler.invalidate();
            this.itemHandler = null;
        }
    }

    @Override
    public void remove() {
        super.remove();
        if (itemHandler != null) {
            itemHandler.invalidate();
        }
    }

    private IItemHandlerModifiable createHandler() {
        return new InvWrapper(this);
    }

    public boolean getIsLocked() {
        return this.isLocked;
    }
    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }
    public void setShouldOpen(boolean shouldOpen) {
        this.shouldOpen = shouldOpen;
    }
    public boolean getIsFever() { return this.isFever; }

    public void setCrystalLevel(int level) {
        this.crystalLevel = level;
    }
    public int getDelay() {
        return this.delay;
    }
    public void setDelay(int delay) {
        this.delay = delay;
    }
    public void setShooterIndex(int shooterIndex) {
        this.shooterIndex = shooterIndex;
    }
    public void setEngineerIndex(int engineerIndex) {
        this.engineerIndex = engineerIndex;
    }
    public void setFlameIndex(int flameIndex) {
        this.flameIndex = flameIndex;
    }
    public int getFlameIndex() {
        return this.flameIndex;
    }
    public void resetButtonCounter() {
        this.buttonCounter = 0;
    }
    public int getButtonCounter() {
        return this.buttonCounter;
    }
    public void addButtonCounter() {
        this.buttonCounter++;
    }
    public int getCylinderCounter() { return this.cylinderCounter; }
    public int getOriginalCylinderCounter() { return this.originalCylinderCounter; }
    public int getCylinderGauge() { return this.cylinderGauge; }
    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public UUID getPlayerUsing() {
        return this.playerUsing;
    }
    public void setPlayerUsing(UUID playerUsing) {
        this.playerUsing = playerUsing;
    }

    public void setElements(ArrayList<String> elements) {
        this.elements = elements;
    }
    public void setAttributes(ArrayList<String> attributes) {
        this.attributeList = attributes;
    }
    public ArrayList<String> getAttributes() {
        return this.attributeList;
    }
    public void setStrengths(ArrayList<Integer> strengths) {
        this.strengthList = strengths;
    }
    public ArrayList<Integer> getStrengths() {
        return this.strengthList;
    }
    public ArrayList<Integer> getAmountList() { return this.amountList; }

    @Override
    public int getSizeInventory() {
        return 44;
    }

    @Override
    public void setItems (NonNullList<ItemStack> itemsIn) {
        this.furnaceContents = itemsIn;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.ether_furnace");
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.furnaceContents;
    }
}
