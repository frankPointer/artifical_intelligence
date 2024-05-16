import com.puzzle.Puzzle;

public class Main {
    public static void main(String[] args) {
        int[] initial = {2, 8, 3, 1, 6, 4, 7, 0, 5};
        Puzzle puzzle = new Puzzle(initial);
        puzzle.solve();
        puzzle.printSolution();
    }
}
