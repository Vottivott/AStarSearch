package search;

public interface SearchAlgorithm<T> {
    SearchResult<T> search(T initialState);
}
