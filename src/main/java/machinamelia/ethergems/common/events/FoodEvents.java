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

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.util.GemHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FoodEvents {
    public static final Field saturationLevel = ObfuscationReflectionHelper.findField(FoodStats.class, "field_75125_b");
    @SubscribeEvent
    public static void eat(LivingEntityUseItemEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            double fullStrength = GemHandler.getPlayerGemStrength(player, "Auto-Heal Up");
            if (fullStrength > 25.0) {
                fullStrength = 25.0;
            }
            if (event.getItem().isFood() && fullStrength > 0) {
                float currentSaturation = player.getFoodStats().getSaturationLevel();
                FoodStats food = player.getFoodStats();
                try {
                    saturationLevel.set(food, currentSaturation + (float) (event.getItem().getItem().getFood().getSaturation() * (fullStrength / 100.0)));
                } catch (IllegalAccessException e) {
                    LOGGER.warn("IllegalAccessException using reflection on PlayerEntity saturationLevel: " + e);
                }
            }
        }
    }
    private static final Logger LOGGER = LogManager.getLogger();
}
