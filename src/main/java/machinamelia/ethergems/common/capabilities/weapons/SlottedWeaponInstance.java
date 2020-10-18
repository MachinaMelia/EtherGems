package machinamelia.ethergems.common.capabilities.weapons;

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
import net.minecraftforge.common.util.LazyOptional;
import machinamelia.ethergems.common.capabilities.gems.GemProvider;
import machinamelia.ethergems.common.capabilities.gems.IGem;
import machinamelia.ethergems.common.items.gems.Gem;
import machinamelia.ethergems.common.util.GemHandler;

import java.util.Random;

public class SlottedWeaponInstance implements ISlottedWeapon {

    protected boolean hasInited = false;
    protected boolean isWrongGem;
    protected int slots = 0;
    protected int[] levels;
    protected double[] strengths;
    protected String[] attributes;
    protected String[] elements;
    protected ItemStack[] gems;

    public SlottedWeaponInstance() {
        super();
    }

    public void init() {
        Random randy = new Random();
        int roll = randy.nextInt(100);
        if (roll < 50) {
            this.slots = 1;
        } else {
            this.slots = 1;
        }
        this.gems = new ItemStack[this.slots];
        this.levels = new int[this.slots];
        this.strengths = new double[this.slots];
        this.attributes = new String[this.slots];
        this.elements = new String[this.slots];
        this.hasInited = true;
    }

    public void setAttribute(int index, String attribute) {
        if (this.attributes == null) {
            this.attributes = new String[this.slots];
        }
        this.attributes[index] = attribute;
    }
    public String getAttribute(int index) {
        return this.attributes[index];
    }
    public void setLevel(int index, int level) {
        if (this.levels == null) {
            this.levels = new int[this.slots];
        }
        this.levels[index] = level;
    }
    public int getLevel(int index) {
        return this.levels[index];
    }
    public void setStrength(int index, double strength) {
        if (this.strengths == null) {
            this.strengths = new double[this.slots];
        }
        this.strengths[index] = strength;
    }
    public double getStrength(int index) {
        return this.strengths[index];
    }
    public void setElement(int index, String element) {
        if (this.elements == null) {
            this.elements = new String[this.slots];
        }
        this.elements[index] = element;
    }
    public String getElement(int index) {
        return this.elements[index];
    }

    public boolean getHasInited() {
        return this.hasInited;
    }

    public void setHasInited(boolean hasInited) {
        this.hasInited = hasInited;
    }

    public ItemStack getGem(int index) {
        if (slots == 0 || (this.attributes[index] == null || this.elements[index] == null)) {
            return null;
        }
        if (this.gems[index] == null && this.attributes[index] != null && this.elements[index] != null) {
            this.gems[index] = GemHandler.createGem(this.levels[index], this.elements[index], this.attributes[index], this.strengths[index]);
        }
        return this.gems[index];
    }
    public void setGem(int index, ItemStack gem) {
        if (this.gems == null) {
            this.gems = new ItemStack[this.slots];
        }
        this.gems[index] = gem;
    }

    public int getSlots() {
        return this.slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public void setIsWrongGem(boolean value) {
        this.isWrongGem = value;
    }

    public String getTooltip() {
        String tooltip = "";
        if (this.isWrongGem) {
            tooltip = "Armor Gem Incompatible with Weapon Slot";
            return tooltip;
        }
        int emptyGems = 0;
        for (int i = 0; i < gems.length; i++) {
            if (this.gems[i] != null && this.gems[i].getItem() instanceof Gem) {
                LazyOptional<IGem> gemCapability = this.gems[i].getCapability(GemProvider.GEM_CAPABILITY);
                try {
                    IGem gemInstance = gemCapability.orElseThrow(IllegalStateException::new);
                    tooltip += gemInstance.getAttributeTooltip() + "\n";
                } catch (IllegalStateException e) {
                }
            } else {
                emptyGems++;
            }
        }
        if (emptyGems == gems.length) {
            return "Slots: " + gems.length;
        }

        return tooltip;
    }
}
