package engine.lifecycle;

import lombok.Builder;
import lombok.Getter;
import org.lwjgl.opengl.GL30;

@Getter
@Builder
public class MiniMapFrameBuffer implements ELifeCycle{
    private int id, colorTexture, depthStencilTexture, width, height;

    @Override
    public void create() {
        id = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, id);

        colorTexture = Texture.generateEmptyColorTexture(width, height);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, colorTexture);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_TEXTURE_2D, colorTexture, 0);

        depthStencilTexture = Texture.generateEmptyDepthStencilTexture(width, height);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, depthStencilTexture);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_TEXTURE_2D, depthStencilTexture, 0);

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
