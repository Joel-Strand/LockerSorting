import java.util.ArrayList;

public class Edge {
    Node source, destination;
    double[] weight; // x, y, z (floor level)
    boolean hasLockers;
    ArrayList<String> lockers; // come back to this

    public Edge(Node source, Node destination, double[] weight, boolean hasLockers, ArrayList<String> lockers) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.hasLockers = hasLockers;
        this.lockers = lockers;
    }
}