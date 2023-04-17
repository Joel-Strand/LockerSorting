import java.util.ArrayList;

public class Student {
    ArrayList<String> classes;
    String finalLocker, name;
    public Student(String name, ArrayList<String> classes) {
        this.name = name;
        this.classes = classes;
    }
    void setFinalLocker(String locker) {
        this.finalLocker = locker;
    }
}
