package machinamelia.ethergems.client.screens;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.container.CrystalInventoryContainer;

public class CrystalInventoryScreen extends ContainerScreen<CrystalInventoryContainer> {

    private static final ResourceLocation CRYSTAL_INVENTORY_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crystal_inventory.png");
    private static final ResourceLocation EQUIPMENT_INVENTORY_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/equipment_no_gems.png");
    private static final ResourceLocation CRYSTAL_INVENTORY_UI_ELEMENTS = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crystal_inventory_ui_elements.png");
    private static final ResourceLocation ATTRIBUTE_DISPLAY_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/inventory_attribute_display.png");

    private boolean gemButtonPressed = false;
    private PlayerEntity player;

    public CrystalInventoryScreen(CrystalInventoryContainer screenContainer, PlayerInventory playerInventory, ITextComponent titleIn) {
        super(screenContainer, playerInventory, titleIn);
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 320;
        this.ySize = 190;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void init() {
        super.init();
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.addButton(new ImageButton(x + 115, y + 7, 28, 29, 0, 2, 0, CRYSTAL_INVENTORY_UI_ELEMENTS, (onPressed) -> {
            this.gemButtonPressed = true;
            this.container.openGui();
            this.gemButtonPressed = false;
        }));
        this.addButton(new ImageButton(x + 143, y + 7, 28, 32, 30, 32, 0, CRYSTAL_INVENTORY_UI_ELEMENTS, (onPressed) -> {
        }));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
        // Do nothing you fool!
        return false;
    }
    @Override
    public void renderBackground() {
        super.renderBackground();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        // Attribute display
        this.minecraft.getTextureManager().bindTexture(ATTRIBUTE_DISPLAY_TEXTURE);
        this.blit(x, y + 115, 0, 0, 112, 67);
        // Crystal Inventory
        this.minecraft.getTextureManager().bindTexture(EQUIPMENT_INVENTORY_TEXTURE);
        this.blit(x + 7, y, 0, 0, 95, 120);
        // Crystal Inventory
        this.minecraft.getTextureManager().bindTexture(CRYSTAL_INVENTORY_TEXTURE);
        this.blit(x + 115, y + 35, 0, 0, 191, 120);
    }
}
