package engine.lighting;

import java.util.ArrayList;
import java.util.List;

public class Lighting {
    public static int MAX_LIGHT_NUM = 10;
    public static DirectionalLight DIRECTIONAL_LIGHT;
    public static final List<PointLight> POINT_LIGHTS = new ArrayList<>(10);
    public static final List<SpotLight> SPOT_LIGHTS = new ArrayList<>(10);
}
