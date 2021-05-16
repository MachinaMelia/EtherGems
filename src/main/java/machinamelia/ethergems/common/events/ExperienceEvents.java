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
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.util.GemHandler;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ExperienceEvents {
    @SubscribeEvent
    public static void experienceDropEvent(LivingExperienceDropEvent event) {
        PlayerEntity player = event.getAttackingPlayer();
        if (player != null) {
            double fullStrength = GemHandler.getPlayerGemStrength(player, "EXP Up");
            if (fullStrength > 200.0) {
                fullStrength = 200.0;
            }
            if (fullStrength > 0) {
                int oldExperience = event.getOriginalExperience();
                event.setDroppedExperience(oldExperience + (int) ((fullStrength / 100.0) * oldExperience));
            }
        }
    }
}
