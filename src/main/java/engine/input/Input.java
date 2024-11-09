package engine.input;

import engine.Window;
import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract class Input {
    @Builder.Default
    private boolean isActive = true;
    @NonNull
    protected Window window;
    public abstract void handle();
    public abstract void destroy();
}
