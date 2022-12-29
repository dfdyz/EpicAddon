package com.jvn.epicaddon.tools;

public class Trail {
    public float x,y,z,ex,ey,ez;
    public int r,g,b,a;

    public Trail(float x, float y, float z, float ex, float ey, float ez,int r,int g,int b,int a){
        this.x = x;
        this.y = y;
        this.z = z;
        this.ex = ex;
        this.ey = ey;
        this.ez = ez;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    public void Mix(Trail org,Trail col){
        this.x = org.x;
        this.y = org.y;
        this.z = org.z;
        this.ex = org.ex;
        this.ey = org.ey;
        this.ez = org.ez;
        this.r = col.r;
        this.g = col.g;
        this.b = col.b;
        this.a = col.a;
    }

    public void Copy(Trail org){
        this.x = org.x;
        this.y = org.y;
        this.z = org.z;
        this.ex = org.ex;
        this.ey = org.ey;
        this.ez = org.ez;
        this.r = org.r;
        this.g = org.g;
        this.b = org.b;
        this.a = org.a;
    }

    public Trail(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.ex = 0;
        this.ey = 0;
        this.ez = 0;
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.a = 0;
    }

    public void Clear(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.ex = 0;
        this.ey = 0;
        this.ez = 0;
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.a = 0;
    }


}
