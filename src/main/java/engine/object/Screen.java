package engine.object;

import engine.lifecycle.Texture;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Screen extends EObject{
    Texture texture;
    @Override
    public void onTick() {

    }
}
