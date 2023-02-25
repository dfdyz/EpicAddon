package com.jvn.epicaddon.utils;

public class HealthBarStyle {
    public float r,y_modify,ang;
    public int count;

    public HealthBarStyle(float r,int count,float y_modify,float ang){
        this.r = r;
        this.count = count;
        this.y_modify = y_modify;
        this.ang = ang;
    }

    public HealthBarStyle(){
        this(0.9f,1,0.1f,110f);
    }

}
