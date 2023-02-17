import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph implements IGraph {
    int vertexCount;
    HashMap<String, double[]> distances;
    HashMap<String, Node> nodes;
    HashMap<String, Node> stairs;
    HashMap<String, Node> composite;

    public Graph() {
        this.vertexCount = 0;
        this.nodes = new HashMap<>();
        this.distances = new HashMap<>();
        this.composite = new HashMap<>();
    }

    public Graph(String mapPath, String conPath) {
        this.vertexCount = 0;
        this.nodes = new HashMap<>();
        this.distances = new HashMap<>();
        this.stairs = new HashMap<>();
        this.composite = new HashMap<>();
        try {
            File map = new File(mapPath);
            File con = new File(conPath);
            Scanner scanner = new Scanner(map);

            // Initialize all nodes
            while (scanner.hasNextLine()) {
                String _info = scanner.nextLine();
                String[] info = _info.split(",");
                String id = info[0];
                int x = Integer.parseInt(info[1]);
                int y = Integer.parseInt(info[2]);
                int z = Integer.parseInt(info[3]);
                Node node = new Node(id,x,y,z);
                if (info[0].startsWith("st")) {
                    this.stairs.put(node.id.toLowerCase(), node);
                } else {
                    this.nodes.put(node.id.toLowerCase(), node);
                }
                this.composite.put(node.id.toLowerCase(), node);
                this.vertexCount++;
            }

            // Connect Edges
            scanner = new Scanner(con);

            while (scanner.hasNextLine()) {
                String _info = scanner.nextLine();
                String[] info = _info.split(",");
                Node source;
                if (info[0].startsWith("st")) {
                    source = stairs.get(info[0].toLowerCase());
                } else {
                    source = nodes.get(info[0].toLowerCase());
                }

                for (int i = 1; i < info.length - 1; i += 2) {
                    String s = info[i+1].toLowerCase();
                    Node destination;
                    if (s.startsWith("st")) {
                        destination = stairs.get(s);
                    } else {
                        destination = nodes.get(s);
                    }

                    double[] weight = weight(source, destination);
                    boolean hasLockers = Integer.parseInt(info[i]) == 1;
                    source.addEdge(destination, weight, hasLockers);
                    destination.addEdge(source, weight, hasLockers);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(Node source, Node destination, double[] weight,
                       boolean hasLockers) {
        vertexCount++;
        if (!this.nodes.containsKey(source.id))      { nodes.put(source.id, source); }
        if (!this.nodes.containsKey(destination.id)) { nodes.put(destination.id, destination); }

        return source.addEdge(destination, weight, hasLockers) &&
                destination.addEdge(source, weight, hasLockers);
    }

    @Override
    public void printGraph() {
        for (Map.Entry<String, Node> mE : nodes.entrySet()) {
            for (int i = 0; i < mE.getValue().connections.size(); i++) {
                Edge edge = mE.getValue().connections.get(i);
                Node source = edge.source;
                Node dest = edge.destination;
                System.out.println(source.id + " -> " + dest.id);
            }
        }
    }

    @Override
    public int size() {
        return vertexCount;
    }

    @Override
    public Node get(String nodeId) {
        return composite.get(nodeId.toLowerCase());
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

    public static void main(String[] args) {
        Graph g = new Graph("/Users/strandj23/Documents/Coding/AStar/src/gfaRooms.txt",
                "/Users/strandj23/Documents/Coding/AStar/src/gfaCon.txt");

        g.printGraph();
    }
}
