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

import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandEvents {
    @SubscribeEvent
    public static void commandEvent(net.minecraftforge.event.CommandEvent event) {
        String command = event.getParseResults().getReader().getRead();
        if (command.length() > 5 && command.startsWith("/give")) {
            ItemStack itemStack;
            if (command.contains("ethergems:")) {
                if (command.endsWith("crystal") || command.endsWith("gem") || command.endsWith("cylinder")) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
