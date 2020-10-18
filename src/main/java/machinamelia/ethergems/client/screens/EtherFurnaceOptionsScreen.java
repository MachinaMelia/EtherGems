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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.capabilities.crystals.CrystalInstance;
import machinamelia.ethergems.common.capabilities.crystals.CrystalProvider;
import machinamelia.ethergems.common.capabilities.crystals.ICrystal;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderInstance;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderProvider;
import machinamelia.ethergems.common.capabilities.cylinders.ICylinder;
import machinamelia.ethergems.common.container.EtherFurnaceOptionsContainer;
import machinamelia.ethergems.common.items.crystals.Crystal;
import machinamelia.ethergems.common.items.cylinders.Cylinder;

import java.util.ArrayList;

public class EtherFurnaceOptionsScreen extends ContainerScreen<EtherFurnaceOptionsContainer> {

    private static boolean confirmButtonPressed;
    private static final ResourceLocation CRYSTAL_SELECTION_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting_crystal_selection.png");
    private static final ResourceLocation ATTRIBUTE_DISPLAY_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting_attribute_display.png");
    private static final ResourceLocation CRAFTING_UI_ELEMENTS = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting_ui_elements.png");
    private static final ResourceLocation CRAFTING_OPTIONS_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crafting2_crafting_options.png");
    private final int[][] engineerAbilities = { { 25, 25, 50 }, { 80, 10, 10 }, { 15, 60, 25 }, { 60, 15, 25 }, { 10, 80, 10 }, { 10, 10, 80 }, { 5, 90, 5 } };
    private final String[] shooterAbilities = { "Fever Plus", "Strong Bonus", "Gentle Bonus", "Combo Master", "Medium Bonus", "Impatient Shooter", "Cylinder Plus" };
    private final String[] shooterDescriptions = { "Gets into a fever more easily.", "Qualities grow stronger with a strong flame", "Cylinder Gauge fills up more with gentle flame", "Qualities grow stronger when flame is constant.", "Qualities grow stronger with a medium flame.", "Gem crafting process happens twice in a row.", "Crafting begins with 3 full cylinder gauges." };
    private final String[] turnString = { "3-5", "5-7", "6-10", "8-13", "10-15" };

    private int shooterIndex = 0;
    private int engineerIndex = 1;
    private int affinityIndex = 0;
    private PlayerEntity player = playerInventory.player;

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
        // Do nothing you fool!
        return false;
    }
    public EtherFurnaceOptionsScreen(EtherFurnaceOptionsContainer screenContainer, PlayerInventory playerInventory, ITextComponent titleIn) {
        super(screenContainer, playerInventory, titleIn);
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 345;
        this.ySize = 155;
        this.shooterIndex = 0;
        this.engineerIndex = 1;
    }

