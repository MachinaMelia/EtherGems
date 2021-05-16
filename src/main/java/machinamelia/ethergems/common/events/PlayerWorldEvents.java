package machinamelia.ethergems.common.events;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import machinamelia.ethergems.common.container.ContainerProvider;
import machinamelia.ethergems.common.container.GemInventoryContainer;
import machinamelia.ethergems.common.init.ContainerInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
import machinamelia.ethergems.common.util.GemHandler;

import java.util.List;
import java.util.Random;

import static machinamelia.ethergems.common.container.EtherFurnaceContainer.writeItemStacksToTag;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerWorldEvents {

    private static int crystalLevel = 0;
    public static void setCrystalLevel(int level) {
        crystalLevel = level;
    }
    public static int getCrystalLevel() {
        return crystalLevel;
    }

    public static final ResourceLocation CRYSTAL_LEVEL_CAPABILITY = new ResourceLocation(EtherGems.MOD_ID, "crystal_level");

    @SubscribeEvent
    public static void getAdvancements(AdvancementEvent event) {
        Advancement advancement = event.getAdvancement();
        ServerPlayerEntity advancedPlayer = (ServerPlayerEntity) event.getPlayer();
        List<? extends PlayerEntity> players = advancedPlayer.getCommandSenderWorld().players();
        if (crystalLevel < 1 && (advancement.getId().toString().equals("minecraft:story/lava_bucket") || advancement.getId().toString().equals("minecraft:story/mine_diamond"))) {
            System.out.println("Level II Crystals are now available");
            setCrystalLevel(1);
            for (PlayerEntity player : players) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                serverPlayer.sendMessage(new StringTextComponent("Level II Crystals are now available"), serverPlayer.getUUID());
                serverPlayer.getPersistentData().putInt("crystal_level", crystalLevel);
                SendAffinityToClientPlayer msg = new SendAffinityToClientPlayer(serverPlayer.getStringUUID(), crystalLevel);
                NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                World world = serverPlayer.getCommandSenderWorld();
                LazyOptional<ICrystalLevel> crystalLevelCapability = world.getCapability(CrystalLevelProvider.CRYSTAL_LEVEL_CAPABILITY);
                ICrystalLevel crystalLevelInstance = crystalLevelCapability.orElse(new CrystalLevelInstance());
                crystalLevelInstance.setCrystalLevel(crystalLevel);
            }
        } else if (crystalLevel < 2 && advancement.getId().toString().equals("minecraft:nether/obtain_blaze_rod")) {
            System.out.println("Level III Crystals are now available");
            setCrystalLevel(2);
            for (PlayerEntity player : players) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                serverPlayer.sendMessage(new StringTextComponent("Level III Crystals are now available"), serverPlayer.getUUID());
                serverPlayer.getPersistentData().putInt("crystal_level", crystalLevel);
                SendAffinityToClientPlayer msg = new SendAffinityToClientPlayer(serverPlayer.getStringUUID(), crystalLevel);
                NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                World world = serverPlayer.getCommandSenderWorld();
                LazyOptional<ICrystalLevel> crystalLevelCapability = world.getCapability(CrystalLevelProvider.CRYSTAL_LEVEL_CAPABILITY);
                ICrystalLevel crystalLevelInstance = crystalLevelCapability.orElse(new CrystalLevelInstance());
                crystalLevelInstance.setCrystalLevel(crystalLevel);
            }
        } else if (crystalLevel < 3 && advancement.getId().toString().equals("minecraft:story/follow_ender_eye")) {
            System.out.println("Level IV Crystals are now available");
            setCrystalLevel(3);
            for (PlayerEntity player : players) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                serverPlayer.sendMessage(new StringTextComponent("Level IV Crystals are now available"), serverPlayer.getUUID());
                serverPlayer.getPersistentData().putInt("crystal_level", crystalLevel);
                SendAffinityToClientPlayer msg = new SendAffinityToClientPlayer(serverPlayer.getStringUUID(), crystalLevel);
                NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                World world = serverPlayer.getCommandSenderWorld();
                LazyOptional<ICrystalLevel> crystalLevelCapability = world.getCapability(CrystalLevelProvider.CRYSTAL_LEVEL_CAPABILITY);
                ICrystalLevel crystalLevelInstance = crystalLevelCapability.orElse(new CrystalLevelInstance());
                crystalLevelInstance.setCrystalLevel(crystalLevel);
            }
        } else if (crystalLevel < 4 && advancement.getId().toString().equals("minecraft:end/kill_dragon")) {
            System.out.println("Level V Crystals are now available");
            setCrystalLevel(4);
            for (PlayerEntity player : players) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
                serverPlayer.sendMessage(new StringTextComponent("Level V Crystals are now available"), serverPlayer.getUUID());
                serverPlayer.getPersistentData().putInt("crystal_level", crystalLevel);
                SendAffinityToClientPlayer msg = new SendAffinityToClientPlayer(serverPlayer.getStringUUID(), crystalLevel);
                NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                World world = serverPlayer.getCommandSenderWorld();
                LazyOptional<ICrystalLevel> crystalLevelCapability = world.getCapability(CrystalLevelProvider.CRYSTAL_LEVEL_CAPABILITY);
                ICrystalLevel crystalLevelInstance = crystalLevelCapability.orElse(new CrystalLevelInstance());
                crystalLevelInstance.setCrystalLevel(crystalLevel);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        // Allow equipment to get gems on respawn by opening and closing the gem inventory
        if (!event.getPlayer().level.isClientSide) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.getPlayer();
            NetworkHooks.openGui(serverPlayer , new ContainerProvider(new StringTextComponent(""), (i, inv, p) -> new GemInventoryContainer(ContainerInit.GEM_INVENTORY_CONTAINER.get(), i, serverPlayer)));
            serverPlayer.doCloseContainer();
            serverPlayer.closeContainer();
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.getPlayer().level.isClientSide) {
            ServerPlayerEntity orignalPlayer = (ServerPlayerEntity) event.getOriginal();
            ServerPlayerEntity newPlayer = (ServerPlayerEntity) event.getPlayer();

            World world = newPlayer.getCommandSenderWorld();

            LazyOptional<ICrystalLevel> crystalLevelCapability = world.getCapability(CrystalLevelProvider.CRYSTAL_LEVEL_CAPABILITY);
            ICrystalLevel crystalLevelInstance = crystalLevelCapability.orElse(new CrystalLevelInstance());
            crystalLevelInstance.setCrystalLevel(crystalLevel);

            newPlayer.getPersistentData().put("crystal_inventory", orignalPlayer.getPersistentData().get("crystal_inventory"));
            CompoundNBT compoundNBT = (CompoundNBT) orignalPlayer.getPersistentData().get("gem_inventory");
            newPlayer.getPersistentData().put("gem_inventory", compoundNBT);

            ItemStack[] empty = new ItemStack[27];
            compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(empty, 27));
            compoundNBT.putByte("size", (byte) 27);
            newPlayer.getPersistentData().put("gem_confirm_inventory", compoundNBT);
            newPlayer.getPersistentData().put("armor_slots", orignalPlayer.getPersistentData().get("armor_slots"));

            // Allow equipment to get gems on respawn by opening and closing the gem inventory
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.getPlayer();
            NetworkHooks.openGui(serverPlayer , new ContainerProvider(new StringTextComponent(""), (i, inv, p) -> new GemInventoryContainer(ContainerInit.GEM_INVENTORY_CONTAINER.get(), i, serverPlayer)));
            serverPlayer.doCloseContainer();
            serverPlayer.closeContainer();

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
            newPlayer.getPersistentData().putString("remove_debuff", "");
        } else {
            ClientPlayerEntity orignalPlayer = (ClientPlayerEntity) event.getOriginal();
            ClientPlayerEntity newPlayer = (ClientPlayerEntity) event.getPlayer();
            newPlayer.getPersistentData().put("crystal_inventory", orignalPlayer.getPersistentData().get("crystal_inventory"));
            CompoundNBT compoundNBT = (CompoundNBT) orignalPlayer.getPersistentData().get("gem_inventory");
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
            newPlayer.getPersistentData().putString("remove_debuff", "");
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getPlayer().level.isClientSide) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            SendAffinityToClientPlayer msg = new SendAffinityToClientPlayer(player.getStringUUID(), crystalLevel);
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
            NonNullList<ItemStack> armorInventory = event.getPlayer().inventory.armor;
            compoundNBT = new CompoundNBT();
            compoundNBT.putBoolean("Head", event.getPlayer().inventory.armor.get(0).getItem() != null);
            compoundNBT.putBoolean("Chest", event.getPlayer().inventory.armor.get(1).getItem() != null);
            compoundNBT.putBoolean("Legs", event.getPlayer().inventory.armor.get(2).getItem() != null);
            compoundNBT.putBoolean("Feet", event.getPlayer().inventory.armor.get(3).getItem() != null);
            event.getPlayer().getPersistentData().put("armor_slots", compoundNBT);
        }
        // Allow equipment to get gems on respawn by opening and closing the gem inventory
        if (!event.getPlayer().level.isClientSide) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) event.getPlayer();
            NetworkHooks.openGui(serverPlayer , new ContainerProvider(new StringTextComponent(""), (i, inv, p) -> new GemInventoryContainer(ContainerInit.GEM_INVENTORY_CONTAINER.get(), i, serverPlayer)));
            serverPlayer.doCloseContainer();
            serverPlayer.closeContainer();
        }
        if (!event.getPlayer().level.isClientSide) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            World world = player.getCommandSenderWorld();
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
            player.getPersistentData().putString("remove_debuff", "");
            SendAffinityToClientPlayer msg = new SendAffinityToClientPlayer(player.getStringUUID().toString(), crystalLevel);
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
    @SubscribeEvent
    public static void onAttachWorldCapabilities(final AttachCapabilitiesEvent<World> event) {
        event.addCapability(CRYSTAL_LEVEL_CAPABILITY, new CrystalLevelProvider());
    }
}
