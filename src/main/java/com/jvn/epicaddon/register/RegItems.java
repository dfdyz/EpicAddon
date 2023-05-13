package com.jvn.epicaddon.register;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.item.CustomSkillBook;
import com.jvn.epicaddon.item.Destiny.DestinyWeaponItem;
import com.jvn.epicaddon.item.GenShinImpact.BowWeaponItem;
import com.jvn.epicaddon.item.SAO.DarkRepulsorItem;
import com.jvn.epicaddon.item.SAO.ElucidatorItem;
import com.jvn.epicaddon.item.SAO.LambentLightItem;
import com.jvn.epicaddon.item.SAO.SingelSwordItem;
import com.jvn.epicaddon.resources.EpicAddonItemGroup;
import com.jvn.epicaddon.resources.EpicAddonTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.item.EpicFightItemGroup;
import yesman.epicfight.world.item.SkillBookItem;

public class RegItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, EpicAddon.MODID);

    //Weapons
    public static final RegistryObject<Item> Elucidator = ITEMS.register("elucidator", () -> new ElucidatorItem(new Item.Properties().tab(EpicAddonItemGroup.ITEMS), EpicAddonTier.SAO_Special));
    public static final RegistryObject<Item> DarkRepulsor = ITEMS.register("dark_repulsor", () -> new DarkRepulsorItem(new Item.Properties().tab(EpicAddonItemGroup.ITEMS), EpicAddonTier.SAO_Normal));
    public static final RegistryObject<Item> LambentLight = ITEMS.register("lambent_light", () -> new LambentLightItem(new Item.Properties().tab(EpicAddonItemGroup.ITEMS), EpicAddonTier.SAO_Normal));

    //Sources
    public static final RegistryObject<Item> DragonShitCrystal = ITEMS.register("dragon_shit_crystal", () -> new Item(new  Item.Properties().tab(EpicAddonItemGroup.ITEMS)));

    public static final RegistryObject<Item> AnnealBlade = ITEMS.register("anneal_blade", () -> new SingelSwordItem(new Item.Properties().tab(EpicAddonItemGroup.ITEMS), EpicAddonTier.SAO_IRON,8));

    //public static final RegistryObject<Item> TestStick = ITEMS.register("test_bow", () -> new SingelSwordItem(new Item.Properties().tab(EpicAddonItemGroup.ITEMS), EpicAddonTier.SAO_IRON,8));
    public static final RegistryObject<Item> TrainingBow = ITEMS.register("training_bow", () -> new BowWeaponItem(new  Item.Properties().durability(600).tab(EpicAddonItemGroup.ITEMS)));

    public static final RegistryObject<Item> BattleScythe = ITEMS.register("battle_scythe", () -> new SingelSwordItem(new Item.Properties().tab(EpicAddonItemGroup.ITEMS), EpicAddonTier.SAO_IRON,8));
    public static final RegistryObject<Item> Destiny = ITEMS.register("destiny", () -> new DestinyWeaponItem(new Item.Properties().tab(EpicAddonItemGroup.ITEMS)));
    public static final RegistryObject<Item> SR_BaseBallBat = ITEMS.register("sr_baseball_bat", () -> new SingelSwordItem(new Item.Properties().tab(EpicAddonItemGroup.ITEMS), EpicAddonTier.SAO_IRON,8));
    public static final RegistryObject<Item> SKILLBOOK = ITEMS.register("custom_skillbook", () -> new CustomSkillBook(new Item.Properties().tab(EpicAddonItemGroup.ITEMS).rarity(Rarity.RARE).stacksTo(1)));
    //mtllib battle_scythe.mtl

    //public static final RegistryObject<Item> SteelSword = ITEMS.register("steel_sword", () -> new SteelSword(new Item.Properties().tab(EpicAddonItemGroup.ITEMS), Tiers.NETHERITE));
}
