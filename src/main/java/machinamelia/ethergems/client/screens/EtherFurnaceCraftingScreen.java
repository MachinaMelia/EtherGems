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
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.common.MinecraftForge;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.container.EtherFurnaceCraftingContainer;

import java.util.ArrayList;

public class EtherFurnaceCraftingScreen extends ContainerScreen<EtherFurnaceCraftingContainer> {
    private final String[] flames = { "Strong Flame", "Medium Flame", "Gentle Flame", "    Done!   ", "" };

    private static final ResourceLocation ATTRIBUTE_DISPLAY_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting_attribute_display.png");
    private static final ResourceLocation CRAFTING_UI_ELEMENTS_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting_ui_elements.png");
    private static final ResourceLocation CYLINDER_GAUGE_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting3_cylinder_gauge.png");
    private static final ResourceLocation ETHER_FURNACE_START_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting3_ether_furnace_start.png");
    private static final ResourceLocation ETHER_FURNACE_STRONG_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting3_ether_furnace_strong.png");
    private static final ResourceLocation ETHER_FURNACE_MEDIUM_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting3_ether_furnace_medium.png");
    private static final ResourceLocation ETHER_FURNACE_GENTLE_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting3_ether_furnace_gentle.png");
    private static boolean confirmButtonPressed;

    private int previousFlameIndex = 4;
    private int x;
    private int y;

