package machinamelia.ethergems.common.events;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import machinamelia.ethergems.common.blocks.elemental.EtherDeposit;
import machinamelia.ethergems.common.capabilities.crystals.CrystalInstance;
import machinamelia.ethergems.common.capabilities.crystals.CrystalProvider;
import machinamelia.ethergems.common.capabilities.crystals.ICrystal;
import machinamelia.ethergems.common.items.crystals.Crystal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.capabilities.armor.ISlottedArmor;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorInstance;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorProvider;
import machinamelia.ethergems.common.capabilities.weapons.ISlottedWeapon;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponInstance;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponProvider;
import machinamelia.ethergems.common.container.CrystalInventoryContainer;
import machinamelia.ethergems.common.container.EtherFurnaceContainer;
import machinamelia.ethergems.common.container.GemInventoryContainer;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemEvents {
    @SubscribeEvent
    public static void playerDropEvent(ItemTossEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.openContainer instanceof EtherFurnaceContainer || player.openContainer instanceof CrystalInventoryContainer) {
            event.setCanceled(true);
            boolean hasPlaced = false;
            for (int i = 0; i < 36; i++) {
                if (player.openContainer.getInventory().get(i).isEmpty() && !hasPlaced) {
                    player.openContainer.putStackInSlot(i, event.getEntityItem().getItem());
                    hasPlaced = true;
                }
            }
        }
    }
    @SubscribeEvent
    public static void getPickedUpEvent(PlayerEvent.ItemPickupEvent event) {
        ItemStack itemStack = event.getStack();
        Item item = itemStack.getItem();
        if (item instanceof ArmorItem) {
            LazyOptional<ISlottedArmor> armorCapability = itemStack.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
            ISlottedArmor armor = armorCapability.orElse(new SlottedArmorInstance());
        }
        if (item instanceof SwordItem || item instanceof AxeItem) {
            LazyOptional<ISlottedWeapon> weaponCapability = itemStack.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
            ISlottedWeapon weapon = weaponCapability.orElse(new SlottedWeaponInstance());
        }
    }
}
