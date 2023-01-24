import java.util.ArrayList;

public class Graph implements IGraph{
    int vertexCount;
    ArrayList<Node> nodes;

    public Graph() {
        nodes = new ArrayList<>();
    }

    @Override
    public boolean add(Node source, Node destination, float weight,
                       boolean hasLockers) {
        vertexCount++;
        if (!this.nodes.contains(source))      { nodes.add(source); }
        if (!this.nodes.contains(destination)) { nodes.add(destination); }

        return source.addEdge(destination,weight,hasLockers);
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


    public static void main(String[] args) {

        Graph g = new Graph();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; i < 10; i++) {
                for (int k = 0; i < 10; i++) {
                    g.add(new Node(i+j+k,0,i,j,k), new Node(i+j+k+1,1,i+1,j+1,k+1), 10, false);
                }
            }
        }
        g.printGraph();
    }
}
