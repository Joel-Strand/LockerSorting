import java.util.*;

public class Ant implements IPath, IScore {

    private final Graph graph;

    public Ant(Graph g) {
        this.graph = g;
    }

    @Override
    public ArrayList<Node> findShortestPath(String class1, String class2) {

        Node a = graph.get(class1);
        Node b = graph.get(class2);

        // Null Parameter Handling
        if (a == null) {
            if (b == null) {
                throw new IllegalArgumentException("A & B cannot be null");
            }
            throw new IllegalArgumentException("A cannot be null");
        } else if (b == null) {
            throw new IllegalArgumentException("B cannot be null");
        }

        HashMap<Node, Node> parents = new HashMap<>();
        HashSet<Node> visited = new HashSet<>();
        Map<Node, Double> distances = initInfinity();

        Queue<Node> priorityQueue = initQueue();

        a.g = 0.0d;
        distances.put(a, a.g);
        priorityQueue.add(a);
        Node current;

        while (!priorityQueue.isEmpty()) {
            current = priorityQueue.remove();
            if (!visited.contains(current)) {
                if (current.equals(b)) {
                    return reconstructPath(parents, a, b);
                }
                visited.add(current);
                HashSet<Node> neighbors = getNeighbors(current);
                for (Node neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        double h = Math.abs(h(neighbor,b));
                        double g = Math.abs(g(a,current) + g(current,neighbor));
                        double f = h + g;

                        if (f < distances.get(neighbor)) {
                            distances.put(neighbor,f);
                            neighbor.f = f;
                            neighbor.g = g;
                            neighbor.h = h;
                            parents.put(neighbor,current);
                            priorityQueue.add(neighbor);
                        }
                    }
                }
            }
        }
        return null;
    }

    private ArrayList<Node> reconstructPath(HashMap<Node, Node> parents, Node a, Node b) {
        ArrayList<Node> path = new ArrayList<>();
        path.add(b);
        Node ptr = parents.get(b);

        // Handles one-edge case
        if (parents.get(ptr) == null) {
            path.add(a);
            Collections.reverse(path);
            return path;
        }

        while (true) {
            Node temp = parents.get(ptr);
            if (!temp.equals(a)) {
                path.add(ptr);
                ptr = temp;
            } else {
                path.add(ptr);
                path.add(a);
                break;
            }
        }
        Collections.reverse(path);
        return path;
    }

    private Map<Node, Double> initInfinity() {
        Map<Node,Double> distances = new HashMap<>();
        for (Node node : this.graph.composite.values()) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }
        return distances;
    }

    private HashSet<Node> getNeighbors(Node node) {
        HashSet<Node> neighbors = new HashSet<>();
        for (Edge e : node.connections) {
            neighbors.add(e.destination);
        }
        return neighbors;
    }

    private PriorityQueue<Node> initQueue() {
        return new PriorityQueue<>(100, Comparator.comparing(x -> x.f)); // Lambda expression
    }

    public static void main(String[] args) {
        Graph g = new Graph("/Users/strandj23/Documents/Coding/AStar/src/map1.txt",
                "/Users/strandj23/Documents/Coding/AStar/src/con2.txt");

       Ant a = new Ant(g);
       List<Node> list = a.findShortestPath("a100","a105");

       for (Node n : list) {
           System.out.println(n.id);
       }

    }

    @Override
    public double g(Node source, Node self) {
        return (self.x - source.x) + (self.y - source.y) + (self.z - source.z);
    }

    @Override
    public double h(Node self, Node dest) {
        return (dest.x - self.x) + (dest.y - self.y) + (dest.z - self.z);
    }
}
