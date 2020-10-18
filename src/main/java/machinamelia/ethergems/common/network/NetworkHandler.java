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

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.network.client.RenderParticleOnClientMessage;
import machinamelia.ethergems.common.network.client.SendAffinityToClientPlayer;
import machinamelia.ethergems.common.network.client.SendArmorGemToClientMessage;
import machinamelia.ethergems.common.network.client.SendPotionEffectToClientMessage;
import machinamelia.ethergems.common.network.server.*;

import java.util.Optional;

import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_SERVER;

public class NetworkHandler {

    public static final byte OPEN_CRYSTAL_INVENTORY_MESSAGE_ID = 1;
    public static final byte OPEN_GEM_INVENTORY_MESSAGE_ID = 2;
    public static final byte OPEN_CRAFTING_CONTAINER_MESSAGE_ID = 3;
    public static final byte OPEN_GEM_CONFIRM_CONTAINER_MESSAGE_ID = 4;
    public static final byte OPEN_CYLINDER_CONFIRM_CONTAINER_MESSAGE_ID = 5;
    public static final byte OPEN_OPTIONS_CONTAINER_MESSAGE_ID = 6;
    public static final byte PUT_GEMS_IN_INVENTORY_MESSAGE_ID = 8;
    public static final byte ADD_GEMS_TO_CONFIRM_MESSAGE_ID = 9;
    public static final byte ADD_CYLINDERS_TO_CONFIRM_MESSAGE_ID = 10;
    public static final byte PUT_CRYSTALS_IN_INVENTORY_MESSAGE_ID = 11;
    public static final byte SEND_MESSAGE_TO_ALL_PLAYERS_MESSAGE_ID = 12;
    public static final byte SEND_AFFINITY_TO_CLIENT_PLAYER_MESSAGE_ID = 13;
    public static final byte GET_CLIENT_GEM_STRENGTH_MESSAGE_ID = 14;
    public static final byte SEND_GEM_STRENGTH_TO_SERVER_MESSAGE_ID = 15;
    public static final byte SEND_POTION_EFFECT_TO_CLIENT_MESSAGE_ID = 16;
    public static final byte SEND_PARTICLE_TO_SERVER_WORLD_MESSAGE_ID = 17;
    public static final byte RENDER_PARTICLE_ON_CLIENT_MESSAGE_ID = 18;
    public static final byte SEND_DOUBLE_ATTACK_TO_SERVER_MESSAGE_ID = 19;
    public static final ResourceLocation simpleChannelRL = new ResourceLocation(EtherGems.MOD_ID, "guichannel");
    public static final String MESSAGE_PROTOCOL_VERSION = "1.0";
    public static SimpleChannel simpleChannel;


