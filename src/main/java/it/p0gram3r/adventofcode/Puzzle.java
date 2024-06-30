package it.p0gram3r.adventofcode;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.SneakyThrows;

public interface Puzzle {

    String solutionA(List<String> input);

    String solutionB(List<String> input);

    @SneakyThrows
    static List<String> getPuzzleInput(String fileName) {
        URL resource = Puzzle.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new FileNotFoundException(fileName);
        }
        return Files.readAllLines(Path.of(resource.toURI()));
    }

    static String guessInputFileName(Object o) {
        String packageName = o.getClass().getPackageName();
        packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
        String className = o.getClass().getSimpleName().toLowerCase();
        return packageName + "/" + className + ".txt";
    }
}
