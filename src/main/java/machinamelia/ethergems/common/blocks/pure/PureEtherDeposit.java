package machinamelia.ethergems.common.blocks.pure;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import machinamelia.ethergems.common.blocks.elemental.EtherDeposit;

import java.util.Random;
import java.util.function.ToIntFunction;

public class PureEtherDeposit extends EtherDeposit {
    public PureEtherDeposit() {
        super(Block.Properties.of(Material.STONE)
                .strength(4.0f, 0.3f)
                .sound(SoundType.STONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .lightLevel(new ToIntFunction<BlockState>() {
                    @Override
                    public int applyAsInt(BlockState value) {
                        return 10;
                    }
                }));
    }
    protected IParticleData getEtherParticle() {
        return null;
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.animateTick(stateIn, worldIn, pos, rand);
        Random randy = new Random();

//        double d0 = (double)pos.getX() + randy.nextDouble() * 1.3 - randy.nextDouble() * 1.3;
//        double d1 = (double)pos.getY() + randy.nextDouble() * 1.3 - randy.nextDouble() * 1.3;
//        double d2 = (double)pos.getZ() + randy.nextDouble() * 1.3 - randy.nextDouble() * 1.3;
//        worldIn.addParticle(getEtherParticle(), d0, d1, d2, d0, d1, d2);
      double d0 = (double)pos.getX() + randy.nextDouble() * 1.3 - randy.nextDouble() * 1.3;
        double d1 = (double)pos.getY() + randy.nextDouble() * 1.3 - randy.nextDouble() * 1.3;
        double d2 = (double)pos.getZ() + randy.nextDouble() * 1.3 - randy.nextDouble() * 1.3;
        worldIn.addParticle(getEtherParticle(), d0, d1, d2, d0, d1, d2);
//        d0 = (double)pos.getX() + randy.nextDouble() * 1.3 - randy.nextDouble() * 1.3;
//        d1 = (double)pos.getY() + randy.nextDouble() * 1.3 - randy.nextDouble() * 1.3;
//        d2 = (double)pos.getZ() + randy.nextDouble() * 1.3 - randy.nextDouble() * 1.3;
//        worldIn.addParticle(getEtherParticle(), d0, d1, d2, d0, d1, d2);
        d0 = (double)pos.getX() + randy.nextDouble() * 1.3 - randy.nextDouble() * 1.3;
        d1 = (double)pos.getY() + randy.nextDouble() * 1.3 - randy.nextDouble() * 1.3;
        d2 = (double)pos.getZ() + randy.nextDouble() * 1.3 - randy.nextDouble() * 1.3;
        worldIn.addParticle(getEtherParticle(), d0, d1, d2, d0, d1, d2);
    }
}
