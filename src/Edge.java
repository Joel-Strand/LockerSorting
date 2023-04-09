import java.util.ArrayList;

public class Edge {
    Node source, destination;
    double[] weight; // x, y, z (floor level)
    boolean hasLockers;
    ArrayList<Locker> lockers;

    public Edge(Node source, Node destination, double[] weight, boolean hasLockers, ArrayList<Locker> lockers) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.hasLockers = hasLockers;
        this.lockers = lockers;
    }

    public boolean matching(Edge e) {
        Node source1 = e.source;
        Node dest1 = e.destination;
        // Checks if the connection values are found in a different node (equivalence)
        return (source1 == source || source1 == destination) && (dest1 == source || dest1 == destination);
    }
}