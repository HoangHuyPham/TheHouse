package engine.lighting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Vector3f;

@SuperBuilder
@Setter
@Getter
public class SpotLight extends Light{
    @Builder.Default private Vector3f ambient = new Vector3f(0.2f);
    @Builder.Default float cutOff = 15f, outerCutOff = 17f;
    private boolean isActive;
    @Override
    public void onTick() {}
}
