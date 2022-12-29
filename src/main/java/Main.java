import java.io.*;

public class Main {
    public static void main(String[] args) {
        DirectoryHandler directoryHandler = new DirectoryHandler();
        directoryHandler.formFileNodesMap();
        if (!directoryHandler.topologicalSort()) {
            System.out.println("Сортировка невозможна по причине циклической зависимости.");
        } else {
            try {
                Concatenation.concatenateFiles(directoryHandler.getSortedFileNames());
            } catch (IOException e) {
                System.out.println("Не удалось записать результат в файл.");
            }
        }
    }
}
