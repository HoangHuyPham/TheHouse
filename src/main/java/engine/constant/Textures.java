package engine.constant;

import engine.lifecycle.Texture;

public class Textures {
    public static final Texture WALL = new Texture("texture/wall.png");
    public static final Texture CONTAINER_0 = new Texture("texture/container_0.png");
    public static final Texture CONTAINER_1 = new Texture("texture/container_1.png");
    public static final Texture GRASS = new Texture("texture/grass.png");
    public static final Texture DIRT = new Texture("texture/dirt.png");
    public static final Texture HOUSE = new Texture("texture/house.png");
    public static final Texture CAMERA = new Texture("texture/camera.png");

    public static void create(){
        WALL.create();
        CONTAINER_0.create();
        CONTAINER_1.create();
        GRASS.create();
        DIRT.create();
        HOUSE.create();
        CAMERA.create();
    }

    public static void destroyAll(){
        WALL.destroy();
        CONTAINER_0.destroy();
        CONTAINER_1.destroy();
        GRASS.destroy();
        DIRT.destroy();
        HOUSE.destroy();
        CAMERA.destroy();
    }
}
