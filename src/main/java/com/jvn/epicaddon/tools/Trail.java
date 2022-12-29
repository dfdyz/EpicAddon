package com.jvn.epicaddon.tools;

public class Trail {
    public final float x,y,z,ex,ey,ez;
    public final int r,g,b,a;

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
    public Trail(Trail org,Trail col){
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
}
