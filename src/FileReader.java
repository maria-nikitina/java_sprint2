import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.List;

public class FileReader {
    String readFileContents(String fileName) {
        String path = "./resources/" + fileName;
        String output = "";
        try {
            return output = Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с отчётом. Возможно, файл отсутствует в нужной директории.");
            return output;
        }
    }

} 