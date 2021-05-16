package machinamelia.ethergems.common.network;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import machinamelia.ethergems.common.items.armor.material.SlottedArmor;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import machinamelia.ethergems.common.capabilities.armor.ISlottedArmor;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorInstance;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorProvider;
import machinamelia.ethergems.common.capabilities.weapons.ISlottedWeapon;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponInstance;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponProvider;
import machinamelia.ethergems.common.init.EffectInit;
import machinamelia.ethergems.common.init.ParticleInit;
import machinamelia.ethergems.common.items.weapon.SlottedAxe;
import machinamelia.ethergems.common.items.weapon.SlottedSword;
import machinamelia.ethergems.common.network.client.RenderParticleOnClientMessage;
import machinamelia.ethergems.common.network.client.SendAffinityToClientPlayer;
import machinamelia.ethergems.common.network.client.SendArmorGemToClientMessage;
import machinamelia.ethergems.common.network.client.SendPotionEffectToClientMessage;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class MessageHandlerOnClient {
    public static boolean isThisProtocolAcceptedByClient(String protocolVersion) {
        return NetworkHandler.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }
    public static void onMessageReceived(final SendAffinityToClientPlayer message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("SendAffinityToClientPlayer received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("SendAffinityToClientPlayer was invalid" + message.toString());
            return;
        }

        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("SendAffinityToClientPlayer context could not provide a ClientWorld.");
            return;
        }

        ctx.enqueueWork(() -> processMessage(clientWorld.get(), message));
    }
    static void processMessage(ClientWorld world, SendAffinityToClientPlayer message)
    {
        UUID uuid = UUID.fromString(message.uuid);
        PlayerEntity player = world.getPlayerByUUID(uuid);
        player.getPersistentData().putInt("crystal_level", message.maxAffinity);
        return;
    }
    public static void onMessageReceived(final SendArmorGemToClientMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("SendArmorGemToClientMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("SendArmorGemToClientMessage was invalid" + message.toString());
            return;
        }

        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("SendArmorGemToClientMessage context could not provide a ClientWorld.");
            return;
        }

        ctx.enqueueWork(() -> processMessage(clientWorld.get(), message));
    }
    static void processMessage(ClientWorld world, SendArmorGemToClientMessage message)
    {
        UUID uuid = UUID.fromString(message.uuid);
        PlayerEntity player = world.getPlayerByUUID(uuid);
        if (message.index < 4) {
            ItemStack armor = player.inventory.getArmor(message.index);
            if (armor.getItem() instanceof SlottedArmor) {
                LazyOptional<ISlottedArmor> armorCapability = armor.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
                ISlottedArmor armorInstance = armorCapability.orElse(new SlottedArmorInstance());
                if (armorInstance.getSlots() > 0) {
                    armorInstance.setGem(message.armorGem);
                }
            }
        } else {
            ItemStack weapon = player.getMainHandItem();
            if (weapon.getItem() instanceof SlottedSword || weapon.getItem() instanceof SlottedAxe) {
                LazyOptional<ISlottedWeapon> weaponCapability = weapon.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                ISlottedWeapon weaponInstance = weaponCapability.orElse(new SlottedWeaponInstance());
                if (weaponInstance.getSlots() > message.index - 4) {
                     weaponInstance.setGem(message.index - 4, message.armorGem);
                }
            }
        }
        return;
    }
    public static void onMessageReceived(final SendPotionEffectToClientMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("SendPotionEffectToClientMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("SendPotionEffectToClientMessage was invalid" + message.toString());
            return;
        }

        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("SendPotionEffectToClientMessage context could not provide a ClientWorld.");
            return;
        }

        ctx.enqueueWork(() -> processMessage(clientWorld.get(), message));
    }
    static void processMessage(ClientWorld world, SendPotionEffectToClientMessage message)
    {
        UUID uuid = UUID.fromString(message.uuid);
        String effectName = message.effectName;
        int duration = message.duration;
        int amplifier = message.amplifier;
        PlayerEntity player = world.getPlayerByUUID(uuid);
        switch(effectName) {
            case "effect.minecraft.slowness":
                player.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, duration, 2));
                break;
            case "effect.ethergems.bind_effect":
                player.addEffect(new EffectInstance(EffectInit.BIND_EFFECT.get(), duration));
                break;
            case "effect.minecraft.poison":
                player.addEffect(new EffectInstance(Effects.POISON, duration));
                break;
            case "effect.ethergems.chill_effect":
                player.addEffect(new EffectInstance(EffectInit.CHILL_EFFECT.get(), duration));
                break;
            case "effect.ethergems.bleed_effect":
                player.addEffect(new EffectInstance(EffectInit.BLEED_EFFECT.get(), duration));
                break;
            case "effect.ethergems.strength_down_effect":
                player.addEffect(new EffectInstance(EffectInit.STRENGTH_DOWN_EFFECT.get(), duration, amplifier));
                break;
            case "effect.ethergems.phys_def_down_effect":
                player.addEffect(new EffectInstance(EffectInit.PHYS_DEF_DOWN_EFFECT.get(), duration, amplifier));
                break;
        }
        return;
    }
    public static void onMessageReceived(final RenderParticleOnClientMessage message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("RenderParticleOnClientMessage received on wrong side:" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("RenderParticleOnClientMessage was invalid" + message.toString());
            return;
        }

        Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (!clientWorld.isPresent()) {
            LOGGER.warn("RenderParticleOnClientMessage context could not provide a ClientWorld.");
            return;
        }

        ctx.enqueueWork(() -> processMessage(clientWorld.get(), message));
    }
    static void processMessage(ClientWorld world, RenderParticleOnClientMessage message)
    {
        UUID uuid = UUID.fromString(message.uuid);
        PlayerEntity player = world.getPlayerByUUID(uuid);
        if (message.type == 0) {
            world.addParticle(ParticleInit.BLOOD_PARTICLE.get(), true, message.x, message.y, message.z, 0.0, 0.0, 0.0);
        } else if (message.type == 1) {
            world.addParticle(ParticleInit.CHILL_PARTICLE.get(), true, message.x, message.y, message.z, message.xSpeed, message.ySpeed, message.zSpeed);
        } else if (message.type == 2) {
            world.addParticle(ParticleInit.BACK_ATTACK.get(), true, message.x, message.y, message.z, message.xSpeed, message.ySpeed, message.zSpeed);
        } else if (message.type == 3) {
            player.level.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, player.getSoundSource(), 1.0F, 1.0F);
            Entity target = world.getEntity(message.targetID);
            player.crit(target);
        }
        return;
    }
    private static final Logger LOGGER = LogManager.getLogger();
}
