import java.util.LinkedList;

public class NewGraph {
    int vertexCount;
    LinkedList<Node> adjListArray[];

    public NewGraph(int v) {
        this.vertexCount = v;
        adjListArray = new LinkedList[vertexCount];
        for(int i = 0; i < vertexCount; i++) {
            adjListArray[i] = new LinkedList<>();
        }
    }

    public void addEdge(Node source, Node destination) {
        adjListArray[source.index].add(destination);
        adjListArray[destination.index].add(source);
    }

    void printGraph() {
        for (int v = 0; v < this.vertexCount; v++) {
            System.out.println("Adjacency List of vertex " + v + " is ");
            for (Node val : adjListArray[v]) {
                System.out.println(" -> " + val.index);
            }
        }
    }

    public static void main(String[] args) {

        Node[] arr = new Node[10];

        for (int i = 0; i < 10; i++) {
            arr[i] = new Node(i, 0);
        }

        NewGraph g = new NewGraph(10);

        for (int i = 0; i < 9; i++) {
            g.addEdge(arr[i], arr[i%2]);
        }

        g.printGraph();
    }








}
