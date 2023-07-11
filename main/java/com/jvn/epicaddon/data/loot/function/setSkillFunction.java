package com.jvn.epicaddon.data.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;

public class setSkillFunction implements LootItemFunction {
	@Override
	public LootItemFunctionType getType() {
		return LootItemFunctions.SET_NBT;
	}

	@Override
	public ItemStack apply(ItemStack t, LootContext u) {
		t.getOrCreateTag().putString("skill", skillname);
		return t;
	}

	public final String skillname;
	public setSkillFunction(String skillname){
		this.skillname = skillname;
	}

	public static Builder builder(String skillname) {
		return new BuilderEx(skillname);
	}

	public static class BuilderEx implements Builder {
		public final String skillname;
		public BuilderEx(String n){
			skillname = n;
		}
		@Override
		public LootItemFunction build() {
			return new setSkillFunction(skillname);
		}
	}

	public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<setSkillFunction> {
		@Override
		public void serialize(JsonObject p_79325_, setSkillFunction p_79326_, JsonSerializationContext p_79327_) {

		}
		
		@Override
		public setSkillFunction deserialize(JsonObject p_79323_, JsonDeserializationContext p_79324_) {
			return new setSkillFunction(p_79323_.get("skillname").getAsString());
		}
	}
}