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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import machinamelia.ethergems.common.capabilities.gems.GemInstance;
import machinamelia.ethergems.common.capabilities.gems.GemProvider;
import machinamelia.ethergems.common.capabilities.gems.IGem;
import machinamelia.ethergems.common.util.GemHandler;

public class SlottedWeaponStorage implements Capability.IStorage<ISlottedWeapon> {
    @Override
    public INBT writeNBT(Capability<ISlottedWeapon> capability, ISlottedWeapon instance, Direction side)
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        for (int i = 0; i < instance.getSlots(); i++) {
            if (instance.getGem(i) != null) {
                LazyOptional<IGem> gemCapability = instance.getGem(i).getCapability(GemProvider.GEM_CAPABILITY);
                IGem gem = gemCapability.orElse(new GemInstance());
                if (gem != null && !gem.getAttribute().equals("") && instance.getAttribute(i) != null) {
                    compoundNBT.putInt("Level" + i, instance.getLevel(i));
                    compoundNBT.putString("Attribute" + i, instance.getAttribute(i));
                    compoundNBT.putDouble("Strength" + i, instance.getStrength(i));
                    compoundNBT.putString("Element" + i, instance.getElement(i));
                    compoundNBT.putString("Tooltip", gem.getTooltipString());
                }
            }
        }
        compoundNBT.putBoolean("HasInited", instance.getHasInited());
        compoundNBT.putInt("Slots", instance.getSlots());
        return compoundNBT;
    }

    @Override
    public void readNBT(Capability<ISlottedWeapon> capability, ISlottedWeapon instance, Direction side, INBT nbt)
    {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        instance.setSlots(compoundNBT.getInt("Slots"));
        instance.setHasInited(compoundNBT.getBoolean("HasInited"));
        for (int i = 0; i < instance.getSlots(); i++) {
            int level = compoundNBT.getInt("Level" + i);
            String attribute = compoundNBT.getString("Attribute" + i);
            double strength = compoundNBT.getDouble("Strength" + i);
            String element = compoundNBT.getString("Element" + i);
            String tooltipString = compoundNBT.getString("Tooltip");
            ItemStack gem = GemHandler.createGem(level, element, attribute, strength);
            instance.setGem(i, gem);
            instance.setAttribute(i, attribute);
            instance.setElement(i, element);
            instance.setLevel(i, level);
            instance.setStrength(i, strength);
        }
    }
}
