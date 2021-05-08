package machinamelia.ethergems.common.util;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.capabilities.armor.ISlottedArmor;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorProvider;
import machinamelia.ethergems.common.capabilities.gems.GemInstance;
import machinamelia.ethergems.common.capabilities.gems.GemProvider;
import machinamelia.ethergems.common.capabilities.gems.IGem;
import machinamelia.ethergems.common.capabilities.weapons.ISlottedWeapon;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponProvider;
import machinamelia.ethergems.common.init.ItemInit;
import machinamelia.ethergems.common.items.gems.*;
import machinamelia.ethergems.common.items.weapon.SlottedAxe;
import machinamelia.ethergems.common.items.weapon.SlottedSword;

import java.util.Iterator;

import static machinamelia.ethergems.common.container.EtherFurnaceContainer.readItemStacksFromTag;

public class GemHandler {

    public static final ResourceLocation GEM_CAPABILITY = new ResourceLocation(EtherGems.MOD_ID, "gem");

    public static ItemStack createGem(int level, String element, String attribute, int strength) {
        ItemStack itemStack = ItemStack.EMPTY;
        switch (element) {
            case "Fire":
                itemStack = new ItemStack(ItemInit.FIRE_GEM.get());
                break;
            case "Water":
                itemStack = new ItemStack(ItemInit.WATER_GEM.get());
                break;
            case "Electric":
                itemStack = new ItemStack(ItemInit.ELECTRIC_GEM.get());
                break;
            case "Ice":
                itemStack = new ItemStack(ItemInit.ICE_GEM.get());
                break;
            case "Wind":
                itemStack = new ItemStack(ItemInit.WIND_GEM.get());
                break;
            case "Earth":
                itemStack = new ItemStack(ItemInit.EARTH_GEM.get());
                break;
        }
        itemStack.setCount(1);
        LazyOptional<IGem> gemCapability = itemStack.getCapability(GemProvider.GEM_CAPABILITY);
        IGem gem = gemCapability.orElse(new GemInstance());
        double gemStrength = strengthConverter(level, strength, attribute);
        gem.init(level, element, attribute, gemStrength);
        gem.setTooltipString(strengthConverter(gemStrength, attribute));
        return itemStack;
    }

    public static ItemStack createGem(int level, String element, String attribute, double strength) {
        ItemStack itemStack = ItemStack.EMPTY;
        switch (element) {
            case "Fire":
                itemStack = new ItemStack(ItemInit.FIRE_GEM.get());
                break;
            case "Water":
                itemStack = new ItemStack(ItemInit.WATER_GEM.get());
                break;
            case "Electric":
                itemStack = new ItemStack(ItemInit.ELECTRIC_GEM.get());
                break;
            case "Ice":
                itemStack = new ItemStack(ItemInit.ICE_GEM.get());
                break;
            case "Wind":
                itemStack = new ItemStack(ItemInit.WIND_GEM.get());
                break;
            case "Earth":
                itemStack = new ItemStack(ItemInit.EARTH_GEM.get());
                break;
        }
        itemStack.setCount(1);
        LazyOptional<IGem> gemCapability = itemStack.getCapability(GemProvider.GEM_CAPABILITY);
        IGem gem = gemCapability.orElse(new GemInstance());
        gem.init(level, element, attribute, strength);
        gem.setTooltipString(strengthConverter(strength, attribute));
        return itemStack;
    }

    public static String strengthConverter(double strength, String attribute) {
        switch (attribute) {
            case "Blaze Plus":
            case "Spike":
            case "Strength Down":
            case "Strength Up":
            case "Damage Heal":
            case "HP Up":
            case "HP Steal":
            case "Recovery Up":
            case "Ether Up":
            case "Back Attack Plus":
            case "First Attack Plus":
            case "Phys Def Down":
            case "Chill Plus":
            case "Ether Def Down":
            case "Ether Def Up":
            case "Ether Protect":
            case "Bleed Plus":
            case "Attack Stability":
            case "Attack Plus":
            case "Ether Down":
            case "Muscle Up":
            case "Physical Protect":
            case "Poison Plus":
            {
                return  attribute + ": " + (double) strength;
            }
            default: {
                return attribute + ": " + (int) strength + "%";
            }
        }
    }

