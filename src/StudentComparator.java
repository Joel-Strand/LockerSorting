import java.util.Comparator;

public class StudentComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return CharSequence.compare(o1, o2);
    }
}
