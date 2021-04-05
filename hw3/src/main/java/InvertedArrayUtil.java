import org.json.JSONObject;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class InvertedArrayUtil {

    public static void main(String[] args) throws IOException {
        InvertedArrayUtil ts = new InvertedArrayUtil();
        ts.getInvertedArray();
    }

    private String[] getStrings(Scanner sc, FileUtil fileUtil, Set<String> words, Set<Integer> docIdsSet) throws FileNotFoundException {
        String[] wordArray = sc.nextLine().split(" ");
        for (String s : wordArray) {
            if (words.add(s)) {
                docIdsSet.addAll(fileUtil.getFileNames(s));
            }
        }
        return wordArray;
    }

    public void getInvertedArray() throws IOException {
        try (Scanner sc = new Scanner(new File("src/main/resources/lemmas.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/main/resources/invertedIndex.txt"), true))) {
            FileUtil fileUtil = new FileUtil();
            Set<String> words = new HashSet<>();
            while (sc.hasNext()) {
                Set<Integer> docIdsSet = new HashSet<>();
                String[] wordArray = getStrings(sc, fileUtil, words, docIdsSet);
                JSONObject jsonObject = getJsonObject(docIdsSet, wordArray[0]);
                writer.append(jsonObject.toString())
                        .append("\n");
            }
        }
    }

    private JSONObject getJsonObject(Set<Integer> docIdsSet, String value) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("word", value);
        jsonObject.put("count", docIdsSet.size());
        jsonObject.put("inverted_array", docIdsSet);
        return jsonObject;
    }
}
