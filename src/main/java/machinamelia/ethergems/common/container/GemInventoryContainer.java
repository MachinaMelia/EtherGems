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
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import machinamelia.ethergems.common.capabilities.armor.ISlottedArmor;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorInstance;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorProvider;
import machinamelia.ethergems.common.capabilities.weapons.ISlottedWeapon;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponInstance;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponProvider;
import machinamelia.ethergems.common.init.ContainerInit;
import machinamelia.ethergems.common.items.weapon.SlottedAxe;
import machinamelia.ethergems.common.items.weapon.SlottedSword;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.server.OpenCrystalInventoryMessage;
import machinamelia.ethergems.common.network.client.SendArmorGemToClientMessage;
import machinamelia.ethergems.common.network.server.SendArmorGemToServerMessage;
import javax.annotation.Nullable;
import java.util.Iterator;

public class GemInventoryContainer extends Container {

    private int size;
    private int swordSlots = 0;
    private boolean[] hasArmor = new boolean[4];
    private ItemStackHandler items = new ItemStackHandler(44);
    private ServerPlayerEntity serverPlayer;
    private PlayerInventory playerInventory;

    public GemInventoryContainer(@Nullable ContainerType<?> type, final int windowId, PlayerInventory playerInventory) {
        super(type, windowId);
        this.size = 37;
        this.playerInventory = playerInventory;
        this.initSlots();
        this.initInventory();
    }
    public GemInventoryContainer(@Nullable ContainerType<?> type, final int windowId, ServerPlayerEntity serverPlayer) {
        this(type, windowId, serverPlayer.inventory);
        this.serverPlayer = serverPlayer;
    }
    public GemInventoryContainer(int windowId, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(ContainerInit.GEM_INVENTORY_CONTAINER.get(), windowId, playerInventory);
    }

