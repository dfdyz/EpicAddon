package com.jvn.epicaddon.events;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.api.camera.CamAnim;
import com.jvn.epicaddon.mixin.CameraAccessor;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpicAddon.MODID, value = Dist.CLIENT)
public class CameraEvent {
    private static float yawLock = 0f;
    private static Vec3 posLock = new Vec3(0,0,0);
    public static CamAnim currentAnim;
    private static int tick = 0;
    private static boolean isEnd = true;
    private static boolean linking = false;

    private static final Vec3 Vec3UP = new Vec3(0,1f,0);

    private static CamAnim.Pose pose_;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void TransformCam(EntityViewRenderEvent.CameraSetup event){
        if ((isEnd && !linking) || currentAnim == null) {
            //yawLock = event.getYaw();
            return;
        }

        Camera camera = event.getCamera();
        CameraAccessor cameraAccessor = (CameraAccessor) camera;
        double partialTicks = event.getPartialTicks();

        CamAnim.Pose pose = currentAnim.getPose((tick + (float)partialTicks)/20f);
        //System.out.println((tick + (float)partialTicks)/20f);

        //Vec3 curPos = camera.getPosition();
        Vec3 camPos = (pose.pos.yRot((float) Math.toRadians(-yawLock-90f))).add(posLock);

        //Vec3 p = camPos.subtract(curPos);


        //System.out.println(pose);

        //cameraAccessor.invokeMove(p.x,p.y,p.z);
        cameraAccessor.invokeSetRotation(yawLock-pose.rotY, pose.rotX);
        cameraAccessor.invokeSetPosition(camPos.x,camPos.y,camPos.z);
        //event.move
        event.setYaw(yawLock-pose.rotY);
        event.setPitch(pose.rotX);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void Tick(TickEvent.ClientTickEvent event){
        if (event.phase == TickEvent.Phase.START && !isEnd) tick++;
        if(event.phase == TickEvent.Phase.START && !linking) tick++;

        if(!isEnd && currentAnim != null && tick/20f >= currentAnim.getMaxTime()){
            isEnd = true;
            tick = 0;
        }
    }
    public static void SetAnim(CamAnim anim, LivingEntity org){
        if(org instanceof Player){
            if (!((Player) org).isLocalPlayer()) return;
        }
        else {
            return;
        }
        CameraEvent.yawLock = org.getViewYRot(0);
        CameraEvent.posLock = org.position();
        linking = false;
        isEnd = false;
        tick = 0;
        currentAnim = anim;
    }
}
