import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

public class AdventOfCode2022 {

    public static void main(String[] args) {
        var lines = parseFile("day03.txt");
        var puzzle = new Day03A();

        System.out.println(puzzle.getSolution(lines));

        StringUtils.trim("asdf")
    }

    @SneakyThrows
    static List<String> parseFile(String name) {
        var uri = AdventOfCode2022.class.getResource(name).toURI();
        return Files.readAllLines(Path.of(uri));
    }
}

