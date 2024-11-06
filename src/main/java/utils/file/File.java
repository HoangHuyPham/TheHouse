package utils.file;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;

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
}
