package engine.object;

import engine.constant.Textures;
import engine.lifecycle.MiniMapFrameBuffer;
import engine.lifecycle.ShadowFrameBuffer;
import engine.lifecycle.Texture;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Screen extends EObject{
    MiniMapFrameBuffer data;
    ShadowFrameBuffer shadow;
    @Builder.Default Texture decorTexture = Textures.CAMERA;

    public void attachFrameBuffer(MiniMapFrameBuffer frameBuffer) {
        if (data == null)
            data = frameBuffer;
    }

    public void attachFrameBuffer(ShadowFrameBuffer frameBuffer) {
        if (shadow == null)
            shadow = frameBuffer;
    }

    @Override
    public void onTick() {}
}
