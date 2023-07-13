package com.jvn.epicaddon.item;

import com.jvn.epicaddon.register.RegEpicAddonSkills;
import com.jvn.epicaddon.resources.EpicAddonItemGroup;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.gameasset.Skills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.item.SkillBookItem;

import java.util.List;

public class CustomSkillBook extends SkillBookItem {
    public CustomSkillBook(Properties properties) {
        super(properties);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (group == EpicAddonItemGroup.ITEMS) {

            ItemStack stack = new ItemStack(this);
            setContainingSkill(RegEpicAddonSkills.SAO_DUALSWORD, stack);
            items.add(stack);

            stack = new ItemStack(this);
            setContainingSkill(RegEpicAddonSkills.SAO_RAPIER_A, stack);
            items.add(stack);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (stack.getTag() != null && stack.getTag().contains("skill")) {
            Skill skill = Skills.getSkill(stack.getTag().getString("skill"));
            if (skill != null) {
                tooltip.clear();
                tooltip.add((new TranslatableComponent("tab."+skill.getTranslatableText())).withStyle(ChatFormatting.AQUA));
            }
        }
    }
}
