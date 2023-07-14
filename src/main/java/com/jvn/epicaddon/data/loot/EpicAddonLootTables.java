package com.jvn.epicaddon.data.loot;

import com.jvn.epicaddon.data.loot.function.setSkillFunction;
import com.jvn.epicaddon.register.RegEpicAddonSkills;
import com.jvn.epicaddon.register.RegItems;
import com.jvn.epicaddon.resources.config.CommonConfig;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import yesman.epicfight.world.item.EpicFightItems;

public class EpicAddonLootTables {
	public static void modifyVanillaLootPools(final LootTableLoadEvent event) {
		int modifier = CommonConfig.Values.SKILL_BOOK_CHEST_LOOT_MODIFYER;
		
    	if (event.getName().equals(BuiltInLootTables.END_CITY_TREASURE)) {
    		event.getTable().addPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
					.add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get()).setWeight(100).apply(setSkillFunction.builder("sao_dual_sword_skill")))
					.build());

			event.getTable().addPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
					.add(LootItem.lootTableItem(EpicFightItems.SKILLBOOK.get()).setWeight(100).apply(setSkillFunction.builder("sao_rapier_skill")))
					.build());
    	}
    }
}
