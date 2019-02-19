package utils.validate_transform;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    private final static String encoding = "UTF-8";

    public File createFile(String filecontent, String fileName) {
        File result = new File(fileName);
        try {
            result.createNewFile();
            PrintWriter writer = new PrintWriter(result, encoding);
            writer.println(filecontent);
            writer.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String readFileContents(String filePath) {
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}
