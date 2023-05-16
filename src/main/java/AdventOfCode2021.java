import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.SneakyThrows;
import lombok.val;

public class AdventOfCode2021 {

    public static void main(String[] args) {
        Puzzle puzzle = new Day02A();

        val inputFileName = guessInputFileName(puzzle);
        val lines = getPuzzleInput(inputFileName);

        System.out.println(puzzle.getSolution(lines));
    }

    @SneakyThrows
    static List<String> getPuzzleInput(String fileName) {
        URL resource = AdventOfCode2021.class.getResource(fileName);
        if (resource == null) {
            throw new FileNotFoundException(fileName);
        }
        return Files.readAllLines(Path.of(resource.toURI()));
    }

    static String guessInputFileName(Puzzle puzzle) {
        String className = puzzle.getClass().getName();
        return className.substring(0, className.length() - 1).toLowerCase() + ".txt";
    }
}

