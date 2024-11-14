#version 330 core
out vec4 FragColor;

in vec2 textureCoord;
in vec3 normal;
in vec3 fragPos;

struct Material {
    vec3 ambient, diffuse, specular;
    float shininess;
};

struct DirectionalLight {
    vec3 position, color, ambient, diffuse, specular;
    float intensity;
};

struct PointLight {
    bool isActive;
    vec3 position, color, ambient, diffuse, specular;
    float intensity, constant, linear, quadratic;
};

struct SpotLight {
    bool isActive;
    vec3 position, color, ambient, diffuse, specular, direction;
    float outerCutOff, cutOff;
};

struct Camera {
    vec3 position;
};

const int P_LIGHT_NUM = 10;
const int S_LIGHT_NUM = 10;

uniform sampler2D texture0;
uniform sampler2D texture1;
uniform bool useTexture;
uniform Material material;
uniform DirectionalLight dLight;
uniform PointLight pLights[P_LIGHT_NUM];
uniform SpotLight sLights[S_LIGHT_NUM];
uniform Camera camera;

vec3 getDirectional();
vec3 getPointLight();
vec3 getSpotLight();

void main()
{

    vec4 texture0_ = texture(texture0, textureCoord);
    vec4 texture1_ = texture(texture1, textureCoord);

    vec3 result = getDirectional() + getPointLight() + getSpotLight();

    if (dot(vec3(texture0_), vec3(1)) == 0 && dot(vec3(texture1_), vec3(1)) == 0){
        FragColor = vec4(result, 1.0);
    }else{
        FragColor = (texture0_ + texture1_) * vec4(result, 1.0);
    }

}

// Directional Light
vec3 getDirectional(){
    // ambient
    vec3 ambient = material.ambient * dLight.ambient;

    // diffuse
    vec3 lightDirect = normalize(dLight.position - fragPos);
    float diff = max(dot(normal, lightDirect), 0.0);
    vec3 diffuse = diff * dLight.diffuse * material.diffuse;

    // specular
    vec3 cameraDirect = normalize(camera.position - fragPos);
    vec3 reflectDirect = reflect(-lightDirect, normal);
    float spec = pow(max(dot(cameraDirect, reflectDirect), 0.0), material.shininess * 128);
    vec3 specular = material.specular * dLight.specular * spec;

    // result
    vec3 result = (ambient + diffuse + specular) * dLight.intensity * dLight.color;

    return result;
}

// Point lights
vec3 getPointLight(){
    vec3 result = vec3(0);
    for (int i=0; i < P_LIGHT_NUM; i++){
        // ambient
        vec3 ambient = material.ambient * pLights[i].ambient;

        // diffuse
        vec3 lightDirect = normalize(pLights[i].position - fragPos);
        float diff = max(dot(normal, lightDirect), 0.0);
        vec3 diffuse = diff * pLights[i].diffuse * material.diffuse;

        // specular
        vec3 cameraDirect = normalize(camera.position - fragPos);
        vec3 reflectDirect = reflect(-lightDirect, normal);
        float spec = pow(max(dot(cameraDirect, reflectDirect), 0.0), material.shininess * 128);
        vec3 specular = material.specular * pLights[i].specular * spec;

        // attenuation
        float distance = length(pLights[i].position - fragPos);
        float attenuation = 1.0 / (pLights[i].constant + pLights[i].linear * distance + pLights[i].quadratic * (distance * distance));

        if (pLights[i].isActive){
            result += (ambient + diffuse + specular) * attenuation;
        }
    }
    return result;
}

// Spot light
vec3 getSpotLight(){
    vec3 result = vec3(0);
    for (int i=0; i < S_LIGHT_NUM; i++){
        // ambient
        vec3 ambient = material.ambient * sLights[i].ambient;

        // diffuse
        vec3 lightDirect = normalize(sLights[i].position - fragPos);
        float diff = max(dot(normal, lightDirect), 0.0);
        vec3 diffuse = diff * sLights[i].diffuse * material.diffuse;

        // specular
        vec3 cameraDirect = normalize(camera.position - fragPos);
        vec3 reflectDirect = reflect(-lightDirect, normal);
        float spec = pow(max(dot(cameraDirect, reflectDirect), 0.0), material.shininess * 128);
        vec3 specular = material.specular * sLights[i].specular * spec;

        if (sLights[i].isActive){
            float theta     = dot(lightDirect, normalize(-sLights[i].direction));
            float epsilon   =  sLights[i].cutOff - sLights[i].outerCutOff ;
            float intensity = clamp((theta - sLights[i].outerCutOff) / epsilon, 0, 1.0);
            result += (ambient + diffuse + specular) * intensity;
        }
    }
    return result;
}
