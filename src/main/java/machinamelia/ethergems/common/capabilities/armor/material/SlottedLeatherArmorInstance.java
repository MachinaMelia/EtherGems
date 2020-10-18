package machinamelia.ethergems.common.capabilities.armor.material;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import machinamelia.ethergems.common.capabilities.armor.SlottedArmorInstance;

import java.util.Random;

public class SlottedLeatherArmorInstance extends SlottedArmorInstance {
    public SlottedLeatherArmorInstance() {
        super();
    }
    @Override
    public void init() {
        Random randy = new Random();
        int roll = randy.nextInt(100);
        if (roll < 50) {
            this.slots = 1;
        } else {
            this.slots = 0;
        }
        this.hasInited = true;
    }
}
