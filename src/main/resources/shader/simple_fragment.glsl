#version 330 core
out vec4 FragColor;
uniform vec3 displayColor;

void main()
{
    FragColor = vec4(displayColor, 1.0);
}