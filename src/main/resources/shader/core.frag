#version 330 core
out vec4 FragColor;

in vec2 textureCoord;
in vec3 normal;
in vec3 fragPos;
in vec4 fragPosLightSpace;

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
uniform sampler2D depthMap;
uniform bool useTexture;
uniform Material material;
uniform DirectionalLight dLight;
uniform PointLight pLights[P_LIGHT_NUM];
uniform SpotLight sLights[S_LIGHT_NUM];
uniform Camera camera;

vec3 getDirectional();
vec3 getPointLight();
vec3 getSpotLight();
float ShadowCalculation(vec4 fragPosLSpace, float bias);
bool isNearestPLight(vec3 lightPos);

void main()
{

    vec4 texture0_ = texture(texture0, textureCoord);
    vec4 texture1_ = texture(texture1, textureCoord);

    vec3 result = getPointLight() + getDirectional();

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
            float shadow = ShadowCalculation(fragPosLightSpace, max(0.0015 * (1.0 - dot(normalize(normal), lightDirect)), 0.0005));
            vec3 lighting = (ambient + (1.0 - shadow) * (diffuse)) * pLights[i].color;
            result += lighting;
        }
    }
    return result;
}


// Check nearest light
bool isNearestPLight(vec3 lightPos) {
    float lightDistance = length(lightPos - fragPos); // Distance of the given light

    for (int i = 0; i < P_LIGHT_NUM; i++) {
        // If any other light is closer, return false
        if (length(pLights[i].position - fragPos) < lightDistance) {
            return false;
        }
    }

    return true; // The light is the nearest
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

float ShadowCalculation(vec4 fragPosLSpace, float bias)
{
    // perform perspective divide
    vec3 projCoords = fragPosLSpace.xyz / fragPosLSpace.w;
    // transform to [0,1] range
    projCoords = projCoords * 0.5 + 0.5;
    // get closest depth value from light's perspective (using [0,1] range fragPosLight as coords)
    float closestDepth = texture(depthMap, projCoords.xy).r;
    // get depth of current fragment from light's perspective
    float currentDepth = projCoords.z;
    // check whether current frag pos is in shadow

    float shadow = currentDepth - bias > closestDepth  ? 1.0 : 0.0;

    if(projCoords.z > 1.0)
        shadow = 0.0;

return shadow;
}
