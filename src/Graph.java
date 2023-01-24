import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph implements IGraph{
    int vertexCount;
    ArrayList<Node> nodes;

    public Graph() {
        this.nodes = new ArrayList<>();
    }

    public Graph(String mapPath, String conPath) {
        this.nodes = new ArrayList<>();

        try {
            File map = new File(mapPath);
            File con = new File(conPath);
            Scanner scanner = new Scanner(map);

            ArrayList<Node> nodesToAdd = new ArrayList<>();

            // Initialize all Nodes
            while (scanner.hasNextLine()) {
                String _info = scanner.nextLine();
                String[] info = _info.split(",");
                int x = Integer.parseInt(info[1]);
                int y = Integer.parseInt(info[2]);
                int z = Integer.parseInt(info[3]);
                nodesToAdd.add(new Node(info[0], x, y, z));
            }

            scanner = new Scanner(con);

            while (scanner.hasNextLine()) {
                String _info = scanner.nextLine();
                String[] info = _info.split(",");
                boolean hasLockers = Integer.parseInt(info[0]) == 1;
                String sourceId = info[1];
                Node source = null, destination = null;

                for (int i = 2; i < info.length; i++) {
                    String idToSearch = info[i];
                    for (Node n : nodesToAdd) {
                        if (n.id.equals(sourceId))   { source      = n; }
                        if (n.id.equals(idToSearch)) { destination = n; }
                    }
                    add(source, destination, weight(source, destination), hasLockers);
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
