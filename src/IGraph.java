public interface IGraph {
    boolean add(Node source, Node destination, float weight, boolean hasLockers);
    void printGraph();
    int size();
}
