package machinamelia.ethergems.common.network;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import machinamelia.ethergems.common.capabilities.armor.ISlottedArmor;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorProvider;
import machinamelia.ethergems.common.capabilities.weapons.ISlottedWeapon;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponProvider;
import machinamelia.ethergems.common.container.*;
import machinamelia.ethergems.common.events.AttackEvents;
import machinamelia.ethergems.common.init.ContainerInit;
import machinamelia.ethergems.common.items.weapon.SlottedAxe;
import machinamelia.ethergems.common.items.weapon.SlottedSword;
import machinamelia.ethergems.common.network.client.RenderParticleOnClientMessage;
import machinamelia.ethergems.common.network.server.*;

import java.util.*;
import java.util.function.Supplier;

import static machinamelia.ethergems.common.container.EtherFurnaceContainer.readItemStacksFromTag;
import static machinamelia.ethergems.common.container.EtherFurnaceContainer.writeItemStacksToTag;

public class MessageHandlerOnServer {
    public static boolean isThisProtocolAcceptedByServer(String protocolVersion) {
        return NetworkHandler.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }
    public static void onMessageReceived(final OpenCrystalInventoryMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("OpenCrystalInventoryMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("OpenCrystalInventoryMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when OpenCrystalInventoryMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(OpenCrystalInventoryMessage message, ServerPlayerEntity sendingPlayer)
    {
        NetworkHooks.openGui(sendingPlayer, new ContainerProvider(new StringTextComponent("Crystal Inventory"), (i, inv, p) -> new CrystalInventoryContainer(ContainerInit.CRYSTAL_INVENTORY_CONTAINER.get(), i, sendingPlayer)));
        return;
    }
    public static void onMessageReceived(final OpenGemInventoryMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("OpenGemInventoryMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("OpenGemInventoryMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when OpenGemInventoryMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(OpenGemInventoryMessage message, ServerPlayerEntity sendingPlayer)
    {
        NetworkHooks.openGui(sendingPlayer , new ContainerProvider(new StringTextComponent("Gem Inventory"), (i, inv, p) -> new GemInventoryContainer(ContainerInit.GEM_INVENTORY_CONTAINER.get(), i, sendingPlayer)));
        return;
    }
    public static void onMessageReceived(final OpenOptionsContainerMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("OpenOptionsContainerMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("OpenOptionsContainerMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when OpenOptionsContainerMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(OpenOptionsContainerMessage message, ServerPlayerEntity sendingPlayer)
    {
        if (sendingPlayer.openContainer instanceof EtherFurnaceInventoryContainer) {
            ItemStack[] items = message.getItems();
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(items, 44));
            compoundNBT.putByte("size", (byte) 44);
            sendingPlayer.getPersistentData().put("crystal_inventory", compoundNBT);
            EtherFurnaceInventoryContainer inventoryContainer = (EtherFurnaceInventoryContainer) sendingPlayer.openContainer;
            inventoryContainer.getTileEntity().setIsLocked(true);
            NetworkHooks.openGui(sendingPlayer, new ContainerProvider(new StringTextComponent("Test"), (i, inv, p) -> new EtherFurnaceOptionsContainer(i, inv, inventoryContainer.getTileEntity())), inventoryContainer.getTileEntity().getPos());
        }
        return;
    }
    public static void onMessageReceived(final OpenCraftingContainerMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("OpenCraftingContainerMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("OpenCraftingContainerMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when OpenCraftingContainerMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(OpenCraftingContainerMessage message, ServerPlayerEntity sendingPlayer)
    {
        if (sendingPlayer.openContainer instanceof EtherFurnaceOptionsContainer) {
            EtherFurnaceOptionsContainer optionsContainer = (EtherFurnaceOptionsContainer) sendingPlayer.openContainer;
            optionsContainer.getTileEntity().setIsLocked(true);
            NetworkHooks.openGui(sendingPlayer, new ContainerProvider(new StringTextComponent("Test"), (i, inv, p) -> new EtherFurnaceCraftingContainer(i, inv, optionsContainer.getTileEntity())), optionsContainer.getTileEntity().getPos());
        }
        return;
    }

    public static void openGemConfirmContainer(ServerPlayerEntity sendingPlayer) {
        if (sendingPlayer.openContainer instanceof EtherFurnaceCylinderConfirmContainer) {
            boolean shouldClose = false;
            CompoundNBT compoundNBT = (CompoundNBT) sendingPlayer.getPersistentData().get("gem_confirm_inventory");
            ItemStack[] items = new ItemStack[27];
            if (compoundNBT != null) {
                ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                if (listNBT != null) {
                    readItemStacksFromTag(items, listNBT);
                    int counter = 0;
                    for (int i = 0; i < 27; i++) {
                        if (items[i] == null || items[i].getItem().equals(Items.AIR)) {
                            counter++;
                        }
                    }
                    if (counter == 27) {
                        shouldClose = true;
                    }
                }
            }
            EtherFurnaceCylinderConfirmContainer craftingContainer = (EtherFurnaceCylinderConfirmContainer) sendingPlayer.openContainer;
            if (!shouldClose) {
                craftingContainer.getTileEntity().setIsLocked(true);
                NetworkHooks.openGui(sendingPlayer, new ContainerProvider(new StringTextComponent("Test"), (i, inv, p) -> new EtherFurnaceGemConfirmContainer(i, inv, craftingContainer.getTileEntity())), craftingContainer.getTileEntity().getPos());
            } else {
                craftingContainer.getTileEntity().setIsLocked(false);
                sendingPlayer.closeScreen();
            }
        }
    }
    public static void onMessageReceived(final OpenGemConfirmContainerMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("OpenGemConfirmContainerMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("OpenGemConfirmContainerMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when OpenGemConfirmContainerMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(OpenGemConfirmContainerMessage message, ServerPlayerEntity sendingPlayer)
    {
        openGemConfirmContainer(sendingPlayer);
        return;
    }
    public static void openCylinderConfirmContainer(ServerPlayerEntity sendingPlayer) {
        if (sendingPlayer.openContainer instanceof EtherFurnaceCraftingContainer) {
            EtherFurnaceCraftingContainer craftingContainer = (EtherFurnaceCraftingContainer) sendingPlayer.openContainer;
            craftingContainer.getTileEntity().setIsLocked(true);
            NetworkHooks.openGui(sendingPlayer, new ContainerProvider(new StringTextComponent("Test"), (i, inv, p) -> new EtherFurnaceCylinderConfirmContainer(i, inv, craftingContainer.getTileEntity())), craftingContainer.getTileEntity().getPos());
        }
    }
    public static void onMessageReceived(final OpenCylinderConfirmContainerMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("OpenCylinderConfirmContainerMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("OpenCylinderConfirmContainerMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when OpenCylinderConfirmContainerMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(OpenCylinderConfirmContainerMessage message, ServerPlayerEntity sendingPlayer)
    {
        openCylinderConfirmContainer(sendingPlayer);
        return;
    }
    public static void onMessageReceived(final PutGemsInInventoryMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PutGemsInInventoryMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("PutGemsInInventoryMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when PutGemsInInventoryMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(PutGemsInInventoryMessage message, ServerPlayerEntity sendingPlayer)
    {
        ItemStack[] gems = message.getItems();
        if (gems != null) {
            CompoundNBT compoundNBT = (CompoundNBT) sendingPlayer.getPersistentData().get("gem_inventory");
            ItemStack[] items = new ItemStack[44];
            if (compoundNBT != null) {
                ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                readItemStacksFromTag(items, listNBT);
            }
            for (ItemStack gem : gems) {
                boolean itemPlaced = false;
                for (int i = 0; i < 44; i++) {
                    if (!itemPlaced && items[i] != null && items[i].getItem().equals(Items.AIR)) {
                        items[i] = gem;
                        itemPlaced = true;
                    } else if (!itemPlaced && items[i] == null) {
                        items[i] = gem;
                        itemPlaced = true;
                    }
                }
            }
            compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(items, 44));
            compoundNBT.putByte("size", (byte) 44);
            sendingPlayer.getPersistentData().put("gem_inventory", compoundNBT);

            ItemStack[] empty = new ItemStack[27];
            compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(empty, 27));
            compoundNBT.putByte("size", (byte) 27);
            sendingPlayer.getPersistentData().put("gem_confirm_inventory", compoundNBT);
        }
    }
    public static void onMessageReceived(final AddGemsToConfirmMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("AddGemsToConfirmMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("AddGemsToConfirmMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when AddGemsToConfirmMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(AddGemsToConfirmMessage message, ServerPlayerEntity sendingPlayer)
    {
        ItemStack[] items = message.getItems();
        if (items != null) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(items, 36));
            compoundNBT.putByte("size", (byte) 36);
            sendingPlayer.getPersistentData().put("gem_confirm_inventory", compoundNBT);
        }
    }
    public static void onMessageReceived(final AddCylindersToConfirmMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("AddCylindersToConfirmMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("AddCylindersToConfirmMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when AddCylindersToConfirmMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(AddCylindersToConfirmMessage message, ServerPlayerEntity sendingPlayer)
    {
        ItemStack[] items = message.getItems();
        if (items != null) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(items, 9));
            compoundNBT.putByte("size", (byte) 9);
            sendingPlayer.getPersistentData().put("cylinder_confirm_inventory", compoundNBT);
            boolean shouldSkip = false;
            int counter = 0;
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null || items[i].getItem().equals(Items.AIR)) {
                    counter++;
                }
            }
            if (counter == 9) {
                shouldSkip = true;
            }
            if (shouldSkip) {
                openGemConfirmContainer(sendingPlayer);
            }
        }
        if (message.shouldOpen) {
            openCylinderConfirmContainer(sendingPlayer);
        }
    }
    public static void onMessageReceived(final PutCrystalsInInventoryMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("PutCrystalsInInventoryMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("PutCrystalsInInventoryMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when PutCrystalsInInventoryMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(PutCrystalsInInventoryMessage message, ServerPlayerEntity sendingPlayer)
    {
        ItemStack[] crystals = message.getItems();
        if (crystals != null) {
            CompoundNBT compoundNBT = (CompoundNBT) sendingPlayer.getPersistentData().get("crystal_inventory");
            ItemStack[] items = new ItemStack[44];
            if (compoundNBT != null) {
                ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                readItemStacksFromTag(items, listNBT);
            }
            for (ItemStack crystal : crystals) {
                boolean itemPlaced = false;
                for (int i = 0; i < 42; i++) {
                    if (!itemPlaced && items[i] != null && items[i].getItem().equals(Items.AIR)) {
                        items[i] = crystal;
                        itemPlaced = true;
                    } else if (!itemPlaced && items[i] == null) {
                        items[i] = crystal;
                        itemPlaced = true;
                    }
                }
            }
            compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(items, 44));
            compoundNBT.putByte("size", (byte) 44);
            sendingPlayer.getPersistentData().put("crystal_inventory", compoundNBT);
        }
    }
    public static void onMessageReceived(final SendMessageToAllPlayersMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("SendMessageToAllPlayersMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("SendMessageToAllPlayersMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when SendMessageToAllPlayersMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(SendMessageToAllPlayersMessage message, ServerPlayerEntity sendingPlayer)
    {
       String string = message.getMessage();
       List<ServerPlayerEntity> players = sendingPlayer.getServerWorld().getPlayers();
       for (ServerPlayerEntity player : players) {
           player.sendMessage(new StringTextComponent(string));
       }

        return;
    }
    public static void onMessageReceived(final SendArmorGemToServerMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("SendArmorGemToServerMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("SendArmorGemToServerMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when SendArmorGemToServerMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(SendArmorGemToServerMessage message, ServerPlayerEntity sendingPlayer)
    {
        if (message.index < 4) {
            Iterator<ItemStack> armorList = sendingPlayer.getArmorInventoryList().iterator();
            for (int i = 0; i < 4; i++) {
                ItemStack armor = armorList.next();
                if (message.index == i) {
                    LazyOptional<ISlottedArmor> armorCapability = armor.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
                    try {
                        ISlottedArmor armorInstance = armorCapability.orElseThrow(IllegalStateException::new);
                        armorInstance.setGem(message.armorGem);
                    } catch (IllegalStateException e) {
                    }
                }
            }
        } else {
            ItemStack weapon = sendingPlayer.getHeldItemMainhand();
            if (weapon.getItem() instanceof SlottedSword || weapon.getItem() instanceof SlottedAxe) {
                LazyOptional<ISlottedWeapon> weaponCapability = weapon.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                try {
                    ISlottedWeapon weaponInstance = weaponCapability.orElseThrow(IllegalStateException::new);
                    if (weaponInstance.getSlots() > message.index - 4) {

                        if (!message.isCompatible) {
                            weaponInstance.setIsWrongGem(true);
                        } else {
                            weaponInstance.setGem(message.index - 4, message.armorGem);
                        }
                    }
                }  catch (IllegalStateException e) {
                }
            }
        }
        return;
    }
    public static void onMessageReceived(final SendParticleToServerWorldMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("SendParticleToServerWorldMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("SendParticleToServerWorldMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when SendParticleToServerWorldMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(SendParticleToServerWorldMessage message, ServerPlayerEntity sendingPlayer)
    {
        List<ServerPlayerEntity> players = sendingPlayer.getServerWorld().getPlayers();
        for (ServerPlayerEntity player : players) {
            RenderParticleOnClientMessage msg = new RenderParticleOnClientMessage(player.getUniqueID().toString(), message.type, message.x, message.y, message.z, message.xSpeed, message.ySpeed, message.zSpeed);
            NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> player), msg);
        }

        return;
    }
    public static void onMessageReceived(final SendDoubleAttackToServerMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("SendDoubleAttackToServerMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("SendDoubleAttackToServerMessage was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = ctx.getSender();
        if (sendingPlayer == null) {
            LOGGER.warn("EntityPlayerMP was null when SendDoubleAttackToServerMessage was received");
        }

        ctx.enqueueWork(() -> processMessage(message, sendingPlayer));
    }
    static void processMessage(SendDoubleAttackToServerMessage message, ServerPlayerEntity sendingPlayer)
    {
        Entity entity = sendingPlayer.world.getEntityByID(message.entityId);
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            PlayerEntity player = (PlayerEntity) sendingPlayer;
            AttackEvents.attack(player, livingEntity);
        }

        return;
    }
    private static final Logger LOGGER = LogManager.getLogger();
}
