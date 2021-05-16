package machinamelia.ethergems.common.util;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderInstance;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderProvider;
import machinamelia.ethergems.common.capabilities.cylinders.ICylinder;
import machinamelia.ethergems.common.init.ItemInit;

public class CylinderHandler {
    public static ItemStack createCylinder(int level, String element, String attribute, int strength) {
        ItemStack itemStack = ItemStack.EMPTY;
        switch (element) {
            case "Fire":
                itemStack = new ItemStack(ItemInit.FIRE_CYLINDER.get());
                break;
            case "Water":
                itemStack = new ItemStack(ItemInit.WATER_CYLINDER.get());
                break;
            case "Electric":
                itemStack = new ItemStack(ItemInit.ELECTRIC_CYLINDER.get());
                break;
            case "Ice":
                itemStack = new ItemStack(ItemInit.ICE_CYLINDER.get());
                break;
            case "Wind":
                itemStack = new ItemStack(ItemInit.WIND_CYLINDER.get());
                break;
            case "Earth":
                itemStack = new ItemStack(ItemInit.EARTH_CYLINDER.get());
                break;
        }
        itemStack.setCount(1);
        LazyOptional<ICylinder> cylinderCapability = itemStack.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
        ICylinder cylinder = cylinderCapability.orElse(new CylinderInstance());
        cylinder.init(level, element, attribute, strength);
        cylinder.setTooltipString(attribute + ": " + strength + "%");
        return itemStack;
    }
}
