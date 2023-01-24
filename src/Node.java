import java.util.ArrayList;

public class Node implements INode {
    int type, id, x, y, z; // type: 0 = room, 1 = hallway // id: index from csv
    ArrayList<Edge> connections;

    public Node() {
        this.id = 0;
        this.type = 0;
        this.connections = new ArrayList<>();
        System.out.println("Null Constructor Used: " + this);
    }

    public Node(int id, int type, int x, int y, int z) {
        this.id = id;
        this.type = type;
        this.connections = new ArrayList<>();
    }

    @Override
    public boolean addEdge(Node destination,
                           float weight, boolean hasLockers) {
        Edge edge = new Edge(this, destination, weight, hasLockers);

        if (!connections.contains(edge)) {
            connections.add(edge);
            return true;
        }
        return false;
    }
}
