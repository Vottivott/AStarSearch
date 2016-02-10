package search;

import java.util.List;

public interface SearchProblem<T> {
    boolean isGoal(T state);
    double heuristic(T state);
    List<Successor<T>> successors(T state);
}
