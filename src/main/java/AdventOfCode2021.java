import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.SneakyThrows;
import lombok.val;

public class AdventOfCode2021 {

    public static void main(String[] args) {
        Puzzle puzzle = new Day08();

        val inputFileName = guessInputFileName(puzzle);
        val lines = getPuzzleInput(inputFileName);

        System.out.println("Solution to part A:");
        System.out.println(puzzle.solutionA(lines));

        System.out.println();

        System.out.println("Solution to part B:");
        System.out.println(puzzle.solutionB(lines));
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
        return puzzle.getClass().getName().toLowerCase() + ".txt";
    }
}

