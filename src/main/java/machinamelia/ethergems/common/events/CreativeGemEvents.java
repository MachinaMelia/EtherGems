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

import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.capabilities.gems.GemInstance;
import machinamelia.ethergems.common.capabilities.gems.GemProvider;
import machinamelia.ethergems.common.capabilities.gems.IGem;
import machinamelia.ethergems.common.container.GemInventoryContainer;
import machinamelia.ethergems.common.items.gems.*;
import machinamelia.ethergems.common.util.GemHandler;

import static machinamelia.ethergems.common.container.EtherFurnaceContainer.readItemStacksFromTag;
import static machinamelia.ethergems.common.container.EtherFurnaceContainer.writeItemStacksToTag;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeGemEvents {
    @SubscribeEvent
    public static void openGemInventoryEvent(PlayerContainerEvent event) {
        Container container = event.getContainer();
        if (container instanceof GemInventoryContainer) {
            if (container instanceof GemInventoryContainer) {
                CompoundNBT compoundNBT = (CompoundNBT) event.getPlayer().getPersistentData().get("gem_inventory");
                ItemStack[] items = new ItemStack[44];
                if (compoundNBT != null) {
                    ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                    readItemStacksFromTag(items, listNBT);
                }
                for (int i = 0; i < 44; i++) {
                    if (items[i] != null && items[i].getItem() instanceof Gem) {
                        makeCreativeGems(items[i]);

                    }
                }
                compoundNBT = new CompoundNBT();
                compoundNBT.put("Items", writeItemStacksToTag(items, 44));
                compoundNBT.putByte("size", (byte) 44);
                event.getPlayer().getPersistentData().put("gem_inventory", compoundNBT);
            }
        }
    }
    public static String makeCreativeGems(ItemStack itemStack) {
        Item item = itemStack.getItem();
        LazyOptional<IGem> gemCapability = itemStack.getCapability(GemProvider.GEM_CAPABILITY);
        IGem gem = gemCapability.orElse(new GemInstance());
        if (gem.getAttribute().equals("")) {
            String attribute = "";
            String element = "";
            if (item instanceof BlazeAttackGem) {
                attribute = "Blaze Attack";
                element = "Fire";
            } else if (item instanceof BindResistGem) {
                attribute = "Bind Resist";
                element = "Fire";
            } else if (item instanceof BlazePlusGem) {
                attribute = "Blaze Plus";
                element = "Fire";
            } else if (item instanceof BuffTimePlusGem) {
                attribute = "Buff Time Plus";
                element = "Fire";
            } else if (item instanceof ChillDefenceGem) {
                attribute = "Chill Defence";
                element = "Fire";
            } else if (item instanceof EXPGem) {
                attribute = "EXP Up";
                element = "Fire";
            } else if (item instanceof SlowResistGem) {
                attribute = "Slow Resist";
                element = "Fire";
            } else if (item instanceof SpikeGem) {
                attribute = "Spike";
                element = "Fire";
            } else if (item instanceof StrengthDownGem) {
                attribute = "Strength Down";
                element = "Fire";
            } else if (item instanceof WeaponPowerGem) {
                attribute = "Weapon Power";
                element = "Fire";
            } else if (item instanceof FireGem) {
                attribute = "Strength Up";
                element = "Fire";
            } else if (item instanceof AquaticCloakGem) {
                attribute = "Aquatic Cloak";
                element = "Water";
            } else if (item instanceof AutoHealUpGem) {
                attribute = "Auto-Heal Up";
                element = "Water";
            } else if (item instanceof DamageHealGem) {
                attribute = "Damage Heal";
                element = "Water";
            } else if (item instanceof HPStealGem) {
                attribute = "HP Steal";
                element = "Water";
            } else if (item instanceof PoisonDefenceGem) {
                attribute = "Poison Defence";
                element = "Water";
            } else if (item instanceof DebuffResistGem) {
                attribute = "Debuff Resist";
                element = "Water";
            } else if (item instanceof RecoveryUpGem) {
                attribute = "Recovery Up";
                element = "Water";
            } else if (item instanceof SpikeDefenceGem) {
                attribute = "Spike Defence";
                element = "Water";
            } else if (item instanceof UnbeatableGem) {
                attribute = "Unbeatable";
                element = "Water";
            } else if (item instanceof WaterGem) {
                attribute = "HP Up";
                element = "Water";
            } else if (item instanceof BackAttackPlusGem) {
                attribute = "Back Attack Plus";
                element = "Electric";
            } else if (item instanceof FirstAttackPlusGem) {
                attribute = "First Attack Plus";
                element = "Electric";
            } else if (item instanceof PhysDefDownGem) {
                attribute = "Phys Def Down";
                element = "Electric";
            } else if (item instanceof ElectricGem) {
                attribute = "Double Attack";
                element = "Electric";
            } else if (item instanceof BlazeDefenceGem) {
                attribute = "Blaze Defence";
                element = "Ice";
            } else if (item instanceof ChillAttackGem) {
                attribute = "Chill Attack";
                element = "Ice";
            } else if (item instanceof ChillPlusGem) {
                attribute = "Chill Plus";
                element = "Ice";
            } else if (item instanceof SlowGem) {
                attribute = "Slow";
                element = "Ice";
            } else if (item instanceof IceGem) {
                attribute = "Bind";
                element = "Ice";
            } else if (item instanceof AerialCloakGem) {
                attribute = "Aerial Cloak";
                element = "Wind";
            } else if (item instanceof BleedAttackGem) {
                attribute = "Bleed Attack";
                element = "Wind";
            } else if (item instanceof BleedPlusGem) {
                attribute = "Bleed Plus";
                element = "Wind";
            } else if (item instanceof FallDefenceGem) {
                attribute = "Fall Defence";
                element = "Wind";
            } else if (item instanceof GoodFootingGem) {
                attribute = "Good Footing";
                element = "Wind";
            } else if (item instanceof HasteGem) {
                attribute = "Haste";
                element = "Wind";
            } else if (item instanceof WindGem) {
                attribute = "Quick Step";
                element = "Wind";
            } else if (item instanceof BleedDefenceGem) {
                attribute = "Bleed Defence";
                element = "Earth";
            } else if (item instanceof CriticalUpGem) {
                attribute = "Critical Up";
                element = "Earth";
            } else if (item instanceof DebuffPlusGem) {
                attribute = "Debuff Plus";
                element = "Earth";
            } else if (item instanceof PhysicalProtectGem) {
                attribute = "Physical Protect";
                element = "Earth";
            } else if (item instanceof PoisonAttackGem) {
                attribute = "Poison Attack";
                element = "Earth";
            } else if (item instanceof PoisonPlusGem) {
                attribute = "Poison Plus";
                element = "Earth";
            } else if (item instanceof EarthCloakGem) {
                attribute = "Earth Cloak";
                element = "Earth";
            } else if (item instanceof EarthGem) {
                attribute = "Muscle Up";
                element = "Earth";
            }
            double gemStrength = GemHandler.strengthConverter(6, 99, attribute);
            gem.init(6, element, attribute, gemStrength);
            gem.setTooltipString(GemHandler.strengthConverter(gemStrength, attribute));
        }
        String equipmentType = GemHandler.getEquipmentType(gem.getAttribute());
        String colorCode = "";
        switch (equipmentType) {
            case "Equipment":
                colorCode = "\u00A73";
                break;
            case "Armor":
                colorCode = "\u00A71";
                break;
            case "Weapon":
                colorCode = "\u00A72";
                break;
        }
        return colorCode + equipmentType + colorCode + "/" + gem.getAttributeTooltip();
    }
}
