import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph implements IGraph {
    int vertexCount;
    ArrayList<Edge> edges;
    HashMap<String, double[]> distances;
    HashMap<String, Node> nodes;
    HashMap<String, Node> stairs;
    HashMap<String, Node> composite;


    public Graph() {
        this.vertexCount = 0;
        this.edges = new ArrayList<>();
        this.nodes = new HashMap<>();
        this.distances = new HashMap<>();
        this.composite = new HashMap<>();
    }

    public Graph(String mapPath, String conPath) {
        this.vertexCount = 0;
        this.edges = new ArrayList<>();
        this.nodes = new HashMap<>();
        this.distances = new HashMap<>();
        this.stairs = new HashMap<>();
        this.composite = new HashMap<>();
        try {
            File map = new File(mapPath);
            File con = new File(conPath);
            Scanner scanner = new Scanner(map);
            Node node = null;

            // Initialize all nodes
            while (scanner.hasNextLine()) {
                String _info = scanner.nextLine();
                String[] info = _info.split(",");
                String id = info[0];
                int x = Integer.parseInt(info[1]);
                int y = Integer.parseInt(info[2]);
                int z = Integer.parseInt(info[3]);
                node = new Node(id,x,y,z);
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
                String originId = info[0];
                Node source;
                if (originId.startsWith("st")) {
                    source = stairs.get(originId.toLowerCase());
                } else {
                    source = nodes.get(originId.toLowerCase());
                }


                for (int i = 1; i < info.length - 1; i += 2) {
                    ArrayList<Locker> lockers = new ArrayList<>();
                    boolean hasLockers;
                    String stuff = info[i];
                    if (!stuff.equals("0")) {
                        String[] lockerInfo = info[i].split("\\.");
                        for (String s : lockerInfo) {
                            assert node != null;
                            Locker temp = new Locker(node.x, node.y, node.z, s.toLowerCase());
                            lockers.add(temp);
                        }
                        hasLockers = true;
                    } else {
                        hasLockers = false;
                    }

                    String s = info[i+1].toLowerCase();
                    Node destination;
                    if (s.startsWith("st")) {
                        destination = stairs.get(s);
                    } else {
                        destination = nodes.get(s);
                    }

                    double[] weight = weight(source, destination);
                    edges.add(new Edge(source, destination, weight, hasLockers, lockers));
                    source.addEdge(destination, weight, hasLockers, lockers);
                    destination.addEdge(source, weight, hasLockers, lockers);
                    removeDuplicates(this.edges);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(Node source, Node destination, double[] weight,
                       boolean hasLockers, ArrayList<Locker> lockers) {
        vertexCount++;
        if (!this.nodes.containsKey(source.id))      { nodes.put(source.id, source); }
        if (!this.nodes.containsKey(destination.id)) { nodes.put(destination.id, destination); }

        return source.addEdge(destination, weight, hasLockers, lockers) &&
                destination.addEdge(source, weight, hasLockers, lockers);
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

    private void removeDuplicates(ArrayList<Edge> edges) {
        ArrayList<Edge> finalEdges = new ArrayList<>();

        finalEdges.add(edges.get(0));
        edges.remove(0);

        boolean found;
        for (Edge edge : edges) {
            found = false;
            for (Edge finalEdge : finalEdges) {
                if (edge.matching(finalEdge)) {
                    found = true;
                }
            }
            if (!found) {
                finalEdges.add(edge);
            }
        }
        this.edges = finalEdges;
    }

    public Edge getEdge(String node1, String node2) {
        for (Edge e : edges) {
            if ((e.source.id.equals(node1) && e.destination.id.equals(node2))
                    || (e.source.id.equals(node2) && e.destination.id.equals(node1))) {
                return e;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Graph g = new Graph("/Users/strandj23/Documents/Coding/AStar/src/gfaRooms.txt",
                "/Users/strandj23/Documents/Coding/AStar/src/gfaCon.txt");

        g.printGraph();
        System.out.println(g.edges);
    }
}
