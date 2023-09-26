import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class AdventOfCode2021 {

    public static void main(String[] args) {

        val providers = ServiceLoader.load(Puzzle.class);
        for (Puzzle puzzle : providers) {
            val inputFileName = guessInputFileName(puzzle);
            val lines = getPuzzleInput(inputFileName);

            log.info("Puzzle: " + puzzle.getClass().getSimpleName());
            log.info("  solution to part A: {}", puzzle.solutionA(lines));
            log.info("  solution to part B: {}", puzzle.solutionB(lines));
            log.info("");
        }
    }

    @SneakyThrows
    static List<String> getPuzzleInput(String fileName) {
        URL resource = AdventOfCode2021.class.getResource(fileName);
        if (resource == null) {
            throw new FileNotFoundException(fileName);
        }
        return Files.readAllLines(Path.of(resource.toURI()));
    }

    static String guessInputFileName(Object o) {
        return o.getClass().getName().toLowerCase() + ".txt";
    }
}

