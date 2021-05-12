package machinamelia.ethergems.common.events;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.init.EffectInit;
import machinamelia.ethergems.common.init.ParticleInit;
import machinamelia.ethergems.common.items.EtherGemsItemTier;
import machinamelia.ethergems.common.items.weapon.SlottedAxe;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.client.SendPotionEffectToClientMessage;
import machinamelia.ethergems.common.network.server.SendDoubleAttackToServerMessage;
import machinamelia.ethergems.common.util.GemHandler;

import java.util.Random;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttackEvents {

    private static int doubleAttackTickCounter = 65;
    private static LivingEntity[] engagement = new LivingEntity[2];

    @SubscribeEvent
    public static void checkDoubleAttack(TickEvent.PlayerTickEvent event) {
        if (event.player != null && event.player.world.isRemote) {
            if (engagement[0] != null && engagement[1] != null && event.player != null && engagement[0] instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.player;
                if (player.getUniqueID().equals(event.player.getUniqueID())) {
                    if (doubleAttackTickCounter > 0) {
                        doubleAttackTickCounter--;
                    } else {
                        SendDoubleAttackToServerMessage doubleAttackToServerMessage = new SendDoubleAttackToServerMessage(engagement[1].getEntityId());
                        NetworkHandler.simpleChannel.sendToServer(doubleAttackToServerMessage);
                        attack(player, engagement[1]);
                        engagement[0] = null;
                        engagement[1] = null;
                        doubleAttackTickCounter = 65;
                    }
                }
            }
        }
    }
    public static float normalizeAngle(float angle) {
        if (Math.abs(angle) > 360) {
            angle = angle % 360;
        }
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    public static void attack(PlayerEntity player, LivingEntity target) {
        if (player.world.isRemote) {
            UpdateGemEvents.updateServerPlayerGems(player, false);
        }

        Item item = player.getHeldItemMainhand().getItem();
        double strengthUp = GemHandler.getPlayerGemStrength(player, "Strength Up");
        if (strengthUp > 4.5) {
            strengthUp = 4.5;
        }
        if (item instanceof SwordItem || item instanceof SlottedAxe) {
            double firstAttack = 0.0;
            double backAttack = 0.0;
            if (target.getMaxHealth() == target.getHealth()) {
                firstAttack = GemHandler.getPlayerGemStrength(player, "First Attack Plus");
                if (firstAttack > 5.0) {
                    firstAttack = 5.0;
                }
            }
            float playerYaw = normalizeAngle(player.getYaw((float) 2.0));
            float targetYaw = normalizeAngle(target.getYaw((float) 4.0));

            float lowerRange = normalizeAngle(playerYaw - 55);
            float higherRange = normalizeAngle(playerYaw + 55);
            if (lowerRange <= targetYaw && targetYaw <= higherRange) {
                backAttack = GemHandler.getPlayerGemStrength(player, "Back Attack Plus");
                if (backAttack > 4.5) {
                    backAttack = 4.5;
                }
                double d0 = (double) (-MathHelper.sin(player.rotationYaw * ((float) Math.PI / 180F)));
                double d1 = (double) MathHelper.cos(player.rotationYaw * ((float) Math.PI / 180F));
                if (player.world instanceof ServerWorld && backAttack > 0) {
                    ((ServerWorld) player.world).spawnParticle(ParticleInit.BACK_ATTACK.get(), player.getPosX() + d0, player.getPosYHeight(0.5D), player.getPosZ() + d1, 0, d0, 0.0D, d1, 0.0D);
                }
            }
            int physDefDown = 0;
            if (target.getActivePotionEffect(EffectInit.PHYS_DEF_DOWN_EFFECT.get()) != null) {
                EffectInstance physDefDownInstance = target.getActivePotionEffect(EffectInit.PHYS_DEF_DOWN_EFFECT.get());
                physDefDown = physDefDownInstance.getAmplifier();
            }
            float weaponBaseDamage = 0;
            if (item instanceof SwordItem) {
                SwordItem sword = (SwordItem) item;
                weaponBaseDamage = sword.getAttackDamage();
            } else if (item instanceof SlottedAxe) {
                SlottedAxe axe = (SlottedAxe) item;
                weaponBaseDamage = axe.getAttackDamage();
            }


            double fullStrength = GemHandler.getPlayerGemStrength(player, "Critical Up");
            Random randy = new Random();
            int roll = randy.nextInt(100);
            if (player.world.isRemote && roll < fullStrength) {
                net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(player, target, true, true ? 1.5F : 1.0F);
                player.world.playSound((PlayerEntity) null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, player.getSoundCategory(), 1.0F, 1.0F);
                ClientPlayerEntity clientPlayer = (ClientPlayerEntity) player;
                clientPlayer.onCriticalHit(target);
                target.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) strengthUp + (1 + weaponBaseDamage + (float) hitResult.getDamageModifier()) - target.getTotalArmorValue() + (float) firstAttack + (float) backAttack + (float) (physDefDown * 0.5));
            } else {
                target.attackEntityFrom(DamageSource.causePlayerDamage(player), (float) strengthUp + (1 + weaponBaseDamage) - target.getTotalArmorValue() + (float) firstAttack + (float) backAttack + (float) (physDefDown * 0.5));
            }
        }
        if (target instanceof PlayerEntity) {
            PlayerEntity targetPlayer = (PlayerEntity) target;
            if (player.world.isRemote) {
                UpdateGemEvents.updateServerPlayerGems(player, false);
            }
            double fullStrength = GemHandler.getPlayerGemStrength(targetPlayer, "Spike");
            if (fullStrength > 6.0) {
                fullStrength = 6.0;
            }
            double resistStrength = GemHandler.getPlayerGemStrength(player, "Spike Defence");
            if (resistStrength > 100.0) {
                resistStrength = 100.0;
            }
            if (fullStrength > 0.0 && resistStrength == 0.0) {
                player.attackEntityFrom(DamageSource.causeThornsDamage(targetPlayer), (float) (fullStrength));
            } else if (fullStrength > 0.0) {
                player.attackEntityFrom(DamageSource.causeThornsDamage(targetPlayer), (float) (fullStrength - (fullStrength * (resistStrength / 100.0))));
            }
        }
        double fullStrength = GemHandler.getPlayerGemStrength(player, "Blaze Plus");
        if (fullStrength > 3.0) {
            fullStrength = 3.0;
        }
        if (fullStrength > 0) {
            target.getPersistentData().putDouble("blaze_plus_damage", fullStrength);
        }
        fullStrength = GemHandler.getPlayerGemStrength(player, "Chill Plus");
        if (fullStrength > 3.0) {
            fullStrength = 3.0;
        }
        if (fullStrength > 0) {
            target.getPersistentData().putDouble("chill_plus_damage", fullStrength);
        }
        fullStrength = GemHandler.getPlayerGemStrength(player, "Bleed Plus");
        if (fullStrength > 3.0) {
            fullStrength = 3.0;
        }
        if (fullStrength > 0) {
            target.getPersistentData().putDouble("bleed_plus_damage", fullStrength);
        }
        fullStrength = GemHandler.getPlayerGemStrength(player, "Poison Plus");
        if (fullStrength > 3.0) {
            fullStrength = 3.0;
        }
        if (fullStrength > 0) {
            target.getPersistentData().putDouble("poison_plus_damage", fullStrength);
        }
        fullStrength = GemHandler.getPlayerGemStrength(player, "Blaze Attack");
        if (fullStrength > 100.0) {
            fullStrength = 100.0;
        }
        double weaponPower = GemHandler.getPlayerGemStrength(player, "Weapon Power");
        if (weaponPower > 50.0) {
            weaponPower = 50.0;
        }
        double debuffPlus = GemHandler.getPlayerGemStrength(player, "Debuff Plus");
        ;
        if (fullStrength > 0) {
            Random randy = new Random();
            int roll = randy.nextInt(100);
            if (roll < fullStrength + weaponPower) {
                target.setFire(6 + (int) (6 * (debuffPlus / 100.0)));
            }
        }
        fullStrength = GemHandler.getPlayerGemStrength(player, "Chill Attack");
        if (fullStrength > 100.0) {
            fullStrength = 100.0;
        }
        weaponPower = GemHandler.getPlayerGemStrength(player, "Weapon Power");
        if (weaponPower > 50.0) {
            weaponPower = 50.0;
        }
        if (fullStrength > 0) {
            Random randy = new Random();
            int roll = randy.nextInt(100);
            if (roll < fullStrength + weaponPower) {
                target.addPotionEffect(new EffectInstance(EffectInit.CHILL_EFFECT.get(), 80 + (int) (80 * (debuffPlus / 100.0))));
                if (!player.world.isRemote && target instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) target;
                    SendPotionEffectToClientMessage msg = new SendPotionEffectToClientMessage(serverPlayer.getUniqueID().toString(), EffectInit.CHILL_EFFECT.get().getName(), 80 + (int) (80 * (debuffPlus / 100.0)), 0);
                    NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                }
            }
        }
        fullStrength = GemHandler.getPlayerGemStrength(player, "Bleed Attack");
        if (fullStrength > 100.0) {
            fullStrength = 100.0;
        }
        weaponPower = GemHandler.getPlayerGemStrength(player, "Weapon Power");
        if (weaponPower > 50.0) {
            weaponPower = 50.0;
        }
        if (fullStrength > 0) {
            Random randy = new Random();
            int roll = randy.nextInt(100);
            if (roll < fullStrength + weaponPower) {
                target.addPotionEffect(new EffectInstance(EffectInit.BLEED_EFFECT.get(), 80 + (int) (80 * (debuffPlus / 100.0))));
                if (!player.world.isRemote && target instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) target;
                    SendPotionEffectToClientMessage msg = new SendPotionEffectToClientMessage(serverPlayer.getUniqueID().toString(), EffectInit.BLEED_EFFECT.get().getName(), 80 + (int) (80 * (debuffPlus / 100.0)), 0);
                    NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                }
            }
        }
        fullStrength = GemHandler.getPlayerGemStrength(player, "Poison Attack");
        if (fullStrength > 100.0) {
            fullStrength = 100.0;
        }
        weaponPower = GemHandler.getPlayerGemStrength(player, "Weapon Power");
        if (weaponPower > 50.0) {
            weaponPower = 50.0;
        }
        if (fullStrength > 0) {
            Random randy = new Random();
            int roll = randy.nextInt(100);
            if (roll < fullStrength + weaponPower) {
                target.addPotionEffect(new EffectInstance(Effects.POISON, 160 + (int) (160 * (debuffPlus / 100.0))));
                if (!player.world.isRemote && target instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) target;
                    SendPotionEffectToClientMessage msg = new SendPotionEffectToClientMessage(serverPlayer.getUniqueID().toString(), Effects.POISON.getName(), 160 + (int) (160 * (debuffPlus / 100.0)), 0);
                    NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                }
            }
        }
        fullStrength = GemHandler.getPlayerGemStrength(player, "Bind");
        if (fullStrength > 25.0) {
            fullStrength = 25.0;
        }
        weaponPower = GemHandler.getPlayerGemStrength(player, "Weapon Power");
        if (weaponPower > 50.0) {
            weaponPower = 50.0;
        }
        if (fullStrength > 0 && (!(target instanceof EndermanEntity || target instanceof EndermiteEntity))) {
            Random randy = new Random();
            int roll = randy.nextInt(100);
            if (roll < fullStrength + weaponPower) {
                target.addPotionEffect(new EffectInstance(EffectInit.BIND_EFFECT.get(), 40 + (int) (40 * (debuffPlus / 100.0))));
                if (!player.world.isRemote && target instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) target;
                    SendPotionEffectToClientMessage msg = new SendPotionEffectToClientMessage(serverPlayer.getUniqueID().toString(), EffectInit.BIND_EFFECT.get().getName(), 80 + (int) (80 * (debuffPlus / 100.0)), 0);
                    NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                }
            }
        }
        fullStrength = GemHandler.getPlayerGemStrength(player, "Slow");
        if (fullStrength > 25.0) {
            fullStrength = 25.0;
        }
        weaponPower = GemHandler.getPlayerGemStrength(player, "Weapon Power");
        if (weaponPower > 50.0) {
            weaponPower = 50.0;
        }
        if (fullStrength > 0 && (!(target instanceof StrayEntity || target instanceof WitchEntity))) {
            Random randy = new Random();
            int roll = randy.nextInt(100);
            if (roll < fullStrength + weaponPower) {
                target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 80 + (int) (80 * (debuffPlus / 100.0)), 2));
                if (!player.world.isRemote && target instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayer = (ServerPlayerEntity) target;
                    SendPotionEffectToClientMessage msg = new SendPotionEffectToClientMessage(serverPlayer.getUniqueID().toString(), Effects.SLOWNESS.getName(), 80 + (int) (80 * (debuffPlus / 100.0)), 2);
                    NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
                }
            }
        }
        fullStrength = GemHandler.getPlayerGemStrength(player, "Strength Down");
        if (fullStrength < -3.0) {
            fullStrength = -3.0;
        }
        if (fullStrength < 0 && (!(target instanceof MagmaCubeEntity || target instanceof SlimeEntity))) {
            target.addPotionEffect(new EffectInstance(EffectInit.STRENGTH_DOWN_EFFECT.get(), 80 + (int) (80 * (debuffPlus / 100.0)), (int) -(fullStrength / 0.5)));
            if (!player.world.isRemote && target instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) target;
                SendPotionEffectToClientMessage msg = new SendPotionEffectToClientMessage(serverPlayer.getUniqueID().toString(), EffectInit.STRENGTH_DOWN_EFFECT.get().getName(), 160 + (int) (160 * (debuffPlus / 100.0)), (int) -(fullStrength / 0.5));
                NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
            }
        }
        fullStrength = GemHandler.getPlayerGemStrength(player, "Phys Def Down");
        if (fullStrength > 5.0) {
            fullStrength = 5.0;
        }
        if (fullStrength > 0 && (!(target instanceof IronGolemEntity || target instanceof RavagerEntity))) {
            target.addPotionEffect(new EffectInstance(EffectInit.PHYS_DEF_DOWN_EFFECT.get(), 160 + (int) (160 * (debuffPlus / 100.0)), (int) (fullStrength / 0.5)));
            if (!player.world.isRemote && target instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayer = (ServerPlayerEntity) target;
                SendPotionEffectToClientMessage msg = new SendPotionEffectToClientMessage(serverPlayer.getUniqueID().toString(), EffectInit.PHYS_DEF_DOWN_EFFECT.get().getName(), 160 + (int) (160 * (debuffPlus / 100.0)), (int) (fullStrength / 0.5));
                NetworkHandler.simpleChannel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), msg);
            }
        }
        fullStrength = GemHandler.getPlayerGemStrength(player, "HP Steal");
        if (fullStrength > 3.0) {
            fullStrength = 3.0;
        }
        int chance = 0;
        if (fullStrength == 0.5) {
            chance = 5;
        } else if (fullStrength > 0.5 && fullStrength <= 1) {
            chance = 8;
        } else if (fullStrength > 1 && fullStrength <= 1.5) {
            chance = 12;
        } else if (fullStrength > 1.5 && fullStrength <= 2) {
            chance = 15;
        } else if (fullStrength > 2 && fullStrength <= 2.5) {
            chance = 20;
        } else if (fullStrength > 2.5 && fullStrength <= 3) {
            chance = 25;
        }
        if (fullStrength > 0) {
            Random randy = new Random();
            int roll = randy.nextInt(100);
            if (roll < chance + weaponPower) {
                player.heal((float) fullStrength);
            }
        }
    }

    @SubscribeEvent
    public static void attackEvent(AttackEntityEvent event) {
        PlayerEntity player = event.getPlayer();
        Item item = player.getHeldItemMainhand().getItem();

        if (item instanceof SwordItem) {
            SwordItem sword = (SwordItem) item;
            if (sword.getTier().equals(EtherGemsItemTier.MONADO) && (event.getTarget() instanceof PlayerEntity || event.getTarget() instanceof VillagerEntity)) {
                event.setCanceled(true);
                return;
            }
        }
        if (player.getPersistentData().getInt("physical_protect_time") <= 0) {
            double fullStrength = GemHandler.getPlayerGemStrength(player, "Physical Protect");
            if (fullStrength > 4) {
                fullStrength = 4;
            }
            double buffTimePlus = GemHandler.getPlayerGemStrength(player, "Buff Time Plus");
            if (buffTimePlus > 150.0) {
                buffTimePlus = 150.0;
            }
            if (fullStrength > 0) {
                player.addPotionEffect(new EffectInstance(EffectInit.PHYSICAL_PROTECT_EFFECT.get(), 120 + (int) (120 * (buffTimePlus / 100.0)), (int) (fullStrength / 0.5)));
            }
            player.getPersistentData().putInt("physical_protect_time", 360);
        }
        if (event.getTarget() instanceof LivingEntity) {
            attack(player, (LivingEntity) event.getTarget());
            double doubleAttack = GemHandler.getPlayerGemStrength(player, "Double Attack");
            if (doubleAttack > 50.0) {
                doubleAttack = 50.0;
            }
            if (doubleAttack > 0) {
                double weaponPower = GemHandler.getPlayerGemStrength(player, "Weapon Power");
                if (weaponPower > 50.0) {
                    weaponPower = 50.0;
                }
                Random randy = new Random();
                int roll = randy.nextInt(100);
                if (roll < doubleAttack + weaponPower) {
                    engagement = new LivingEntity[2];
                    engagement[0] = player;
                    engagement[1] = (LivingEntity) event.getTarget();
                }
            }
        }
    }

    @SubscribeEvent
    public static void enemyAttackEvent(LivingAttackEvent event) {
        Entity source = event.getSource().getTrueSource();
        LivingEntity target = event.getEntityLiving();
        if (target instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) target;
            if (source instanceof LivingEntity) {
                LivingEntity livingSource = (LivingEntity) source;
                double fullStrength = GemHandler.getPlayerGemStrength(player, "Spike");
                if (fullStrength > 6.0) {
                    fullStrength = 6.0;
                }
                double resistStrength = 0;
                if (!(livingSource instanceof PlayerEntity) && fullStrength > 0) {
                    livingSource.attackEntityFrom(DamageSource.causeThornsDamage(player), (float) fullStrength);
                }
                if (!(livingSource instanceof PlayerEntity) && event.getSource().damageType.equals("thorns")) {
                    resistStrength = GemHandler.getPlayerGemStrength(player, "Spike Defence");
                    if (resistStrength > 100.0) {
                        resistStrength = 100.0;
                    }
                    if (resistStrength > 0) {
                        player.heal((float) (resistStrength / 100.0) * event.getAmount());
                    }
                }
                fullStrength = GemHandler.getPlayerGemStrength(player, "Damage Heal");
                if (fullStrength > 4.5) {
                    fullStrength = 4.5;
                }
                int chance = 0;
                if (fullStrength == 0.5) {
                    chance = 5;
                } else if (fullStrength > 0.5 && fullStrength <= 1) {
                    chance = 8;
                } else if (fullStrength > 1 && fullStrength <= 1.5) {
                    chance = 12;
                } else if (fullStrength > 1.5 && fullStrength <= 2) {
                    chance = 15;
                } else if (fullStrength > 2 && fullStrength <= 2.5) {
                    chance = 20;
                } else if (fullStrength > 2.5 && fullStrength <= 3) {
                    chance = 25;
                } else {
                    chance = 25;
                }
                if (fullStrength > 0) {
                    Random randy = new Random();
                    int roll = randy.nextInt(100);
                    if (roll < chance) {
                        player.heal((float) fullStrength);
                    }
                }
            }
        }
    }
}
