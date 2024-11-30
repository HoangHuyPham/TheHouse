package engine.object;

import lombok.experimental.SuperBuilder;
import org.joml.Vector3f;

@SuperBuilder
public class Spin extends BasicObject{
    long tickTimes;
    @Override
    public void onTick() {
        tickTimes %= 1;
        if (tickTimes == 0) {
            rotation.add(new Vector3f(0,0,1f));
            shouldUpdate = true;
        }
        tickTimes++;
    }
}
