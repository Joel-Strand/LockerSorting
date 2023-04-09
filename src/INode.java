import java.util.ArrayList;

public interface INode {
    boolean addEdge(Node destination, double[] weight,
                    boolean hasLockers, ArrayList<Locker> lockers);
}
