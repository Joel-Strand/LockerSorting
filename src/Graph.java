import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Graph implements IGraph {
    int vertexCount;
    ArrayList<Node> nodes;  // make this a hashmap too??
    ArrayList<Node> stairs;
    HashMap<String, double[]> distances;

    public Graph() {
        this.nodes = new ArrayList<>();
        distances = new HashMap<>();
    }

    public Graph(String mapPath, String conPath) {
        this.nodes = new ArrayList<>();
        this.distances = new HashMap<>();

        try {
            File map = new File(mapPath);
            File con = new File(conPath);
            Scanner scanner = new Scanner(map);

            ArrayList<Node> nodesToAdd = new ArrayList<>();
            ArrayList<Node> stairsToAdd = new ArrayList<>();


            // Initialize all Nodes
            while (scanner.hasNextLine()) {
                String _info = scanner.nextLine();
                String[] info = _info.split(",");
                String id = info[0];
                int x = Integer.parseInt(info[1]);
                int y = Integer.parseInt(info[2]);
                int z = Integer.parseInt(info[3]);
                Node node = new Node(id,x,y,z);
                if (info[0].equals("st")) {
                    stairsToAdd.add(node);
                } else {
                    nodesToAdd.add(node);
                }
            }

            scanner = new Scanner(con);

            // this will need to be updated
            while (scanner.hasNextLine()) {
                String _info = scanner.nextLine();
                String[] info = _info.split(",");
                String sourceId = info[0];
                boolean hasLockers = Integer.parseInt(info[1]) == 1;
                Node source = null, destination = null;

                for (int i = 2; i < info.length; i++) {
                    String idToSearch = info[i];
                    for (Node n : nodesToAdd) {
                        if (n.id.equals(sourceId))   { source      = n; }
                        if (n.id.equals(idToSearch)) { destination = n; }
                    }
                    double[] weight = weight(source, destination);
                    add(source, destination, weight, hasLockers);

                    assert source != null;
                    assert destination != null;
                    distances.put(source.id + destination.id, weight);
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    private static double[] weight(Node source, Node destination) {
        if (source == null || destination == null) {
            System.out.println("Null Parameter Used in weight()");
            return null;
        }

        double[] cords = new double[3];
        cords[0] = Math.abs(destination.x - source.x); // dx
        cords[1] = Math.abs(destination.y - source.y); // dy
        cords[2] = Math.abs(destination.z - source.z); // dz
        return cords;
    }

    @Override
    public boolean add(Node source, Node destination, double[] weight,
                       boolean hasLockers) {
        vertexCount++;
        if (!this.nodes.contains(source))      { nodes.add(source); }
        if (!this.nodes.contains(destination)) { nodes.add(destination); }

        return source.addEdge(destination, weight, hasLockers) &&
                destination.addEdge(source, weight, hasLockers);

    }

    @Override
    public void printGraph() {
        for (Node n : nodes) {
            for (Edge e : n.connections) {
                System.out.println(e.source.id + " -> " + e.destination.id);
            }
        }
     }

    @Override
    public int size() {
        return vertexCount;
    }

    @Override
    public Node get(Node node) {
        for (Node n : nodes) {
            if (n.equals(node)) { return node; }
        }
        return null;
    }

    public static void main(String[] args) {


    }
}
