package machinamelia.ethergems.common.capabilities.crystals.elemental;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import machinamelia.ethergems.common.capabilities.crystals.CrystalInstance;

import java.util.Random;

public class IceCrystalInstance extends CrystalInstance {

    private static final String[] ATTRIBUTES1 = { "Blaze Defence", "Chill Attack", "Ether Protect" };
    private static final String[] ATTRIBUTES2 = { "Blaze Defence", "Slow" };
    private static final String[] ATTRIBUTES3 = { "Bind", "Blaze Defence", "Chill Plus", "Slow" };
    private static final String[] ATTRIBUTES4 = { "Bind", "Blaze Defence", "Chill Attack", "Chill Plus", "Slow" };
    private static final String[] ATTRIBUTES5 = { "Bind", "Blaze Defence", "Chill Attack", "Chill Plus", "Slow" };

    public IceCrystalInstance() {
        super();
        this.colorCode = "\u00A7b";
    }

    @Override
    public String getAttributeValues(int strength, String[] previousAttributes) {
        boolean shouldRollAgain;
        int index = 0;
        String[] attributes = null;
        do  {
            shouldRollAgain = false;
            Random randy = new Random();
            switch(this.getLevel()) {
                case 1:
                    attributes = ATTRIBUTES1;
                    break;
                case 2:
                    attributes = ATTRIBUTES2;
                    break;
                case 3:
                    attributes = ATTRIBUTES3;
                    break;
                case 4:
                    attributes = ATTRIBUTES4;
                    break;
                case 5:
                    attributes = ATTRIBUTES5;
                    break;
                default:
                    attributes = ATTRIBUTES1;
            }
            index = randy.nextInt(attributes.length);
            index = randy.nextInt(attributes.length);
            for (String attribute : previousAttributes) {
                if (attribute != null && attribute.contains(attributes[index])) {
                    shouldRollAgain = true;
                }
            }
        } while (shouldRollAgain);

        return attributes[index];
    }
    @Override
    public String getElement() {
        return "Ice";
    }
}
