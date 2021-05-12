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

import machinamelia.ethergems.common.capabilities.armor.ISlottedArmor;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorInstance;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorProvider;
import machinamelia.ethergems.common.capabilities.gems.GemInstance;
import machinamelia.ethergems.common.capabilities.gems.GemProvider;
import machinamelia.ethergems.common.capabilities.gems.IGem;
import machinamelia.ethergems.common.capabilities.weapons.ISlottedWeapon;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponInstance;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponProvider;
import machinamelia.ethergems.common.container.ContainerProvider;
import machinamelia.ethergems.common.container.GemInventoryContainer;
import machinamelia.ethergems.common.init.ContainerInit;
import machinamelia.ethergems.common.items.gems.Gem;
import machinamelia.ethergems.common.items.weapon.SlottedAxe;
import machinamelia.ethergems.common.items.weapon.SlottedSword;
import machinamelia.ethergems.common.network.server.OpenGemInventoryMessage;
import machinamelia.ethergems.common.network.server.SendArmorGemToServerMessage;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.capabilities.world.CrystalLevelInstance;
import machinamelia.ethergems.common.capabilities.world.CrystalLevelProvider;
import machinamelia.ethergems.common.capabilities.world.ICrystalLevel;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.client.SendAffinityToClientPlayer;
import machinamelia.ethergems.common.network.server.PutGemsInInventoryMessage;
import machinamelia.ethergems.common.util.GemHandler;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static machinamelia.ethergems.common.container.EtherFurnaceContainer.readItemStacksFromTag;
import static machinamelia.ethergems.common.container.EtherFurnaceContainer.writeItemStacksToTag;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlayerWorldEvents {

    private static int crystalLevel = 0;
    public static void setCrystalLevel(int level) {
        crystalLevel = level;
    }
    public static int getCrystalLevel() {
        return crystalLevel;
    }

    @SubscribeEvent
    public static void getAdvancements(AdvancementEvent event) {
        Advancement advancement = event.getAdvancement();
        ServerPlayerEntity advancedPlayer = (ServerPlayerEntity) event.getPlayer();
        List<ServerPlayerEntity> players = advancedPlayer.getServerWorld().getPlayers();
        if (crystalLevel < 1 && (advancement.getId().toString().equals("minecraft:story/lava_bucket") || advancement.getId().toString().equals("minecraft:story/mine_diamond"))) {
            System.out.println("Level II Crystals are now available");
            setCrystalLevel(1);
            for (ServerPlayerEntity player : players) {
                player.sendMessage(new StringTextComponent("Level II Crystals are now available"));
                player.getPersistentData().putInt("crystal_level", crystalLevel);
                SendAffinityToClientPlayer msg = new SendAffinityToClientPlayer(player.getUniqueID().toString(), crystalLevel);
                NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> player), msg);
                ServerWorld world = player.getServerWorld();
                LazyOptional<ICrystalLevel> crystalLevelCapability = world.getCapability(CrystalLevelProvider.CRYSTAL_LEVEL_CAPABILITY);
                ICrystalLevel crystalLevelInstance = crystalLevelCapability.orElse(new CrystalLevelInstance());
                crystalLevelInstance.setCrystalLevel(crystalLevel);
            }
        } else if (crystalLevel < 2 && advancement.getId().toString().equals("minecraft:nether/obtain_blaze_rod")) {
            System.out.println("Level III Crystals are now available");
            setCrystalLevel(2);
            for (ServerPlayerEntity player : players) {
                player.sendMessage(new StringTextComponent("Level III Crystals are now available"));
                player.getPersistentData().putInt("crystal_level", crystalLevel);
                SendAffinityToClientPlayer msg = new SendAffinityToClientPlayer(player.getUniqueID().toString(), crystalLevel);
                NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> player), msg);
                ServerWorld world = player.getServerWorld();
                LazyOptional<ICrystalLevel> crystalLevelCapability = world.getCapability(CrystalLevelProvider.CRYSTAL_LEVEL_CAPABILITY);
                ICrystalLevel crystalLevelInstance = crystalLevelCapability.orElse(new CrystalLevelInstance());
                crystalLevelInstance.setCrystalLevel(crystalLevel);
            }
        } else if (crystalLevel < 3 && advancement.getId().toString().equals("minecraft:story/follow_ender_eye")) {
            System.out.println("Level IV Crystals are now available");
            setCrystalLevel(3);
            for (ServerPlayerEntity player : players) {
                player.sendMessage(new StringTextComponent("Level IV Crystals are now available"));
                player.getPersistentData().putInt("crystal_level", crystalLevel);
                SendAffinityToClientPlayer msg = new SendAffinityToClientPlayer(player.getUniqueID().toString(), crystalLevel);
                NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> player), msg);
                ServerWorld world = player.getServerWorld();
                LazyOptional<ICrystalLevel> crystalLevelCapability = world.getCapability(CrystalLevelProvider.CRYSTAL_LEVEL_CAPABILITY);
                ICrystalLevel crystalLevelInstance = crystalLevelCapability.orElse(new CrystalLevelInstance());
                crystalLevelInstance.setCrystalLevel(crystalLevel);
            }
        } else if (crystalLevel < 4 && advancement.getId().toString().equals("minecraft:end/kill_dragon")) {
            System.out.println("Level V Crystals are now available");
            setCrystalLevel(4);
            for (ServerPlayerEntity player : players) {
                player.sendMessage(new StringTextComponent("Level V Crystals are now available"));
                player.getPersistentData().putInt("crystal_level", crystalLevel);
                SendAffinityToClientPlayer msg = new SendAffinityToClientPlayer(player.getUniqueID().toString(), crystalLevel);
                NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> player), msg);
                ServerWorld world = player.getServerWorld();
                LazyOptional<ICrystalLevel> crystalLevelCapability = world.getCapability(CrystalLevelProvider.CRYSTAL_LEVEL_CAPABILITY);
                ICrystalLevel crystalLevelInstance = crystalLevelCapability.orElse(new CrystalLevelInstance());
                crystalLevelInstance.setCrystalLevel(crystalLevel);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.getPlayer().world.isRemote) {
            ServerPlayerEntity orignalPlayer = (ServerPlayerEntity) event.getOriginal();
            ServerPlayerEntity newPlayer = (ServerPlayerEntity) event.getPlayer();

            ServerWorld world = newPlayer.getServerWorld();

            LazyOptional<ICrystalLevel> crystalLevelCapability = world.getCapability(CrystalLevelProvider.CRYSTAL_LEVEL_CAPABILITY);
            ICrystalLevel crystalLevelInstance = crystalLevelCapability.orElse(new CrystalLevelInstance());
            crystalLevelInstance.setCrystalLevel(crystalLevel);

            newPlayer.getPersistentData().put("crystal_inventory", orignalPlayer.getPersistentData().get("crystal_inventory"));
            CompoundNBT compoundNBT = (CompoundNBT) orignalPlayer.getPersistentData().get("gem_inventory");
            ItemStack[] items = new ItemStack[44];
            if (compoundNBT != null) {
                ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                readItemStacksFromTag(items, listNBT);
            }
            ItemStack[] equipmentGems = new ItemStack[7];
            for (int i = 0; i < 7; i++) {
                equipmentGems[i] = items[37 + i];
                items[37 + i] = ItemStack.EMPTY;
            }
            for (ItemStack gem : equipmentGems) {
                boolean itemPlaced = false;
                for (int i = 0; i < 44; i++) {
                    if (!itemPlaced && items[i] != null && items[i].getItem().equals(Items.AIR)) {
                        items[i] = gem;
                        itemPlaced = true;
                    } else if (!itemPlaced && items[i] == null) {
                        items[i] = gem;
                        itemPlaced = true;
                    }
                }
            }
            compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(items, 44));
            compoundNBT.putByte("size", (byte) 44);
            newPlayer.getPersistentData().put("gem_inventory", compoundNBT);

            ItemStack[] empty = new ItemStack[27];
            compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(empty, 27));
            compoundNBT.putByte("size", (byte) 27);
            newPlayer.getPersistentData().put("gem_confirm_inventory", compoundNBT);
            newPlayer.getPersistentData().put("armor_slots", orignalPlayer.getPersistentData().get("armor_slots"));
            newPlayer.getPersistentData().putInt("crystal_level", crystalLevel);
            newPlayer.getPersistentData().putBoolean("secondAttack", false);
            newPlayer.getPersistentData().putBoolean("should_die", false);
            newPlayer.getPersistentData().putBoolean("is_burning", false);
            newPlayer.getPersistentData().putBoolean("shouldCheckPoisonTime", false);
            newPlayer.getPersistentData().putBoolean("shouldCheckChillTime", false);
            newPlayer.getPersistentData().putBoolean("shouldCheckBleedTime", false);
            newPlayer.getPersistentData().putBoolean("shouldCheckPhysicalProtectTime", false);
            newPlayer.getPersistentData().putInt("poison_time", 0);
            newPlayer.getPersistentData().putInt("chill_time", 0);
            newPlayer.getPersistentData().putInt("bleed_time", 0);
            newPlayer.getPersistentData().putInt("physical_protect_time", 360);
        } else {
            ClientPlayerEntity orignalPlayer = (ClientPlayerEntity) event.getOriginal();
            ClientPlayerEntity newPlayer = (ClientPlayerEntity) event.getPlayer();
            newPlayer.getPersistentData().put("crystal_inventory", orignalPlayer.getPersistentData().get("crystal_inventory"));
            CompoundNBT compoundNBT = (CompoundNBT) orignalPlayer.getPersistentData().get("gem_inventory");
            ItemStack[] items = new ItemStack[44];
            if (compoundNBT != null) {
                ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                readItemStacksFromTag(items, listNBT);
            }
            ItemStack[] equipmentGems = new ItemStack[7];
            for (int i = 0; i < 7; i++) {
                equipmentGems[i] = items[37 + i];
                items[37 + i] = ItemStack.EMPTY;
            }
            PutGemsInInventoryMessage putGemsInInventoryMessage = new PutGemsInInventoryMessage(equipmentGems);
            NetworkHandler.simpleChannel.sendToServer(putGemsInInventoryMessage);
            compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(items, 44));
            compoundNBT.putByte("size", (byte) 44);
            newPlayer.getPersistentData().put("gem_inventory", compoundNBT);
            newPlayer.getPersistentData().put("armor_slots", orignalPlayer.getPersistentData().get("armor_slots"));
            newPlayer.getPersistentData().putInt("crystal_level", crystalLevel);
            newPlayer.getPersistentData().putBoolean("secondAttack", false);
            newPlayer.getPersistentData().putBoolean("should_die", false);
            newPlayer.getPersistentData().putBoolean("is_burning", false);
            newPlayer.getPersistentData().putBoolean("shouldCheckPoisonTime", false);
            newPlayer.getPersistentData().putBoolean("shouldCheckChillTime", false);
            newPlayer.getPersistentData().putBoolean("shouldCheckBleedTime", false);
            newPlayer.getPersistentData().putBoolean("shouldCheckPhysicalProtectTime", false);
            newPlayer.getPersistentData().putInt("poison_time", 0);
            newPlayer.getPersistentData().putInt("chill_time", 0);
            newPlayer.getPersistentData().putInt("bleed_time", 0);
            newPlayer.getPersistentData().putInt("physical_protect_time", 360);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getPlayer().world.isRemote) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            SendAffinityToClientPlayer msg = new SendAffinityToClientPlayer(player.getUniqueID().toString(), crystalLevel);
            NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> player), msg);
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        CompoundNBT compoundNBT = (CompoundNBT) event.getPlayer().getPersistentData().get("crystal_inventory");
        if (compoundNBT == null) {
            ItemStack[] items = new ItemStack[42];
            compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(items, 42));
            compoundNBT.putByte("size", (byte) 42);
            event.getPlayer().getPersistentData().put("crystal_inventory", compoundNBT);
        }
        compoundNBT = (CompoundNBT) event.getPlayer().getPersistentData().get("gem_inventory");
        if (compoundNBT == null) {
            ItemStack[] items = new ItemStack[44];
            compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(items, 44));
            compoundNBT.putByte("size", (byte) 44);
            event.getPlayer().getPersistentData().put("gem_inventory", compoundNBT);
        }
        compoundNBT = (CompoundNBT) event.getPlayer().getPersistentData().get("armor_slots");
        if (compoundNBT == null) {
            NonNullList<ItemStack> armorInventory = event.getPlayer().inventory.armorInventory;
            compoundNBT = new CompoundNBT();
            compoundNBT.putBoolean("Head", event.getPlayer().inventory.armorInventory.get(0).getItem() != null);
            compoundNBT.putBoolean("Chest", event.getPlayer().inventory.armorInventory.get(1).getItem() != null);
            compoundNBT.putBoolean("Legs", event.getPlayer().inventory.armorInventory.get(2).getItem() != null);
            compoundNBT.putBoolean("Feet", event.getPlayer().inventory.armorInventory.get(3).getItem() != null);
            event.getPlayer().getPersistentData().put("armor_slots", compoundNBT);
        }
        // Allow equipment to get gems on respawn by opening and closing the gem inventory
        if (!event.getPlayer().world.isRemote) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.getPlayer();
            NetworkHooks.openGui(serverPlayer , new ContainerProvider(new StringTextComponent("Gem Inventory"), (i, inv, p) -> new GemInventoryContainer(ContainerInit.GEM_INVENTORY_CONTAINER.get(), i, serverPlayer)));
            serverPlayer.closeContainer();
            serverPlayer.closeScreen();
        }
        if (!event.getPlayer().world.isRemote) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            ServerWorld world = player.getServerWorld();
            LazyOptional<ICrystalLevel> crystalLevelCapability = world.getCapability(CrystalLevelProvider.CRYSTAL_LEVEL_CAPABILITY);
            ICrystalLevel crystalLevelInstance = crystalLevelCapability.orElse(new CrystalLevelInstance());
            setCrystalLevel(crystalLevelInstance.getCrystalLevel());
            player.getPersistentData().putBoolean("secondAttack", false);

            player.getPersistentData().putInt("crystal_level", crystalLevel);
            player.getPersistentData().putBoolean("should_die", false);
            player.getPersistentData().putBoolean("is_burning", false);
            player.getPersistentData().putBoolean("shouldCheckPoisonTime", false);
            player.getPersistentData().putBoolean("shouldCheckChillTime", false);
            player.getPersistentData().putBoolean("shouldCheckBleedTime", false);
            player.getPersistentData().putBoolean("shouldCheckPhysicalProtectTime", false);
            player.getPersistentData().putInt("poison_time", 0);
            player.getPersistentData().putInt("chill_time", 0);
            player.getPersistentData().putInt("bleed_time", 0);
            player.getPersistentData().putInt("physical_protect_time", 360);
            SendAffinityToClientPlayer msg = new SendAffinityToClientPlayer(player.getUniqueID().toString(), crystalLevel);
            NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> player), msg);
        }
    }

    @SubscribeEvent
    public static void playerDeathEvent(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            double fullStrength = GemHandler.getPlayerGemStrength(player, "Unbeatable");
            if (fullStrength > 50.0) {
                fullStrength = 50.0;
            }
            Random randy = new Random();
            int roll = randy.nextInt(100);
            if (roll < (int) fullStrength) {
                event.setCanceled(true);
                if (!player.getPersistentData().getBoolean("should_die")) {
                    player.setHealth((float) 1.0);
                    player.getPersistentData().putBoolean("should_die", true);
                }
            }
        }
    }
}
