#version 330 core
out vec4 FragColor;

in vec3 color;
in vec2 textureCoord;
in vec3 normal;
in vec3 fragPos;

struct Material {
    vec3 ambient, diffuse, specular;
    float shininess;
};

struct Light {
    vec3 position, color, ambient, diffuse, specular;
};

struct Camera {
    vec3 position;
};

uniform sampler2D textureUnit;
uniform bool useTexture;
uniform Material material;
uniform Light light;
uniform Camera camera;

void main()
{
    // ambient
    vec3 ambient = material.ambient * light.ambient;

    // diffuse
    vec3 lightDirect = normalize(light.position - fragPos);
    float diff = max(dot(normal, lightDirect), 0.0);

    vec3 diffuse = diff * light.diffuse * material.diffuse;

    // specular
    vec3 cameraDirect = normalize(camera.position - fragPos);
    vec3 reflectDirect = reflect(-lightDirect, normal);
    float spec = pow(max(dot(cameraDirect, reflectDirect), 0.0), material.shininess * 128);

    vec3 specular = material.specular * light.specular * spec;

    // result
    vec3 result = (ambient + diffuse + specular) * color * light.color;

    if (useTexture)
    {
        FragColor = texture(textureUnit, textureCoord) * vec4(result, 1.0);
    }
    else
    {
        FragColor = vec4(result, 1.0);
    }

}

