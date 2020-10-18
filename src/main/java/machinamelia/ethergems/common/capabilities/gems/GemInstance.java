package machinamelia.ethergems.common.capabilities.gems;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

public class GemInstance implements IGem {

    protected String colorCode;
    private int level;
    private double strength;
    private String element;
    private String tooltip;
    private String attribute;


    public GemInstance() {
        super();
    }

    public void creativeInit(String element, String attribute, double strength) {
        this.element = element;
        if (this.element != null) {
            this.attribute = attribute;
            this.strength = strength;
            this.level = 6;
            this.tooltip = this.attribute + ": " + this.strength;
        }
    }

    public String getAttribute() {
        if (this.attribute == null) {
            return "";
        }
        return this.attribute;
    }
    public double getStrength() {
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
    public void setStrength(double strength) {
        this.strength = strength;
    }
    public void setElement(String element) {
        this.element = element;
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

    public void init(int level, String element, String attribute, double strength) {
        this.level = level;
        this.attribute = attribute;
        this.strength = strength;
        this.element = element;
    }
}
