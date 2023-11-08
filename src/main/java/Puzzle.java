import java.util.List;

public interface Puzzle<T1, T2> {

    T1 solutionA(List<String> input);

    T2 solutionB(List<String> input);
}
