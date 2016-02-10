package test;

import search.AStarSearch;
import search.SearchAlgorithm;
import search.SearchResult;

import javax.swing.*;

public class GridSolver extends JFrame {
    public static void main(String[] args) {
        Grid problem = new Grid();
        SearchAlgorithm<Grid.State> algorithm = new AStarSearch<>(problem);
        System.out.println(problem.initial());
        System.out.println("Beginning search...");
        long t0 = System.currentTimeMillis();
        SearchResult<Grid.State> result = algorithm.search(problem.initial());
        long t = System.currentTimeMillis() - t0;

        JFrame w = new JFrame();
        w.add(new GridView(problem.getWidth(), problem.getHeight(), result.getGoalPath()));//, result.getOpen(), result.getClosed()));
        w.pack();
        w.setDefaultCloseOperation(EXIT_ON_CLOSE);
        w.setVisible(true);

        System.out.println(result);
        System.out.println("Time taken: " + t + " ms");
        //for (Grid_old.State state : result.getPath()) {
        //    System.out.println(state);
        //}
    }

}
