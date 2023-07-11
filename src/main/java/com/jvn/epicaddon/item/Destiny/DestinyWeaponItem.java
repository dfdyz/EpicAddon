package com.jvn.epicaddon.item.Destiny;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jvn.epicaddon.item.CategoriesChangeItem;
import com.jvn.epicaddon.register.RegParticle;
import com.jvn.epicaddon.renderer.particle.EpicAddonHitParticalType;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.client.particle.EpicFightParticleRenderTypes;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;


public class DestinyWeaponItem extends ProjectileWeaponItem {

    public static final String types[] = {"greatsword","destiny"};
    public DestinyWeaponItem(Properties properties) {
        super(properties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (itestack) -> false;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 8.5, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }

        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 20;
    }


    protected void setType(ItemStack stack, String typeName){
        stack.getOrCreateTag().putString("epicaddon_type",typeName);
    }

    @Override
    public void verifyTagAfterLoad(CompoundTag p_150898_) {
        super.verifyTagAfterLoad(p_150898_);
    }

    protected int getTypeIdx(ItemStack stack){
        return stack.getOrCreateTag().getShort("epicaddon_typeidx");
    }

    public static String getType(ItemStack stack) {
        return stack.getOrCreateTag().getString("epicaddon_type");
    }

    private void setCharged(ItemStack stack,boolean charged){
        stack.getOrCreateTag().putBoolean("Charged",charged);
    }

    private boolean getCharged(ItemStack stack){
        return stack.getOrCreateTag().getBoolean("Charged");
    }

    protected void setTypeIdx(ItemStack stack, int idx){
        stack.getOrCreateTag().putShort("epicaddon_typeidx", (short) (idx));
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
        if (this.allowdedIn(tab)) {
            ItemStack stack = new ItemStack(this);
            InitItemStack(stack);
            stacks.add(stack);
            stack = new ItemStack(this);
            InitItemStack(stack);
            setType(stack,types[1]);
            setCharged(stack,true);
            stacks.add(stack);
        }
    }

    private void InitItemStack(ItemStack itemStack){
        setTypeIdx(itemStack,0);
        setType(itemStack,types[0]);
        setCharged(itemStack,true);
    }

    @Override
    public void onCraftedBy(ItemStack itemStack, Level level, Player player) {
        InitItemStack(itemStack);
    }

    protected void updateItem(Player playerIn, ItemStack itemstack,String type){
        PlayerPatch playerpatch = (PlayerPatch)playerIn.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).orElse((PlayerPatch) null);
        CapabilityItem fromCap =  playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
        //System.out.println("ItemChange("+getType(itemstack)+" -> "+type+")");
        setType(itemstack,type);

        playerpatch.updateHeldItem(fromCap,
                EpicFightCapabilities.getItemStackCapability(itemstack),
                itemstack,itemstack,InteractionHand.MAIN_HAND);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
        //updateItem(playerIn,hand);
        if(worldIn.isClientSide()) return InteractionResultHolder.pass(playerIn.getItemInHand(hand));
        if(!playerIn.isShiftKeyDown()){
            if(getType(playerIn.getItemInHand(hand))==types[1]){
                Vec3 Up = playerIn.getUpVector(1.0F);
                Quaternion quaternion = new Quaternion(new Vector3f(Up), 0, true);
                Vec3 View = playerIn.getViewVector(1.0F);
                Vector3f shootVec = new Vector3f(View);
                shootVec.transform(quaternion);

                Vec3 Pos = playerIn.position();
                Projectile projectile = new LargeFireball(worldIn,playerIn,shootVec.x(), shootVec.y(), shootVec.z(),3);
                projectile.setPos(Pos.x+View.x,Pos.y+playerIn.getEyeHeight()+View.y,Pos.z+View.z);
                projectile.shoot(shootVec.x(), shootVec.y(), shootVec.z(), 4.0f, 1.0f);
                worldIn.addFreshEntity(projectile);
                setCharged(playerIn.getItemInHand(hand),false);
                //System.out.println(playerIn.getLookAngle());
                playerIn.getCooldowns().addCooldown(this,15);
            }
        }
        else{
            setTypeIdx(playerIn.getItemInHand(hand),1);
        }
        return InteractionResultHolder.pass(playerIn.getItemInHand(hand));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 25;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity playerIn, int sort, boolean inHand) {
        if(level.isClientSide()) return;
        if(playerIn instanceof Player){
            if(inHand){
                int i = getTypeIdx(stack);
                if(i>0){
                    if(getCharged(stack)){
                        setCharged(stack,false);
                    }
                    if(++i <= 10){
                        setTypeIdx(stack,i);
                    }
                    else{
                        setTypeIdx(stack,0);
                        if(getType(stack) == types[0]) updateItem((Player) playerIn,stack,types[1]);
                        else updateItem((Player) playerIn,stack,types[0]);
                    }
                }
            }
            else{
                if(getTypeIdx(stack)>0){
                    setTypeIdx(stack,0);
                    if(getType(stack) == types[0]) updateItem((Player) playerIn,stack,types[1]);
                    else updateItem((Player) playerIn,stack,types[0]);
                }
            }

            boolean ch = getType(stack) == types[1];
            if(getCharged(stack) != ch){
                setCharged(stack,ch);
            }

        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        if(getTypeIdx(stack) > 1){
            return UseAnim.CROSSBOW;
        }
        else {
            if(getType(stack) == types[1]) return UseAnim.BOW;
            else return UseAnim.BLOCK;
        }
    }
}
