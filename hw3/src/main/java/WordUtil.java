import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WordUtil {

    private final Map<String, List<Integer>> wordMap = new HashMap<>();

    List<Integer> getIntegers(String word){
        return wordMap.get(word);
    }

    public WordUtil() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/main/resources/invertedIndex.txt"));
        String line;
        while (sc.hasNextLine()){
            line = sc.nextLine();
            JSONObject jsonObject = new JSONObject(line);
            JSONArray jsonArray = (JSONArray) jsonObject.get("inverted_array");

            List<Integer> resultArray = IntStream.range(0, jsonArray.length())
                    .mapToObj(i -> (Integer) jsonArray.get(i))
                    .collect(Collectors.toList());

            wordMap.put((String) jsonObject.get("word"), resultArray);
        }
    }
}
