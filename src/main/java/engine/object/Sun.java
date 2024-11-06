package engine.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Math;
import org.joml.Vector3f;

@SuperBuilder
@Setter
@Getter
public class Sun extends Light{
    @Builder.Default private float angleXY = 90;
    @Builder.Default private Vector3f currentAmbient = new Vector3f();
    long tickTime;
    @Override
    public void onTick() {
        tickTime++;
        float ambientIntensity = Math.max(Math.sin(Math.toRadians(angleXY)), 0.5f);
        float sunX = Math.cos(Math.toRadians(angleXY));
        float sunY = Math.sin(Math.toRadians(angleXY));
        float sunZ = 999999f;
        ambient.mul(ambientIntensity, currentAmbient);
        setPosition(new Vector3f(sunX, sunY, 0).mul(sunZ));
        setShouldUpdate(true);
        if (tickTime % 2 ==0)
            angleXY = angleXY + 1 % 360;
    }
}
