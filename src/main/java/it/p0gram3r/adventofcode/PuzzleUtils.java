package it.p0gram3r.adventofcode;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import lombok.val;

public class PuzzleUtils {
    public static Stream<ServiceLoader.Provider<Puzzle>> loadPuzzlesInPackage(Package pack) {
        val providers = ServiceLoader.load(Puzzle.class);

        return providers.stream()
                .filter(provider -> Objects.equals(provider.getClass().getPackage(), pack));
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
