package search;

/**
 * Represents a successor of an expanded state.
 */
public final class Successor<T> {
    private final T state;
    private final double cost;

    public Successor(T state, double cost) {
        this.state = state;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public T getState() {
        return state;
    }
}
