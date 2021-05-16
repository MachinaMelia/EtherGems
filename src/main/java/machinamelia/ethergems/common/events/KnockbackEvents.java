package machinamelia.ethergems.common.events;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.util.GemHandler;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KnockbackEvents {
    @SubscribeEvent
    public static void knockbackEvent(LivingKnockBackEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            double fullStrength = GemHandler.getPlayerGemStrength(player, "Good Footing");
            if (fullStrength > 100.0) {
                fullStrength = 100.0;
            }
            if (fullStrength > 0) {
                float oldStrength = event.getStrength();
                float newStrength = (float) (oldStrength - (oldStrength * (fullStrength / 100.0)));
                if (newStrength < 0) {
                    newStrength = 0;
                }
                event.setStrength(newStrength);
            }
        }
    }
}
