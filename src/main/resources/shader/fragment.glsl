#version 330 core
out vec4 FragColor;
in vec3 color;
in vec2 textureCoord;

uniform sampler2D textureSrc;

void main()
{
    if (textureCoord.x >= 0.0 && textureCoord.x <= 1.0 &&
        textureCoord.y >= 0.0 && textureCoord.y <= 1.0) {
        FragColor = texture(textureSrc, textureCoord);
    } else {
        FragColor = vec4(color, 1.0f);
    }
}