import java.util.ArrayList;

public class Student {
    ArrayList<String> classes;
    String finalLocker;
    public Student(ArrayList<String> classes) {
        this.classes = classes;
    }
    void setFinalLocker(String locker) {
        this.finalLocker = locker;
    }
}
