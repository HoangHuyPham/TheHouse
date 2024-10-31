package engine;

public class Textures {
    public static final Texture WALL_TEXTURE = new Texture("wall.png");

    public static void create(){
        WALL_TEXTURE.create();
    }

    public static void destroyAll(){
        WALL_TEXTURE.destroy();
    }
}
