import java.util.Comparator;

public class LockerScoringComparator implements Comparator<Object[]> {
    @Override
    public int compare(Object[] o1, Object[] o2) {
        return Double.compare((double) o1[1], (double) o2[1]);
    }
}
