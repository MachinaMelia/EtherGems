package machinamelia.ethergems.client.screens;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.capabilities.armor.ISlottedArmor;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorInstance;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorProvider;
import machinamelia.ethergems.common.capabilities.weapons.ISlottedWeapon;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponInstance;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponProvider;
import machinamelia.ethergems.common.container.GemInventoryContainer;
import machinamelia.ethergems.common.events.UpdateGemEvents;
import machinamelia.ethergems.common.items.weapon.SlottedAxe;
import machinamelia.ethergems.common.items.weapon.SlottedSword;
import machinamelia.ethergems.common.util.GemHandler;

import java.util.Iterator;

public class GemInventoryScreen extends ContainerScreen<GemInventoryContainer> {

    private static final ResourceLocation CRYSTAL_INVENTORY_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crystal_inventory.png");
    private static final ResourceLocation CRYSTAL_INVENTORY_UI_ELEMENTS = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crystal_inventory_ui_elements.png");
    private static final ResourceLocation EQUIPMENT_INVENTORY_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/equipment_no_gems.png");
    private static final ResourceLocation EQUIPMENT_INVENTORY_UI_ELEMENTS = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/equipment_ui_elements.png");
    private static final ResourceLocation ATTRIBUTE_DISPLAY_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/inventory_attribute_display.png");

    private boolean crystalButtonPressed = false;
    private PlayerEntity player;

    public GemInventoryScreen(GemInventoryContainer screenContainer, PlayerInventory playerInventory, ITextComponent titleIn) {
        super(screenContainer, playerInventory, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 320;
        this.imageHeight = 190;
        this.player = playerInventory.player;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void init() {
        super.init();
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.addButton(new ImageButton(x + 143, y + 7, 28, 29, 30, 2, 0, CRYSTAL_INVENTORY_UI_ELEMENTS, (onPressed) -> {
            this.crystalButtonPressed = true;
            this.getMenu().openGui();
            this.crystalButtonPressed = false;
        }));
        this.addButton(new ImageButton(x + 115, y + 7, 28, 32, 0, 32, 0, CRYSTAL_INVENTORY_UI_ELEMENTS, (onPressed) -> {
        }));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(MatrixStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    public void renderBackground(MatrixStack matrixStack) {
        super.renderBackground(matrixStack);
    }
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void slotClicked(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slotIn, slotId, mouseButton, type);
        if (slotId >= 37) {
            UpdateGemEvents.updateServerPlayerGems(this.player, true);
            if (this.player.level.isClientSide) {
//                renderLabels(matrixStack, 0, 0);
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bind(EQUIPMENT_INVENTORY_UI_ELEMENTS);
        this.blit(matrixStack, 35, 5, 0, 57, 64, 22);
        Iterator<ItemStack> armorList = this.player.getArmorSlots().iterator();
        for (int i = 0; i < 4; i++) {
            ItemStack armor = armorList.next();
            LazyOptional<ISlottedArmor> armorCapability = armor.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
            ISlottedArmor armorInstance = armorCapability.orElse(new SlottedArmorInstance());
            if (!armor.getItem().equals(Items.AIR) && !armorInstance.equals(Items.AIR) && armorInstance.getSlots() > 0) {
                this.minecraft.getTextureManager().bind(EQUIPMENT_INVENTORY_UI_ELEMENTS);
                this.blit(matrixStack, 35, 27 + 20 * (3 - i), 0, 80, 64, 20);
                this.minecraft.getTextureManager().bind(EQUIPMENT_INVENTORY_UI_ELEMENTS);
                this.blit(matrixStack, 37, 28 + 20 * (3 - i), 0, 38, 18, 18);
            }
        }
        ItemStack weapon = this.player.getMainHandItem();
        if (weapon.getItem() instanceof SlottedSword || weapon.getItem() instanceof SlottedAxe) {
            LazyOptional<ISlottedWeapon> weaponCapability = weapon.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
            ISlottedWeapon weaponInstance = weaponCapability.orElse(new SlottedWeaponInstance());

            for (int i = 0; i < weaponInstance.getSlots(); i++) {
                this.minecraft.getTextureManager().bind(EQUIPMENT_INVENTORY_UI_ELEMENTS);
                this.blit(matrixStack, 37 + 20 * i, 7, 0, 38, 18, 18);
            }
        }
        final String[] ATTRIBUTES = { "Bind Resist", "Blaze Attack", "Blaze Plus", "Buff Time Plus", "Chill Defence", "EXP Up", "Slow Resist", "Spike", "Strength Down", "Strength Up", "Weapon Power", "Aquatic Cloak", "Auto-Heal Up", "Damage Heal", "Debuff Resist", "HP Up", "HP Steal", "Poison Defence", "Recovery Up", "Spike Defence", "Unbeatable", "Back Attack Plus", "Double Attack", "First Attack Plus", "Phys Def Down", "Bind", "Blaze Defence", "Chill Attack", "Chill Plus", "Slow", "Aerial Cloak", "Bleed Attack", "Bleed Plus", "Fall Defence", "Good Footing", "Haste", "Quick Step", "Attack Stability", "Attack Plus", "Bleed Defence", "Critical Up", "Debuff Plus", "Earth Cloak", "Muscle Up", "Physical Protect", "Poison Attack", "Poison Plus" };
        int counter = 0;
        for (String attribute : ATTRIBUTES) {
           double fullStrength =  GemHandler.getPlayerGemStrength(this.player, attribute);
           if (fullStrength != 0.0) {
               double max = GemHandler.getMax(attribute);
               String colorCode = "\u00A7f";
               if (max < 0) {
                   if (fullStrength <= max) {
                       fullStrength = max;
                       colorCode = "\u00A7a";
                   }
               } else {
                   if (fullStrength >= max) {
                       fullStrength = max;
                       colorCode = "\u00A7a";
                   }
               }
               String strengthString = fullStrength + "";
               if (GemHandler.isPercentage(attribute)) {
                   strengthString = strengthString.substring(0, strengthString.length() - 2);
                   strengthString  += "%";
               }
               String attributeColor = GemHandler.getColorCode(attribute);
               this.font.draw(matrixStack, attributeColor + attribute + attributeColor + ": " + colorCode + strengthString + colorCode, 2.0f, 121.0f + counter, 4210752);
               counter += 8;
           }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        // Attribute display
        this.minecraft.getTextureManager().bind(ATTRIBUTE_DISPLAY_TEXTURE);
        this.blit(matrixStack, x, y + 115, 0, 0, 112, 67);
        // Crystal Inventory
        this.minecraft.getTextureManager().bind(EQUIPMENT_INVENTORY_TEXTURE);
        this.blit(matrixStack, x + 7, y, 0, 0, 95, 120);
        // Crystal Inventory
        this.minecraft.getTextureManager().bind(CRYSTAL_INVENTORY_TEXTURE);
        this.blit(matrixStack, x + 115, y + 35, 0, 0, 191, 120);

    }
}
