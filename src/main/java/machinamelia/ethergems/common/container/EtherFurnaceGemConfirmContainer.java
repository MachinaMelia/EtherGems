package machinamelia.ethergems.common.container;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import machinamelia.ethergems.common.init.ContainerInit;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.server.PutGemsInInventoryMessage;
import machinamelia.ethergems.common.tileentity.EtherFurnaceTileEntity;
import java.util.ArrayList;
import java.util.Objects;

public class EtherFurnaceGemConfirmContainer extends EtherFurnaceContainer {

    public final EtherFurnaceTileEntity tileEntity;
    public final ItemStackHandler items;
    private final IWorldPosCallable canInteractWithCallable;

    private EtherFurnaceTileEntity etherFurnaceTileEntity = new EtherFurnaceTileEntity();

    public EtherFurnaceGemConfirmContainer(final int windowId, final PlayerInventory playerInventory, final EtherFurnaceTileEntity tileEntity) {
        super(ContainerInit.ETHER_FURNACE_GEM_CONFIRM_CONTAINER.get(), windowId, playerInventory, tileEntity);
        this.tileEntity = tileEntity;
        this.items = new ItemStackHandler(28);
        this.size = 28;
        this.canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());
        this.initSlots();
        this.initInventory();
    }
    public EtherFurnaceTileEntity getTileEntity() {
        return tileEntity;
    }
    private static EtherFurnaceTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof EtherFurnaceTileEntity) {
            return (EtherFurnaceTileEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public EtherFurnaceGemConfirmContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    public void initSlots() {
        // Gem Inventory
        int startX = 16;
        int startY = 34;
        int slotSizePlus2 = 18;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new SlotItemHandler(items, (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }

        // Trash
        this.addSlot(new SlotItemHandler(items, 27, 160, 102));
    }

    protected void initInventory() {
        if (!this.tileEntity.getIsLocked()) {
            CompoundNBT compoundNBT = (CompoundNBT) this.playerInventory.player.getPersistentData().get("gem_confirm_inventory");
            ItemStack[] items = new ItemStack[27];
            if (compoundNBT != null) {
                ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                if (listNBT != null) {
                    readItemStacksFromTag(items, listNBT);
                    for (int i = 0; i < 27; i++) {
                        this.setItem(i, items[i]);
                    }
                }
            }
        }
    }

    @Override
    public ItemStack clicked(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        ItemStack stack = super.clicked(slotId, dragType, clickTypeIn, player);
        if (slotId == 27) {
            this.setItem(slotId, ItemStack.EMPTY);
            ItemStack[] itemStacks = new ItemStack[27];
            for (int i = 0; i < 27; i++) {
                itemStacks[i] = this.items.getStackInSlot(i);
            }
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(itemStacks, 27));
            compoundNBT.putByte("size", (byte) 27);
            player.getPersistentData().put("gem_confirm_inventory", compoundNBT);
            stack = ItemStack.EMPTY;
        }
        return stack;
    }

    public void closeGui() {
        ArrayList<ItemStack> itemList = new ArrayList<ItemStack>();
        for (int i = 0; i < 27; i++) {
            ItemStack stack = this.items.getStackInSlot(i);
            if (stack != null && stack.getItem() != Items.AIR) {
                itemList.add(stack);
            }
        }
        this.player.closeContainer();
        ItemStack[] empty = new ItemStack[27];
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("Items", writeItemStacksToTag(empty, 27));
        compoundNBT.putByte("size", (byte) 27);
        player.getPersistentData().put("gem_confirm_inventory", compoundNBT);

        PutGemsInInventoryMessage putGemsInInventoryMessage = new PutGemsInInventoryMessage(itemList.toArray(new ItemStack[0]));
        NetworkHandler.simpleChannel.sendToServer(putGemsInInventoryMessage);

    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }

}
