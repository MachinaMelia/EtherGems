package machinamelia.ethergems.common.blocks.pure;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.world.storage.loot.LootContext;
import machinamelia.ethergems.common.init.ItemInit;
import machinamelia.ethergems.common.init.ParticleInit;

import java.util.List;

public class PureWindDeposit extends PureEtherDeposit {
    public PureWindDeposit() {
        super();
    }
    @Override
    public Item getCrystal() {
        return ItemInit.PURE_WIND_CRYSTAL.get();
    }
    @Override
    protected IParticleData getEtherParticle() {
        return ParticleInit.WIND_ETHER_PARTICLE.get();
    }
}
