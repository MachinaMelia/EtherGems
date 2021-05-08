package machinamelia.ethergems.common.capabilities.crystals;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import machinamelia.ethergems.common.events.PlayerWorldEvents;
import net.minecraft.util.text.ITextComponent;

import java.util.List;
import java.util.Random;

public class CrystalInstance implements ICrystal {

    protected String colorCode;
    private int level;
    private int numAttributes;
    private int[] strengths;
    private String attributesCSV;
    private String[] attributes;
    List<ITextComponent> tooltip;

    public CrystalInstance() {
        super();
        creativeInit();
    }

    public void creativeInit() {
        this.attributes = new String[0];
        this.strengths = new int[0];
        this.colorCode = "";
        this.level = 1;
    }

    public String getElement() {
        return "";
    }

    public void init() {
        Random randy = new Random();
        int roll = randy.nextInt(100);
        if (roll < 40) {
            this.numAttributes = 1;
        } else if (roll < 80) {
            this.numAttributes = 2;
        } else {
            this.numAttributes = 3;
        }
        this.attributes = new String[numAttributes];
        this.strengths = new int[numAttributes];
        String[] shuffledAttributes = this.getShuffledAttributes();
        for (int i = 0; i < numAttributes; i++) {
            int strength = 0;
            int strengthRoll = randy.nextInt(10);
            if (strengthRoll < 5) {
                strength -=strengthRoll;
            } else {
                strength = strengthRoll - 5;
            }
            switch (numAttributes) {
                case 1:
                    strength += 20;
                    break;
                case 2:
                    strength += 35;
                    break;
                case 3:
                    strength += 80;
                    break;
            }
            this.strengths[i] = strength;
            this.attributes[i] = shuffledAttributes[i];
        }
    }

    public void setStrengthArray(int[] strengths) {
        this.strengths = strengths;
    }

    public void setAttributesCSV(String attributes) {
        this.attributes = attributes.split(",");
        this.attributesCSV = attributes;
    }

    public int[] getStrengthArray() {
        return this.strengths;
    }

    public int getLevel() { return this.level; }

    public void setLevel(int level) { this.level = level; }

    public String[] getShuffledAttributes() {
        return null;
    }

    public String getAttributesTooltip() {
        String result = "";
        if (attributes.length > 0 && strengths.length > 0) {
            for (int i = 0; i < attributes.length; i++) {
                // Remove extra space from tooltip
                result += this.colorCode + attributes[i] + " " + strengths[i] + "% " + this.colorCode;
            }
        }
        return result;
    }

    public String getAttributesCSV() {
        String result = "";
        for (String attribute : attributes) {
            result += attribute + ",";
        }
        this.attributesCSV = result;
        return result;
    }


}
