package engine.object;

import engine.lighting.PointLight;
import engine.lighting.SpotLight;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Lamp extends BasicObject{
    PointLight pointLight;
    SpotLight spotLight;
}
