package machinamelia.ethergems.common.events;

/*
 *   Copyright (C) 2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import machinamelia.ethergems.client.screens.UpdatedInventoryScreen;
import machinamelia.ethergems.common.EtherGems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class GuiEvents {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void replacePlayerInventoryEvent(GuiOpenEvent event) {
        Screen screen = event.getGui();
        if (screen instanceof InventoryScreen) {
            PlayerEntity player = Minecraft.getInstance().player;
            event.setGui(new UpdatedInventoryScreen(player));
        }
    }
}
