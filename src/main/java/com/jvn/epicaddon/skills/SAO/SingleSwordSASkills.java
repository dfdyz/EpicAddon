package com.jvn.epicaddon.skills.SAO;

import com.jvn.epicaddon.register.RegEpicAddonSkills;
import com.jvn.epicaddon.resources.EpicAddonAnimations;
import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import com.jvn.epicaddon.skills.IMutiSpecialSkill;
import com.jvn.epicaddon.skills.SAOInternal.MutiSpecialSkill;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SpecialAttackSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.ArrayList;

public class SingleSwordSASkills extends SpecialAttackSkill implements IMutiSpecialSkill {
    private final ArrayList<ResourceLocation> noPower = new ArrayList<>();
    private final ArrayList<ResourceLocation> morePower = new ArrayList<>();

    private final StaticAnimation noPowerAnimation1;
    private final StaticAnimation morePowerAnimation1;

    public SingleSwordSASkills(Builder<? extends Skill> builder) {
        super(builder);
        noPowerAnimation1 = Animations.SWEEPING_EDGE;
        morePowerAnimation1 = EpicAddonAnimations.DMC5_V_JC;

        ResourceLocation name = this.getRegistryName();
        noPower.add(new ResourceLocation(name.getNamespace(), "textures/gui/skills/" + name.getPath() + ".png"));
        noPower.add(new ResourceLocation(name.getNamespace(), "textures/gui/skills/single/judgement_cut.png"));
    }

    public static Skill.Builder<SingleSwordSASkills> createBuilder(ResourceLocation resourceLocation) {
        return (new Skill.Builder<SingleSwordSASkills>(resourceLocation)).setCategory(SkillCategories.WEAPON_SPECIAL_ATTACK).setResource(Resource.SPECIAL_GAUAGE);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getSkillCapability()
                .skillContainers[EpicAddonSkillCategories.MutiSpecialAttack.universalOrdinal()]
                .setSkill(RegEpicAddonSkills.MUTI_SPECIAL_ATTACK);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        if (executer.isLogicalClient()) {
            boolean ok = false;
            SkillContainer skillContainer = executer.getSkill(this.category);
            int selected = executer.getSkill(EpicAddonSkillCategories.MutiSpecialAttack).getDataManager().getDataValue(MutiSpecialSkill.CHILD_SKILL_INDEX);

            ok = skillContainer.getStack() > (selected == 0 ? 0:1);

            return ok || (executer.getOriginal()).isCreative();
        } else {
            return executer.getHoldingItemCapability(InteractionHand.MAIN_HAND).getSpecialAttack(executer) == this && (executer.getOriginal()).getVehicle() == null && (!executer.getSkill(this.category).isActivated() || this.activateType == ActivateType.TOGGLE);
        }
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        SkillContainer skill = executer.getSkill(this.category);
        int selected = executer.getSkill(EpicAddonSkillCategories.MutiSpecialAttack).getDataManager().getDataValue(MutiSpecialSkill.CHILD_SKILL_INDEX);

        if(selected == 0){
            executer.playAnimationSynchronized(this.noPowerAnimation1, 0.0F);
            this.setStackSynchronize(executer, executer.getSkill(this.category).getStack() - 1);
        }
        else {
            executer.playAnimationSynchronized(this.morePowerAnimation1, 0.0F);
            this.setStackSynchronize(executer, executer.getSkill(this.category).getStack() - 1);
        }

        this.setDurationSynchronize(executer, this.maxDuration);
        skill.activate();
    }


    @Override
    public SpecialAttackSkill registerPropertiesToAnimation() {
        return this;
    }

    @Override
    public ArrayList<ResourceLocation> getSkillTextures(PlayerPatch<?> executer) {
        return noPower;
    }

    @Override
    public boolean isSkillActive(PlayerPatch<?> executer, int idx) {
        return true;
    }
}
