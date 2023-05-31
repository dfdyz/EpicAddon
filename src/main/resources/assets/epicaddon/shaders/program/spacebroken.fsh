#version 150

#define _PI_ 3.14159265359

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseSampler2;
uniform vec2 Crot;
uniform float cweight;
uniform float Time;

in vec2 texCoord;
out vec4 fragColor;

void main(){
    float a = Time;
    vec2 moved = (texCoord+Crot*a)/(1+a);
    vec4 cut = texture(DiffuseSampler2, texCoord);
    vec4 mov = texture(DiffuseSampler, moved);
    vec4 col = texture(DiffuseSampler, texCoord);

    if(cut.x > 0.1){
        col = mov + cut*cweight;
    }

    fragColor = vec4(col.rgb,1);
}
