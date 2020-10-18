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

public interface ISlottedArmor {
    public ItemStack getGem();
    public void setGem(ItemStack gem);
    public int getSlots();
    public void setSlots(int slots);
    public void setAttribute(String attribute);
    public String getAttribute();
    public void setElement(String element);
    public String getElement();
    public void setLevel(int level);
    public int getLevel();
    public void setStrength(double strength);
    public double getStrength();
    public boolean getHasInited();
    public void setHasInited(boolean hasInited);
    public String getTooltip();
    public void setIsWrongGem(boolean value);
    public void init();
}
