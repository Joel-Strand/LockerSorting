import java.util.ArrayList;

public interface IGraph {
    boolean add(Node source, Node destination, double[] weight,
                boolean hasLockers, ArrayList<Locker> lockers);
    void printGraph();
    int size();
    Node get(String node);
}