//    @Override
//    public void tick() {
//        if (Minecraft.getInstance().world.isRemote) {
//            this.player.getPersistentData().putInt("crystal_level", this.container.getMaxAffinity());
//        }
//    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void init() {
        super.init();
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.addButton(new ImageButton(x + 58, y + 133, 72, 13, 0, 0, 14, CRAFTING_UI_ELEMENTS, (onPressed) -> {
            ((ImageButton)onPressed).setPosition(x + 58, y + 133);
            this.confirmButtonPressed = true;
            this.container.saveInformation(shooterIndex, engineerIndex, affinityIndex);
            this.container.openGui();
        }));
        // Crafting Turn Left and Right Toggles
        this.addButton(new ImageButton(x + 49, y + 31, 6, 9, 0, 38, 0, CRAFTING_UI_ELEMENTS, (onPressed) -> {
            decrementAffinity();
        }));
        this.addButton(new ImageButton(x + 137, y + 31, 6, 9, 0, 28, 0, CRAFTING_UI_ELEMENTS, (onPressed) -> {
            incrementAffinity();
        }));

        // Shooter Left and Right Toggles
        this.addButton(new ImageButton(x + 1, y + 80, 6, 9, 0, 38, 0, CRAFTING_UI_ELEMENTS, (onPressed) -> {
            decrementShooter();
        }));

        this.addButton(new ImageButton(x + 99, y + 80, 6, 9, 0, 28, 0, CRAFTING_UI_ELEMENTS, (onPressed) -> {
            incrementShooter();
        }));

        // Engineer Left and Right Toggles
        this.addButton(new ImageButton(x + 105, y + 95, 6, 9, 0, 38, 0, CRAFTING_UI_ELEMENTS, (onPressed) -> {
            decrementEngineer();
        }));

        this.addButton(new ImageButton(x + 179, y + 95, 6, 9, 0, 28, 0, CRAFTING_UI_ELEMENTS, (onPressed) -> {
            incrementEngineer();
        }));


        buttons.get(0).changeFocus(false);
    }

    public void decrementEngineer() {
        engineerIndex--;
        if (engineerIndex < 0) {
            engineerIndex = 6;
        }
        if (engineerIndex == shooterIndex) {
            engineerIndex--;
        }
        if (engineerIndex < 0) {
            engineerIndex = 6;
        }
    }

    public void incrementEngineer() {
        engineerIndex++;
        if (engineerIndex >= 7) {
            engineerIndex = 0;
        }
        if (engineerIndex == shooterIndex) {
            engineerIndex++;
        }
        if (engineerIndex >= 7) {
            engineerIndex = 0;
        }
    }

    public void decrementShooter() {
        shooterIndex--;
        if (shooterIndex < 0) {
            shooterIndex = 6;
        }
        if (shooterIndex == engineerIndex) {
            shooterIndex--;
        }
        if (shooterIndex < 0) {
            shooterIndex = 6;
        }
    }

    public void incrementShooter() {
        shooterIndex++;
        if (shooterIndex >= 7) {
            shooterIndex = 0;
        }
        if (shooterIndex == engineerIndex) {
            shooterIndex++;
        }
        if (shooterIndex >= 7) {
            shooterIndex = 0;
        }
    }

    public void decrementAffinity() {
        affinityIndex--;
        if (affinityIndex < 0) {
            affinityIndex = player.getPersistentData().getInt("crystal_level");
        }
    }

    public void incrementAffinity() {
        affinityIndex++;
        if (affinityIndex >= player.getPersistentData().getInt("crystal_level") + 1) {
            affinityIndex = 0;
        }
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
        // Crystal inventory
        this.minecraft.getTextureManager().bindTexture(CRAFTING_OPTIONS_TEXTURE);
        this.blit(x, y + 13, 0, 0, 192, 140);
        // Attribute display
        this.minecraft.getTextureManager().bindTexture(ATTRIBUTE_DISPLAY_TEXTURE);
        this.blit(x + 204, y + 64, 0, 0, 148, 91);
        // Crystal selection
        this.minecraft.getTextureManager().bindTexture(CRYSTAL_SELECTION_TEXTURE);
        this.blit(x + 204, y, 0, 0, 86, 60);
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString("Confirm", 75.0f, 136.0f, 4210752);
        ItemStack[] itemStacks = this.container.getCurrentSlotStacks();
        ArrayList<Integer> startXList = new ArrayList<Integer>();
        ArrayList<Integer> startYList = new ArrayList<Integer>();
        ArrayList<String> colorCodes = new ArrayList<String>();
        ArrayList<String> attributeList = new ArrayList<String>();
        ArrayList<Integer> strengthList = new ArrayList<Integer>();
        boolean shouldDisableSlot = false;
        for (int i = 0; i < itemStacks.length; i++) {
            ItemStack stack = itemStacks[i];
            String attributeString = "";
            String[] attributes = new String[1];
            int[] strengths = new int[1];
            String element = "";
            if (stack != null) {
                if (stack.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = stack.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
                    ICrystal crystal = crystalCapability.orElse(new CrystalInstance());
                    attributeString = crystal.getAttributesCSV();
                    attributes = attributeString.split(",");
                    strengths = crystal.getStrengthArray();
                    element = crystal.getElement();
                } else if (stack.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = stack.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
                    ICylinder cylinder = cylinderCapability.orElse(new CylinderInstance());
                    attributes[0] = cylinder.getAttribute();
                    strengths[0] = cylinder.getStrength();
                    element = cylinder.getElement();
                }
                if (stack.getItem() instanceof Crystal || stack.getItem() instanceof Cylinder) {
                    for (int j = 0; j < attributes.length; j++) {
                        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                        if (attributes[j] != null) {
                            if (element != null) {
                                int sameAttributeIndex = -1;
                                for (int k = 0; k < attributeList.size(); k++) {
                                    if (attributes[j].equals(attributeList.get(k))) {
                                        sameAttributeIndex = k;
                                    }
                                }
                                if (sameAttributeIndex == -1) {
                                    attributeList.add(attributes[j]);
                                    strengthList.add(strengths[j]);
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
                                } else {
                                    int currentStrength = strengthList.get(sameAttributeIndex);
                                    strengthList.set(sameAttributeIndex, currentStrength + strengths[j]);
                                    if (strengthList.get(sameAttributeIndex) >= 100) {
                                        shouldDisableSlot = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        int numAttributes = attributeList.size();
        if (numAttributes >= 9) {
            numAttributes = 9;
            shouldDisableSlot = true;
        }
        this.container.disableSlots(shouldDisableSlot);
        final String whiteColorCode = "\u00A7f";
        this.font.drawString(whiteColorCode + "Quality" + whiteColorCode, (float) 218, (float) 67, 4210752);
        this.font.drawString(whiteColorCode + "Strength" + whiteColorCode, (float) 295, (float) 67, 4210752);
        this.font.drawString("Craft Turns", (float) 66, (float) 27, 4210752);
        this.font.drawString("Shooter", (float) 28, (float) 52, 4210752);
        this.font.drawString("Engineer", (float) 125, (float) 52, 4210752);
        this.font.drawString(turnString[affinityIndex], (float) 90, (float) 37, 4210752);
        if (shooterIndex >= 0 && shooterIndex < 7) {
            final String shooterColorCode = "\u00A7e";
            final String boldCode = "\u00A7l";
            this.font.drawString(shooterColorCode + shooterAbilities[shooterIndex] + shooterColorCode, (float) 11, (float) 81, 4210752);
            this.font.drawSplitString(shooterDescriptions[shooterIndex], 11, 92, 80,4210752);
        }
        final String strongFlameColorCode = "\u00A74";
        final String mediumFlameColorCode = "\u00A79";
        final String gentleFlameColorCode = "\u00A7a";
        if (engineerIndex >= 0 && engineerIndex < 7) {
            this.font.drawString(strongFlameColorCode + "Strong Flame" + strongFlameColorCode, (float) 114, (float) 71, 4210752);
            this.font.drawString(strongFlameColorCode + engineerAbilities[engineerIndex][0] + "%" + strongFlameColorCode, (float) 140, (float) 81, 4210752);
            this.font.drawString(mediumFlameColorCode + "Medium Flame" + mediumFlameColorCode, (float) 114, (float) 91, 4210752);
            this.font.drawString(mediumFlameColorCode + engineerAbilities[engineerIndex][1] + "%" + mediumFlameColorCode, (float) 140, (float) 101, 4210752);
            this.font.drawString(gentleFlameColorCode + "Gentle Flame" + gentleFlameColorCode, (float) 114, (float) 111, 4210752);
            this.font.drawString(gentleFlameColorCode + engineerAbilities[engineerIndex][2] + "%" + gentleFlameColorCode, (float) 140, (float) 121, 4210752);
        }
        for (int i = 0; i < numAttributes; i++) {
            this.minecraft.getTextureManager().bindTexture(CRAFTING_UI_ELEMENTS);
            this.blit(207, 78 + 8 * i, startXList.get(i), startYList.get(i), 9, 8);
            this.font.drawString(colorCodes.get(i) + attributeList.get(i) + colorCodes.get(i), (float) 218, (float) 78 + 8 * i, 4210752);
            this.font.drawString(colorCodes.get(i) + strengthList.get(i) + "%" + colorCodes.get(i), (float) 315, (float) 78 + 8 * i, 4210752);
        }
    }
}
