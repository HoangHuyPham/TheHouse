#version 330 core
out vec4 FragColor;

in vec2 texCoords;

uniform sampler2D texture0;
uniform sampler2D texture1;

void main()
{
    vec4 texture0_ = texture(texture0, texCoords);
    vec4 texture1_ = texture(texture1, texCoords);

    if (texture1_.a != 0) {
        FragColor = texture(texture1, texCoords);
    } else {
        FragColor = texture(texture0, texCoords);
    }
}