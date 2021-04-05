import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InputReader {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            InputReader inputReader = new InputReader();
            inputReader.process(sc.nextLine());
        }
    }

    public void process(String input) throws FileNotFoundException {
        String[] wordsArray = input.split(" ");
        WordUtil wordUtil = new WordUtil();
        List<Object> result = new ArrayList<>();

        for (String s : wordsArray) {
            if (!(s.equals(OR) | s.equals(AND) | s.equals(NOT))) {
                List<Integer> docList = wordUtil.getIntegers(s);
                result.add(docList);
            } else {
                result.add(s);
            }
        }
        processNOT(result);
        processOR(result);
        processAND(result);
        System.out.println(result);
    }

    public List<Integer> getInvertedList(List<Integer> integers) {
        return IntStream.range(0, 100)
                .filter(i -> !integers.contains(i))
                .boxed()
                .collect(Collectors.toList());
    }

    private final static String AND = "and";

    private void processAND(List<Object> parsedLineWithArray) {
        while (parsedLineWithArray.contains(AND)){
            int index = parsedLineWithArray.indexOf(AND);
            List<Integer> integersBefore = (List<Integer>) parsedLineWithArray.get(index - 1);
            List<Integer> integersAfter = (List<Integer>) parsedLineWithArray.get(index + 1);
            parsedLineWithArray.remove(index + 1);
            parsedLineWithArray.set(index, integersBefore.retainAll(integersAfter));
            parsedLineWithArray.remove(index - 1);
        }
    }

    private final static String OR = "or";

    private void processOR(List<Object> parsedLineWithArray) {
        while (parsedLineWithArray.contains(OR)){
            int index = parsedLineWithArray.indexOf(OR);
            List<Integer> integersBefore = (List<Integer>) parsedLineWithArray.get(index - 1);
            List<Integer> integersAfter = (List<Integer>) parsedLineWithArray.get(index + 1);
            integersAfter.addAll(integersBefore);
            parsedLineWithArray.remove(index + 1);
            parsedLineWithArray.set(index, new HashSet<>(integersAfter));
            parsedLineWithArray.remove(index - 1);
        }
    }

    private final static String NOT = "not";

    private void processNOT(List<Object> parsedLineWithArray) {
        while (parsedLineWithArray.contains(NOT)) {
            int index = parsedLineWithArray.indexOf(NOT);
            List<Integer> integers = (List<Integer>) parsedLineWithArray.get(index + 1);
            parsedLineWithArray.remove(index + 1);
            parsedLineWithArray.set(index, getInvertedList(integers));
        }
    }
}
