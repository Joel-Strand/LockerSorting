import java.util.*;

public class Ant implements IPath, IScore {

    private Graph graph;

    public Ant(Graph g) {
        this.graph = g;
    }

    @Override
    public ArrayList<Node> findShortestPath(Node a, Node b) {

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
                visited.add(current);
                if (current.equals(b)) return reconstructPath(parents,b);  // is this the problem?
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

    private ArrayList<Node> reconstructPath(HashMap<Node, Node> parents, Node b) {
        ArrayList<Node> path = new ArrayList<>();

        for (Map.Entry<Node, Node> mE : parents.entrySet()) {
            path.add(mE.getValue());
        }
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
        return new PriorityQueue<>(100, Comparator.comparing(x -> x.g)); // Lambda expression
    }

    public static void main(String[] args) {
        Graph g = new Graph("/Users/strandj23/Documents/Coding/AStar/src/map1.txt","/Users/strandj23/Documents/Coding/AStar/src/con1.txt");

       Ant a = new Ant(g);
       Node n1 = g.get("a100");
       Node n2 = g.get("b100");
       System.out.println(a.findShortestPath(n1, n2));

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
