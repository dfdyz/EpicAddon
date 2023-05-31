#version 150

#define _PI_ 3.14159265359

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthTex;
uniform vec2 Crot;
uniform float Time;

in vec2 texCoord;
out vec4 fragColor;

void main(){
    float a = Time;
    vec2 moved = (texCoord+Crot*a)/(1+a);
    vec4 cut = texture(DepthTex, texCoord);
    vec4 mov = texture(DiffuseSampler, moved);
    vec4 col = texture(DiffuseSampler, texCoord);

    if(cut.x > 0.1){
        col = mov + cut*0.2;
    }

    fragColor = vec4(col.rgb,1);
}
