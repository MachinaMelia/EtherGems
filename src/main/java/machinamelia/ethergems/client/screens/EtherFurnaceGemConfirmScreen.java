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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import machinamelia.ethergems.client.gui.HoverlessImageButton;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.container.EtherFurnaceGemConfirmContainer;

public class EtherFurnaceGemConfirmScreen extends ContainerScreen<EtherFurnaceGemConfirmContainer> {

    private static final ResourceLocation GEM_CONFIRM_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting4_gem_confirm.png");
    private static final ResourceLocation CRAFTING_UI_ELEMENTS = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting_ui_elements.png");

    private PlayerEntity player;

    public EtherFurnaceGemConfirmScreen(EtherFurnaceGemConfirmContainer screenContainer, PlayerInventory playerInventory, ITextComponent titleIn) {
        super(screenContainer, playerInventory, titleIn);
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 190;
        this.ySize = 126;
        this.player = playerInventory.player;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void init() {
        super.init();
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.addButton(new HoverlessImageButton(x + 60, y + 104, 72, 13, 0, 14, 14, CRAFTING_UI_ELEMENTS, (onPressed) -> {
            this.container.closeGui();
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
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString("Confirm", 77.0f, 107.0f, 4210752);
        final String whiteColorCode = "\u00A7f";
        this.font.drawString(whiteColorCode + "Gem Confirm" + whiteColorCode, 67.0f, 14.0f, 4210752);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        // Attribute display
        // Crystal Inventory
        this.minecraft.getTextureManager().bindTexture(GEM_CONFIRM_TEXTURE);
        this.blit(x, y, 0, 0, 190, 126);

    }
}
