package machinamelia.ethergems.common.capabilities.weapons.material;

/*
 *   Copyright (C) 2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponInstance;
import net.minecraft.item.ItemStack;

public class SlottedNetheriteWeaponInstance extends SlottedWeaponInstance {
    public SlottedNetheriteWeaponInstance() { super(); }
    @Override
    public void init() {
        this.slots = 3;
        this.gems = new ItemStack[this.slots];
        this.levels = new int[this.slots];
        this.strengths = new double[this.slots];
        this.attributes = new String[this.slots];
        this.elements = new String[this.slots];
        this.hasInited = true;
    }
}
