package machinamelia.ethergems.common.capabilities.cylinders;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import machinamelia.ethergems.common.capabilities.crystals.ICrystal;

public class CylinderInstance implements ICylinder, ICrystal {
    protected String colorCode;
    private int strength;
    private int level;
    private String element;
    private String tooltip;
    private String attribute;


    public CylinderInstance() {
        super();
    }

    public void init() {

    }

    public void creativeInit(String element) {
        this.element = element;
        if (this.element != null) {
            switch (this.element) {
                case "Fire":
                    this.attribute = "Strength Up";
                    this.strength = 99;
                    this.level = 5;
                    this.tooltip = this.attribute + ": " + this.strength + "%";
                    break;
                case "Water":
                    this.attribute = "HP Up";
                    this.strength = 99;
                    this.level = 5;
                    this.tooltip = this.attribute + ": " + this.strength + "%";
                    break;
                case "Electric":
                    this.attribute = "Double Attack";
                    this.strength = 99;
                    this.level = 5;
                    this.tooltip = this.attribute + ": " + this.strength + "%";
                    break;
                case "Ice":
                    this.attribute = "Bind";
                    this.strength = 99;
                    this.level = 5;
                    this.tooltip = this.attribute + ": " + this.strength + "%";
                    break;
                case "Wind":
                    this.attribute = "Quick Step";
                    this.strength = 99;
                    this.level = 5;
                    this.tooltip = this.attribute + ": " + this.strength + "%";
                    break;
                case "Earth":
                    this.attribute = "Muscle Up";
                    this.strength = 99;
                    this.level = 5;
                    this.tooltip = this.attribute + ": " + this.strength + "%";
                    break;
            }
        }
    }

    public String getAttributesCSV() {
        return this.getAttribute();
    }

    public int[] getStrengthArray() {
        int[] strengths = new int[1];
        strengths[0] = this.getStrength();
        return strengths;
    }

    public String[] getShuffledAttributes() {
        return null;
    }

    public void setAttributesCSV(String attributes) {
        this.attribute = attributes.split(",")[0];
    }
    public void setStrengthArray(int[] strengths) {
        this.strength = strengths[0];
    }

    public String getAttribute() {
        if (this.attribute == null) {
            return "";
        }
        return this.attribute;
    }
    public int getStrength() {
        return this.strength;
    }
    public String getElement() {
        if (this.element == null) {
            return "";
        }
        return this.element;
    }
    public int getLevel() { return this.level; }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setElement(String element) { this.element = element; }
    public String getAttributesTooltip() {
        return this.getAttributeTooltip();
    }
    public String getAttributeTooltip() {
        if (this.element != null) {
            switch (this.element) {
                case "Fire":
                    this.colorCode = "\u00A74";
                    break;
                case "Water":
                    this.colorCode = "\u00A79";
                    break;
                case "Electric":
                    this.colorCode = "\u00A7e";
                    break;
                case "Ice":
                    this.colorCode = "\u00A7b";
                    break;
                case "Wind":
                    this.colorCode = "\u00A7a";
                    break;
                case "Earth":
                    this.colorCode = "\u00A76";
                    break;
            }
        }
        String result = this.colorCode + this.tooltip + this.colorCode;
        return result;
    }
    public void setTooltipString(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getTooltipString() {
        if (this.tooltip == null) {
            return "";
        }
        return this.tooltip;
    }
    public void init(int level, String element, String attribute, int strength) {
        this.level = level;
        this.attribute = attribute;
        this.strength = strength;
        this.element = element;
    }
}
