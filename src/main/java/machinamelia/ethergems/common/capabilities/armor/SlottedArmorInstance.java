package machinamelia.ethergems.common.capabilities.armor;

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

public class SlottedArmorInstance implements ISlottedArmor {

    protected boolean hasInited = false;
    protected boolean isWrongGem;
    protected int slots = 0;
    private int level;
    private double strength;
    private String attribute;
    private String element;
    private ItemStack gem;

    public SlottedArmorInstance() {
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
        this.hasInited = true;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    public String getAttribute() {
        return this.attribute;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return this.level;
    }
    public void setStrength(double strength) {
        this.strength = strength;
    }
    public double getStrength() {
        return this.strength;
    }
    public void setElement(String element) {
        this.element = element;
    }
    public String getElement() {
        return this.element;
    }

    public boolean getHasInited() {
        return this.hasInited;
    }

    public void setHasInited(boolean hasInited) {
        this.hasInited = hasInited;
    }

    public ItemStack getGem() {
        if (slots == 0) {
            return null;
        }
        if (this.gem == null && this.attribute != null && this.element != null) {
            this.gem = GemHandler.createGem(this.level, this.element, this.attribute, this.strength);
        }
        return this.gem;
    }
    public void setGem(ItemStack gem) {
        this.gem = gem;
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
        if (isWrongGem) {
            return "Weapon Gem Incompatible with Armor Slot";

        }
        if (this.gem != null && this.gem.getItem() instanceof Gem) {
            LazyOptional<IGem> gemCapability = this.gem.getCapability(GemProvider.GEM_CAPABILITY);
            try {
                IGem gemInstance = gemCapability.orElseThrow(IllegalStateException::new);
                return gemInstance.getAttributeTooltip();
            } catch (IllegalStateException e) {
            }
        }
        if (slots > 0) {
            return "Slots: 1";
        }
        return "";
    }
}
