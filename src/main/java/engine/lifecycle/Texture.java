package engine.lifecycle;

import lombok.Getter;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Objects;

@Getter
public class Texture implements ELifeCycle {
    private final String filename;
    private int textureId;

    public Texture(String fileName){
        this.filename = fileName;
    }

    @Override
    public void create(){
        textureId = loadTexture(this.filename);
        System.out.println("Texture created: " + textureId + (filename == null ? "" : " (" + filename + ")"));
    }

    @Override
    public void destroy(){
        GL30.glDeleteTextures(textureId);
    }

    public int loadTexture(String fileName) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(Objects.requireNonNull(Texture.class.getClassLoader().getResource(fileName)).toURI()));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return 0;
        }

        // Get image dimensions and color data
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        // Create a ByteBuffer for the texture data
        ByteBuffer buffer = ByteBuffer.allocateDirect(width * height * 4); // 4 bytes per pixel (RGBA)

        // Convert pixel data to the buffer
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixels[y * width + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red
                buffer.put((byte) ((pixel >> 8) & 0xFF));  // Green
                buffer.put((byte) (pixel & 0xFF));         // Blue
                buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha
            }
        }

        buffer.flip();

        int textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        return textureID;
    }


}
