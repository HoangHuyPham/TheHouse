package engine.object;

import engine.constant.Textures;
import engine.lifecycle.FrameBuffer;
import engine.lifecycle.Texture;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Screen extends EObject{
    FrameBuffer data;
    @Builder.Default Texture decorTexture = Textures.CAMERA;

    public void attachFrameBuffer(FrameBuffer frameBuffer) {
        if (data == null)
            data = frameBuffer;
    }

    @Override
    public void onTick() {}
}
