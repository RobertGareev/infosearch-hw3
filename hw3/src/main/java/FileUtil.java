import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class FileUtil {
    private final Map<Integer, File> FILES = new HashMap<>();

    public FileUtil() {
        IntStream.range(0, 100)
                .forEach(i -> FILES.put(i, new File(String.format("src/main/resources/files/%d.txt", i))));
    }

    private boolean scanFile(File doc, String token) throws FileNotFoundException {
        Scanner sc = new Scanner(doc).useDelimiter(" ");
        while (sc.hasNext()) {
            if (Pattern.compile("[^а-яА-Я]").matcher(sc.next())
                    .replaceAll(" ")
                    .trim()
                    .equals(token)) {
                return true;
            }
        }
        return false;
    }

    public Set<Integer> getFileNames(String token) throws FileNotFoundException {
        Set<Integer> result = new HashSet<>();
        for (Map.Entry<Integer, File> entry : FILES.entrySet()) {
            if (scanFile(entry.getValue(), token)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }
}