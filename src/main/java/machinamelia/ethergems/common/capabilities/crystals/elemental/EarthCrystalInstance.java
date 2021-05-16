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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EarthCrystalInstance extends CrystalInstance {

    private static final String[] ATTRIBUTES1 = { "Bleed Defence", "Earth Cloak", "Muscle Up", "Physical Protect" };
    private static final String[] ATTRIBUTES2 = { "Bleed Defence", "Debuff Plus", "Earth Cloak", "Muscle Up", "Physical Protect" };
    private static final String[] ATTRIBUTES3 = { "Bleed Defence", "Debuff Plus", "Earth Cloak", "Muscle Up", "Physical Protect", "Poison Attack", "Poison Plus" };
    private static final String[] ATTRIBUTES4 = { "Bleed Defence", "Critical Up", "Debuff Plus", "Earth Cloak", "Muscle Up", "Physical Protect", "Poison Plus" };
    private static final String[] ATTRIBUTES5 = { "Bleed Defence", "Critical Up", "Debuff Plus", "Earth Cloak", "Muscle Up", "Physical Protect", "Poison Attack", "Poison Plus" };

    public EarthCrystalInstance() {
        super();
        this.colorCode = "\u00A76";
    }

    @Override
    public String[] getShuffledAttributes() {
        String[] attributes = null;
        switch (this.getLevel()) {
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
        List<String> attributeList = Arrays.asList(attributes);
        Collections.shuffle(attributeList);

        return attributeList.toArray(new String[0]);
    }

    @Override
    public String getElement() {
        return "Earth";
    }
}
