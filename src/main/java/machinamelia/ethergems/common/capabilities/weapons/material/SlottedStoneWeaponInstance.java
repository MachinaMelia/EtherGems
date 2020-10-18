package machinamelia.ethergems.common.capabilities.weapons.material;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.item.ItemStack;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponInstance;

import java.util.Random;

public class SlottedStoneWeaponInstance extends SlottedWeaponInstance {
    public SlottedStoneWeaponInstance() {
        super();
    }
    @Override
    public void init() {
        Random randy = new Random();
        int roll = randy.nextInt(100);
        if (roll < 25) {
            this.slots = 1;
        } else if (roll < 50) {
            this.slots = 2;
        } else {
            this.slots = 0;
        }
        this.gems = new ItemStack[this.slots];
        this.levels = new int[this.slots];
        this.strengths = new double[this.slots];
        this.attributes = new String[this.slots];
        this.elements = new String[this.slots];
        this.hasInited = true;
    }
}
