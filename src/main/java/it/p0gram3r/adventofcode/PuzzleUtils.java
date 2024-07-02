package it.p0gram3r.adventofcode;

import com.google.common.reflect.ClassPath;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import lombok.SneakyThrows;
import lombok.val;
import org.slf4j.Logger;

public class PuzzleUtils {
    @SneakyThrows
    public static List<Puzzle> loadPuzzlesInPackage(Package pack) {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(clazz -> clazz.getPackageName().equalsIgnoreCase(pack.getName()))
                .map(ClassPath.ClassInfo::load)
                .filter(Puzzle.class::isAssignableFrom)
                .sorted(Comparator.comparing((Function<Class<?>, String>) Class::getName))
                .map(PuzzleUtils::createPuzzleInstance)
                .toList();
    }

    public static void solvePuzzles(List<Puzzle> puzzles, Logger log) {
        for (Puzzle puzzle : puzzles) {
            val inputFileName = PuzzleUtils.guessInputFileName(puzzle);
            val lines = PuzzleUtils.getPuzzleInput(inputFileName);

            log.info("Puzzle: " + puzzle.getClass().getName());
            log.info("  solution to part A: {}", puzzle.solutionA(lines));
            log.info("  solution to part B: {}", puzzle.solutionB(lines));
            log.info("");
        }
    }

    private static Puzzle createPuzzleInstance(Class<?> clazz) {
        try {
            return (Puzzle) clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public static List<String> getPuzzleInput(String fileName) {
        URL resource = Puzzle.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new FileNotFoundException(fileName);
        }
        return Files.readAllLines(Path.of(resource.toURI()));
    }

    public static String guessInputFileName(Object o) {
        String packageName = o.getClass().getPackageName();
        packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
        String className = o.getClass().getSimpleName().toLowerCase();
        return packageName + "/" + className + ".txt";
    }
}
