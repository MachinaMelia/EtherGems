package machinamelia.ethergems.common.items.cylinders;

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
import machinamelia.ethergems.common.capabilities.cylinders.CylinderProvider;
import machinamelia.ethergems.common.items.crystals.SingleItemBase;

import javax.annotation.Nullable;
import java.util.Objects;

public class Cylinder extends SingleItemBase {

        public Cylinder() {
            super();
        }
        @Nullable
        @Override
        public CompoundNBT getShareTag(ItemStack stack) {
            CompoundNBT nbt = stack.getOrCreateTag();
            stack.getCapability(CylinderProvider.CYLINDER_CAPABILITY).ifPresent(
                    handler -> {
                        nbt.put("cap_sync", Objects.requireNonNull(CylinderProvider.CYLINDER_CAPABILITY.writeNBT(handler, null)));
                    }
            );
            return nbt;
        }

        @Override
        public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
            super.readShareTag(stack, nbt);

            if (nbt != null) {
                stack.getCapability(CylinderProvider.CYLINDER_CAPABILITY).ifPresent(
                        handler -> {
                            CylinderProvider.CYLINDER_CAPABILITY.readNBT(handler, null, nbt.get("cap_sync"));
                        }
                );
            }
        }
}
