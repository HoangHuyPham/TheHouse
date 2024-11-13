package engine.lighting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Vector3f;

@SuperBuilder
@Getter
@Setter
public class DirectionalLight extends Light{
    @Builder.Default
    protected Vector3f ambient = new Vector3f(0.5f);

    @Override
    public void onTick() {}
}
