package machinamelia.ethergems.common.events;

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
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.capabilities.armor.ISlottedArmor;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorProvider;
import machinamelia.ethergems.common.capabilities.gems.GemInstance;
import machinamelia.ethergems.common.capabilities.gems.GemProvider;
import machinamelia.ethergems.common.capabilities.gems.IGem;
import machinamelia.ethergems.common.capabilities.weapons.ISlottedWeapon;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponProvider;
import machinamelia.ethergems.common.items.gems.Gem;
import machinamelia.ethergems.common.items.weapon.SlottedAxe;
import machinamelia.ethergems.common.items.weapon.SlottedSword;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.client.SendArmorGemToClientMessage;
import machinamelia.ethergems.common.network.server.SendArmorGemToServerMessage;
import machinamelia.ethergems.common.util.GemHandler;

import java.util.Iterator;

import static machinamelia.ethergems.common.container.EtherFurnaceContainer.readItemStacksFromTag;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UpdateGemEvents {
    @SubscribeEvent
    public static void playerEvent(PlayerEvent event) {
        PlayerEntity player = event.getPlayer();
        updateServerPlayerGems(player, false);
    }
    public static void updateClientPlayerGems(PlayerEntity player) {
        if (player != null && !player.world.isRemote) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            CompoundNBT compoundNBT = (CompoundNBT) player.getPersistentData().get("gem_inventory");
            ItemStack[] items = new ItemStack[44];
            if (compoundNBT != null) {
                ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                readItemStacksFromTag(items, listNBT);
            }
            // Gem Placed in Slot
            Iterator<ItemStack> armorList = player.getArmorInventoryList().iterator();
            ItemStack[] armorArray = new ItemStack[4];
            for (int i = 0; i < 4; i++) {
                armorArray[i] = armorList.next();
            }
            for (int i = 0; i < 4; i++) {
                ItemStack armor = armorArray[i];

                LazyOptional<ISlottedArmor> armorCapability = armor.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
                try {
                    ISlottedArmor armorInstance = armorCapability.orElseThrow(IllegalStateException::new);
                    if (!armorInstance.equals(Items.AIR) && /* && */ armorInstance.getSlots() > 0) {
                        if (items[i + 37] != null && items[i + 37].getItem() instanceof Gem) {
                            LazyOptional<IGem> gemCapability = items[i + 37].getCapability(GemProvider.GEM_CAPABILITY);
                            IGem gem = gemCapability.orElse(new GemInstance());
                            if (GemHandler.isArmorGem(gem.getAttribute())) {
                                armorInstance.setGem(items[i + 37]);
                                armorInstance.setIsWrongGem(false);
                                if (items[i + 37] != null && !items[i + 37].getItem().equals(Items.AIR)) {
                                    SendArmorGemToClientMessage msg = new SendArmorGemToClientMessage(player.getUniqueID().toString(), items[i + 37], i);
                                    NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                                } else {
                                    SendArmorGemToClientMessage msg = new SendArmorGemToClientMessage(player.getUniqueID().toString(), ItemStack.EMPTY, i);
                                    NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                                }
                            } else {
                                armorInstance.setGem(ItemStack.EMPTY);
                                armorInstance.setIsWrongGem(true);
                                SendArmorGemToServerMessage sendArmorGemToServerMessage = new SendArmorGemToServerMessage(ItemStack.EMPTY, i + 5, false);
                                NetworkHandler.simpleChannel.sendToServer(sendArmorGemToServerMessage);
                            }
                        }
                    }
                } catch (IllegalStateException e) {
                }
            }
            ItemStack weapon = player.getHeldItemMainhand();
            if (weapon.getItem() instanceof SlottedSword || weapon.getItem() instanceof SlottedAxe) {
                LazyOptional<ISlottedWeapon> weaponCapability = weapon.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                try {
                    ISlottedWeapon weaponInstance = weaponCapability.orElseThrow(IllegalStateException::new);
                    if (!weaponInstance.equals(Items.AIR) && weaponInstance.getSlots() > 0) {
                        for (int i = 0; i < weaponInstance.getSlots(); i++) {
                            if (items[i + 41] != null && items[i + 41].getItem() instanceof Gem) {
                                LazyOptional<IGem> gemCapability = items[i + 41].getCapability(GemProvider.GEM_CAPABILITY);
                                IGem gem = gemCapability.orElse(new GemInstance());
                                if (GemHandler.isWeaponGem(gem.getAttribute())) {
                                    if (weaponInstance.getGem(i) == null || weaponInstance.getGem(i).getItem().equals(Items.AIR)) {
                                        weaponInstance.setGem(i, items[i + 41]);
                                        if (items[i + 41] != null && !items[i + 41].getItem().equals(Items.AIR)) {
                                            SendArmorGemToClientMessage msg = new SendArmorGemToClientMessage(player.getUniqueID().toString(), items[i + 41], i + 5);
                                            NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                                        }
                                        else {
                                            SendArmorGemToClientMessage msg = new SendArmorGemToClientMessage(player.getUniqueID().toString(), ItemStack.EMPTY, i + 5);
                                            NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                                        }
                                    }
                                } else {
                                    weaponInstance.setGem(i, ItemStack.EMPTY);
                                    weaponInstance.setIsWrongGem(true);
                                    SendArmorGemToServerMessage sendArmorGemToServerMessage = new SendArmorGemToServerMessage(ItemStack.EMPTY, i + 5, false);
                                    NetworkHandler.simpleChannel.sendToServer(sendArmorGemToServerMessage);
                                }
                            }
                        }
                    }
                } catch (IllegalStateException e) {
                }
            }
        }
    }

    public static void updateServerPlayerGems(PlayerEntity player, boolean isGemInventory) {
        if (player != null && player.world.isRemote) {
            CompoundNBT compoundNBT = (CompoundNBT) player.getPersistentData().get("gem_inventory");
            ItemStack[] items = new ItemStack[44];
            if (compoundNBT != null) {
                ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                readItemStacksFromTag(items, listNBT);
            }
            // Gem Placed in Slot
            Iterator<ItemStack> armorList = player.getArmorInventoryList().iterator();
            ItemStack[] armorArray = new ItemStack[4];
            for (int i = 0; i < 4; i++) {
                armorArray[i] = armorList.next();
            }
            for (int i = 0; i < 4; i++) {
                ItemStack armor = armorArray[i];

                LazyOptional<ISlottedArmor> armorCapability = armor.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
                try {
                    ISlottedArmor armorInstance = armorCapability.orElseThrow(IllegalStateException::new);
                    if (!armorInstance.equals(Items.AIR) && /* && */ armorInstance.getSlots() > 0) {
                        if (items[i + 37] != null && items[i + 37].getItem() instanceof Gem) {
                            LazyOptional<IGem> gemCapability = items[i + 37].getCapability(GemProvider.GEM_CAPABILITY);
                            IGem gem = gemCapability.orElse(new GemInstance());
                            if (GemHandler.isArmorGem(gem.getAttribute())) {
                                armorInstance.setGem(items[i + 37]);
                                armorInstance.setIsWrongGem(false);
                                if (items[i + 37] != null && !items[i + 37].getItem().equals(Items.AIR)) {
                                    SendArmorGemToServerMessage sendArmorGemToServerMessage = new SendArmorGemToServerMessage(items[i + 37], i, true);
                                    NetworkHandler.simpleChannel.sendToServer(sendArmorGemToServerMessage);
                                } else {
                                    SendArmorGemToServerMessage sendArmorGemToServerMessage = new SendArmorGemToServerMessage(ItemStack.EMPTY, i, true);
                                    NetworkHandler.simpleChannel.sendToServer(sendArmorGemToServerMessage);
                                }
                            } else {
                                armorInstance.setGem(ItemStack.EMPTY);
                                armorInstance.setIsWrongGem(true);
                                SendArmorGemToServerMessage sendArmorGemToServerMessage = new SendArmorGemToServerMessage(ItemStack.EMPTY, i, false);
                                NetworkHandler.simpleChannel.sendToServer(sendArmorGemToServerMessage);
                            }
                        } else if (isGemInventory) {
                            armorInstance.setGem(ItemStack.EMPTY);
                            SendArmorGemToServerMessage sendArmorGemToServerMessage = new SendArmorGemToServerMessage(ItemStack.EMPTY, i, false);
                            NetworkHandler.simpleChannel.sendToServer(sendArmorGemToServerMessage);
                        }
                    }
                } catch (IllegalStateException e) {
                }
            }
            ItemStack weapon = player.getHeldItemMainhand();
            if (weapon.getItem() instanceof SlottedSword || weapon.getItem() instanceof SlottedAxe) {
                LazyOptional<ISlottedWeapon> weaponCapability = weapon.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                try {
                    ISlottedWeapon weaponInstance = weaponCapability.orElseThrow(IllegalStateException::new);
                    if (!weaponInstance.equals(Items.AIR) && weaponInstance.getSlots() > 0) {
                        for (int i = 0; i < weaponInstance.getSlots(); i++) {
                            if (items[i + 41] != null && items[i + 41] != null && items[i + 41].getItem() instanceof Gem) {
                                LazyOptional<IGem> gemCapability = items[i + 41].getCapability(GemProvider.GEM_CAPABILITY);
                                IGem gem = gemCapability.orElseThrow(IllegalStateException::new);
                                if (GemHandler.isWeaponGem(gem.getAttribute())) {
                                    if (weaponInstance.getGem(i) == null || weaponInstance.getGem(i).getItem().equals(Items.AIR)) {
                                        weaponInstance.setGem(i, items[i + 41]);
                                        weaponInstance.setIsWrongGem(false);
                                    }
                                    if (!(items[i + 41] != null && !items[i + 41].getItem().equals(Items.AIR))) {
                                        SendArmorGemToServerMessage sendArmorGemToServerMessage = new SendArmorGemToServerMessage(ItemStack.EMPTY, i + 5, true);
                                        NetworkHandler.simpleChannel.sendToServer(sendArmorGemToServerMessage);
                                    }
                                } else {
                                    weaponInstance.setGem(i, ItemStack.EMPTY);
                                    weaponInstance.setIsWrongGem(true);
                                    SendArmorGemToServerMessage sendArmorGemToServerMessage = new SendArmorGemToServerMessage(ItemStack.EMPTY, i + 5, false);
                                    NetworkHandler.simpleChannel.sendToServer(sendArmorGemToServerMessage);
                                }
                            } else if (isGemInventory) {
                                weaponInstance.setGem(i, ItemStack.EMPTY);
                                SendArmorGemToServerMessage sendArmorGemToServerMessage = new SendArmorGemToServerMessage(ItemStack.EMPTY, i, false);
                                NetworkHandler.simpleChannel.sendToServer(sendArmorGemToServerMessage);
                            }
                        }
                    }
                } catch (IllegalStateException e) {
                }
            }
        }
    }

}
