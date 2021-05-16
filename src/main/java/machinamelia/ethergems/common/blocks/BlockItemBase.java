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

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import machinamelia.ethergems.common.EtherGems;

public class BlockItemBase extends BlockItem {
    public BlockItemBase(Block block) {
        super(block, new Item.Properties().tab(EtherGems.TAB));
    }
}
