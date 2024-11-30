package engine.lifecycle;

import lombok.Builder;
import lombok.Getter;
import org.lwjgl.opengl.GL30;

@Getter
@Builder
public class ShadowFrameBuffer implements ELifeCycle{
    private int id, depthCompTexture, width, height;

    @Override
    public void create() {
        id = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, id);

        depthCompTexture = Texture.generateEmptyDepthCompTexture(width, height);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, depthCompTexture);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_TEXTURE_2D, depthCompTexture, 0);
        GL30.glDrawBuffer(GL30.GL_NONE);
        GL30.glReadBuffer(GL30.GL_NONE);

        if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE)
            System.err.println("Framebuffer is not complete!");
        else
            System.out.println("Framebuffer created: "+ id);

        GL30.glBindTexture(GL30.GL_TEXTURE_2D, 0);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    @Override
    public void destroy() {
        GL30.glDeleteFramebuffers(id);
    }
}
