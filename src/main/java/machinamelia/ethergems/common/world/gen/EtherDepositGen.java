package machinamelia.ethergems.common.world.gen;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import machinamelia.ethergems.common.init.BlockInit;

public class EtherDepositGen {
    public static void depositEther() {
        String lavaTarget = "lava_target";
        String snowTarget = "snow_target";
        String iceTarget = "ice_target";
        String sandTarget = "sand_target";
        String dirtTarget = "dirt_target";
        String coarseDirtTarget = "coarse_dirt_target";
        String gravelTarget = "gravel_target";
        String redSandstoneTarget = "red_sandstone_target";
        String terracotaTarget = "terracota_target";
        String grassTarget = "grass_target";
        String podzolTarget = "podzol_target";
        OreFeatureConfig.FillerBlockType.create(lavaTarget.toUpperCase(), lavaTarget, new BlockMatcher(Blocks.LAVA));
        OreFeatureConfig.FillerBlockType.create(snowTarget.toUpperCase(), snowTarget, new BlockMatcher(Blocks.SNOW));
        OreFeatureConfig.FillerBlockType.create(iceTarget.toUpperCase(), iceTarget, new BlockMatcher(Blocks.PACKED_ICE));
        OreFeatureConfig.FillerBlockType.create(sandTarget.toUpperCase(), sandTarget, new BlockMatcher(Blocks.SAND));
        OreFeatureConfig.FillerBlockType.create(iceTarget.toUpperCase(), iceTarget, new BlockMatcher(Blocks.PACKED_ICE));
        OreFeatureConfig.FillerBlockType.create(dirtTarget.toUpperCase(), dirtTarget, new BlockMatcher(Blocks.DIRT));
        OreFeatureConfig.FillerBlockType.create(coarseDirtTarget.toUpperCase(), coarseDirtTarget, new BlockMatcher(Blocks.COARSE_DIRT));
        OreFeatureConfig.FillerBlockType.create(gravelTarget.toUpperCase(), gravelTarget, new BlockMatcher(Blocks.GRAVEL));
        OreFeatureConfig.FillerBlockType.create(redSandstoneTarget.toUpperCase(), redSandstoneTarget, new BlockMatcher(Blocks.RED_SANDSTONE));
        OreFeatureConfig.FillerBlockType.create(terracotaTarget.toUpperCase(), terracotaTarget, new BlockMatcher(Blocks.TERRACOTTA));
        OreFeatureConfig.FillerBlockType.create(grassTarget.toUpperCase(), grassTarget, new BlockMatcher(Blocks.GRASS_BLOCK));
        OreFeatureConfig.FillerBlockType.create(podzolTarget.toUpperCase(), podzolTarget, new BlockMatcher(Blocks.PODZOL));

        for (Biome biome : ForgeRegistries.BIOMES) {
            // Impure Deposits
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.FIRE_DEPOSIT.get().getDefaultState(), 9))
                .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 5, 0, 70)))
            );
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                    .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.WATER_DEPOSIT.get().getDefaultState(), 9))
                    .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 5, 0, 70)))
            );
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                    .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.ICE_DEPOSIT.get().getDefaultState(), 9))
                    .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 5, 0, 70)))
            );
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                    .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.WIND_DEPOSIT.get().getDefaultState(), 9))
                    .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 5, 0, 70)))
            );
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                    .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.EARTH_DEPOSIT.get().getDefaultState(), 9))
                    .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 5, 0, 70)))
            );
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                    .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.ELECTRIC_DEPOSIT.get().getDefaultState(), 9))
                    .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 5, 0, 70)))
            );
            // Fire Deposits
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                    .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("lava_target"), BlockInit.PURE_FIRE_DEPOSIT.get().getDefaultState(), 9))
                    .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 5, 0, 70)))
            );
            // Water Deposits
            if (biome.equals(Biomes.RIVER) || biome.equals(Biomes.BEACH) || biome.equals(Biomes.DESERT_LAKES)) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                        .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("sand_target"), BlockInit.PURE_WATER_DEPOSIT.get().getDefaultState(), 9))
                        .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 65, 0, 70)))
                );
            }
            // Ice Deposits
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                    .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("snow_target"), BlockInit.PURE_ICE_DEPOSIT.get().getDefaultState(), 1))
                    .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 65, 0, 70)))
            );
            if (biome.equals(Biomes.SNOWY_TUNDRA) || biome.equals(Biomes.ICE_SPIKES) || biome.equals(Biomes.SNOWY_TAIGA) || biome.equals(Biomes.SNOWY_TAIGA_MOUNTAINS) || biome.equals(Biomes.FROZEN_RIVER) || biome.equals(Biomes.SNOWY_BEACH)) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                        .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("ice_target"), BlockInit.PURE_ICE_DEPOSIT.get().getDefaultState(), 9))
                        .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 65, 0, 70)))
                );
            }
            // Wind Deposits
            if (biome.equals(Biomes.MOUNTAINS) || biome.equals(Biomes.GRAVELLY_MOUNTAINS) || biome.equals(Biomes.SNOWY_MOUNTAINS) || biome.equals(Biomes.SNOWY_TAIGA_MOUNTAINS) || biome.equals(Biomes.TAIGA_MOUNTAINS) || biome.equals(Biomes.WOODED_MOUNTAINS) || biome.equals(Biomes.MODIFIED_GRAVELLY_MOUNTAINS) || biome.equals(Biomes.TAIGA_MOUNTAINS) || biome.equals(Biomes.SHATTERED_SAVANNA) || biome.equals(Biomes.SHATTERED_SAVANNA_PLATEAU) || biome.equals(Biomes.BADLANDS_PLATEAU) || biome.equals(Biomes.MODIFIED_BADLANDS_PLATEAU) || biome.equals(Biomes.WOODED_BADLANDS_PLATEAU) || biome.equals(Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU)) {
                // Dirt
                if (biome.equals(Biomes.SNOWY_MOUNTAINS) || biome.equals(Biomes.SNOWY_TAIGA_MOUNTAINS) || biome.equals(Biomes.TAIGA_MOUNTAINS) || biome.equals(Biomes.WOODED_MOUNTAINS)) {
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                            .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("dirt_target"), BlockInit.PURE_WIND_DEPOSIT.get().getDefaultState(), 9))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 72, 40, 100)))
                    );
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                            .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("podzol_target"), BlockInit.PURE_ELECTRIC_DEPOSIT.get().getDefaultState(), 9))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 62, 40, 150)))
                    );
                }
                // Coarse Dirt
                if (biome.equals(Biomes.SHATTERED_SAVANNA) || biome.equals(Biomes.SHATTERED_SAVANNA_PLATEAU) || biome.equals(Biomes.WOODED_BADLANDS_PLATEAU) || biome.equals(Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU)) {
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                            .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("coarse_dirt_target"), BlockInit.PURE_WIND_DEPOSIT.get().getDefaultState(), 9))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 72, 70, 150)))
                    );
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                            .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("grass_target"), BlockInit.PURE_ELECTRIC_DEPOSIT.get().getDefaultState(), 9))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 62, 40, 150)))
                    );
                }
                // Gravel
                if (biome.equals(Biomes.GRAVELLY_MOUNTAINS) || biome.equals(Biomes.MODIFIED_GRAVELLY_MOUNTAINS) ) {
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                            .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("gravel_target"), BlockInit.PURE_WIND_DEPOSIT.get().getDefaultState(), 9))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 72, 70, 150)))
                    );
                }
            }
            // Earth Deposits
            if (biome.equals(Biomes.MOUNTAINS) || biome.equals(Biomes.GRAVELLY_MOUNTAINS) || biome.equals(Biomes.SNOWY_MOUNTAINS) || biome.equals(Biomes.SNOWY_TAIGA_MOUNTAINS) || biome.equals(Biomes.TAIGA_MOUNTAINS) || biome.equals(Biomes.WOODED_MOUNTAINS) || biome.equals(Biomes.MODIFIED_GRAVELLY_MOUNTAINS) || biome.equals(Biomes.TAIGA_MOUNTAINS)   || biome.equals(Biomes.WOODED_BADLANDS_PLATEAU) || biome.equals(Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU) || biome.equals(Biomes.DARK_FOREST_HILLS) || biome.equals(Biomes.SWAMP_HILLS) || biome.equals(Biomes.BAMBOO_JUNGLE_HILLS) || biome.equals(Biomes.BIRCH_FOREST_HILLS) || biome.equals(Biomes.GIANT_SPRUCE_TAIGA_HILLS) || biome.equals(Biomes.GIANT_TREE_TAIGA_HILLS) || biome.equals(Biomes.JUNGLE_HILLS) || biome.equals(Biomes.SNOWY_TAIGA_HILLS) || biome.equals(Biomes.TAIGA_HILLS) || biome.equals(Biomes.TALL_BIRCH_HILLS) || biome.equals(Biomes.WOODED_HILLS) || biome.equals(Biomes.BADLANDS) || biome.equals(Biomes.BADLANDS_PLATEAU) || biome.equals(Biomes.MODIFIED_BADLANDS_PLATEAU) || biome.equals(Biomes.ERODED_BADLANDS)) {
                if (biome.equals(Biomes.WOODED_BADLANDS_PLATEAU) || biome.equals(Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU) || biome.equals(Biomes.BADLANDS) || biome.equals(Biomes.BADLANDS_PLATEAU) || biome.equals(Biomes.MODIFIED_BADLANDS_PLATEAU) || biome.equals(Biomes.ERODED_BADLANDS)) {
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                            .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("terracota_target"), BlockInit.PURE_EARTH_DEPOSIT.get().getDefaultState(), 9))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 62, 0, 72)))
                    );
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                            .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("red_sandstone_target"), BlockInit.PURE_EARTH_DEPOSIT.get().getDefaultState(), 9))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 62, 0, 72)))
                    );
                } else if (biome.equals(Biomes.GRAVELLY_MOUNTAINS) || biome.equals(Biomes.MODIFIED_GRAVELLY_MOUNTAINS) ) {
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                            .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("gravel_target"), BlockInit.PURE_EARTH_DEPOSIT.get().getDefaultState(), 9))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 62, 0, 72)))
                    );
                } else {
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                            .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockInit.PURE_EARTH_DEPOSIT.get().getDefaultState(), 9))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 62, 0, 72)))
                    );
                }
            }
            // Electric Deposits
            if (biome.equals(Biomes.JUNGLE_HILLS) || biome.equals(Biomes.JUNGLE) || biome.equals(Biomes.JUNGLE_EDGE) || biome.equals(Biomes.BAMBOO_JUNGLE_HILLS) || biome.equals(Biomes.BAMBOO_JUNGLE) || biome.equals(Biomes.MODIFIED_JUNGLE) || biome.equals(Biomes.MODIFIED_JUNGLE_EDGE) || biome.equals(Biomes.SWAMP_HILLS) || biome.equals(Biomes.SWAMP) || biome.equals(Biomes.SUNFLOWER_PLAINS) || biome.equals(Biomes.FLOWER_FOREST) || biome.equals(Biomes.GIANT_TREE_TAIGA_HILLS) || biome.equals(Biomes.GIANT_TREE_TAIGA) || biome.equals(Biomes.GIANT_SPRUCE_TAIGA_HILLS) || biome.equals(Biomes.GIANT_SPRUCE_TAIGA)) {
                if (biome.equals(Biomes.GIANT_TREE_TAIGA_HILLS) || biome.equals(Biomes.GIANT_TREE_TAIGA) || biome.equals(Biomes.GIANT_SPRUCE_TAIGA_HILLS) || biome.equals(Biomes.GIANT_SPRUCE_TAIGA)) {
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                            .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("podzol_target"), BlockInit.PURE_ELECTRIC_DEPOSIT.get().getDefaultState(), 9))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 62, 40, 150)))
                    );
                } else {
                    biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                            .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("grass_target"), BlockInit.PURE_ELECTRIC_DEPOSIT.get().getDefaultState(), 9))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 62, 40, 150)))
                    );
                }
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
                        .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.byName("dirt_target"), BlockInit.PURE_ELECTRIC_DEPOSIT.get().getDefaultState(), 9))
                        .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 62, 40, 150)))
                );
            }

        }
    }
}
