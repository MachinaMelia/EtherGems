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

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import java.util.UUID;

public final class MaxHealthHandler {

    private static final UUID HEALTH_MODIFIER_ID = UUID.fromString("7bacb222-cd35-4068-88cd-5ab7ef0b6683");

    public static void updateHealthModifier(PlayerEntity player, double HPModifier) {
        float oldMax = player.getMaxHealth();
        double totalHealthModifier =  HPModifier;
        float newMax = (float) (oldMax + HPModifier);
        AttributeModifier modifier = new AttributeModifier(
                HEALTH_MODIFIER_ID,
                "Health Gained From Gems",
                totalHealthModifier,
                AttributeModifier.Operation.ADDITION
        );
        IAttributeInstance attribute = maxHealthAttribute(player);
        attribute.removeModifier(modifier);
        attribute.applyModifier(modifier);

        float newHealth = player.getHealth() * player.getMaxHealth() / oldMax;
        player.setHealth(1);
        player.setHealth(newHealth);
    }

    private static IAttributeInstance maxHealthAttribute(PlayerEntity player) {
        return player.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
    }

    private MaxHealthHandler() {}
}
