package machinamelia.ethergems.common.blocks.elemental;

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
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import machinamelia.ethergems.common.init.ItemInit;

public class EarthDeposit extends EtherDeposit {
    public EarthDeposit() {
        super(Block.Properties.of(Material.STONE)
                .strength(4.0f, 0.3f)
                .sound(SoundType.STONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE));
    }
    @Override
    public Item getCrystal() {
        return ItemInit.EARTH_CRYSTAL.get();
    }
}
