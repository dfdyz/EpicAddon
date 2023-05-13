#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

uniform float InverseAmount;

out vec4 fragColor;

void main(){
    vec4 org = texture(DiffuseSampler, texCoord);
    vec4 rgba = org.rgba;
    float l = rgba.r * 0.3 + rgba.g * 0.59 + rgba.b * 0.11;

    l = l>0.5f ? 0 : 1;

    fragColor = vec4(l,l,l, 1.0);
}