    public static void setupNetwork() {
        simpleChannel = NetworkRegistry.newSimpleChannel(simpleChannelRL, () -> MESSAGE_PROTOCOL_VERSION,
                MessageHandlerOnClient::isThisProtocolAcceptedByClient,
                MessageHandlerOnServer::isThisProtocolAcceptedByServer);
        simpleChannel.registerMessage(OPEN_CRYSTAL_INVENTORY_MESSAGE_ID, OpenCrystalInventoryMessage.class,
                OpenCrystalInventoryMessage::encode, OpenCrystalInventoryMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(OPEN_GEM_INVENTORY_MESSAGE_ID, OpenGemInventoryMessage.class,
                OpenGemInventoryMessage::encode, OpenGemInventoryMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(OPEN_CRAFTING_CONTAINER_MESSAGE_ID, OpenCraftingContainerMessage.class,
                OpenCraftingContainerMessage::encode, OpenCraftingContainerMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(OPEN_GEM_CONFIRM_CONTAINER_MESSAGE_ID, OpenGemConfirmContainerMessage.class,
                OpenGemConfirmContainerMessage::encode, OpenGemConfirmContainerMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(OPEN_CYLINDER_CONFIRM_CONTAINER_MESSAGE_ID, OpenCylinderConfirmContainerMessage.class,
                OpenCylinderConfirmContainerMessage::encode, OpenCylinderConfirmContainerMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(OPEN_OPTIONS_CONTAINER_MESSAGE_ID, OpenOptionsContainerMessage.class,
                OpenOptionsContainerMessage::encode, OpenOptionsContainerMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(PUT_GEMS_IN_INVENTORY_MESSAGE_ID, PutGemsInInventoryMessage.class,
                PutGemsInInventoryMessage::encode, PutGemsInInventoryMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(ADD_GEMS_TO_CONFIRM_MESSAGE_ID, AddGemsToConfirmMessage.class,
                AddGemsToConfirmMessage::encode, AddGemsToConfirmMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(ADD_CYLINDERS_TO_CONFIRM_MESSAGE_ID, AddCylindersToConfirmMessage.class,
                AddCylindersToConfirmMessage::encode, AddCylindersToConfirmMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(PUT_CRYSTALS_IN_INVENTORY_MESSAGE_ID, PutCrystalsInInventoryMessage.class,
                PutCrystalsInInventoryMessage::encode, PutCrystalsInInventoryMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(SEND_MESSAGE_TO_ALL_PLAYERS_MESSAGE_ID, SendMessageToAllPlayersMessage.class,
                SendMessageToAllPlayersMessage::encode, SendMessageToAllPlayersMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(SEND_GEM_STRENGTH_TO_SERVER_MESSAGE_ID, SendArmorGemToServerMessage.class,
                SendArmorGemToServerMessage::encode, SendArmorGemToServerMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(SEND_PARTICLE_TO_SERVER_WORLD_MESSAGE_ID, SendParticleToServerWorldMessage.class,
                SendParticleToServerWorldMessage::encode, SendParticleToServerWorldMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(SEND_DOUBLE_ATTACK_TO_SERVER_MESSAGE_ID, SendDoubleAttackToServerMessage.class,
                SendDoubleAttackToServerMessage::encode, SendDoubleAttackToServerMessage::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));
        simpleChannel.registerMessage(SEND_AFFINITY_TO_CLIENT_PLAYER_MESSAGE_ID, SendAffinityToClientPlayer.class,
                SendAffinityToClientPlayer::encode, SendAffinityToClientPlayer::decode,
                MessageHandlerOnClient::onMessageReceived,
                Optional.of(PLAY_TO_CLIENT));
        simpleChannel.registerMessage(GET_CLIENT_GEM_STRENGTH_MESSAGE_ID, SendArmorGemToClientMessage.class,
                SendArmorGemToClientMessage::encode, SendArmorGemToClientMessage::decode,
                MessageHandlerOnClient::onMessageReceived,
                Optional.of(PLAY_TO_CLIENT));
        simpleChannel.registerMessage(SEND_POTION_EFFECT_TO_CLIENT_MESSAGE_ID, SendPotionEffectToClientMessage.class,
                SendPotionEffectToClientMessage::encode, SendPotionEffectToClientMessage::decode,
                MessageHandlerOnClient::onMessageReceived,
                Optional.of(PLAY_TO_CLIENT));
        simpleChannel.registerMessage(SEND_POTION_EFFECT_TO_CLIENT_MESSAGE_ID, SendPotionEffectToClientMessage.class,
                SendPotionEffectToClientMessage::encode, SendPotionEffectToClientMessage::decode,
                MessageHandlerOnClient::onMessageReceived,
                Optional.of(PLAY_TO_CLIENT));
        simpleChannel.registerMessage(RENDER_PARTICLE_ON_CLIENT_MESSAGE_ID, RenderParticleOnClientMessage.class,
                RenderParticleOnClientMessage::encode, RenderParticleOnClientMessage::decode,
                MessageHandlerOnClient::onMessageReceived,
                Optional.of(PLAY_TO_CLIENT));
    }
}
