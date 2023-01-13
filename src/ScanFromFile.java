import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScanFromFile {

    static NewGraph createGraph(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            ArrayList<Integer> nodesByIndex = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String info = scanner.nextLine().replace(",","");


            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

}
