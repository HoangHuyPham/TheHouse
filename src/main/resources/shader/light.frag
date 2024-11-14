#version 330 core
out vec4 FragColor;

uniform sampler2D texture0;
uniform float intensity;

in vec2 texCoord;

void main()
{
    vec4 texture0_ = texture(texture0, texCoord);
    if (dot(vec3(texture0_), vec3(1)) == 0){
        FragColor = vec4(1.0);
    }else{
        FragColor = texture0_ * intensity;
    }
}