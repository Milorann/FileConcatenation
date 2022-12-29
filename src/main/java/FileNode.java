import java.util.HashSet;
import java.util.Set;

/**
 * Класс файла-узла для удобства в топологической сортировке.
 */
public class FileNode {
    /** Имя файла */
    private final String fileName;
    /** Файлы-узлы, от которых зависит этот файл. */
    Set<FileNode> dependencySet = new HashSet<>();
    /** Цвет файла узла. */
    private Color color = Color.WHITE;

    public FileNode(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileNode fileNode = (FileNode) o;
        return getFileName().equals(fileNode.getFileName());
    }

    @Override
    public int hashCode() {
        return getFileName().hashCode();
    }

    /**
     * Getter имени файла.
     * @return Имя файла.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Getter цвета узла.
     * @return Цвет узла.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Setter цвета узла.
     * @param color новый цвет узла.
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
