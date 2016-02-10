package search;

import java.util.*;

public class AStarSearch<T> implements SearchAlgorithm<T> {

    private SearchProblem<T> problem;

    private final NodeComparator nodeComparator = new NodeComparator();

    public AStarSearch(SearchProblem<T> problem) {
        this.problem = problem;
    }

    @Override
    public SearchResult<T> search(T initialState) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(nodeComparator);
        Map<T, Node> frontierStateMap = new HashMap<>();
        Set<T> explored = new HashSet<>();

        Node initialNode = new Node(initialState);
        frontier.add(initialNode);
        frontierStateMap.put(initialState, initialNode);
        while (!frontier.isEmpty()) {
            Node current = frontier.poll();
            if (problem.isGoal(current.state)) {
                return new SearchResult<>(current.getPathFromRoot());
            }
            explored.add(current.state);
            for (Successor<T> successor : problem.successors(current.state)) { // For each successor node,
                if (explored.contains(successor.getState()))                   // add it to the frontier if it's never
                    continue;                                                  // been seen before, OR if it is on the
                Node successorNode = new Node(successor, current);             // frontier but with a higher cost
                if (frontierStateMap.containsKey(successor.getState())) {      // (in which case the old node is
                    Node old = frontierStateMap.get(successor.getState());     // replaced with the cheaper one)
                    if (nodeComparator.compare(successorNode, old) > 0) {
                        frontier.remove(old);
                    } else {
                        continue;
                    }
                }
                frontier.add(successorNode);
                frontierStateMap.put(successor.getState(), successorNode);
            }
        }
        return failure();
    }

    private SearchResult<T> failure() {
        return new SearchResult<>(null);
    }

    private final class Node {
        final T state;
        final Node parent;
        final double g;
        final double f;

        public Node(T state, Node parent, double cost) {
            this.state = state;
            this.parent = parent;
            this.g = parent.g + cost;
            this.f = this.g + problem.heuristic(state);
        }

        public Node(Successor<T> successor, Node parent) {
            this(successor.getState(), parent, successor.getCost());
        }

        /** Initial node constructor**/
        public Node(T state) {
            this.state = state;
            this.parent = null;
            this.g = 0;
            this.f = problem.heuristic(state);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null) return false;
            return obj.getClass() != this.getClass() && ((AStarSearch<?>.Node) obj).state.equals(state);
        }

        @Override
        public int hashCode() {
            return state.hashCode();
        }

        public List<T> getPathFromRoot() {
            List<T> path = new ArrayList<>();
            Node node = this;
            while (node != null) {
                path.add(0, node.state);
                node = node.parent;
            }
            return path;
        }
    }


    public class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node a, Node b) {
            if (a.f == b.f) {
                return Double.compare(a.g, b.g);
            } else {
                return Double.compare(a.f, b.f);
            }
        }
    }


}
