package utils.file;

import engine.object.Obj;
import engine.object.Vertex;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class File {

    /**
     * @param filename
     * @return empty string if has exception or something like this
     */
    public static String readAllLines(String filename){
        try{
            URL url = File.class.getClassLoader().getResource(filename);
            java.io.File file = new java.io.File(url.getFile());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder lines = new StringBuilder();
            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                lines.append(line+"\r\n");
            }
            return lines.toString();
        }catch (Exception e){
            e.fillInStackTrace();
        }
        return "";
    }

    public static Obj getObj(String filename, boolean hasUVCoord){
        List<Vertex> vertices = new ArrayList<>();
        List<Integer> faces = new ArrayList<>();

        String data = readAllLines(filename);
        for(String line : data.split("\n")){
            String[] parts = line.split(" ");
            switch (parts[0]){
                case "v"->{
                    vertices.add(Vertex.builder().position(new Vector3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3]))).build());
                }
                case "f"->{
                    for (int i=1; i<parts.length; i++){
                        int value;
                        if (hasUVCoord){
                            value = Integer.parseInt(parts[i].split("/")[0].trim());
                        }else{
                            value = Integer.parseInt(parts[i].trim());
                        }
                        // Sub -1 for format of OpenGL
                        faces.add(value-1);
                    }
                }
            }
        }

        return Obj.create(vertices.toArray(new Vertex[0]), null, null, faces.toArray(new Integer[0]));
    }
}
