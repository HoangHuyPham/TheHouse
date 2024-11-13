package engine.constant;

import engine.lifecycle.Mesh;
import engine.object.Vertex;
import org.joml.Vector2f;
import org.joml.Vector3f;
import utils.model.AssimpSupport;

public class Meshes {
    public static final Mesh BOX_INDICES = Mesh.builder().vertices(new Vertex[]{
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, 0.5f)).normal(new Vector3f(0.0f, 0.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, 0.5f)).normal(new Vector3f(0.0f, 0.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, 0.5f)).normal(new Vector3f(0.0f, 0.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, 0.5f)).normal(new Vector3f(0.0f, 0.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, -0.5f)).normal(new Vector3f(0.0f, 0.0f, -1.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, -0.5f)).normal(new Vector3f(0.0f, 0.0f, -1.0f)).build(),

            Vertex.builder().position(new Vector3f(0.5f, 0.5f, -0.5f)).normal(new Vector3f(0.0f, 0.0f, -1.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, -0.5f)).normal(new Vector3f(0.0f, 0.0f, -1.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, -0.5f)).normal(new Vector3f(-1.0f, 0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, 0.5f)).normal(new Vector3f(-1.0f, 0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, 0.5f)).normal(new Vector3f(-1.0f, 0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, -0.5f)).normal(new Vector3f(-1.0f, 0.0f, 0.0f)).build(),

            Vertex.builder().position(new Vector3f(0.5f, -0.5f, -0.5f)).normal(new Vector3f(1.0f, 0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, 0.5f)).normal(new Vector3f(1.0f, 0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, 0.5f)).normal(new Vector3f(1.0f, 0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, -0.5f)).normal(new Vector3f(1.0f, 0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, -0.5f)).normal(new Vector3f(0.0f, 1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, -0.5f)).normal(new Vector3f(0.0f, 1.0f, 0.0f)).build(),

