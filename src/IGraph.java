public interface IGraph {
    boolean add(Node source, Node destination, double[] weight, boolean hasLockers);
    void printGraph();
    int size();
    Node get(Node node);
}
