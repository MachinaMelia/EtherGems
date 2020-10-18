package machinamelia.ethergems.common;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import machinamelia.ethergems.client.screens.UpdatedInventoryScreen;
import machinamelia.ethergems.common.init.*;
import machinamelia.ethergems.common.util.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import machinamelia.ethergems.common.capabilities.armor.ISlottedArmor;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorInstance;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorStorage;
import machinamelia.ethergems.common.capabilities.crystals.*;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderInstance;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderStorage;
import machinamelia.ethergems.common.capabilities.cylinders.ICylinder;
import machinamelia.ethergems.common.capabilities.gems.GemInstance;
import machinamelia.ethergems.common.capabilities.gems.GemStorage;
import machinamelia.ethergems.common.capabilities.gems.IGem;
import machinamelia.ethergems.common.capabilities.weapons.ISlottedWeapon;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponInstance;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponStorage;
import machinamelia.ethergems.common.capabilities.world.CrystalLevelInstance;
import machinamelia.ethergems.common.capabilities.world.CrystalLevelProvider;
import machinamelia.ethergems.common.capabilities.world.CrystalLevelStorage;
import machinamelia.ethergems.common.capabilities.world.ICrystalLevel;
import machinamelia.ethergems.common.events.*;
import machinamelia.ethergems.common.network.*;
import machinamelia.ethergems.common.world.gen.EtherDepositGen;
import java.util.*;

@Mod("ethergems")
@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Bus.MOD)
public class EtherGems {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "ethergems";
    public static final ResourceLocation CRYSTAL_LEVEL_CAPABILITY = new ResourceLocation(EtherGems.MOD_ID, "crystal_level");

    private static IEventBus MOD_EVENT_BUS = null;
    private static Map<PlayerEntity, Effect> potionEffects = new HashMap<>();

    public EtherGems() {
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        init();
        MinecraftForge.EVENT_BUS.register(this);
        MOD_EVENT_BUS.addListener(this::setup);
        registerCommonEvents();
        DistExecutor.runWhenOn(Dist.CLIENT, () -> EtherGems::registerClientOnlyEvents);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC);
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
        ConfigHandler.bakeConfig();
    }

    public static void init() {
        ItemInit.ITEMS.register(MOD_EVENT_BUS);
        BlockInit.BLOCKS.register(MOD_EVENT_BUS);
        TileEntityInit.TILE_ENTITY_TYPES.register(MOD_EVENT_BUS);
        ContainerInit.CONTAINER_TYPES.register(MOD_EVENT_BUS);
        ParticleInit.PARTICLES.register(MOD_EVENT_BUS);
        EffectInit.EFFECTS.register(MOD_EVENT_BUS);
        FeatureInit.FEATURES.register(MOD_EVENT_BUS);
    }

    public static void registerCommonEvents() {
        MinecraftForge.EVENT_BUS.register(AttackEvents.class);
        MinecraftForge.EVENT_BUS.register(BlazeEvents.class);
        MinecraftForge.EVENT_BUS.register(CapabilityEvents.class);
        MinecraftForge.EVENT_BUS.register(CommandEvents.class);
        MinecraftForge.EVENT_BUS.register(CraftItemEvents.class);
        MinecraftForge.EVENT_BUS.register(CreativeGemEvents.class);
        MinecraftForge.EVENT_BUS.register(EffectEvents.class);
        MinecraftForge.EVENT_BUS.register(ExperienceEvents.class);
        MinecraftForge.EVENT_BUS.register(FallEvents.class);
        MinecraftForge.EVENT_BUS.register(FollowRangeEvents.class);
        MinecraftForge.EVENT_BUS.register(FoodEvents.class);
        MinecraftForge.EVENT_BUS.register(HealEvents.class);
        MinecraftForge.EVENT_BUS.register(ItemEvents.class);
        MinecraftForge.EVENT_BUS.register(KnockbackEvents.class);
        MinecraftForge.EVENT_BUS.register(PlayerUpdateEvents.class);
        MinecraftForge.EVENT_BUS.register(PlayerWorldEvents.class);
        MinecraftForge.EVENT_BUS.register(UpdateGemEvents.class);
    }

    public static void registerClientOnlyEvents() {
        MinecraftForge.EVENT_BUS.register(TooltipEvents.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        EtherDepositGen.depositEther();
        for (Biome biome : ForgeRegistries.BIOMES)
        {
            if (biome.equals(Biomes.SNOWY_MOUNTAINS) || biome.equals(Biomes.SNOWY_TUNDRA)) {
                biome.addStructure(FeatureInit.OSE_TOWER.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
                biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, FeatureInit.OSE_TOWER.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                        .withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
            }
        }
        NetworkHandler.setupNetwork();
    }
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ICrystal.class, new CrystalStorage(), CrystalInstance::new);
        CapabilityManager.INSTANCE.register(IGem.class, new GemStorage(), GemInstance::new);
        CapabilityManager.INSTANCE.register(ICylinder.class, new CylinderStorage(), CylinderInstance::new);
        CapabilityManager.INSTANCE.register(ISlottedArmor.class, new SlottedArmorStorage(), SlottedArmorInstance::new);
        CapabilityManager.INSTANCE.register(ISlottedWeapon.class, new SlottedWeaponStorage(), SlottedWeaponInstance::new);
        CapabilityManager.INSTANCE.register(ICrystalLevel.class, new CrystalLevelStorage(), CrystalLevelInstance::new);
    }

    @SubscribeEvent
    public void onAttachWorldCapabilities(final AttachCapabilitiesEvent<World> event) {
        event.addCapability(CRYSTAL_LEVEL_CAPABILITY, new CrystalLevelProvider());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void replacePlayerInventoryEvent(GuiOpenEvent event) {
        Screen screen = event.getGui();
        if (screen instanceof InventoryScreen) {
            PlayerEntity player = Minecraft.getInstance().player;
            event.setGui(new UpdatedInventoryScreen(player));
        }
    }

    public static final ItemGroup TAB = new ItemGroup("ethergemsTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.FIRE_GEM.get());
        }
    };
}