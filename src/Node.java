import java.util.ArrayList;

public class Node {

    int type, index, x, y, z; // type: 0 = room, 1 = hallway // id: index from csv
    ArrayList<Integer> edges;
    public Node(int id, int type) {
        this.index = id;
        this.type = type;
        edges = new ArrayList<>();
    }

    void addEdge(int destination) {

    }
}
