public class Edge {
    Node source, destination;
    float weight;
    boolean hasLockers;

    public Edge(Node source, Node destination, float weight, boolean hasLockers) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.hasLockers = hasLockers;
    }
}