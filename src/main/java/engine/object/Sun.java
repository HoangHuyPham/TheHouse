package engine.object;

import engine.lighting.DirectionalLight;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Math;
import org.joml.Vector3f;

@SuperBuilder
@Setter
@Getter
public class Sun extends BasicObject  {
    @Builder.Default
    DirectionalLight sunLight = DirectionalLight.builder().build();
    @Builder.Default private float angleXY = 90;
    long tickTime;
    @Override
    public void onTick() {
        tickTime++;
        float sunX = Math.cos(Math.toRadians(angleXY));
        float sunY = Math.sin(Math.toRadians(angleXY));
        float sunZ = 999999f;
        setPosition(new Vector3f(sunX, sunY, 0).mul(sunZ));
        setShouldUpdate(true);
        sunLight.setPosition(new Vector3f(sunX, sunY, 0).mul(sunZ));
        sunLight.setShouldUpdate(true);

        if (tickTime % 2 ==0)
            angleXY = angleXY + 1 % 360;
    }
}
