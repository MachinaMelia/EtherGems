package machinamelia.ethergems.common.world.gen;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import com.mojang.serialization.Codec;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.init.ConfiguredStructureInit;
import machinamelia.ethergems.common.init.StructureInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import machinamelia.ethergems.common.init.BlockInit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EtherDepositGen {
    @SubscribeEvent
    public static void depositEther(final BiomeLoadingEvent event) {
        // Impure Deposits
        generateDeposits(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.FIRE_DEPOSIT.get().defaultBlockState(), 9, 5, 70, 2);
        generateDeposits(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.WATER_DEPOSIT.get().defaultBlockState(), 9, 5, 70, 2);
        generateDeposits(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.ICE_DEPOSIT.get().defaultBlockState(), 9, 5, 70, 2);
        generateDeposits(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.WIND_DEPOSIT.get().defaultBlockState(), 9, 5, 70, 2);
        generateDeposits(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.EARTH_DEPOSIT.get().defaultBlockState(), 9, 5, 70, 2);
        generateDeposits(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.ELECTRIC_DEPOSIT.get().defaultBlockState(), 9, 5, 70, 2);

        // Fire Deposits
        generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.LAVA), BlockInit.PURE_FIRE_DEPOSIT.get().defaultBlockState(), 15, 5, 70, 9);

        // Water Deposits
        if (event.getName().toString().equals("minecraft:river") || event.getName().toString().equals("minecraft:beach") || event.getName().toString().equals("minecraft:desert_lakes")) {
            generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.SAND), BlockInit.PURE_WATER_DEPOSIT.get().defaultBlockState(), 15, 65, 70, 9);
        }

        // Ice Deposits
        generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.SNOW), BlockInit.PURE_ICE_DEPOSIT.get().defaultBlockState(), 15, 65, 70, 1);

        if (event.getName().toString().equals("minecraft:snowy_tundra") || event.getName().toString().equals("minecraft:ice_spikes") || event.getName().toString().equals("minecraft:snowy_taiga") || event.getName().toString().equals("minecraft:snowy_taiga_mountains") || event.getName().toString().equals("minecraft:frozen_river") || event.getName().toString().equals("minecraft:snowy_beach")) {
            generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.ICE), BlockInit.PURE_ICE_DEPOSIT.get().defaultBlockState(), 15, 65, 70, 9);
        }

        // Wind Deposits
        if (event.getName().toString().equals("minecraft:mountains") || event.getName().toString().equals("minecraft:gravelly_mountains") || event.getName().toString().equals("minecraft:snowy_mountains") || event.getName().toString().equals("minecraft:snowy_taiga_mountains") || event.getName().toString().equals("minecraft:wooded_mountains") || event.getName().toString().equals("minecraft:modified_gravelly_mountains") || event.getName().toString().equals("minecraft:shattered_savanna") || event.getName().toString().equals("minecraft:shattered_savanna_plateau") || event.getName().toString().equals("minecraft:badlands_plateau") || event.getName().toString().equals("minecraft:modified_badlands_plateau") || event.getName().toString().equals("minecraft:wooded_badlands_plateau") || event.getName().toString().equals("minecraft:modified_wooded_badlands_plateau")) {
            // Dirt
            if (event.getName().toString().equals("minecraft:snowy_mountains") || event.getName().toString().equals("minecraft:snowy_taiga_mountains") || event.getName().toString().equals("minecraft:taiga_mountains") || event.getName().toString().equals("minecraft:wooded_mountains")) {
                generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.DIRT), BlockInit.PURE_WIND_DEPOSIT.get().defaultBlockState(), 9, 72, 100, 7);
                generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.PODZOL), BlockInit.PURE_WIND_DEPOSIT.get().defaultBlockState(), 9, 72, 100, 7);
            }
            // Coarse Dirt
            if (event.getName().toString().equals("minecraft:shattered_savanna") || event.getName().toString().equals("minecraft:shattered_savanna_plateau") || event.getName().toString().equals("minecraft:badlands_plateau") || event.getName().toString().equals("minecraft:modified_badlands_plateau") || event.getName().toString().equals("minecraft:wooded_badlands_plateau") || event.getName().toString().equals("minecraft:modified_wooded_badlands_plateau")) {
                generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.COARSE_DIRT), BlockInit.PURE_WIND_DEPOSIT.get().defaultBlockState(), 9, 72, 150, 7);
                generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.GRASS_BLOCK), BlockInit.PURE_WIND_DEPOSIT.get().defaultBlockState(), 9, 62, 150, 7);
            }
            // Gravel
            if (event.getName().toString().equals("minecraft:gravelly_mountains") || event.getName().toString().equals("minecraft:modified_gravelly_mountains")) {
                generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.GRAVEL), BlockInit.PURE_WIND_DEPOSIT.get().defaultBlockState(), 9, 72, 150, 15);
            }
        }
        // Earth Deposits
        if (event.getName().toString().equals("minecraft:mountains") || event.getName().toString().equals("minecraft:gravelly_mountains") || event.getName().toString().equals("minecraft:snowy_mountains") || event.getName().toString().equals("minecraft:snowy_taiga_mountains") || event.getName().toString().equals("minecraft:wooded_mountains") || event.getName().toString().equals("minecraft:modified_gravelly_mountains") || event.getName().toString().equals("minecraft:shattered_savanna") || event.getName().toString().equals("minecraft:shattered_savanna_plateau") || event.getName().toString().equals("minecraft:badlands_plateau") || event.getName().toString().equals("minecraft:modified_badlands_plateau") || event.getName().toString().equals("minecraft:wooded_badlands_plateau") || event.getName().toString().equals("minecraft:modified_wooded_badlands_plateau") || event.getName().toString().equals("minecraft:dark_forest_hills") || event.getName().toString().equals("minecraft:swamp_hills") || event.getName().toString().equals("minecraft:bamboo_jungle_hills") || event.getName().toString().equals("minecraft:birch_forest_hills") || event.getName().toString().equals("minecraft:giant_spruce_taiga_hills") || event.getName().toString().equals("minecraft:giant_tree_taiga_hills") || event.getName().toString().equals("minecraft:snowy_taiga_hills") || event.getName().toString().equals("minecraft:taiga_hills") || event.getName().toString().equals("minecraft:tall_birch_hills") || event.getName().toString().equals("minecraft:wooded_hills") || event.getName().toString().equals("minecraft:badlands_plateau") || event.getName().toString().equals("minecraft:modified_badlands_plateau") || event.getName().toString().equals("minecraft:badlands") || event.getName().toString().equals("minecraft:eroded_badlands") || event.getName().toString().equals("minecraft:wooded_badlands_plateau") || event.getName().toString().equals("minecraft:modified_wooded_badlands_plateau")) {
            if (event.getName().toString().equals("minecraft:wooded_badlands_plateau") || event.getName().toString().equals("minecraft:modified_wooded_badlands_plateau") || event.getName().toString().equals("minecraft:badlands_plateau") || event.getName().toString().equals("minecraft:modified_badlands_plateau") || event.getName().toString().equals("minecraft:badlands") || event.getName().toString().equals("minecraft:eroded_badlands")) {
                generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.TERRACOTTA), BlockInit.PURE_EARTH_DEPOSIT.get().defaultBlockState(), 9, 62, 72, 7);
                generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.RED_SANDSTONE), BlockInit.PURE_EARTH_DEPOSIT.get().defaultBlockState(), 9, 62, 72, 7);
            } else if (event.getName().toString().equals("minecraft:gravelly_mountains") || event.getName().toString().equals("minecraft:modified_gravelly_mountains")) {
                generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.GRAVEL), BlockInit.PURE_EARTH_DEPOSIT.get().defaultBlockState(), 9, 62, 72, 15);
            } else {
                generateDeposits(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.PURE_EARTH_DEPOSIT.get().defaultBlockState(), 9, 62, 72, 15);
            }
        }
        // Electric Deposits
        if (event.getName().toString().equals("minecraft:jungle_hills") || event.getName().toString().equals("minecraft:jungle") || event.getName().toString().equals("minecraft:jungle_edge") || event.getName().toString().equals("minecraft:bamboo_jungle_hills") || event.getName().toString().equals("minecraft:bamboo_jungle") || event.getName().toString().equals("minecraft:modified_jungle") || event.getName().toString().equals("minecraft:modified_jungle_edge") || event.getName().toString().equals("minecraft:swamp_hills") || event.getName().toString().equals("minecraft:swamp") || event.getName().toString().equals("minecraft:sunflower_plains") || event.getName().toString().equals("minecraft:flower_forest") || event.getName().toString().equals("minecraft:giant_spruce_taiga") || event.getName().toString().equals("minecraft:giant_spruce_taiga_hills") || event.getName().toString().equals("minecraft:giant_tree_taiga") || event.getName().toString().equals("minecraft:giant_tree_taiga_hills")) {
            if (event.getName().toString().equals("minecraft:giant_spruce_taiga") || event.getName().toString().equals("minecraft:giant_spruce_taiga_hills") || event.getName().toString().equals("minecraft:giant_tree_taiga") || event.getName().toString().equals("minecraft:giant_tree_taiga_hills")) {
                generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.PODZOL), BlockInit.PURE_ELECTRIC_DEPOSIT.get().defaultBlockState(), 9, 62, 150, 7);
            } else {
                generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.GRASS_BLOCK), BlockInit.PURE_ELECTRIC_DEPOSIT.get().defaultBlockState(), 9, 62, 150, 7);
            }
            generateDeposits(event.getGeneration(), new BlockMatchRuleTest(Blocks.DIRT), BlockInit.PURE_ELECTRIC_DEPOSIT.get().defaultBlockState(), 9, 62, 150, 7);
        }

        if (event.getName().toString().equals("minecraft:snowy_tundra") || event.getName().toString().equals("minecraft:snowy_mountains")) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructureInit.CONFIGURED_OSE_TOWER);
        }
    }
    public static void generateDeposits(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state, int veinSize, int minHeight, int maxHeight, int amount) {
        settings.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(fillerType, state, veinSize)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeight, 0, maxHeight)).squared().count(amount)));
    }
    private static Method GETCODEC_METHOD;
    @SubscribeEvent
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();

            try {
                if (GETCODEC_METHOD == null) {
                    GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                }
                ResourceLocation chunkGeneratorLocation = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if (chunkGeneratorLocation != null && chunkGeneratorLocation.getNamespace().equals("terraforged")) {
                    return;
                }
            } catch (Exception e) {
                LOGGER.warn("Unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator");
            }
            if (serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator && serverWorld.dimension().equals(World.OVERWORLD)) {
                return;
            }
            serverWorld.getChunkSource().generator.getSettings().structureConfig().putIfAbsent(StructureInit.OSE_TOWER.get(), DimensionStructuresSettings.DEFAULTS.get(StructureInit.OSE_TOWER.get()));
        }
    }
    private static final Logger LOGGER = LogManager.getLogger();
}
