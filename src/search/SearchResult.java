package search;

import java.util.List;

public class SearchResult<T> {

    List<T> goalPath;
    boolean successful;

    public SearchResult(List<T> goalPath) {
        if (goalPath == null) {
            successful = false;
        } else {
            this.goalPath = goalPath;
            successful = true;
        }
    }

    public List<T> getGoalPath() {
        return goalPath;
    }

    public boolean successful() {
        return successful;
    }

    @Override
    public String toString() {
        if (!successful) {
            return "FAILURE";
        }
        String s = "Found solution: " + (goalPath.size()-1) + " moves";
        //s += "\nNodes expanded: " + nodesExpanded;
        //s += "\nNodes generated: " + nodesGenerated;
        return s;
    }
}