    private ArrayList<Integer> strengthList = new ArrayList<Integer>();

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
        // Do nothing you fool!
        return false;
    }

    public EtherFurnaceCraftingScreen(EtherFurnaceCraftingContainer screenContainer, PlayerInventory playerInventory, ITextComponent titleIn) {
        super(screenContainer, playerInventory, titleIn);
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 345;
        this.ySize = 155;
        this.container.setFlameIndex(4);
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void init() {
        super.init();
        this.x = (this.width - this.xSize) / 2;
        this.y = (this.height - this.ySize) / 2;
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground();

        if (!this.confirmButtonPressed) {
            super.render(mouseX, mouseY, partialTicks);
        } else {
            int i = this.guiLeft;
            int j = this.guiTop;
            this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
            MinecraftForge.EVENT_BUS.post(new GuiContainerEvent.DrawBackground(this, mouseX, mouseY));
            RenderSystem.disableRescaleNormal();
            RenderSystem.disableDepthTest();
            super.render(mouseX, mouseY, partialTicks);
            RenderSystem.pushMatrix();
            RenderSystem.translatef((float) i, (float) j, 0.0F);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableRescaleNormal();
            this.hoveredSlot = null;
            int k = 240;
            int l = 240;
            RenderSystem.glMultiTexCoord2f(33986, 240.0F, 240.0F);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawGuiContainerForegroundLayer(mouseX, mouseY);
            MinecraftForge.EVENT_BUS.post(new GuiContainerEvent.DrawForeground(this, mouseX, mouseY));
            RenderSystem.popMatrix();
            RenderSystem.enableDepthTest();
        }

        this.renderHoveredToolTip(mouseX, mouseY);
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
        this.blit(x + 184, y + 36, 0, 0, 148, 91);
        this.minecraft.getTextureManager().bindTexture(CYLINDER_GAUGE_TEXTURE);
        this.blit(x + 169, y + 36, 0, 0, 14, 91);
        int flameIndex = this.container.getFlameIndex();
        switch(flameIndex) {
            case 0:
                this.minecraft.getTextureManager().bindTexture(ETHER_FURNACE_STRONG_TEXTURE);
                this.blit(x + 75, y + 18, 0, 0, 65, 125);
                break;
            case 1:
                this.minecraft.getTextureManager().bindTexture(ETHER_FURNACE_MEDIUM_TEXTURE);
                this.blit(x + 75, y + 18, 0, 0, 65, 125);
                break;
            case 2:
                this.minecraft.getTextureManager().bindTexture(ETHER_FURNACE_GENTLE_TEXTURE);
                this.blit(x + 75, y + 18, 0, 0, 65, 125);
                break;
            case 3:
                if (previousFlameIndex == 0) {
                    this.minecraft.getTextureManager().bindTexture(ETHER_FURNACE_STRONG_TEXTURE);
                    this.blit(x + 75, y + 18, 0, 0, 65, 125);
                } else if (previousFlameIndex == 1) {
                    this.minecraft.getTextureManager().bindTexture(ETHER_FURNACE_MEDIUM_TEXTURE);
                    this.blit(x + 75, y + 18, 0, 0, 65, 125);
                } else if (previousFlameIndex == 2) {
                    this.minecraft.getTextureManager().bindTexture(ETHER_FURNACE_GENTLE_TEXTURE);
                    this.blit(x + 75, y + 18, 0, 0, 65, 125);
                } else {
                    this.minecraft.getTextureManager().bindTexture(ETHER_FURNACE_START_TEXTURE);
                    this.blit(x + 75, y + 18, 0, 0, 65, 125);
                }
            case 4:
                this.minecraft.getTextureManager().bindTexture(ETHER_FURNACE_START_TEXTURE);
                this.blit(x + 75, y + 18, 0, 0, 65, 125);
                break;
        }
        previousFlameIndex = flameIndex;
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        final String strongFlameColorCode = "\u00A74";
        final String mediumFlameColorCode = "\u00A79";
        final String gentleFlameColorCode = "\u00A7a";

        String flameColorCode = "\u00A7f";
        int flameIndex = this.container.getFlameIndex();
        switch(flameIndex) {
            case 0:
                flameColorCode = strongFlameColorCode;
                break;
            case 1:
                flameColorCode = mediumFlameColorCode;
                break;
            case 2:
                flameColorCode = gentleFlameColorCode;
        }
        if (flameIndex < 4) {
            this.minecraft.getTextureManager().bindTexture(CRAFTING_UI_ELEMENTS_TEXTURE);
            this.blit(75, 60, 0, 204, 66, 9);
            this.font.drawString(flameColorCode + this.flames[flameIndex] + flameColorCode, 77.0f, 61.0f, 4210752);
        }
        ItemStack[] itemStacks = this.container.getCurrentSlotStacks();
        ArrayList<Integer> startXList = new ArrayList<Integer>();
        ArrayList<Integer> startYList = new ArrayList<Integer>();
        ArrayList<String> colorCodes = new ArrayList<String>();

        boolean shouldDisableSlot = false;
        if (this.container instanceof EtherFurnaceCraftingContainer) {
            EtherFurnaceCraftingContainer container = (EtherFurnaceCraftingContainer) this.container;
            ArrayList<String> attributeList = container.getAttributes();
            strengthList = container.getStrengths();
            ArrayList<Integer> amountList = container.getAmountList();
            for (String element : container.getElements()) {
                switch (element) {
                    case "Fire":
                        startXList.add(61);
                        startYList.add(150);
                        colorCodes.add("\u00A74");
                        break;
                    case "Water":
                        startXList.add(49);
                        startYList.add(159);
                        colorCodes.add("\u00A71");
                        break;
                    case "Electric":
                        startXList.add(37);
                        startYList.add(168);
                        colorCodes.add("\u00A7e");
                        break;
                    case "Ice":
                        startXList.add(25);
                        startYList.add(177);
                        colorCodes.add("\u00A7b");
                        break;
                    case "Wind":
                        startXList.add(13);
                        startYList.add(186);
                        colorCodes.add("\u00A7a");
                        break;
                    case "Earth":
                        startXList.add(1);
                        startYList.add(195);
                        colorCodes.add("\u00A76");
                        break;
                }
            }
            int numAttributes = attributeList.size();
            if (numAttributes >= 9) {
                numAttributes = 9;
                shouldDisableSlot = true;
            }
            this.container.disableSlots(shouldDisableSlot);
            final String whiteColorCode = "\u00A7f";
            this.font.drawString(whiteColorCode + "Quality" + whiteColorCode, (float) 197, (float) 39, 4210752);
            this.font.drawString(whiteColorCode + "Strength" + whiteColorCode, (float) 283, (float) 39, 4210752);
            this.font.drawString(whiteColorCode + this.container.getCylinderCounter() + whiteColorCode, (float) 173, (float) 39, 4210752);
            boolean hasDrawHeat = false;
            for (int i = 0; i < numAttributes; i++) {
                this.minecraft.getTextureManager().bindTexture(CRAFTING_UI_ELEMENTS_TEXTURE);
                this.blit(187, 50 + 8 * i, startXList.get(i), startYList.get(i), 9, 8);
                this.font.drawString(colorCodes.get(i) + attributeList.get(i) + colorCodes.get(i), (float) 197, (float) 50 + 8 * i, 4210752);

                String heatColorCode = "\u00A7f";
                    if (strengthList.get(i) >= 300) {
                        heatColorCode = "\u00A74";
                        if (!hasDrawHeat) {
                            this.font.drawString(heatColorCode + "MEGA HEAT" + heatColorCode, (float) 256, (float) 25, 4210752);
                            hasDrawHeat = true;
                        }
                    } else if (strengthList.get(i) >= 200) {
                        heatColorCode = "\u00A76";
                        if (!hasDrawHeat) {
                            this.font.drawString(heatColorCode + "  HEAT  " + heatColorCode, (float) 258, (float) 25, 4210752);
                            hasDrawHeat = true;
                        }
                    } else if (strengthList.get(i) >= 100) {
                        heatColorCode = "\u00A73";
                    }

                if (this.container.getIsFever()) {
                    String feverColorCode = "\u00A7e";
                    this.font.drawString(feverColorCode + "  FEVER  " + feverColorCode, (float) 192, (float) 25, 4210752);
                }

                this.font.drawString(heatColorCode + strengthList.get(i) + "%" + heatColorCode, (float) 284, (float) 50 + 8 * i, 4210752);
                final String lightBlueColorCode = "\u00A7b";
                if (amountList != null && amountList.size() > 0 && i < amountList.size() && amountList.get(i) != null && amountList.get(i) > 0) {
                    this.font.drawString(lightBlueColorCode + amountList.get(i) + lightBlueColorCode, (float) 309, (float) 50 + 8 * i, 4210752);
                    this.minecraft.getTextureManager().bindTexture(CRAFTING_UI_ELEMENTS_TEXTURE);
                    this.blit(321, 50 + 8 * i,0, 139, 8, 7);
                }
                int inversePercentage = 100 - this.container.getCylinderGauge();
                int cylinderGaugePixels = (int) (inversePercentage * 0.01 * 72);
                this.minecraft.getTextureManager().bindTexture(CRAFTING_UI_ELEMENTS_TEXTURE);
                this.blit(175, 53 + cylinderGaugePixels, 0, 66 + 66 - (72 - cylinderGaugePixels), 4, (72 - cylinderGaugePixels));
                int numGemAttributes = 0;
                if (strengthList.get(i) >= 100) {
                    numGemAttributes++;
                }
                if (flameIndex == 3 && strengthList.get(i) < 100 && this.container.getButtonCounter() < numAttributes - numGemAttributes) {
                    final int index = i;
                    this.container.addButtonCounter();
                    if (i % 2 == 0) {
                        this.addButton(new ImageButton(x + 186, y + 50 + 8 * i, 148, 8, 0, 231, 8, CRAFTING_UI_ELEMENTS_TEXTURE, (onPressed) -> {
                            this.container.createCylinder(index);
                            if (this.container.getCylinderCounter() < 1 || this.container.getOriginalCylinderCounter() - this.container.getButtonCounter() == this.container.getCylinderCounter()) {
                                this.container.resetButtonCounter();
                                this.buttons.remove(index);
                                this.container.getTileEntity().setShouldOpen(true);
                            }
                        }));
                    } else {
                        this.addButton(new ImageButton(x + 186, y + 50 + 8 * i, 148, 8, 0, 214, 8, CRAFTING_UI_ELEMENTS_TEXTURE, (onPressed) -> {
                            this.container.createCylinder(index);
                            if (this.container.getCylinderCounter() < 1 || this.container.getOriginalCylinderCounter() - this.container.getButtonCounter() == this.container.getCylinderCounter()) {
                               this.container.resetButtonCounter();
                               this.buttons.remove(index);
                               this.container.getTileEntity().setShouldOpen(true);
                            }
                        }));
                    }
                }
                if (flameIndex == 4) {
                    for (Widget button : buttons) {
                        buttons.remove(button);
                    }
                }
            }
            if (flameIndex == 3 && this.container.getButtonCounter() == 0) {
                this.container.getTileEntity().setShouldOpen(true);
            }
        }
    }
}
