import java.util.ArrayList;
import java.util.HashMap;

public class Pathing {


    private void findShortestPath(Node start, Node dest) {
        ArrayList<Node> open = new ArrayList<>();
        ArrayList<Node> closed = new ArrayList<>();
        HashMap<String, Double> fValues = new HashMap<>();

        Node parent = start;
        Node current = start;
        double h = manhattan(start, dest);
        double g = manhattan(current, start);
        double f = g + h;

        while (!current.equals(dest)) {
            for (Edge e : current.connections) {
                Node vertex = e.destination;
                if (!open.contains(vertex) && !closed.contains(vertex)) {
                    open.add(vertex);
                    h = manhattan(vertex, dest);
                    g = manhattan(start, vertex);
                    double tempF = g + h;
                    if (tempF < f || f == 0) {
                        f = tempF;
                        fValues.put(vertex.id.toLowerCase(), f);
                        parent = vertex;
                    }
                }
            }
            closed.add(current);
            double fToRemove = 0;
            Node nodeToRemove = null;

            for (int i = 0; i < fValues.size(); i++) {
                Node tempN = open.get(i);
                double tempF = fValues.get(tempN.id.toLowerCase());
                if (i == 0) {
                    fToRemove = tempF;
                    continue;
                }
                if (tempF < fToRemove) {
                    fToRemove = tempF;
                    nodeToRemove = tempN;
                }
            }
            open.remove(nodeToRemove);
            current = nodeToRemove;
        }
    }

    private double manhattan(Node source, Node dest) {
        return (dest.x - source.x) + (dest.y - source.y) + (dest.z - source.z);
    }

    public static void main(String[] args) {
        Graph g = new Graph("/Users/strandj23/Documents/Coding/AStar/src/map1.txt","/Users/strandj23/Documents/Coding/AStar/src/con1.txt");

    }
}
