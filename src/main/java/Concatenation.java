import java.io.*;
import java.util.LinkedList;

/**
 * Класс для конкатенации.
 */
public class Concatenation {
    /**
     * Выполняет конкатенацию списка файлов.
     * @param fileNames список имен файлов.
     * @throws IOException возникает при невозможности чтения файлов из списка или записи в файл.
     */
    public static void concatenateFiles(LinkedList<String> fileNames) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(".\\src\\main\\resources\\result.txt"));

        for (String fileName : fileNames) {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + '\n');
            }
            reader.close();
        }
        writer.close();
    }
}