            Vertex.builder().position(new Vector3f(0.5f, 0.5f, 0.5f)).normal(new Vector3f(0.0f, 1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, 0.5f)).normal(new Vector3f(0.0f, 1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, -0.5f)).normal(new Vector3f(0.0f, -1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, -0.5f)).normal(new Vector3f(0.0f, -1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, 0.5f)).normal(new Vector3f(0.0f, -1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, 0.5f)).normal(new Vector3f(0.0f, -1.0f, 0.0f)).build()
    }).indices(new int[]{
            0, 1, 2, 2, 3, 0,
            4, 5, 6, 6, 7, 4,
            8, 9, 10, 10, 11, 8,
            12, 13, 14, 14, 15, 12,
            16, 17, 18, 18, 19, 16,
            20, 21, 22, 22, 23, 20
    }).build();

    public static final Mesh BOX_NO_INDICES = Mesh.builder().vertices(new Vertex[]{
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, -0.5f)).normal(new Vector3f(0.0f, 0.0f, -1.0f)).texture(new Vector2f(0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, -0.5f)).normal(new Vector3f(0.0f, 0.0f, -1.0f)).texture(new Vector2f(1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, -0.5f)).normal(new Vector3f(0.0f, 0.0f, -1.0f)).texture(new Vector2f(1.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, -0.5f)).normal(new Vector3f(0.0f, 0.0f, -1.0f)).texture(new Vector2f(1.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, -0.5f)).normal(new Vector3f(0.0f, 0.0f, -1.0f)).texture(new Vector2f(0.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, -0.5f)).normal(new Vector3f(0.0f, 0.0f, -1.0f)).texture(new Vector2f(0.0f, 0.0f)).build(),

            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, 0.5f)).normal(new Vector3f(0.0f, 0.0f, 1.0f)).texture(new Vector2f(0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, 0.5f)).normal(new Vector3f(0.0f, 0.0f, 1.0f)).texture(new Vector2f(1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, 0.5f)).normal(new Vector3f(0.0f, 0.0f, 1.0f)).texture(new Vector2f(1.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, 0.5f)).normal(new Vector3f(0.0f, 0.0f, 1.0f)).texture(new Vector2f(1.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, 0.5f)).normal(new Vector3f(0.0f, 0.0f, 1.0f)).texture(new Vector2f(0.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, 0.5f)).normal(new Vector3f(0.0f, 0.0f, 1.0f)).texture(new Vector2f(0.0f, 0.0f)).build(),

            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, 0.5f)).normal(new Vector3f(-1.0f, 0.0f, 0.0f)).texture(new Vector2f(1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, -0.5f)).normal(new Vector3f(-1.0f, 0.0f, 0.0f)).texture(new Vector2f(1.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, -0.5f)).normal(new Vector3f(-1.0f, 0.0f, 0.0f)).texture(new Vector2f(0.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, -0.5f)).normal(new Vector3f(-1.0f, 0.0f, 0.0f)).texture(new Vector2f(0.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, 0.5f)).normal(new Vector3f(-1.0f, 0.0f, 0.0f)).texture(new Vector2f(0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, 0.5f)).normal(new Vector3f(-1.0f, 0.0f, 0.0f)).texture(new Vector2f(1.0f, 0.0f)).build(),

            Vertex.builder().position(new Vector3f(0.5f, 0.5f, 0.5f)).normal(new Vector3f(1.0f, 0.0f, 0.0f)).texture(new Vector2f(1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, -0.5f)).normal(new Vector3f(1.0f, 0.0f, 0.0f)).texture(new Vector2f(1.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, -0.5f)).normal(new Vector3f(1.0f, 0.0f, 0.0f)).texture(new Vector2f(0.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, -0.5f)).normal(new Vector3f(1.0f, 0.0f, 0.0f)).texture(new Vector2f(0.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, 0.5f)).normal(new Vector3f(1.0f, 0.0f, 0.0f)).texture(new Vector2f(0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, 0.5f)).normal(new Vector3f(1.0f, 0.0f, 0.0f)).texture(new Vector2f(1.0f, 0.0f)).build(),

            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, -0.5f)).normal(new Vector3f(0.0f, -1.0f, 0.0f)).texture(new Vector2f(0.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, -0.5f)).normal(new Vector3f(0.0f, -1.0f, 0.0f)).texture(new Vector2f(1.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, 0.5f)).normal(new Vector3f(0.0f, -1.0f, 0.0f)).texture(new Vector2f(1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, -0.5f, 0.5f)).normal(new Vector3f(0.0f, -1.0f, 0.0f)).texture(new Vector2f(1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, 0.5f)).normal(new Vector3f(0.0f, -1.0f, 0.0f)).texture(new Vector2f(0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, -0.5f, -0.5f)).normal(new Vector3f(0.0f, -1.0f, 0.0f)).texture(new Vector2f(0.0f, 1.0f)).build(),

            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, -0.5f)).normal(new Vector3f(0.0f, 1.0f, 0.0f)).texture(new Vector2f(0.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, -0.5f)).normal(new Vector3f(0.0f, 1.0f, 0.0f)).texture(new Vector2f(1.0f, 1.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, 0.5f)).normal(new Vector3f(0.0f, 1.0f, 0.0f)).texture(new Vector2f(1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(0.5f, 0.5f, 0.5f)).normal(new Vector3f(0.0f, 1.0f, 0.0f)).texture(new Vector2f(1.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, 0.5f)).normal(new Vector3f(0.0f, 1.0f, 0.0f)).texture(new Vector2f(0.0f, 0.0f)).build(),
            Vertex.builder().position(new Vector3f(-0.5f, 0.5f, -0.5f)).normal(new Vector3f(0.0f, 1.0f, 0.0f)).texture(new Vector2f(0.0f, 1.0f)).build()
    }).build();

    public static final Mesh BOX_FROM_OBJ = Mesh.parseMesh(AssimpSupport.loadObj("obj/box.obj"));
    public static final Mesh SUN_FROM_OBJ = Mesh.parseMesh(AssimpSupport.loadObj("obj/sun.obj"));
    public static final Mesh LAND_FROM_OBJ = Mesh.parseMesh(AssimpSupport.loadObj("obj/land.obj"));
    public static final Mesh MOUNTAIN_FROM_OBJ = Mesh.parseMesh(AssimpSupport.loadObj("obj/mountain.obj"));
    public static final Mesh HOUSE_FROM_OBJ = Mesh.parseMesh(AssimpSupport.loadObj("obj/house.obj"));
    public static final Mesh SCREEN_FROM_OBJ = Mesh.parseMesh(AssimpSupport.loadObj("obj/screen.obj"));

    public static void create(){
        BOX_NO_INDICES.create();
        BOX_INDICES.create();
        BOX_FROM_OBJ.create();
        SUN_FROM_OBJ.create();
        LAND_FROM_OBJ.create();
        MOUNTAIN_FROM_OBJ.create();
        HOUSE_FROM_OBJ.create();
        SCREEN_FROM_OBJ.create();
    }

    public static void destroyAll(){
        BOX_NO_INDICES.destroy();
        BOX_INDICES.destroy();
        BOX_FROM_OBJ.destroy();
        SUN_FROM_OBJ.destroy();
        LAND_FROM_OBJ.destroy();
        MOUNTAIN_FROM_OBJ.destroy();
        HOUSE_FROM_OBJ.destroy();
        SCREEN_FROM_OBJ.destroy();
    }
}
