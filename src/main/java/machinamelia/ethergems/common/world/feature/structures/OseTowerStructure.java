package machinamelia.ethergems.common.world.feature.structures;

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
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import machinamelia.ethergems.common.EtherGems;

public class OseTowerStructure extends Structure<NoFeatureConfig> {
    public OseTowerStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }
    @Override
    public boolean isFeatureChunk(ChunkGenerator generator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX,
                                  int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        ChunkPos pos = this.getStartPositionForPosition(chunkRandom, chunkX, chunkZ, 0, 0);
        if (chunkX == pos.x && chunkZ == pos.z) {
            return true;
        }

        return true;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return OseTowerStructure.Start::new;
    }

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public String getFeatureName() {
        return EtherGems.MOD_ID + ":ose_tower";
    }

    protected ChunkPos getStartPositionForPosition(SharedSeedRandom chunkRandom, int x, int z, int offsetX,
                                                   int offsetZ) {
        int maxDistance = 15;
        int minDistance = 7;

        int xTemp = x + maxDistance * offsetX;
        int ztemp = z + maxDistance * offsetZ;
        int xTemp2 = xTemp < 0 ? xTemp - maxDistance + 1 : xTemp;
        int zTemp2 = ztemp < 0 ? ztemp - maxDistance + 1 : ztemp;
        int validChunkX = xTemp2 / maxDistance;
        int validChunkZ = zTemp2 / maxDistance;

        validChunkX = validChunkX * maxDistance;
        validChunkZ = validChunkZ * maxDistance;
        validChunkX = validChunkX + chunkRandom.nextInt(maxDistance - minDistance);
        validChunkZ = validChunkZ + chunkRandom.nextInt(maxDistance - minDistance);
        return new ChunkPos(validChunkX, validChunkZ);
    }

    protected int getSeedModifier() {
        return 696840419;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {

        public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int reference,
                     long seed) {
            super(structure, chunkX, chunkZ, boundingBox, reference, seed);
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistriesManager, ChunkGenerator generator, TemplateManager templateManagerIn, int chunkX, int chunkZ,
                                   Biome biomeIn, NoFeatureConfig config) {
            Rotation rotation = Rotation.values()[this.random.nextInt(Rotation.values().length)];

            int x = (chunkX << 4) + 7;
            int z = (chunkX << 4) + 7;
            int y = generator.getBaseHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
            BlockPos pos = new BlockPos(x, y, z);

            OseTowerPieces.start(templateManagerIn, pos, rotation, this.pieces, this.random);

            this.calculateBoundingBox();
        }
    }
}
