package engine.object;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class BasicObject extends EObject {
    @Override
    public void onTick() {}
}
