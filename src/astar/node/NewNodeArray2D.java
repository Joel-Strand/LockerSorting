package astar.node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NewNodeArray2D {
    Node[][] array;
    ArrayList<Integer> map;
    int magX, magY;

    public NewNodeArray2D(String fileName) {
        try {
            map = new ArrayList<>();
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int fileLength = 0;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public void print() {
        StringBuilder s = new StringBuilder("[ ");

        for (int i = 2; i < map.size(); i++) {
            if (map.get(i) == 4)
            { s.append("\n  "); }
            else
            { s.append(map.get(i)).append(", "); }
        }
        System.out.println(s.substring(0,s.length()-3) + " ]");
    }

    public void printRaw() {
        StringBuilder s = new StringBuilder("[ ");
        for (int row = 0; row < array.length; row++) {
            for (int ind = 0; ind < array[row].length; ind++) {
                if (array[ind][row] == null) { s.append("   "); continue; }
                s.append(array[ind][row].state).append("  ");
            }
            s.append("\n  ");
        }
        System.out.println(s.substring(0,s.length()-3) + "]");
    }


}
