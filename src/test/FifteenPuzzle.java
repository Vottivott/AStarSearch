package test;

import search.Successor;
import search.SearchProblem;
import test.FifteenPuzzle.TileState;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class FifteenPuzzle implements SearchProblem<TileState> {

    private static int width = 4;
    private static int height = 4;
    private static int Ntiles = width * height;
    private static int[] numOfActionsTable = new int[Ntiles];
    private static int[][] actionTable = new int[Ntiles][4];
    private static int[][] md = new int[Ntiles][Ntiles];
    private static int[][][] mdinc = new int[Ntiles][Ntiles][Ntiles];
    private static int[] initial = new int[Ntiles];

    public FifteenPuzzle(InputStream inputStream) {
        initial = new int[Ntiles];
        parseFifteenPuzzleInput(inputStream);
        numOfActionsTable = new int[Ntiles];
        actionTable = new int[Ntiles][4];
        md = new int[Ntiles][Ntiles];
        mdinc = new int[Ntiles][Ntiles][Ntiles];
        initActionTables();
        initManhattandDistTables();
    }

    public TileState initial() {
        int blank = -1;
        int[] tiles = new int[Ntiles];
        for (int i = 0; i < Ntiles; i++) {
            if (initial[i] == 0) {
                blank = i;
            }
            tiles[i] = initial[i];
        }
        if (blank < 0) {
            throw new IllegalArgumentException("No blank tile");
        }
        return new TileState(tiles, blank, mdist(blank, tiles));
    }

    /**
     * Computes the Manhattan distance for the specified configuration of blank and tiles.
     */
    private int mdist(int blank, int[] tiles) {
        int sum = 0;
        for (int tile = 0; tile < Ntiles; tile++) {
            sum += md[tiles[tile]][tile];
        }
        return sum;
    }

    private void initManhattandDistTables() {
        for (int a = 1; a < Ntiles; a++) {
            int grow = a / width, gcol = a % width;
            for (int b = 0; b < Ntiles; b++) {
                int row = b / width, col = b % width;
                md[a][b] = Math.abs(col - gcol) + Math.abs(row - grow);
            }
        }
        for (int a = 0; a < Ntiles; a++) {
            for (int b = 0; b < Ntiles; b++) {
                for (int i = 0; i < Ntiles; i++) {
                    mdinc[a][b][i] = -100; // Some invalid value
                }
                int prevmd = md[a][b];
                if (b >= width)
                    mdinc[a][b][b - width] = md[a][b - width] - prevmd;
                if (b % width > 0)
                    mdinc[a][b][b - 1] = md[a][b - 1] - prevmd;
                if (b % width < width - 1)
                    mdinc[a][b][b + 1] = md[a][b + 1] - prevmd;
                if (b < Ntiles - width)
                    mdinc[a][b][b + width] = md[a][b + width] - prevmd;
            }
        }
    }

    private void initActionTables() {
        for (int i = 0; i < Ntiles; i++) {
            if (i >= width)
                actionTable[i][numOfActionsTable[i]++] = i - width;
            if (i % width > 0)
                actionTable[i][numOfActionsTable[i]++] = i - 1;
            if (i % width < width - 1)
                actionTable[i][numOfActionsTable[i]++] = i + 1;
            if (i < Ntiles - width)
                actionTable[i][numOfActionsTable[i]++] = i + width;
        }
    }

    @Override
    public boolean isGoal(TileState state) {
        return state.h == 0;
    }

    @Override
    public double heuristic(TileState state) {
        return state.h;
    }

    @Override
    public List<Successor<TileState>> successors(TileState state) {
        List<Successor<TileState>> successors = new ArrayList<>();
        for (int i = 0; i < numOfActions(state); i++) {
            TileState s = copy(state);
            int action = nthAction(s, i);
            int tile = s.tiles[action];
            s.tiles[s.blank] = tile;
            s.h = s.h + mdinc[tile][action][s.blank];
            s.blank = action;
            successors.add(new Successor<>(s, 1));
        }
        return successors;
    }

    public int numOfActions(TileState state) {
        return numOfActionsTable[state.blank];
    }

    public int nthAction(TileState state, int n) {
        return actionTable[state.blank][n];
    }

    public TileState copy(TileState state) {
        return new TileState(Arrays.copyOf(state.tiles, Ntiles), state.blank, state.h);
    }


    public static final class TileState {
        int[] tiles;
        int blank;
        int h;

        public TileState(int[] tiles, int blank, int h) {
            this.tiles = Arrays.copyOf(tiles, tiles.length);
            this.blank = blank;
            this.h = h;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null) return false;
            if (obj.getClass() != this.getClass()) return false;
            TileState ts = (TileState)obj;
            if (ts.blank != blank) return false;
            for (int i = 0; i < Ntiles; i++) {
                if (blank != i && ts.tiles[i] != tiles[i]) return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            tiles[blank] = 0;
            int result = tiles[0];
            for (int i = 1; i < Ntiles; i++) {
                result = 31 * result + tiles[i];
            }
            return result;
        }

        @Override
        public String toString() {
            String s = "";
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pos = y*width + x;
                    if (pos == blank) {
                        s += "    ";
                    } else {
                        int tile = tiles[pos];
                        if (tile < 10) {
                            s += "[ "+tile+"]";
                        } else {
                            s += "[" + tile + "]";
                        }
                    }
                }
                s += "\n";
            }
            return s;
        }
    }

    void parseFifteenPuzzleInput(InputStream inputStream) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            String dim[] = line.split(" ");
            width =  Integer.parseInt(dim[0]);
            height = Integer.parseInt(dim[1]);
            Ntiles = width*height;
            line = reader.readLine();
            for (int tile = 0; tile < Ntiles; tile++) {
                int pos = Integer.parseInt(reader.readLine());
                initial[pos] = tile;
            }
            line = reader.readLine();
            for (int tile = 0; tile < Ntiles; tile++) {
                int goalPos = Integer.parseInt(reader.readLine());
                if (goalPos != tile) {
                    throw new IllegalArgumentException("Non-standard goal positions");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
