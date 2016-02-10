package test;

import search.Successor;
import search.SearchProblem;
import test.Grid.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grid implements SearchProblem<State> {

    private static final int width = 70;
    private static final int height = 50;
    private static final State start = new State(20, 2);
    private static final State goal = new State(35, 25);
    private static final Set<State> skip = new HashSet<>();
    static {
        skip.add(new State(10,10));
        skip.add(new State(11,10));
        skip.add(new State(12,10));
        skip.add(new State(13,10));
        skip.add(new State(14,10));
        skip.add(new State(15,10));
        skip.add(new State(16,10));
        skip.add(new State(17,10));
        skip.add(new State(18,10));
        skip.add(new State(19,10));
        skip.add(new State(20,10));
        skip.add(new State(21,10));
        skip.add(new State(22,10));
        skip.add(new State(23,10));
        skip.add(new State(23,9));
        skip.add(new State(23,8));
        skip.add(new State(23,7));
        skip.add(new State(23,6));
        skip.add(new State(23,5));
        skip.add(new State(23,4));
        skip.add(new State(23,3));
        skip.add(new State(23,2));
        skip.add(new State(23,1));
        skip.add(new State(23,0));
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public State initial() {
        return new State(start);
    }

    @Override
    public boolean isGoal(State state) {
        return state.equals(goal);
    }

    @Override
    public double heuristic(State state) {
        return Math.abs(state.x - goal.x) + Math.abs(state.y - goal.y);
    }

    private List<State> getDeltas(State state) {
        List<State> deltas = new ArrayList<>();
        State right = new State(1,0);
        State up = new State(0,-1);
        State left = new State(-1,0);
        State down = new State(0,1);
        if (state.x < width-1 && !skip.contains(state.add(right))) deltas.add(right);
        if (state.x > 0 && !skip.contains(state.add(left))) deltas.add(left);
        if (state.y < height-1 && !skip.contains(state.add(down))) deltas.add(down);
        if (state.y > 0 && !skip.contains(state.add(up))) deltas.add(up);
        return deltas;
    }

    @Override
    public List<Successor<State>> successors(State state) {
        List<Successor<State>> successors = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            State successorState = new State(state);
            successorState.x += dir.dx();
            successorState.y += dir.dy();
            if (successorState.x >= 0 && successorState.x < width && successorState.y >= 0 && successorState.y < height) {
                successors.add(new Successor<>(successorState, 1));
            }
        }
        return successors;
    }


    public static final class State {
        private int x;
        private int y;

        public State(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public State(State state) {
            this(state.x, state.y);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public State add(State state) {
            return new State(x+state.x, y+state.y);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null) return false;
            if (obj.getClass() != this.getClass()) return false;
            State s = (State)obj;
            return s.x == x && s.y == y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString() {
            return "("+x+", "+y+")";
            /*String s = "";
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (this.x == x && this.y == y) {
                        s += "()";
                    } else if (x == goal.x && y == goal.y) {
                        s += "GG";
                    } else {
                        s += "  ";
                    }
                }
                s += "\n";
            }
            return s;*/
        }
    }
}
