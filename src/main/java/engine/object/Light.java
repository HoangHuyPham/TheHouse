package engine.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Math;
import org.joml.Random;
import org.joml.Vector3f;

@Setter
@Getter
@SuperBuilder
public class Light extends EObject{
    @Builder.Default private Vector3f color = new Vector3f(1.0f);
    @Builder.Default private Vector3f ambient = new Vector3f(0.5f);
    @Builder.Default private Vector3f diffuse = new Vector3f(1f);
    @Builder.Default private Vector3f specular = new Vector3f(1f);
    @Builder.Default private float angle = 270;
    @Builder.Default private long time = System.currentTimeMillis();

    @Override
    public void onTick() {
//        angle = angle + 1 % 360;
        float radius = 999999f;
        float sunY = Math.cos(Math.toRadians(angle));
        float sunZ = Math.sin(Math.toRadians(angle));
        setPosition(new Vector3f(0, sunY, sunZ).mul(radius));
        setShouldUpdate(true);
    }
}