    public static double getPercentage(int crystalStrength, int start, int end) {
        int range = end - start;
        double specialSauce = 100.0 / (double) range;
        return (int) ((crystalStrength + 1) / specialSauce) + start;
    }

    public static double getValue(int crystalStrength, double start, double end) {
        double range = end - start;
        int divisions = (int) (range / 0.5) + 1;
        double specialSauce = 100.0 / (divisions);
        int num = 0;
        for (int i = 1; i < divisions; i++) {
            if (crystalStrength <= (specialSauce * divisions)) {
                return i * range + start;
            }
        }
        return 0.0;
    }

    public static double strengthConverter(int level, int crystalStrength, String attribute) {
        switch (attribute) {
            case "Blaze Attack":
                {
                    if (level == 1) {
                        return 10;
                    } else if (level == 2) {
                        return 12;
                    } else if (level == 3) {
                        return 16;
                    } else if (level == 4) {
                        return 20;
                    } else if (level == 5) {
                        return 25;
                    } else if (level == 6) {
                        return 30;
                    }
                }
                break;
            case "Bind Resist":
            {
                if (level == 1) {
                    return 0;
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 12, 18);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 20, 26);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 28, 34);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 36, 50);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 75, 100);
                }
            }
            break;
            case "Blaze Plus":
                {
                    if (level == 1) {
                        return 0;
                    } else if (level == 2) {
                        return getValue(crystalStrength, 0.5, 1.0);
                    } else if (level == 3) {
                        return getValue(crystalStrength, 1, 1.5);
                    } else if (level == 4) {
                        return 0;
                    } else if (level == 5) {
                        return getValue(crystalStrength, 2, 2.5);
                    } else if (level == 6) {
                        return 3.0;
                    }
                }
                break;
            case "Buff Time Plus":
                {
                    if (level == 1) {
                        return getPercentage(crystalStrength, 10, 20);
                    } else if (level == 2) {
                        return getPercentage(crystalStrength, 30, 40);
                    } else if (level == 3) {
                        return getPercentage(crystalStrength, 50, 60);
                    } else if (level == 4) {
                        return getPercentage(crystalStrength, 70, 80);
                    } else if (level == 5) {
                        return getPercentage(crystalStrength, 90, 100);
                    } else if (level == 6) {
                        return getPercentage(crystalStrength, 120, 150);
                    }
                }
                break;
            case "Chill Defence":
                {
                    if (level == 1) {
                        return -getPercentage(crystalStrength, 2, 4);
                    } else if (level == 2) {
                        return -getPercentage(crystalStrength, 6, 8);
                    } else if (level == 3) {
                        return -getPercentage(crystalStrength, 10, 12);
                    } else if (level == 4) {
                        return -getPercentage(crystalStrength, 14, 16);
                    } else if (level == 5) {
                        return -getPercentage(crystalStrength, 18, 25);
                    } else if (level == 6) {
                        return -getPercentage(crystalStrength, 50, 75);
                    }
                }
                break;
            case "EXP Up":
                {
                    if (level == 1) {
                        return getPercentage(crystalStrength, 5, 10);
                    } else if (level == 2) {
                        return getPercentage(crystalStrength, 15, 20);
                    } else if (level == 3) {
                        return getPercentage(crystalStrength, 25, 30);
                    } else if (level == 4) {
                        return getPercentage(crystalStrength, 35, 40);
                    } else if (level == 5) {
                        return getPercentage(crystalStrength, 45, 50);
                    } else if (level == 6) {
                        return getPercentage(crystalStrength, 75, 100);
                    }
                }
                break;
            case "Slow Resist":
                {
                    if (level == 1) {
                        return getPercentage(crystalStrength, 4, 10);
                    } else if (level == 2) {
                        return getPercentage(crystalStrength, 12, 18);
                    } else if (level == 3) {
                        return getPercentage(crystalStrength, 20, 26);
                    } else if (level == 4) {
                        return getPercentage(crystalStrength, 28, 34);
                    } else if (level == 5) {
                        return getPercentage(crystalStrength, 36, 50);
                    } else if (level == 6) {
                        return getPercentage(crystalStrength, 75, 100);
                    }
                }
                break;
            case "Spike":
                {
                    if (level == 1) {
                        return 0.5;
                    } else if (level == 2) {
                        return getValue(crystalStrength, 1, 2);
                    } else if (level == 3) {
                        return getValue(crystalStrength, 2, 3);
                    } else if (level == 4) {
                        return getValue(crystalStrength, 3, 4);
                    } else if (level == 5) {
                        return getValue(crystalStrength, 4, 5);
                    } else if (level == 6) {
                        return getValue(crystalStrength, 5, 6);
                    }
                }
                break;
            case "Strength Down":
                {
                    if (level == 1) {
                        return -0.5;
                    } else if (level == 2) {
                        return -getValue(crystalStrength, 0.5, 1);
                    } else if (level == 3) {
                        return -getValue(crystalStrength, 1.0, 1.5);
                    } else if (level == 4) {
                        return -getValue(crystalStrength, 1.5, 2);
                    } else if (level == 5) {
                        return -getValue(crystalStrength, 2.0, 2.5);
                    } else if (level == 6) {
                        return -getValue(crystalStrength, 2.5, 3);
                    }
                }
                break;
            case "Strength Up":
                {
                    if (level == 1) {
                        return getValue(crystalStrength, 0.5, 1);
                    } else if (level == 2) {
                        return getValue(crystalStrength, 1.0, 1.5);
                    } else if (level == 3) {
                        return 1.5;
                    } else if (level == 4) {
                        return getValue(crystalStrength, 1.5, 2);
                    } else if (level == 5) {
                        return getValue(crystalStrength, 2, 2.5);
                    } else if (level == 6) {
                        return getValue(crystalStrength, 2.5, 3);
                    }
                }
                break;
            case "Weapon Power":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 2, 6);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 8, 12);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 14, 18);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 20, 24);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 26, 30);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 40, 50);
                }
            }
            case "Aquatic Cloak":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 2, 10);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 12, 20);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 22, 30);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 32, 40);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 42, 50);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 52, 60);
                }
            }
            break;
            case "Auto-Heal Up":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 2, 5);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 6, 9);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 10, 13);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 14, 17);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 18, 21);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 22, 25);
                }
            }
            break;
            case "Damage Heal":
            {
                if (level == 1) {
                    return 1.5;
                } else if (level == 2) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 2.5, 3.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 3.0, 3.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 4.0,  4.5);
                }
            }
            break;
            case "Debuff Resist":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 4, 10);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 12, 18);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 20, 26);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 28, 34);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 36, 50);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 75, 100);
                }
            }
            break;
            case "HP Up":
            {
                if (level == 1) {
                    return getValue(crystalStrength, 1.0, 2.0);
                } else if (level == 2) {
                    return getValue(crystalStrength, 2.0, 3.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 3.0, 4.0);
                } else if (level == 4) {
                    return getValue(crystalStrength, 4.0, 5.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 5.0, 6.0);
                } else if (level == 6) {
                    return getValue(crystalStrength, 6.0, 8.0);
                }
            }
            break;
            case "HP Steal":
            {
                if (level == 1) {
                    return 0.5;
                } else if (level == 2) {
                    return getValue(crystalStrength, .5, 1);
                } else if (level == 3) {
                    return getValue(crystalStrength, 1.0, 1.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 2.5, 3);
                }
            }
            break;
            case "Poison Defence":
            {
                if (level == 1) {
                    return -getPercentage(crystalStrength, 2, 4);
                } else if (level == 2) {
                    return -getPercentage(crystalStrength, 6, 8);
                } else if (level == 3) {
                    return -getPercentage(crystalStrength, 10, 12);
                } else if (level == 4) {
                    return -getPercentage(crystalStrength, 14, 16);
                } else if (level == 5) {
                    return -getPercentage(crystalStrength, 18, 25);
                } else if (level == 6) {
                    return -getPercentage(crystalStrength, 50, 75);
                }
            }
            break;
            case "Recovery Up":
            {
                if (level == 1) {
                    return 0.5;
                } else if (level == 2) {
                    return getValue(crystalStrength, 0.5, 1.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 1.0, 1.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 2.5, 3.0);
                }
            }
            break;
            case "Spike Defence":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 4, 10);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 12, 18);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 20, 26);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 28, 34);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 36, 50);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 60, 75);
                }
            }
            break;
            case "Unbeatable":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 4, 10);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 12, 18);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 20, 26);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 28, 34);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 36, 42);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 44, 50);
                }
            }
            break;
            case "Back Attack Plus":
            {
                if (level == 1) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 2) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 3) {
                    return getValue(crystalStrength, 2.5, 3.0);
                } else if (level == 4) {
                    return getValue(crystalStrength, 3.0, 3.5);
                } else if (level == 5) {
                    return getValue(crystalStrength, 3.5, 4.0);
                } else if (level == 6) {
                    return getValue(crystalStrength, 4.0, 4.5);
                }
            }
            break;
            case "Double Attack":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 2, 6);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 8, 11);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 14, 18);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 20, 24);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 26, 30);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 40, 50);
                }
            }
            break;
            /* case "Ether Up":
            {
                if (level == 1) {
                    return getValue(crystalStrength, 1.0, 1.5);
                } else if (level == 2) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 2.5, 3.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 3.0, 3.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 3.5, 4.0);
                }
            }
            break; */
            case "First Attack Plus":
            {
                if (level == 1) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 2) {
                    return getValue(crystalStrength, 2.5, 3.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 3.0, 3.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 3.5, 4);
                } else if (level == 5) {
                    return getValue(crystalStrength, 4, 4.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 4.5, 5.0);
                }
            }
            break;
            case "Phys Def Down":
            {
                if (level == 1) {
                    return 0.5;
                } else if (level == 2) {
                    return getValue(crystalStrength, 0.5, 1.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 4) {
                    return getValue(crystalStrength, 2.5, 3.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 3.5, 4.0);
                } else if (level == 6) {
                    return getValue(crystalStrength, 4.5, 5.0);
                }
            }
            break;
            case "Bind":
            {
                if (level == 1) {
                    return 5;
                } else if (level == 2) {
                    return 8;
                } else if (level == 3) {
                    return 12;
                } else if (level == 4) {
                    return 15;
                } else if (level == 5) {
                    return 20;
                } else if (level == 6) {
                    return 25;
                }
            }
            break;
            case "Blaze Defence":
            {
                if (level == 1) {
                    return -getPercentage(crystalStrength, 2, 4);
                } else if (level == 2) {
                    return -getPercentage(crystalStrength, 6, 8);
                } else if (level == 3) {
                    return -getPercentage(crystalStrength, 10, 12);
                } else if (level == 4) {
                    return -getPercentage(crystalStrength, 14, 16);
                } else if (level == 5) {
                    return -getPercentage(crystalStrength, 18, 25);
                } else if (level == 6) {
                    return -getPercentage(crystalStrength, 50, 75);
                }
            }
            break;
            case "Chill Attack":
            {
                if (level == 1) {
                    return 10;
                } else if (level == 2) {
                    return 12;
                } else if (level == 3) {
                    return 16;
                } else if (level == 4) {
                    return 20;
                } else if (level == 5) {
                    return 25;
                } else if (level == 6) {
                    return 30;
                }
            }
            break;
            case "Chill Plus":
            {
                if (level == 1) {
                    return 0.5;
                } else if (level == 2) {
                    return getValue(crystalStrength, 0.5, 1.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 1.0, 1.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 2.5, 3.0);
                }
            }
            break;
            /* case "Ether Def Down":
            {
                if (level == 1) {
                    return 0.5;
                } else if (level == 2) {
                    return getValue(crystalStrength, 0.5, 1.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 4) {
                    return getValue(crystalStrength, 2.5, 3.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 3.5, 4.0);
                } else if (level == 6) {
                    return getValue(crystalStrength, 4.5, 5.0);
                }
            }
            break;
            case "Ether Def Up":
            {
                if (level == 1) {
                    return 0.5;
                } else if (level == 2) {
                    return getValue(crystalStrength, 0.5, 1.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 1.0, 1.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 2.5, 3.0);
                }
            }
            break;
            case "Ether Protect":
            {
                if (level == 1) {
                    return getValue(crystalStrength, 1.0, 1.5);
                } else if (level == 2) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 2.5, 3.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 3.0, 3.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 3.5, 4.0);
                }
            }
            break; */
            case "Slow":
            {
                if (level == 1) {
                   return 5;
                } else if (level == 2) {
                    return 8;
                } else if (level == 3) {
                    return 12;
                } else if (level == 4) {
                    return 15;
                } else if (level == 5) {
                    return 20;
                } else if (level == 6) {
                    return 25;
                }
            }
            break;
            case "Aerial Cloak":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 2, 10);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 12, 20);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 22, 30);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 32, 40);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 42, 50);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 52, 60);
                }
            }
            break;
            case "Bleed Attack":
            {
                if (level == 1) {
                    return 10;
                } else if (level == 2) {
                    return 12;
                } else if (level == 3) {
                    return 16;
                } else if (level == 4) {
                    return 20;
                } else if (level == 5) {
                    return 25;
                } else if (level == 6) {
                    return 30;
                }
            }
            break;
            case "Bleed Plus":
            {
                if (level == 1) {
                    return 0.5;
                } else if (level == 2) {
                    return getValue(crystalStrength, 0.5, 1.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 1.0, 1.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 2.5, 3.0);
                }
            }
            break;
            case "Fall Defence":
            {
                if (level == 1) {
                    return 12;
                } else if (level == 2) {
                    return 24;
                } else if (level == 3) {
                    return 36;
                } else if (level == 4) {
                    return 48;
                } else if (level == 5) {
                    return 60;
                } else if (level == 6) {
                    return 72;
                }
            }
            break;
            case "Good Footing":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 4, 10);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 12, 18);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 20, 26);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 28, 34);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 36, 50);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 75, 100);
                }
            }
            break;
            case "Haste":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 2, 6);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 8, 12);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 14, 18);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 20, 24);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 26, 30);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 40, 50);
                }
            }
            break;
            case "Quick Step":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 2, 4);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 6, 8);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 10, 12);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 14, 16);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 18, 20);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 22, 25);
                }
            }
            break;
            /*
            case "Attack Stability":
            {
                if (level == 1) {
                    return 0.5;
                } else if (level == 2) {
                    return getValue(crystalStrength, 0.5, 1.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 1, 1.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 2.5, 3.0);
                }
            }
            break;
            case "Attack Plus":
            {
                if (level == 1) {
                    return 0.5;
                } else if (level == 2) {
                    return getValue(crystalStrength, 0.5, 1.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 1, 1.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 2.5, 3.0);
                }
            }
            break; */
            case "Bleed Defence":
            {
                if (level == 1) {
                    return -getPercentage(crystalStrength, 2, 4);
                } else if (level == 2) {
                    return -getPercentage(crystalStrength, 6, 8);
                } else if (level == 3) {
                    return -getPercentage(crystalStrength, 10, 12);
                } else if (level == 4) {
                    return -getPercentage(crystalStrength, 14, 16);
                } else if (level == 5) {
                    return -getPercentage(crystalStrength, 18, 25);
                } else if (level == 6) {
                    return -getPercentage(crystalStrength, 50, 75);
                }
            }
            break;
            case "Critical Up":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 2, 4);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 6, 8);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 10, 12);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 14, 16);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 18, 20);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 22, 25);
                }
            }
            break;
            case "Debuff Plus":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 10, 20);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 30, 40);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 50, 60);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 70, 80);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 90, 100);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 120, 150);
                }
            }
            break;
            case "Earth Cloak":
            {
                if (level == 1) {
                    return getPercentage(crystalStrength, 2, 10);
                } else if (level == 2) {
                    return getPercentage(crystalStrength, 12, 20);
                } else if (level == 3) {
                    return getPercentage(crystalStrength, 22, 30);
                } else if (level == 4) {
                    return getPercentage(crystalStrength, 32, 40);
                } else if (level == 5) {
                    return getPercentage(crystalStrength, 42, 50);
                } else if (level == 6) {
                    return getPercentage(crystalStrength, 52, 60);
                }
            }
            break;
            /* case "Ether Down":
            {
                if (level == 1) {
                    return -0.5;
                } else if (level == 2) {
                    return -getValue(crystalStrength, 0.5, 1.0);
                } else if (level == 3) {
                    return -getValue(crystalStrength, 1, 1.5);
                } else if (level == 4) {
                    return -getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 5) {
                    return -getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 6) {
                    return -getValue(crystalStrength, 2.5, 3.0);
                }
            }
            break; */
            case "Muscle Up":
            {
                if (level == 1) {
                    return 0.5;
                } else if (level == 2) {
                    return getValue(crystalStrength, 0.5, 1.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 1, 1.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 2.5, 3.0);
                }
            }
            break;
            case "Physical Protect":
            {
                if (level == 1) {
                    return getValue(crystalStrength, 1.0, 1.5);
                } else if (level == 2) {
                    return getValue(crystalStrength, 1.5, 2.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 2.0, 2.5);
                } else if (level == 4) {
                    return getValue(crystalStrength, 2.5, 3.0);
                } else if (level == 5) {
                    return getValue(crystalStrength, 3.0, 3.5);
                } else if (level == 6) {
                    return getValue(crystalStrength, 3.5, 4.0);
                }
            }
            break;
            case "Poison Attack":
            {
                if (level == 1) {
                   return 10;
                } else if (level == 2) {
                    return 12;
                } else if (level == 3) {
                    return 16;
                } else if (level == 4) {
                    return 20;
                } else if (level == 5) {
                    return 25;
                } else if (level == 6) {
                    return 30;
                }
            }
            break;
            case "Poison Plus":
            {
                if (level == 1) {
                    return 0.5;
                } else if (level == 2) {
                    return getValue(crystalStrength, 0.5, 1.0);
                } else if (level == 3) {
                    return getValue(crystalStrength, 1.0, 1.5);

                } else if (level == 4) {
                    return getValue(crystalStrength, 1.5, 2.0);

                } else if (level == 5) {
                    return getValue(crystalStrength, 2.0, 2.5);

                } else if (level == 6) {
                    return getValue(crystalStrength, 2.5, 3.0);
                }
            }
            break;

            default: {
                return 1;
            }
        }
        return 1;
    }
    public static String getColorCode(String attribute) {
        switch (attribute) {
            case "Bind Resist":
            case "Blaze Attack":
            case "Blaze Plus":
            case "Buff Time Plus":
            case "Chill Defence":
            case "EXP Up":
            case "Slow Resist":
            case "Spike":
            case "Strength Down":
            case "Strength Up":
            case "Weapon Power": {
                return "\u00A74";
            }
            case "Aquatic Cloak":
            case "Auto-Heal Up":
            case "Damage Heal":
            case "Debuff Resist":
            case "HP Up":
            case "HP Steal":
            case "Poison Defence":
            case "Recovery Up":
            case "Spike Defence":
            case "Unbeatable": {
                return "\u00A79";
            }
            case "Back Attack Plus":
            case "Double Attack":
            case "First Attack Plus":
            case "Phys Def Down": {
                return "\u00A7e";
            }
            case "Bind":
            case "Blaze Defence":
            case "Chill Attack":
            case "Chill Plus":
            case "Slow": {
                return "\u00A7b";
            }
            case "Aerial Cloak":
            case "Bleed Attack":
            case "Bleed Plus":
            case "Fall Defence":
            case "Good Footing":
            case "Haste":
            case "Quick Step": {
                return "\u00A7a";
            }
            case "Attack Stability":
            case "Attack Plus":
            case "Bleed Defence":
            case "Critical Up":
            case "Debuff Plus":
            case "Earth Cloak":
            case "Muscle Up":
            case "Physical Protect":
            case "Poison Attack":
            case "Poison Plus": {
                return "\u00A76";
            }
            default: {
                return "";
            }
        }
    }
    public static double getMax(String attribute) {
       switch (attribute) {
           case "Bind Resist":
           case "Blaze Attack":
           case "Slow Resist":
           case "Debuff Resist":
           case "Poison Defence":
           case "Chill Attack":
           case "Bleed Attack":
           case "Good Footing":
           case "Poison Attack":
           case "Bleed Defence":
           case "Spike Defence": {
               return 100;
           }
           case "Blaze Plus":
           case "HP Steal":
           case "Recovery Up":
           case "Chill Plus":
           case "Bleed Plus":
           case "Muscle Up":
           case "Poison Plus": {
               return 3;
           }
           case "Strength Down": {
               return -3;
           }
           case "Physical Protect": {
               return 4;
           }
           case "Strength Up":
           case "Damage Heal":
           case "Back Attack Plus": {
               return 4.5;
           }
           case "First Attack Plus":
           case "Phys Def Down": {
               return 5;
           }
           case "Spike": {
               return 6;
           }
           case "HP Up": {
               return 7*8;
           }
           case "Auto-Heal Up":
           case "Bind":
           case "Slow":
           case "Quick Step":
           case "Critical Up": {
               return 25;
           }
           case "Weapon Power":
           case "Unbeatable":
           case "Double Attack":
           case "Haste": {
               return 50;
           }
           case "Aquatic Cloak":
           case "Aerial Cloak":
           case "Earth Cloak": {
               return 60;
           }
           case "Fall Defence": {
               return 72;
           }
           case "Buff Time Plus": {
                return 150;
           }
           case "Chill Defence":
           case "Blaze Defence": {
               return -100;
           }
           case "Debuff Plus": {
               return 150;
           }
           case "EXP Up": {
               return 200;
           }
           default: {
               return 200;
           }
       }
    }

    public static boolean isNegative(String attribute) {
        switch (attribute) {
            case "Chill Defence":
            case "Strength Down":
            case "Poison Defence":
            case "Blaze Defence":
            case "Bleed Defence": {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    public static boolean isPercentage(String attribute) {
        switch (attribute) {
            case "Bind Resist":
            case "Blaze Attack":
            case "Buff Time Plus":
            case "Chill Defence":
            case "EXP Up":
            case "Slow Resist":
            case "Weapon Power":
            case "Aquatic Cloak":
            case "Auto-Heal Up":
            case "Poison Defence":
            case "Unbeatable":
            case "Double Attack":
            case "Bind":
            case "Blaze Defence":
            case "Chill Attack":
            case "Slow":
            case "Aerial Cloak":
            case "Bleed Attack":
            case "Fall Defence":
            case "Good Footing":
            case "Haste":
            case "Quick Step":
            case "Bleed Defence":
            case "Critical Up":
            case "Spike Defence":
            case "Debuff Plus":
            case "Earth Cloak":
            case "Poison Attack": {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    public static boolean isArmorGem(String attribute) {
         switch(attribute) {
             case "Bind Resist":
             case "Buff Time Plus":
             case "Chill Defence":
             case "EXP Up":
             case "Slow Resist":
             case "Spike":
             case "Strength Up":
             case "Aquatic Cloak":
             case "Auto-Heal Up":
             case "Damage Heal":
             case "Debuff Resist":
             case "HP Up":
             case "Poison Defence":
             case "Recovery Up":
             case "Spike Defence":
             case "Unbeatable":
             case "Blaze Defence":
             case "Aerial Cloak":
             case "Fall Defence":
             case "Good Footing":
             case "Quick Step":
             case "Bleed Defence":
             case "Earth Cloak":
             case "Muscle Up":
             case "Physical Protect": {
                 return true;
             }
             default: {
                 return false;
             }
         }
    }
    public static boolean isWeaponGem(String attribute) {
        switch(attribute) {
            case "Blaze Attack":
            case "Blaze Plus":
            case "EXP Up":
            case "Strength Down":
            case "Strength Up":
            case "Weapon Power":
            case "Aquatic Cloak":
            case "HP Up":
            case "HP Steal":
            case "Back Attack Plus":
            case "Double Attack":
            case "First Attack Plus":
            case "Phys Def Down":
            case "Bind":
            case "Chill Attack":
            case "Chill Plus":
            case "Slow":
            case "Aerial Cloak":
            case "Bleed Attack":
            case "Bleed Plus":
            case "Haste":
            case "Attack Stability":
            case "Attack Plus":
            case "Critical Up":
            case "Debuff Plus":
            case "Earth Cloak":
            case "Muscle Up":
            case "Poison Attack":
            case "Poison Plus": {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    public static String getEquipmentType(String attribute) {
        String equipmentType = "";
        if (GemHandler.isWeaponGem(attribute) && GemHandler.isArmorGem(attribute)) {
            equipmentType = "Equipment";
        } else if (GemHandler.isArmorGem(attribute)) {
            equipmentType = "Armor";
        }  else if (GemHandler.isWeaponGem(attribute)) {
            equipmentType = "Weapon";
        }
        return equipmentType;
    }
    public static double getPlayerGemStrength(PlayerEntity player, String attribute) {
        double fullStrength = 0;
        Iterator<ItemStack> armorList = player.getArmorInventoryList().iterator();
        for (int i = 0; i < 4; i++) {
            ItemStack armor = armorList.next();
            LazyOptional<ISlottedArmor> armorCapability = armor.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
            try {
                ISlottedArmor armorInstance = armorCapability.orElseThrow(IllegalStateException::new);
                ItemStack gem = armorInstance.getGem();
                if (gem != null && gem.getItem() instanceof Gem && !gem.getItem().equals(Items.AIR)) {
                    LazyOptional<IGem> gemCapability = gem.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElseThrow(IllegalStateException::new);
                    String gemAttribute = gemInstance.getAttribute();
                    double strength = gemInstance.getStrength();
                    if (gemAttribute != null && !gemAttribute.equals("")) {
                        if (gemAttribute.equals(attribute)) {
                            fullStrength += strength;
                        }
                    }
                }
            } catch (IllegalStateException e) {
            }
        }
        if (player.world.isRemote) {
            ItemStack weapon = player.getHeldItemMainhand();
            if (weapon.getItem() instanceof SlottedSword || weapon.getItem() instanceof SlottedAxe) {
                LazyOptional<ISlottedWeapon> weaponCapability = weapon.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                try {
                    ISlottedWeapon weaponInstance = weaponCapability.orElseThrow(IllegalStateException::new);
                    for (int i = 0; i < weaponInstance.getSlots(); i++) {
                        ItemStack gem = weaponInstance.getGem(i);
                        if (gem != null && gem.getItem() instanceof Gem && !gem.getItem().equals(Items.AIR)) {
                            LazyOptional<IGem> gemCapability = gem.getCapability(GemProvider.GEM_CAPABILITY);
                            IGem gemInstance = gemCapability.orElseThrow(IllegalStateException::new);
                            String gemAttribute = gemInstance.getAttribute();
                            double strength = gemInstance.getStrength();
                            if (gemAttribute != null && !gemAttribute.equals("")) {
                                if (gemAttribute.equals(attribute)) {
                                    fullStrength += strength;
                                }
                            }
                        }
                    }
                } catch (IllegalStateException e) {
                }
            }
        } else {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            CompoundNBT compoundNBT = (CompoundNBT) player.getPersistentData().get("gem_inventory");
            ItemStack[] items = new ItemStack[44];
            if (compoundNBT != null) {
                ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                readItemStacksFromTag(items, listNBT);
            }
            for (int i = 41; i < 44; i++) {
                if (items[i] != null && items[i].getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = items[i].getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    String gemAttribute = gemInstance.getAttribute();
                    double strength = gemInstance.getStrength();
                    if (gemAttribute != null && !gemAttribute.equals("")) {
                        if (gemAttribute.equals(attribute)) {
                            fullStrength += strength;
                        }
                    }
                }
            }
        }
        return fullStrength;
    }
}
