package engine.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class BasicObject extends EObject {
    @Builder.Default
    Material material = Materials.EMERALD;
}
