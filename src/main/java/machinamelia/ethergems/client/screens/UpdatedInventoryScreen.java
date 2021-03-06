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

import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
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
import machinamelia.ethergems.common.items.armor.material.SlottedArmor;
import machinamelia.ethergems.common.items.weapon.SlottedAxe;
import machinamelia.ethergems.common.items.weapon.SlottedSword;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.server.OpenCrystalInventoryMessage;
import machinamelia.ethergems.common.network.server.PutGemsInInventoryMessage;
import machinamelia.ethergems.common.network.server.SendArmorGemToServerMessage;
import static machinamelia.ethergems.common.container.EtherFurnaceContainer.writeItemStacksToTag;

import static machinamelia.ethergems.common.container.EtherFurnaceContainer.readItemStacksFromTag;


@OnlyIn(Dist.CLIENT)
public class UpdatedInventoryScreen extends InventoryScreen {

    private boolean crystalButtonClicked = false;
    private boolean widthTooNarrow;
    private static final ResourceLocation CRYSTAL_BUTTON_TEXTURE = new ResourceLocation(EtherGems.MOD_ID, "textures/gui/crystal_button.png");
    private static PlayerEntity player;
    private ItemStack[] gems = null;

    @OnlyIn(Dist.CLIENT)
    public UpdatedInventoryScreen(PlayerEntity player) {
        super(player);
        this.player = player;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void init() {
        super.init();
        this.addButton(new ImageButton(this.guiLeft + 128, this.height / 2 - 22, 20, 18, 0, 0, 19, CRYSTAL_BUTTON_TEXTURE, (onPressed) -> {
            ((ImageButton)onPressed).setPosition(this.guiLeft + 128, this.height / 2 - 22);
            OpenCrystalInventoryMessage openCrystalInventoryMessage = new OpenCrystalInventoryMessage();
            NetworkHandler.simpleChannel.sendToServer(openCrystalInventoryMessage);
        }));
    }

    @Override
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        if (slotId > 0) {
            if (slotIn != null && slotIn.getHasStack() && (slotIn.getStack().getItem() instanceof SlottedSword || slotIn.getStack().getItem() instanceof SlottedAxe)) {
                ItemStack weapon = slotIn.getStack();
                LazyOptional<ISlottedWeapon> weaponCapability = weapon.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                ISlottedWeapon weaponInstance = weaponCapability.orElse(new SlottedWeaponInstance());
                gems = new ItemStack[weaponInstance.getSlots()];
                for (int i = 0; i < gems.length; i++) {
                    gems[i] = weaponInstance.getGem(i);
                }
                slotIn.putStack(ItemStack.EMPTY);
            }
            super.handleMouseClick(slotIn, slotId, mouseButton, type);
            if (slotIn != null && slotIn.getHasStack() && slotIn.getStack() != null && slotIn.getStack().getItem() instanceof ArmorItem) {
                ItemStack armor = slotIn.getStack();
                LazyOptional<ISlottedArmor> armorCapability = armor.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
                ISlottedArmor armorInstance = armorCapability.orElse(new SlottedArmorInstance());
            }
            if (slotIn != null && slotIn.getHasStack() && slotIn.getStack() != null && slotIn.getStack().getItem() instanceof SwordItem) {
                ItemStack weapon = slotIn.getStack();
                LazyOptional<ISlottedWeapon> weaponCapability = weapon.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                ISlottedWeapon weaponInstance = weaponCapability.orElse(new SlottedWeaponInstance());
            }
            if (slotId < 9 && slotId > 4) {
                CompoundNBT compoundNBT = (CompoundNBT) this.player.getPersistentData().get("gem_inventory");
                ItemStack[] items = new ItemStack[44];
                if (compoundNBT != null) {
                    ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                    readItemStacksFromTag(items, listNBT);
                }
                boolean isRemoving = this.player.getPersistentData().getBoolean("isRemoving");
                boolean hasPlaced = false;
                if (slotIn.getStack().getItem() instanceof SlottedArmor) {
                    ItemStack armor = slotIn.getStack();
                    LazyOptional<ISlottedArmor> armorCapability = armor.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
                    ISlottedArmor armorInstance = armorCapability.orElse(new SlottedArmorInstance());
                    if (armorInstance.getGem() == null || armorInstance.getGem() != null && armorInstance.getGem().getItem().equals(Items.AIR)) {
                        if (items[(slotId - 5) + 37] != null && !items[(slotId - 5) + 37].getItem().equals(Items.AIR)) {
                            armorInstance.setGem(items[(slotId - 5) + 37]);
                            SendArmorGemToServerMessage sendArmorGemToServerMessage = new SendArmorGemToServerMessage(items[(slotId - 5) + 37], (slotId - 5), true);
                            NetworkHandler.simpleChannel.sendToServer(sendArmorGemToServerMessage);
                        }
                        if (isRemoving) {
                            isRemoving = false;
                        }
                    } else if (items[(slotId - 5) + 37] != null && items[(slotId - 5) + 37].getItem() == null || items[(slotId - 5) + 37].getItem() != null && items[(slotId - 5) + 37].getItem().equals(Items.AIR)) {
                        items[(slotId - 5) + 37] = armorInstance.getGem();
                    }
                } else {
                    ItemStack[] gems = new ItemStack[1];
                    gems[0] = items[(slotId - 5) + 37];
                    isRemoving = true;
                    if (hasPlaced) {
                        PutGemsInInventoryMessage putGemsInInventoryMessage = new PutGemsInInventoryMessage(gems);
                        NetworkHandler.simpleChannel.sendToServer(putGemsInInventoryMessage);
                        this.player.getPersistentData().putBoolean("isRemoving", isRemoving);
                    }
                }
            }
            if (slotIn != null && slotIn.getHasStack() && (slotIn.getStack().getItem() instanceof SlottedSword || slotIn.getStack().getItem() instanceof SlottedAxe)) {
                if (this.gems != null) {
                    ItemStack weapon = slotIn.getStack();
                    LazyOptional<ISlottedWeapon> weaponCapability = weapon.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                    ISlottedWeapon weaponInstance = weaponCapability.orElse(new SlottedWeaponInstance());
                    weaponInstance.setSlots(gems.length);
                    for (int i = 0; i < gems.length; i++) {
                        weaponInstance.setGem(i, gems[i]);
                    }
                }
            }
            if (player.getHeldItemMainhand().getItem() instanceof SlottedSword || player.getHeldItemMainhand().getItem() instanceof SlottedAxe) {
                ItemStack weapon = player.getHeldItemMainhand();
                LazyOptional<ISlottedWeapon> weaponCapability = weapon.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                ISlottedWeapon weaponInstance = weaponCapability.orElse(new SlottedWeaponInstance());
                if (gems != null) {
                    CompoundNBT compoundNBT = (CompoundNBT) player.getPersistentData().get("gem_inventory");
                    ItemStack[] items = new ItemStack[44];
                    if (compoundNBT != null) {
                        ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                        readItemStacksFromTag(items, listNBT);
                    }
                    if (weaponInstance != null) {
                        for (int i = 0; i < weaponInstance.getSlots(); i++) {
                            items[41 + i] = weaponInstance.getGem(i);
                        }
                    }
                    compoundNBT = new CompoundNBT();
                    compoundNBT.put("Items", writeItemStacksToTag(items, 44));
                    compoundNBT.putByte("size", (byte) 44);
                    player.getPersistentData().put("gem_inventory", compoundNBT);
                }
            }
        } else {
            super.handleMouseClick(slotIn, slotId, mouseButton, type);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tick() {
        ImageButton button = (ImageButton) this.buttons.get(1);
        button.setPosition(this.guiLeft + 128, this.height / 2 - 22);
    }
}
