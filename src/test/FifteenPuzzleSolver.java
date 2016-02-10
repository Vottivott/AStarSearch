package test;

import search.AStarSearch;
import search.SearchAlgorithm;
import search.SearchResult;
import test.FifteenPuzzle.TileState;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FifteenPuzzleSolver {
    public static void main(String[] args) throws FileNotFoundException {
        FifteenPuzzle problem = new FifteenPuzzle(new FileInputStream("8puzzle.txt"));
        SearchAlgorithm<TileState> algorithm = new AStarSearch<>(problem);
        System.out.println(problem.initial());
        System.out.println("Beginning search...");
        long t0 = System.currentTimeMillis();
        SearchResult<TileState> result = algorithm.search(problem.initial());
        long t = System.currentTimeMillis() - t0;

        System.out.println("Found optimal solution!");
        result.getGoalPath().forEach(System.out::println);
        System.out.println(result);
        System.out.println("Time taken: " + t + " ms");



    }
}