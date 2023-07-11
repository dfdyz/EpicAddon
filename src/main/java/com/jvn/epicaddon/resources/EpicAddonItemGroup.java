package com.jvn.epicaddon.resources;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.register.RegItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.item.EpicFightItems;

public class EpicAddonItemGroup {

    public static final CreativeModeTab ITEMS = new CreativeModeTab(EpicAddon.MODID + ".items") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RegItems.Elucidator.get());
        }
    };

}
