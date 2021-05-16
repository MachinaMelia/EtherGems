package machinamelia.ethergems.common.blocks;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.block.*;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraft.block.material.Material;
import machinamelia.ethergems.common.init.TileEntityInit;
import machinamelia.ethergems.common.tileentity.EtherFurnaceTileEntity;

import java.util.function.ToIntFunction;

public class EtherFurnace extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 31.0D, 15.0D);

    public EtherFurnace() {
        super(Block.Properties.of(Material.METAL, MaterialColor.STONE)
                .strength(3.5f, 6.0f)
                .sound(SoundType.STONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .lightLevel(new ToIntFunction<BlockState>() {
                    @Override
                    public int applyAsInt(BlockState value) {
                        return 7;
                    }
                })
        );
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return TileEntityInit.ETHER_FURNACE_TILE_ENTITY.get().create();
    }
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
        if (!worldIn.isClientSide) {
            TileEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof EtherFurnaceTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (EtherFurnaceTileEntity) tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
      // Do nothing you fool!

    }
}
