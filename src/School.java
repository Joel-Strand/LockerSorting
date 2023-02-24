import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class School {
    Graph g;
    Ant ant;
    ArrayList<Student> students;

    public School(String mapPath, String conPath, String studentPath) {
        this.g = new Graph(mapPath, conPath);
        this.ant = new Ant(g);
        try {
            File file = new File(studentPath);
            Scanner scanner = new Scanner(file).useDelimiter(",");

            while (scanner.hasNextLine()) {
                ArrayList<String> classList = new ArrayList<>();
                while (scanner.hasNext()) {
                    System.out.println(scanner.next());
                    classList.add(scanner.next());
                }
                Student student = new Student(classList);
                students.add(student);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public String findBestLocker(Student student) {
        ArrayList<ArrayList<Node>> paths = new ArrayList<>();
        for (int i = 0; i < student.classes.size(); i++) {
            for (int j = i + 1; j < student.classes.size(); j++) {
                ant.findShortestPath(student.classes.get(i), student.classes.get(j));
            }
        }



        return null;
    }
}
