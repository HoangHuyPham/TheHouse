package utils.model;

import engine.object.Obj;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.assimp.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

public class AssimpSupport {
    public static Obj loadObj(String filename) {

        AIScene scene = Assimp.aiImportFile(AssimpSupport.class.getClassLoader().getResource(filename).getFile().substring(1), Assimp.aiProcess_Triangulate | Assimp.aiProcess_JoinIdenticalVertices);
        if (scene == null) {
            return null;
        }
        AIMesh mesh = AIMesh.create(scene.mMeshes().get(0));
        AIFace.Buffer aFaces = mesh.mFaces();
        AIVector3D.Buffer aNormals = mesh.mNormals();
        AIVector3D.Buffer aPos = mesh.mVertices();
        AIVector3D.Buffer aTexCoords = mesh.mTextureCoords(0);

        Vector3f[] position = new Vector3f[mesh.mNumVertices()], normals = new Vector3f[mesh.mNumVertices()];
        Vector2f[] texCoords = new Vector2f[mesh.mNumVertices()];
        IntBuffer indices = MemoryUtil.memAllocInt(aFaces.limit() * aFaces.mNumIndices());

        for (int i=0; i<mesh.mNumVertices(); i++){
            position[i] = new Vector3f(aPos.get(i).x(), aPos.get(i).y(), aPos.get(i).z());
            if (aNormals != null)
                normals[i] = new Vector3f(aNormals.get(i).x(), aNormals.get(i).y(), aNormals.get(i).z());
            if (aTexCoords != null)
                texCoords[i] = new Vector2f(aTexCoords.get(i).x(), aTexCoords.get(i).y());
        }

        for (int i=0; i<aFaces.limit(); i++){
            indices.put(aFaces.get(i).mIndices().get(0));
            indices.put(aFaces.get(i).mIndices().get(1));
            indices.put(aFaces.get(i).mIndices().get(2));
        }
        indices.flip();
        return Obj.builder().positions(position).normals(normals).indices(indices).texCoords(texCoords).build();
    }

    public static void main(String[] args) {
        loadObj("obj/terrain.obj");
    }
}
