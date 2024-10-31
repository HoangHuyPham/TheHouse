package engine.constant;

import engine.lifecycle.Mesh;
import engine.object.Vertex;
import org.joml.Vector2f;
import org.joml.Vector3f;
import utils.file.File;

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

    public static final Mesh BOX_FROM_OBJ = Mesh.parseMesh(File.getObj("obj/box.obj", false));
    public static final Mesh SPHRERE_FROM_OBJ = Mesh.parseMesh(File.getObj("obj/sphere.obj", true));

    public static void create(){
        BOX_NO_INDICES.create();
        BOX_INDICES.create();
        BOX_FROM_OBJ.create();
        SPHRERE_FROM_OBJ.create();
    }

    public static void destroyAll(){
        BOX_NO_INDICES.destroy();
        BOX_INDICES.destroy();
        BOX_FROM_OBJ.destroy();
        SPHRERE_FROM_OBJ.destroy();
    }
}
