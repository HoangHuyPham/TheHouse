package engine.object;

import org.joml.Vector3f;

public class Materials {
    public static final Material EMERALD = Material.builder().ambient(new Vector3f(0.0215f, 0.1745f, 0.0215f)).diffuse(new Vector3f(0.07568f, 0.61424f, 0.07568f)).specular(new Vector3f(0.633f, 0.727811f, 0.633f)).shininess(0.6f).build();
    public static final Material JADE = Material.builder().ambient(new Vector3f(0.135f, 0.2225f, 0.1575f)).diffuse(new Vector3f(0.54f, 0.89f, 0.63f)).specular(new Vector3f(0.316228f, 0.316228f, 0.316228f)).shininess(0.1f).build();
    public static final Material OBSIDIAN = Material.builder().ambient(new Vector3f(0.05375f, 0.05f, 0.06625f)).diffuse(new Vector3f(0.18275f, 0.17f, 0.22525f)).specular(new Vector3f(0.332741f, 0.328634f, 0.346435f)).shininess(0.3f).build();
    public static final Material PEARL = Material.builder().ambient(new Vector3f(0.25f, 0.20725f, 0.20725f)).diffuse(new Vector3f(1.0f, 0.829f, 0.829f)).specular(new Vector3f(0.296648f, 0.296648f, 0.296648f)).shininess(0.088f).build();
    public static final Material RUBY = Material.builder().ambient(new Vector3f(0.1745f, 0.01175f, 0.01175f)).diffuse(new Vector3f(0.61424f, 0.04136f, 0.04136f)).specular(new Vector3f(0.727811f, 0.626959f, 0.626959f)).shininess(0.6f).build();
    public static final Material TURQUOISE = Material.builder().ambient(new Vector3f(0.1f, 0.18725f, 0.1745f)).diffuse(new Vector3f(0.396f, 0.74151f, 0.69102f)).specular(new Vector3f(0.297254f, 0.30829f, 0.306678f)).shininess(0.1f).build();
    public static final Material BRASS = Material.builder().ambient(new Vector3f(0.329412f, 0.223529f, 0.027451f)).diffuse(new Vector3f(0.780392f, 0.568627f, 0.113725f)).specular(new Vector3f(0.992157f, 0.941176f, 0.807843f)).shininess(0.21794872f).build();
    public static final Material BRONZE = Material.builder().ambient(new Vector3f(0.2125f, 0.1275f, 0.054f)).diffuse(new Vector3f(0.714f, 0.4284f, 0.18144f)).specular(new Vector3f(0.393548f, 0.271906f, 0.166721f)).shininess(0.2f).build();
    public static final Material CHROME = Material.builder().ambient(new Vector3f(0.25f, 0.25f, 0.25f)).diffuse(new Vector3f(0.4f, 0.4f, 0.4f)).specular(new Vector3f(0.774597f, 0.774597f, 0.774597f)).shininess(0.6f).build();
    public static final Material COPPER = Material.builder().ambient(new Vector3f(0.19125f, 0.0735f, 0.0225f)).diffuse(new Vector3f(0.7038f, 0.27048f, 0.0828f)).specular(new Vector3f(0.256777f, 0.137622f, 0.086014f)).shininess(0.1f).build();
    public static final Material GOLD = Material.builder().ambient(new Vector3f(0.24725f, 0.1995f, 0.0745f)).diffuse(new Vector3f(0.75164f, 0.60648f, 0.22648f)).specular(new Vector3f(0.628281f, 0.555802f, 0.366065f)).shininess(0.4f).build();
    public static final Material SILVER = Material.builder().ambient(new Vector3f(0.19225f, 0.19225f, 0.19225f)).diffuse(new Vector3f(0.50754f, 0.50754f, 0.50754f)).specular(new Vector3f(0.508273f, 0.508273f, 0.508273f)).shininess(0.4f).build();
    public static final Material BLACK_PLASTIC = Material.builder().ambient(new Vector3f(0.0f, 0.0f, 0.0f)).diffuse(new Vector3f(0.01f, 0.01f, 0.01f)).specular(new Vector3f(0.5f, 0.5f, 0.5f)).shininess(0.25f).build();
    public static final Material CYAN_PLASTIC = Material.builder().ambient(new Vector3f(0.0f, 0.1f, 0.06f)).diffuse(new Vector3f(0.0f, 0.50980392f, 0.50980392f)).specular(new Vector3f(0.50196078f, 0.50196078f, 0.50196078f)).shininess(0.25f).build();
    public static final Material GREEN_PLASTIC = Material.builder().ambient(new Vector3f(0.0f, 0.0f, 0.0f)).diffuse(new Vector3f(0.1f, 0.35f, 0.1f)).specular(new Vector3f(0.45f, 0.55f, 0.45f)).shininess(0.25f).build();
    public static final Material RED_PLASTIC = Material.builder().ambient(new Vector3f(0.0f, 0.0f, 0.0f)).diffuse(new Vector3f(0.5f, 0.0f, 0.0f)).specular(new Vector3f(0.7f, 0.6f, 0.6f)).shininess(0.25f).build();
    public static final Material WHITE_PLASTIC = Material.builder().ambient(new Vector3f(0.0f, 0.0f, 0.0f)).diffuse(new Vector3f(0.55f, 0.55f, 0.55f)).specular(new Vector3f(0.7f, 0.7f, 0.7f)).shininess(0.25f).build();
    public static final Material YELLOW_PLASTIC = Material.builder().ambient(new Vector3f(0.0f, 0.0f, 0.0f)).diffuse(new Vector3f(0.5f, 0.5f, 0.0f)).specular(new Vector3f(0.6f, 0.6f, 0.5f)).shininess(0.25f).build();
    public static final Material BLACK_RUBBER = Material.builder().ambient(new Vector3f(0.02f, 0.02f, 0.02f)).diffuse(new Vector3f(0.01f, 0.01f, 0.01f)).specular(new Vector3f(0.4f, 0.4f, 0.4f)).shininess(0.078125f).build();
    public static final Material CYAN_RUBBER = Material.builder().ambient(new Vector3f(0.0f, 0.05f, 0.05f)).diffuse(new Vector3f(0.4f, 0.5f, 0.5f)).specular(new Vector3f(0.04f, 0.7f, 0.7f)).shininess(0.078125f).build();
    public static final Material GREEN_RUBBER = Material.builder().ambient(new Vector3f(0.0f, 0.05f, 0.0f)).diffuse(new Vector3f(0.4f, 0.5f, 0.4f)).specular(new Vector3f(0.04f, 0.7f, 0.04f)).shininess(0.078125f).build();
    public static final Material RED_RUBBER = Material.builder().ambient(new Vector3f(0.05f, 0.0f, 0.0f)).diffuse(new Vector3f(0.5f, 0.4f, 0.4f)).specular(new Vector3f(0.7f, 0.04f, 0.04f)).shininess(0.078125f).build();
    public static final Material WHITE_RUBBER = Material.builder().ambient(new Vector3f(0.05f, 0.05f, 0.05f)).diffuse(new Vector3f(0.5f, 0.5f, 0.5f)).specular(new Vector3f(0.7f, 0.7f, 0.7f)).shininess(0.078125f).build();
    public static final Material YELLOW_RUBBER = Material.builder().ambient(new Vector3f(0.05f, 0.05f, 0.0f)).diffuse(new Vector3f(0.5f, 0.5f, 0.4f)).specular(new Vector3f(0.7f, 0.7f, 0.04f)).shininess(0.078125f).build();
}
