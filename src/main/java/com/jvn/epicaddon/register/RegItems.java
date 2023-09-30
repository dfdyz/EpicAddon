package com.jvn.epicaddon.register;

import com.google.common.collect.Maps;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.item.Destiny.DestinyWeaponItem;
import com.jvn.epicaddon.item.GenShinImpact.BowWeaponItem;
import com.jvn.epicaddon.item.SAO.DarkRepulsorItem;
import com.jvn.epicaddon.item.SAO.ElucidatorItem;
import com.jvn.epicaddon.item.SAO.LambentLightItem;
import com.jvn.epicaddon.item.SAO.SingelSwordItem;
import com.jvn.epicaddon.resources.EpicAddonItemGroup;
import com.jvn.epicaddon.resources.EpicAddonTier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.model.JsonModelLoader;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
    //public static final RegistryObject<Item> SR_BaseBallBat = ITEMS.register("sr_baseball_bat", () -> new SingelSwordItem(new Item.Properties().tab(EpicAddonItemGroup.ITEMS), EpicAddonTier.SAO_IRON,8));
    //public static final RegistryObject<Item> SKILLBOOK = ITEMS.register("custom_skillbook", () -> new CustomSkillBook(new Item.Properties().tab(EpicAddonItemGroup.ITEMS).rarity(Rarity.RARE).stacksTo(1)));
    //mtllib battle_scythe.mtl

    //public static final RegistryObject<Item> SteelSword = ITEMS.register("steel_sword", () -> new SteelSword(new Item.Properties().tab(EpicAddonItemGroup.ITEMS), Tiers.NETHERITE));



    public static class AnimatedItems{
        public static class AnimatedItemModel{
            private Armature armature;

            @OnlyIn(Dist.CLIENT)
            private AnimatedMesh mesh;
            public AnimatedItemModel(ResourceManager rm, ResourceLocation rl){
                JsonModelLoader jsonModelLoader = new JsonModelLoader(rm, rl);
                armature = jsonModelLoader.loadArmature(Armature::new);

                if(FMLEnvironment.dist == Dist.CLIENT) {
                    mesh = jsonModelLoader.loadAnimatedMesh(AnimatedMesh::new);
                }
            }
            public Armature getArmature(){ return armature; }

            @OnlyIn(Dist.CLIENT)
            public AnimatedMesh getMesh(){ return mesh; }
        }

        public static Supplier<Animator> AnimatedItem_AnimatorGetter;
        public static HashMap<Item, AnimatedItemModel> ModelRegistry = Maps.newHashMap();


        public static void registerAnimatedItem(Item item, AnimatedItemModel m){
            ModelRegistry.put(item, m);
        }

        public static Animator getAnimator(ItemStack itemStack){
            return AnimatedItem_AnimatorGetter.get();
        }

        public static void registerAnimatedItems(){

        }
    }

}
