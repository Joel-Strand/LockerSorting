import java.util.ArrayList;

public class Node implements INode {
    int x, y, z; // type: 0 = room, 1 = hallway // id: index from csv
    String id;
    ArrayList<Edge> connections;

    public Node() {
        this.id = null;
        this.connections = new ArrayList<>();
        System.out.println("Null Constructor Used: " + this);
    }

    public Node(String id,  int x, int y, int z) {
        this.id = id;
        this.connections = new ArrayList<>();
    }

    @Override
    public boolean addEdge(Node destination,
                           double[] weight, boolean hasLockers) {
        Edge edge = new Edge(this, destination, weight, hasLockers);

        if (!matching(edge)) {
            connections.add(edge);
            return true;
        }
        return false;
    }

    private boolean matching(Edge e) {
        Node source1 = e.source;
        Node dest1 = e.destination;
        for (Edge connection : connections) {
            Node source2 = connection.source;
            Node dest2 = connection.destination;
            // Checks if the node values are found in a different node (equivalence)
            if ((source1 == source2 || source1 == dest2) && (dest1 == source2 || dest1 == dest2)) {
                return true;
            }
        }
        return false;
    }
}
