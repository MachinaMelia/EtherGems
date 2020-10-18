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

public interface ISlottedWeapon {
    public ItemStack getGem(int index);
    public void setGem(int index, ItemStack gem);
    public int getSlots();
    public void setSlots(int slots);
    public void setAttribute(int index, String attribute);
    public String getAttribute(int index);
    public void setElement(int index, String element);
    public String getElement(int index);
    public void setLevel(int index, int level);
    public int getLevel(int index);
    public void setStrength(int index, double strength);
    public double getStrength(int index);
    public boolean getHasInited();
    public void setHasInited(boolean hasInited);
    public String getTooltip();
    public void setIsWrongGem(boolean value);
    public void init();
}
