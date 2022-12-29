import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.*;

/**
 * Класс для получения всех файлов из условной корневой папки resources и их сортировки.
 */
public class DirectoryHandler {
    /** Множество имен всех файлов. */
    private final Set<String> fileNamesSet;
    /** Коллекция узлов для топологической сортировки. */
    private final Map<String, FileNode> fileNodes;
    /** Топологически отсортированный список имен файлов. */
    private final LinkedList<String> sortedFileNames;

    public DirectoryHandler() {
        fileNamesSet =
                formAllFileNames(new File(FileSystems.getDefault().getPath(".\\src\\main\\resources").toString()));
        fileNodes = new HashMap<>();
        sortedFileNames = new LinkedList<>();
    }

    /**
     * Для каждого из файлов-узлов запускает шаг сортировки.
     * @return false — если сортировку осуществить нельзя, true — если можно.
     */
    public boolean topologicalSort() {
        for (Map.Entry<String, FileNode> entry : fileNodes.entrySet()) {
            if (topologicalSortStep(entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Выполняет шаг сортировки для файла-узла.
     * @param fileNode узел для шага сортировки.
     * @return false — если нет цикла, true — если цикл обнаружен.
     */
    private boolean topologicalSortStep(FileNode fileNode) {
        if (fileNode.getColor() == Color.GREY) {
            System.out.println("Цикл с файлом " + fileNode.getFileName());
            return true;
        }
        if (fileNode.getColor() == Color.WHITE) {
            fileNode.setColor(Color.GREY);
            for (FileNode file : fileNode.dependencySet) {
                if (topologicalSortStep(file)) {
                    return true;
                }
            }
            fileNode.setColor(Color.BLACK);
            getSortedFileNames().addLast(".\\src\\main\\resources\\" + fileNode.getFileName());
        }
        return false;
    }

    /** Формирует файлы-узлы для корневого каталога. */
    public void formFileNodesMap() {
        for (String fileName : fileNamesSet) {
            if (!fileNodes.containsKey(fileName)) {
                fileNodes.put(fileName, new FileNode(fileName.substring(21)));
            }
            try {
                formDependencySet(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Поиск файлов, от которых зависит текущий.
     * @param fileName имя файла, для которого выполняется поиск зависимостей.
     * @throws IOException если не удалось прочитать файл.
     */
    private void formDependencySet(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("require ‘")) {
                line = ".\\src\\main\\resources\\" +
                        line.substring(9, line.length() - 1).replace('/', '\\');
                String linetxt = line + ".txt";
                if (fileNodes.containsKey(line)) {
                    fileNodes.get(fileName).dependencySet.add(fileNodes.get(line));
                } else if (fileNodes.containsKey(linetxt)) {
                    fileNodes.get(fileName).dependencySet.add(fileNodes.get(linetxt));
                } else if (fileNamesSet.contains(line)) {
                    fileNodes.put(line, new FileNode(line.substring(21)));
                    fileNodes.get(fileName).dependencySet.add(fileNodes.get(line));
                } else if (fileNamesSet.contains(linetxt)) {
                    fileNodes.put(linetxt, new FileNode(linetxt.substring(21)));
                    fileNodes.get(fileName).dependencySet.add(fileNodes.get(linetxt));
                }
            }
        }
        br.close();
    }

    /**
     * Выполняет поиск всех файлов в директории.
     * @param dir директория.
     * @return Множество имен файлов.
     */
    public static Set<String> formAllFileNames(File dir) {
        Set<String> filesSet = new HashSet<>();
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile()) {
                filesSet.add(file.getPath());
            } else {
                filesSet.addAll(formAllFileNames(file));
            }
        }
        return filesSet;
    }

    /**
     * Getter топологически отсортированного списка имен файлов.
     * @return Топологически отсортированный список имен файлов.
     */
    public LinkedList<String> getSortedFileNames() {
        return sortedFileNames;
    }
}
