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

import com.google.common.collect.ImmutableMap;
import machinamelia.ethergems.common.init.StructureInit;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.*;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import machinamelia.ethergems.common.EtherGems;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class OseTowerPieces  {

    private static final ResourceLocation CORE = new ResourceLocation(EtherGems.MOD_ID, "ose_tower_core");
    private static final ResourceLocation TOP = new ResourceLocation(EtherGems.MOD_ID, "ose_tower_top");
    private static final ResourceLocation FRONT = new ResourceLocation(EtherGems.MOD_ID, "ose_tower_front");
    private static final ResourceLocation MONADO_LOOT = new ResourceLocation(EtherGems.MOD_ID, "chests/monado_chest");
    private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(CORE,
            new BlockPos(0, -6, 0) , FRONT, new BlockPos(0, -6, 0), TOP, new BlockPos(0, 26, 0));

    public static void start(TemplateManager manager, BlockPos pos, Rotation rot, List<StructurePiece> pieces,
                             Random rand) {
        int x = pos.getX();
        int z = pos.getZ();
        BlockPos rotationOffset = new BlockPos(0, 0, 0).rotate(rot);
        BlockPos blockpos = rotationOffset.offset(x, pos.getY(), z);
        pieces.add(new OseTowerPieces.Piece(manager, CORE, blockpos, rot));
        BlockPos rotationOffset2 = new BlockPos(32, 0, 0).rotate(rot);
        BlockPos blockpos2 = rotationOffset2.offset(x, pos.getY(), z);
        pieces.add(new OseTowerPieces.Piece(manager, FRONT, blockpos2, rot));
        BlockPos rotationOffset3 = new BlockPos(0, 0, 0).rotate(rot);
        BlockPos blockpos3 = rotationOffset3.offset(x, pos.getY(), z);
        pieces.add(new OseTowerPieces.Piece(manager, TOP, blockpos3, rot));
    }

    public static class Piece extends TemplateStructurePiece {
        private ResourceLocation resourceLocation;
        private Rotation rotation;

        public Piece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos,
                     Rotation rotationIn) {
            super(StructureInit.OSE_TOWER_PIECE, 0);
            this.resourceLocation = resourceLocationIn;
            BlockPos blockpos = OseTowerPieces.OFFSET.get(resourceLocation);
            this.templatePosition = pos.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            this.rotation = rotationIn;
            this.setupPiece(templateManagerIn);
        }

        public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
            super(StructureInit.OSE_TOWER_PIECE, tagCompound);
            this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.setupPiece(templateManagerIn);
        }

        private void setupPiece(TemplateManager templateManager) {
            Template template = templateManager.get(this.resourceLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation)
                    .setMirror(Mirror.NONE);
            this.setup(template, this.templatePosition, placementsettings);
        }

        @Override
        protected void addAdditionalSaveData(CompoundNBT tagCompound) {
            super.addAdditionalSaveData(tagCompound);
            tagCompound.putString("Template", this.resourceLocation.toString());
            tagCompound.putString("Rot", this.rotation.name());
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand,
                                        MutableBoundingBox sbb) {
            if ("chest".equals(function)) {
                worldIn.removeBlock(pos, false);
                worldIn.setBlock(pos, Blocks.TRAPPED_CHEST.defaultBlockState().rotate(worldIn, pos, Rotation.CLOCKWISE_90), 2);
                TileEntity tileentity = worldIn.getBlockEntity(pos);
                if (tileentity instanceof TrappedChestTileEntity) {
                    ((TrappedChestTileEntity) tileentity).setLootTable(new ResourceLocation(EtherGems.MOD_ID, "chests/monado_chest"), rand.nextLong());
                }
            }
        }

        // create
        @Override
        public boolean postProcess(ISeedReader reader, StructureManager manager, ChunkGenerator chunkGenerator, Random random, MutableBoundingBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation)
                    .setMirror(Mirror.NONE);
            BlockPos blockpos = OseTowerPieces.OFFSET.get(this.resourceLocation);
            this.templatePosition.offset(Template.calculateRelativePosition(placementsettings,
                    new BlockPos(0 - blockpos.getX(), 0, 0 - blockpos.getZ())));

            return super.postProcess(reader, manager, chunkGenerator, random, boundingBox, chunkPos, blockPos);
        }
    }
}
