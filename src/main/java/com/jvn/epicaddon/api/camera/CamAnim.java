package com.jvn.epicaddon.api.camera;

import com.google.common.base.Utf8;
import com.google.gson.internal.Streams;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.compress.utils.Lists;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.text.Format;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class CamAnim {
    public final float linkTime;
    public final List<Key> keys_ = Lists.newArrayList();
    public final ResourceLocation resourceLocation;

    public CamAnim(float linkTime, ResourceLocation resourceLocation){
        this.resourceLocation = resourceLocation;
        this.linkTime = linkTime;
    }

    public CamAnim(float linkTime, String namespace, String path){
        this.resourceLocation = new ResourceLocation(namespace,path);
        this.linkTime = linkTime;
    }

    public void load(){
        System.out.println("[EpicAddon] CamAnimLoad: "+resourceLocation.getNamespace()+":"+resourceLocation.getPath());
        try {
            keys_.clear();
            String str = "";
            Resource resource = Minecraft.getInstance().getResourceManager().getResource(resourceLocation);

            InputStreamReader isr = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);

            int c = 0;
            while((c = isr.read()) != -1){
                str += (char)c;
            }

            //System.out.println(str);

            String[] strs = str.split("\n");

            for(int i =0; i< strs.length; ++i){
                if(strs[i] != "")
                {
                    keys_.add(Key.ReadFromText(strs[i]));
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pose getPose(float t){
        //System.out.println("t = "+ t);
        if(t >= getMaxTime()) return keys_.get(keys_.size() - 1).pose;
        Pose p1 = null;
        Pose p2 = null;
        float t_ = 0f;
        float _t = 0f;
        for (int i = 0; i < keys_.size(); i++) {
            if(keys_.get(i).time <= t){
                p1 = keys_.get(i).pose;
                t_ = keys_.get(i).time;
            }
            else{
                p2 = keys_.get(i).pose;
                _t = keys_.get(i).time;
                break;
            }
        }

        return Pose.lerp(p1,p2,(t-t_)/(_t - t_));
    }

    public float getMaxTime(){
        if (keys_.size() == 0) return 0;
        return keys_.get(keys_.size() - 1).time;
    }


    public static class Pose{
        public final Vec3 pos;
        public final float rotY;
        public final float rotX;
        public Pose(Vec3 pos, float rotX, float rotY){
            this.pos = pos;
            this.rotY = rotY;
            this.rotX = rotX;
        }

        public static Pose lerp(Pose p1, Pose p2, float t){
            double x = (p1.pos.x*(1-t) + p2.pos.x*t);
            double y = (p1.pos.y*(1-t) + p2.pos.y*t);
            double z = (p1.pos.z*(1-t) + p2.pos.z*t);
            Vec3 p = new Vec3(x,y,z);
            float _rotY = p1.rotY*(1-t) + p2.rotY*t;
            float _rotX = p1.rotX*(1-t) + p2.rotX*t;

            return new Pose(p, _rotX, _rotY);
        }

        @Override
        public String toString() {
            return "Pose{" +
                    "pos=" + pos +
                    ", rotY=" + rotY +
                    ", rotX=" + rotX +
                    '}';
        }


    }

    public static class Key{
        public final Pose pose;
        public final float time;

        public Key(Vec3 pos, float rotX, float rotY, float time){
            this.pose = new Pose(pos,rotX,rotY);
            this.time = time;
        }

        static Comparator<Key> cmp = new Comparator<Key>(){
            @Override
            public int compare(Key o1, Key o2) {
                return o1.time - o2.time > 0 ? 1 : -1;
            }
        };

        public static Key ReadFromText(String str){
            Scanner scanner = new Scanner(str);
            float x,y,z,rx,ry,time;
            x = scanner.nextFloat();
            y = scanner.nextFloat();
            z = scanner.nextFloat();
            rx = scanner.nextFloat();
            ry = scanner.nextFloat();
            time = scanner.nextFloat();

            return new Key(new Vec3(x,y,z), rx, ry, time);
        }
    }
}
