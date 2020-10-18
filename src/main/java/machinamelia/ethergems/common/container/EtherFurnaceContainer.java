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
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.StringTextComponent;
import machinamelia.ethergems.common.init.BlockInit;
import machinamelia.ethergems.common.items.crystals.Crystal;
import machinamelia.ethergems.common.items.cylinders.Cylinder;
import machinamelia.ethergems.common.tileentity.EtherFurnaceTileEntity;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IWorldPosCallable;
import javax.annotation.Nullable;
import java.util.*;

public class EtherFurnaceContainer extends Container {
    public final EtherFurnaceTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;
    protected final ItemStack[] currentSlotStacks = new ItemStack[8];
    private static boolean staticIsSlotsDisabled = false;

    public boolean ableToConfirm =  false;
    protected int size;
    protected PlayerEntity player;
    protected PlayerInventory playerInventory;
    private boolean isSlotsDisabled = false;


    public EtherFurnaceContainer(@Nullable ContainerType<?> type, final int windowId, final PlayerInventory playerInventory, final EtherFurnaceTileEntity tileEntity) {
        super(type, windowId);
        this.tileEntity = tileEntity;
        this.playerInventory = playerInventory;
        this.player = playerInventory.player;
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
        this.size = 44;
    }

    public boolean getIsSlotsDisabled() {
        return this.isSlotsDisabled;
    }

    public void disableSlots(boolean isSlotsDisabled) {
        this.isSlotsDisabled = isSlotsDisabled;
        staticIsSlotsDisabled = this.isSlotsDisabled;
    }

    public EtherFurnaceTileEntity getTileEntity() {
        return tileEntity;
    }
    private static EtherFurnaceTileEntity getTileEntity(final PlayerEntity player, final PacketBuffer data) {
        Objects.requireNonNull(player, "player cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof EtherFurnaceTileEntity) {
            return (EtherFurnaceTileEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }


    public EtherFurnaceContainer(@Nullable ContainerType<?> type, final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(type, windowId, playerInventory, getTileEntity(playerInventory.player, data));
    }

    public boolean isCrystal(ItemStack stack) {
        return stack.getItem() instanceof Crystal || stack.getItem() instanceof Cylinder;
    }

    public ItemStack[] getCurrentSlotStacks() {
        return currentSlotStacks;
    }

    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {
        if (this.isSlotsDisabled && slotID >= 36) {
            boolean isPutInSlot = false;
            for (int i = 0; i < 36; i++) {
                Slot slot = this.getSlot(i);
                if (!slot.getHasStack() && !isPutInSlot) {
                    this.getSlot(i).putStack(stack);
                    isPutInSlot = true;
                }
            }
        } else if(this.getSlot(slotID) != null && stack != null) {
            if (slotID < 36) {
                this.getSlot(slotID).putStack(stack);
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        return itemstack;
    }

    @Override
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        return false;
    }

    public static ListNBT writeItemStacksToTag(ItemStack[] items, int maxQuantity) {
        ListNBT tagList = new ListNBT();
        for (int i = 0; i < items.length; i++) {
            CompoundNBT tag = new CompoundNBT();
            tag.putShort("Slot", (short) i);
            if (items[i] != null) {
                items[i].write(tag);
                if (maxQuantity > Short.MAX_VALUE) {
                    tag.putInt("Quantity", items[i].getCount());
                } else if (maxQuantity > Byte.MAX_VALUE) {
                    tag.putShort("Quantity", (short) items[i].getCount());
                }
            }
            tagList.add(tag);
        }
        return tagList;
    }
    public static void readItemStacksFromTag(ItemStack[] items, ListNBT tagList) {
        for (int i = 0; i < tagList.size(); i++) {
            CompoundNBT tag = tagList.getCompound(i);
            int b = tag.getShort("Slot");
            if (b < items.length) {
                items[b] = ItemStack.read(tag);
                INBT quant = tag.get("Quantity");
                if (quant instanceof NumberNBT) {
                    items[b].setCount(((NumberNBT) quant).getInt());
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
            if (!this.tileEntity.getIsLocked()) {
                this.tileEntity.setPlayerUsing(playerIn.getUniqueID());
                this.tileEntity.setIsLocked(true);
                return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.ETHER_FURNACE.get());
            } else if (this.tileEntity.getPlayerUsing().equals(playerIn.getUniqueID())) {
                return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockInit.ETHER_FURNACE.get());
            } else {
                playerIn.sendMessage(new StringTextComponent("Ether Furnace in use!"));
                return false;
            }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        if (this.tileEntity.getPlayerUsing() != null && this.tileEntity.getPlayerUsing().equals(playerIn.getUniqueID())) {
            this.tileEntity.setIsLocked(false);
            this.tileEntity.setPlayerUsing(null);
        }
    }

    public void openGui() {

    }

}