    public void initSlots() {
        // Gem Inventory
        int startX = 16 + 115;
        int startY = 18 + 35;
        int slotSizePlus2 = 18;
        for (int row = 0; row < 4; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new SlotItemHandler(items, (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }

        // Trash
        this.addSlot(new SlotItemHandler(items, 36, 275, 129));


        slotSizePlus2 = 20;
        // Armor Slots
        Iterator<ItemStack> armorList = this.playerInventory.player.getArmorSlots().iterator();
        for (int i = 0; i < 4; i++) {
            ItemStack armor = armorList.next();
            LazyOptional<ISlottedArmor> armorCapability = armor.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
            ISlottedArmor armorInstance = armorCapability.orElse(new SlottedArmorInstance());
            this.addSlot(new SlotItemHandler(items, this.size, 38, 29 + ((3 - i) * slotSizePlus2)));
            this.size++;
            this.hasArmor[i] = true;
        }

        // Weapon Slots
        for (int i = 0; i < 3; i++) {
            this.addSlot(new SlotItemHandler(items, this.size + i, 38 + i * slotSizePlus2, 8));
            this.swordSlots++;
        }
        ItemStack weapon = this.playerInventory.player.getMainHandItem();
        if (weapon.getItem() instanceof SlottedSword || weapon.getItem() instanceof SlottedAxe) {
            LazyOptional<ISlottedWeapon> weaponCapability = weapon.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
            ISlottedWeapon weaponInstance = weaponCapability.orElse(new SlottedWeaponInstance());
            if (!weaponInstance.getHasInited()) {
                weaponInstance.init();
            }
            if (!weapon.getItem().equals(Items.AIR) && !weaponInstance.equals(Items.AIR) && weaponInstance.getSlots() > 0) {

            }
        }
    }

    protected void initInventory() {
        CompoundNBT compoundNBT = (CompoundNBT) this.playerInventory.player.getPersistentData().get("gem_inventory");
        ItemStack[] items = new ItemStack[44];
        if (compoundNBT != null) {
            ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
            readItemStacksFromTag(items, listNBT, 44);
            int spaces = 0;
            for (int i = 0; i < 36; i++) {
                this.setItem(i, items[i]);
            }
            for (int i = 0; i < 4; i++) {
                if (this.hasArmor[i]) {
                    this.setItem(i + 37 - spaces, items[i + 37]);
                } else {
                    spaces++;
                }
            }
            if (this.swordSlots > 0) {
                for (int i = 0; i < swordSlots; i++) {
                    this.setItem(i + 41 - spaces, items[i + 41]);
                }
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }

    public static ListNBT writeItemStacksToTag(ItemStack[] items, int maxQuantity, int size) {
        ListNBT tagList = new ListNBT();
        for (int i = 0; i < size; i++) {
            CompoundNBT tag = new CompoundNBT();
            tag.putShort("Slot", (short) i);
            if (items[i] != null) {
                items[i].save(tag);
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

    public static void readItemStacksFromTag(ItemStack[] items, ListNBT tagList, int size) {
        for (int i = 0; i < size; i++) {
            CompoundNBT tag = tagList.getCompound(i);
            int b = tag.getShort("Slot");
            items[b] = ItemStack.of(tag);
            INBT quant = tag.get("Quantity");
            if (quant instanceof NumberNBT) {
                items[b].setCount(((NumberNBT) quant).getAsInt());
            }
        }
    }

    @Override
    public ItemStack clicked(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        ItemStack stack = super.clicked(slotId, dragType, clickTypeIn, player);
        if (slotId >= 36) {
            if (slotId == 36) {
                this.setItem(slotId, ItemStack.EMPTY);
            }
            ItemStack[] itemStacks = new ItemStack[44];
            for (int i = 0; i < 44; i++) {
                itemStacks[i] = this.items.getStackInSlot(i);
            }
            itemStacks[36] = ItemStack.EMPTY;
            if (swordSlots > 0) {
                for (int i = 0; i < swordSlots; i++) {
                    itemStacks[i + 41] = this.items.getStackInSlot(i + this.size);
                }
            }
            if (slotId >= this.size && (clickTypeIn.equals(ClickType.PICKUP) | clickTypeIn.equals(ClickType.SWAP))) {
                ItemStack weapon = player.getMainHandItem();
                LazyOptional<ISlottedWeapon> weaponCapability = weapon.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                ISlottedWeapon weaponInstance = weaponCapability.orElse(new SlottedWeaponInstance());

                for (int i = 0; i < 4; i++) {
                    if (hasArmor[i]) {
                        if (player.level.isClientSide) {
                            SendArmorGemToServerMessage sendArmorGemToServerMessage = new SendArmorGemToServerMessage(itemStacks[i + 37], i, true);
                            NetworkHandler.simpleChannel.sendToServer(sendArmorGemToServerMessage);
                        } else {
                            SendArmorGemToClientMessage msg = new SendArmorGemToClientMessage(player.getStringUUID(), itemStacks[i + 37], i);
                            NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                        }
                } else {
                        if (player.level.isClientSide) {
                            SendArmorGemToServerMessage sendArmorGemToServerMessage = new SendArmorGemToServerMessage(ItemStack.EMPTY, i, true);
                            NetworkHandler.simpleChannel.sendToServer(sendArmorGemToServerMessage);
                        } else {
                            SendArmorGemToClientMessage msg = new SendArmorGemToClientMessage(player.getStringUUID(), ItemStack.EMPTY, i);
                            NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                        }
                    }
                }
            }
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(itemStacks, 44, 44));
            compoundNBT.putByte("size", (byte) 44);
            player.getPersistentData().put("gem_inventory", compoundNBT);
        }
        return stack;
    }

    public void openGui() {
        OpenCrystalInventoryMessage openCrystalInventoryMessage = new OpenCrystalInventoryMessage();
        NetworkHandler.simpleChannel.sendToServer(openCrystalInventoryMessage);
    }

    @Override
    public void removed(PlayerEntity playerIn) {
        ItemStack[] itemStacks = new ItemStack[44];
        for (int i = 0; i < 36; i++) {
            itemStacks[i] = this.items.getStackInSlot(i);
        }
        itemStacks[36] = ItemStack.EMPTY;
        int spaces = 0;
        for (int i = 0; i < 4; i++) {
            if (this.hasArmor[i]) {
                itemStacks[i + 37] = this.items.getStackInSlot(i + 37 - spaces);
            } else {
                spaces++;
            }
        }
        if (swordSlots > 0) {
            for (int i = 0; i < swordSlots; i++) {
                itemStacks[i + 41] = this.items.getStackInSlot(i + this.size);
            }
        }
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("Items", writeItemStacksToTag(itemStacks, 44, 44));
        compoundNBT.putByte("size", (byte) 44);
        playerIn.getPersistentData().put("gem_inventory", compoundNBT);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return true;
    }
}
