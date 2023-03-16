package com.jvn.epicaddon.skills.GenShinInternal;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.api.anim.FallAtkAnim;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.List;
import java.util.UUID;


public class GSFallAttack extends Skill {
    public static final SkillDataManager.SkillDataKey<Integer> FALLATKING = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);
    private static final UUID EVENT_UUID = UUID.fromString("2797461e-2bcc-7015-bf07-390a557fed81");

    public Builder<GSFallAttack> GetBuilder(String registryName){
        return new Builder<GSFallAttack>(new ResourceLocation(EpicAddon.MODID, registryName)).setCategory(SkillCategories.AIR_ATTACK);
    }
    public GSFallAttack(Builder<? extends Skill> builder) {
        super(builder);
    }
    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getDataManager().registerData(FALLATKING);
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT,EVENT_UUID,(event) -> {
            int state = container.getDataManager().getDataValue(FALLATKING);
            if(state == 0) return;
            ServerPlayerPatch playerPatch = event.getPlayerPatch();
            List<StaticAnimation> motions = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getAutoAttckMotion(playerPatch);
            StaticAnimation attackMotion = motions.get(motions.size() - 1);

            if(attackMotion != null && attackMotion instanceof FallAtkAnim){
                FallAtkAnim fallAtkAnim = (FallAtkAnim) attackMotion;
                if (playerPatch.getOriginal().getY() - playerPatch.getOriginal().yo < 0f){
                    if(state == 0){
                        container.getDataManager().setData(FALLATKING, 2);
                        playerPatch.playAnimationSynchronized(fallAtkAnim.Loop, 0);
                    }
                }
                else
                {
                    container.getDataManager().setData(FALLATKING, 0);
                    playerPatch.playAnimationSynchronized(fallAtkAnim.End, 0);
                }
            }
        });

        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID,(event) -> {
            int state = container.getDataManager().getDataValue(FALLATKING);
            if(state != 0) return;
            ServerPlayerPatch playerPatch = event.getPlayerPatch();
            List<StaticAnimation> motions = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getAutoAttckMotion(playerPatch);
            StaticAnimation attackMotion = motions.get(motions.size() - 1);

            playerPatch.getSkill(SkillCategories.AIR_ATTACK).getDataManager().setData(FALLATKING, 1);

            if (attackMotion != null) {
                if(attackMotion instanceof FallAtkAnim){
                    playerPatch.playAnimationSynchronized(((FallAtkAnim)attackMotion).Start, 0);
                }
                else{
                    playerPatch.playAnimationSynchronized(attackMotion, 0);
                }
            }

        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getDataManager().setData(FALLATKING, 0);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);

    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        List<StaticAnimation> motions = executer.getHoldingItemCapability(InteractionHand.MAIN_HAND).getAutoAttckMotion(executer);
        StaticAnimation attackMotion = motions.get(motions.size() - 1);

        executer.getSkill(SkillCategories.AIR_ATTACK).getDataManager().setData(FALLATKING, 1);

        if (attackMotion != null) {
            if(attackMotion instanceof FallAtkAnim){
                super.executeOnServer(executer, args);
                executer.playAnimationSynchronized(((FallAtkAnim)attackMotion).Start, 0);
            }
            else{
                super.executeOnServer(executer, args);
                executer.playAnimationSynchronized(attackMotion, 0);
            }
        }
    }
    public void updateContainer(SkillContainer container) {
        container.getDataManager().setData(FALLATKING, 0);
    }
}
