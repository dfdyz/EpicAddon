package com.jvn.epicaddon.api.PostEffect;

import com.google.common.collect.Sets;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.utils.LocationUtils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Matrix4f;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;

public class ShaderProgram {
    public static final HashSet<ShaderProgram> Registries = Sets.newHashSet();

    public static ShaderProgram register(ShaderProgram shaderProgram){
        Registries.add(shaderProgram);
        return shaderProgram;
    }
    public static void LoadAll(){
        Registries.forEach(obj -> {
            obj.CompileShader();
        });
    }

    public static ShaderProgram Blit = register(new ShaderProgram(EpicAddon.MODID, "shaders/program/blit"));

    protected int programId;
    protected ResourceLocation vsh;
    protected ResourceLocation fsh;

    protected FloatBuffer fb = MemoryUtil.memAllocFloat(16);
    protected boolean init = false;

    public ShaderProgram(String namespace, String vshPath, String fshPath){
        vsh = new ResourceLocation(namespace, vshPath + ".vsh");
        fsh = new ResourceLocation(namespace, fshPath + ".fsh");
    }

    public ShaderProgram(String namespace, String path){
        vsh = new ResourceLocation(namespace, path + ".vsh");
        fsh = new ResourceLocation(namespace, path + ".fsh");
    }

    public void CompileShader(){
        if(init){
            GlStateManager.glDeleteProgram(programId);
        }

        //Compile vsh & fsh
        int vertex, fragment;

        EpicAddon.LOGGER.info("Compile Vertex Shader: " + vsh);
        String vshCode = LocationUtils.ReadString(vsh);
        vertex = GlStateManager.glCreateShader(GL20.GL_VERTEX_SHADER);
        GlStateManager.glShaderSource(vertex, Collections.singletonList(vshCode));
        GlStateManager.glCompileShader(vertex);

        EpicAddon.LOGGER.info("Compile Fragment Shader: " + fsh);
        String fshCode = LocationUtils.ReadString(fsh);
        fragment = GlStateManager.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GlStateManager.glShaderSource(fragment, Collections.singletonList(fshCode));
        GlStateManager.glCompileShader(fragment);

        //create & link program
        programId = GlStateManager.glCreateProgram();
        GlStateManager.glAttachShader(programId, vertex);
        GlStateManager.glAttachShader(programId, fragment);
        GlStateManager.glLinkProgram(programId);

        //clean
        GlStateManager.glDeleteShader(vertex);
        GlStateManager.glDeleteShader(fragment);

        init = true;
    }

    public void Active(){
        if(init) GlStateManager._glUseProgram(programId);
    }

    public int getProgramId() {
        return programId;
    }

    protected int GetUniformLocation(String name){
        return GlStateManager._glGetUniformLocation(programId, name);
    }

    public void SetInt(String name, int f){
        GL20.glUniform1i(GetUniformLocation(name), f);
    }

    public void SetVec2i(String name, int f1, int f2){
        GL20.glUniform2i(GetUniformLocation(name), f1, f2);
    }

    public void SetVec3i(String name, int f1, int f2, int f3){
        GL20.glUniform3i(GetUniformLocation(name), f1, f2, f3);
    }

    public void SetVec4i(String name, int f1, int f2, int f3, int f4){
        GL20.glUniform4i(GetUniformLocation(name), f1, f2, f3, f4);
    }

    public void SetFloat(String name, float f){
        GL20.glUniform1f(GetUniformLocation(name), f);
    }

    public void SetVec2f(String name, float f1, float f2){
        GL20.glUniform2f(GetUniformLocation(name), f1, f2);
    }

    public void SetVec3f(String name, float f1, float f2, float f3){
        GL20.glUniform3f(GetUniformLocation(name), f1, f2, f3);
    }

    public void SetVec4f(String name, float f1, float f2, float f3, float f4){
        GL20.glUniform4f(GetUniformLocation(name), f1, f2, f3, f4);
    }

    public void SetMat4x4f(String name, Matrix4f matrix4f){
        this.fb.position(0);
        matrix4f.store(this.fb);
        GL20.glUniformMatrix4fv(GetUniformLocation(name), false, fb);
    }

    public void SetSampler(String name, int textureId){
        SetInt(name, textureId);
    }

    public static class ShaderStack{
        Stack<Integer> stack;

        public ShaderStack(){
            stack = new Stack<>();
            stack.push(RenderSystem.getShader().getId());
        }

        public void PushShader(ShaderProgram s){
            stack.push(s.getProgramId());
            s.Active();
        }

        public void PopShader(){
            if(stack.size() > 0) stack.pop();
            else return;
            if (stack.size() > 0){
                GlStateManager._glUseProgram(stack.peek());
            }
        }
    }




}
