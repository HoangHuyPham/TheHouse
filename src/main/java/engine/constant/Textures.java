package engine.constant;

import engine.lifecycle.Texture;

public class Textures {
    public static final Texture WALL = new Texture("texture/wall.png");
    public static final Texture CONTAINER_TEX0 = new Texture("texture/container_tex0.png");
    public static final Texture CONTAINER_TEX1 = new Texture("texture/container_tex1.png");

    public static void create(){
        WALL.create();
        CONTAINER_TEX0.create();
        CONTAINER_TEX1.create();
    }

    public static void destroyAll(){
        WALL.destroy();
        CONTAINER_TEX0.destroy();
        CONTAINER_TEX1.destroy();
    }
}
