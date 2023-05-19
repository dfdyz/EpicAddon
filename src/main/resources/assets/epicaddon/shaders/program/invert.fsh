#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseSampler2;
uniform float Time;

in vec2 texCoord;
out vec4 fragColor;

void main(){
    vec4 org = texture(DiffuseSampler, texCoord);
    vec4 ccc = texture(DiffuseSampler2, texCoord);
    float contrast = 2;

    float b = ccc.r * 0.3 + ccc.g * 0.59 + ccc.b * 0.11;

    vec3 color1 = org.rgb + vec3(0.15*(0.3-b));
    color1 = (color1 - vec3(0.5)) *  contrast + vec3(0.5);
    float a = color1.r * 0.3 + color1.g * 0.59 + color1.b * 0.11;

    a = a >= b ? 1:0;

    color1 = vec3(a,a,a);
    float t = max(1 - (0.15 - Time)/0.15, 1);
    vec3 color0 = org.rgb * (1-t) + color1 * t;

    fragColor = vec4(color0, org.w);
}