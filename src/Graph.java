import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Graph implements IGraph {
    int vertexCount;
    HashMap<String, double[]> distances;
    HashMap<String, Node> nodes;
    HashMap<String, Node> stairs;


    public Graph() {
        this.nodes = new HashMap<>();
        distances = new HashMap<>();
    }

    public Graph(String mapPath, String conPath) {
        this.nodes = new HashMap<>();
        this.distances = new HashMap<>();

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
                if (info[0].equals("st")) {
                    nodes.put(node.id,node);
                } else {
                    stairs.put(node.id,node);
                }
            }

            // Connect Edges
            scanner = new Scanner(con);

            // this will need to be updated
            while (scanner.hasNextLine()) {
                String _info = scanner.nextLine();
                String[] info = _info.split(",");
                Node source = nodes.get(info[0]);

                for (int i = 1; i < info.length; i++) {
                    Node destination = nodes.get(info[i+1]);
                    double[] weight = weight(source, destination);
                    boolean hasLockers = Integer.parseInt(info[i]) == 1;
                    source.addEdge(destination, weight, hasLockers);
                    destination.addEdge(destination, weight, hasLockers);
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
        if (!this.nodes.containsKey(source.id))      { nodes.put(source.id, source); }
        if (!this.nodes.containsKey(destination.id)) { nodes.put(destination.id, destination); }

        return source.addEdge(destination, weight, hasLockers) &&
                destination.addEdge(source, weight, hasLockers);
    }

    @Override
    public void printGraph() {
        // change for hashmap
     }

    @Override
    public int size() {
        return vertexCount;
    }

    public static void main(String[] args) {


    }
}
