import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class School  {
    Graph g;
    Ant ant;
    ArrayList<Student> students;

    int pixelRatio = 6; // 1 ft = 6 px
    int gfaWidth = 5850; // in pixels
    ArrayList<Locker> takenLockers;

    public School(String mapPath, String conPath, String studentPath) {
        this.g = new Graph(mapPath, conPath);
        this.ant = new Ant(g);
        this.students = new ArrayList<>();
        takenLockers = new ArrayList<>();
        try {
            File file = new File(studentPath);
            Scanner scanner = new Scanner(file).useDelimiter(",");

            while (scanner.hasNextLine()) {
                String[] info = scanner.nextLine().split(",");
                ArrayList<String> classList = new ArrayList<>(Arrays.asList(info));
                students.add(new Student(classList));
            }


            optimizeLockers(students);

            for (Student s : students) {
                System.out.println(s.finalLocker);
            }



            //optimizeLockers(students);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public void optimizeLockers(ArrayList<Student> students) {
        for (Student student : students) {
            student.finalLocker = findBestLocker(student).id;
        }
    }

    public Locker findBestLocker(Student student) {
        // Find all paths between classes
        ArrayList<ArrayList<Node>> paths = new ArrayList<>();
        for (int i = 0; i < student.classes.size() - 1; i++) {
            for (int j = i + 1; j < student.classes.size(); j++) {
                paths.add(ant.findShortestPath(student.classes.get(i), student.classes.get(j)));
            }
        }

        ArrayList<Object[]> lockerScores = new ArrayList<>();
        ArrayList<Locker> lockers = getAllLockers(this.g.edges);

        for (Edge e : this.g.edges) {
            for (Locker L : lockers) {
                Object[] arr = new Object[5];
                arr[0] = L;
                arr[1] = e;
                arr[2] = 0;
                for (ArrayList<Node> path : paths) {
                    for (int i = 0; i < path.size() - 1; i++) {
                        Node source = path.get(i);
                        Node dest = path.get(i + 1);
                        Edge edge = this.g.getEdge(source.id, dest.id);
                        if (edge == null) {
                            edge = this.g.getEdge(dest.id,source.id);
                        }
                        if (edge.matching(e)) {
                            arr[2] = (int) arr[2] + 1;
                        }
                    }
                }
                arr[3] = lockerScore(e,L);
                arr[4] = ((int) arr[2] * (double) arr[3]) * 10;
                lockerScores.add(arr);
            }
        }

        Object[] arrToRemove = null;
        Locker lockerToReturn = null;
        double highScore = 0;
        PriorityQueue<Student> queue = new PriorityQueue<>(100, Comparator.comparing(x -> x.name));

        for (Object[] o : lockerScores) {
            Locker L = (Locker) o[0];
            if (!L.taken) {
                double finalScore = (double) o[4];
                if (finalScore > highScore) {
                    highScore = finalScore;
                    lockerToReturn = L;
                    lockerToReturn.taken = true;
                    arrToRemove = o;
                }
            } else {
                queue.add(student);
            }
        }

        lockerScores.remove(arrToRemove);
        takenLockers.add(lockerToReturn);

        for (Object[] o : lockerScores) {
            Locker L = (Locker) o[0];
            Edge e = (Edge) o[1];
            int edgeCount = (int) o[2];
            double lockerScore = (double) o[3];
            double finalScore = (double) o[4];
            System.out.println("Locker: " + L.id + " Edge: "
                    + e.source.id + "-->" + e.destination.id
                    + " Edge Count: " + edgeCount + " Locker Score : " + lockerScore
                    + " Final Score: " + finalScore);
        }
        return lockerToReturn;
    }

    private static ArrayList<Edge> getAllEdges(Graph g) {
        ArrayList<Edge> tempEdges = new ArrayList<>();

        for (Map.Entry<String, Node> node : g.composite.entrySet()) {
            tempEdges.addAll(node.getValue().connections);
        }

        ArrayList<Edge> finalEdges = new ArrayList<>();
        finalEdges.add(tempEdges.get(0));
        tempEdges.remove(0);

        boolean found;
        for (Edge edge : tempEdges) {
            found = false;
            for (Edge finalEdge : finalEdges) {
                if (edge.matching(finalEdge)) {
                    found = true;
                }
            }
            if (!found) {
                finalEdges.add(edge);
            }
        }
        return finalEdges;
    }

    private ArrayList<Locker> getAllLockers(ArrayList<Edge> edges) {
        ArrayList<Locker> lockers = new ArrayList<>();
        for (Edge e : edges) {
            lockers.addAll(e.lockers);
        }
        return lockers;


    }

    private static Object[] findEdgeTally(ArrayList<Object[]> arr, Node source, Node dest) {
        for (Object[] tally : arr) {
            if ((tally[0] == source && tally[1] == dest) || (tally[0] == dest && tally[1] == source)) {
                return tally;
            }
        }
        return null;
    }

    private static Object[] findLockerDuo(ArrayList<Object[]> arr, Edge e, Locker L) {
        for (Object[] tally : arr) {
            if (tally[0] == L && tally[1] == e) {
                return tally;
            }
        }
        return null;
    }

    private void sort(ArrayList<Object[]> arr) {
        LockerScoringComparator L = new LockerScoringComparator();
        arr.sort(L);
    }

    private double lockerScore(Edge e, Locker L) {
        double x = (e.source.x + e.destination.x) / 2;
        double y = (e.source.y + e.destination.y) / 2;
        double z = (e.source.z + e.destination.z) / 2;
        double dx, dy, dz;

        if (L.x > x) {
            dx = (L.x - x) * (L.x - x);
        } else {
            dx = (x - L.x) * (x - L.x);
        }

        if (L.y > y) {
            dy = (L.y - y) * (L.y - y);
        } else {
            dy = (y - L.y) * (y - L.y);
        }

        if (L.z > z) {
            dz = (L.z - z) * (L.z - z);
        } else {
            dz = (z - L.z) * (z - L.z);
        }
        double dist = Math.sqrt(dx + dy + dz);
        return (dist / gfaWidth);
    }

    public static void main(String[] args) {
        School s = new School("/Users/strandj23/Documents/Coding/AStar/src/matrixTestingMap.txt",
                "/Users/strandj23/Documents/Coding/AStar/src/matrixTestingCon.txt",
                "/Users/strandj23/Documents/Coding/AStar/src/matrixTestingStudents.txt");
//        s.findBestLocker(s.students.get(0));
    }
}
