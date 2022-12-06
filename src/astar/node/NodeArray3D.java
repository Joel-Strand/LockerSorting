package astar.node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NodeArray3D {


    Node[][][] array;
    ArrayList<Integer> map;
    int magX, magY, magZ;

    public NodeArray3D(String fileName) {
        try {
            map = new ArrayList<>();
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int fileLength = 0;

            while (scanner.hasNextInt()) {
                map.add(scanner.nextInt());
                fileLength++;
            }

            // poor way to do this
            if (map.isEmpty()) {
                throw new IllegalArgumentException("Cannot construct a map" +
                        "out of an empty map file.");
            }

            this.magX = map.get(0);
            this.magY = map.get(1);
            this.magZ = map.get(2);
            this.array = new Node[magX][magY][magZ];
            int x = 0;
            int y = 0;
            int z = 0;

            // why is z not updating???
            for (int i = 3; i < fileLength; i++) {

                if (map.get(i) == 4 && map.get(i+1) == 4) {
                    z++;
                    y = 0;
                    x = 0;
                    continue;
                }
                if (map.get(i) == 4) {
                    y++;
                    x = 0;
                    continue;
                }
                switch(map.get(i)) {
                    // Hallway
                    case 0:
                        this.array[x][y][z] = new Node(x, y, z,0);
                        break;
                    // Locker
                    case 1:
                        this.array[x][y][z] = new Node(x, y, z, 1);
                        break;
                    // Stairs
                    case 2:
                        this.array[x][y][z] = new Node(x, y, z, 2);
                        break;
                    // Room
                    case 3:
                        this.array[x][y][z] = new Node(x, y, z, 3);
                        break;
                    // Empty
                    case 5:
                        this.array[x][y][z] = null;
                        break;
                    default:
                        throw new NumberFormatException("Please use "
                                + " values 0-5 to describe the map.");

                }
                x++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    // Creates a 10x10 walkable array.
    public static Node[][][] ten() {
        Node[][][] temp = new Node[10][10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                for (int z = 0; z < 10; z++) {
                    temp[x][y][z] = new Node(x, y, z, 0);
                }

            }
        }
        return temp;
    }

    // Creates a 100x100 walkable array.

    public static Node[][][] oneHundred() {
        Node[][][] temp = new Node[100][100][100];
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                for (int z = 0; z < 100; z++) {
                    temp[x][y][z] = new Node(x, y, z, 0);
                }

            }
        }
        return temp;
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
            for (int col = 0; col < array[row].length; col++) {
                for (int arr = 0; arr < array[row][col].length; arr++) {
                    if (array[col][row][arr] == null) {
                        s.append("   ");
                        continue;
                    }
                    s.append(array[col][row][arr].state).append("  ");
                }
            }
            s.append("\n  ");
        }

        System.out.println(s.substring(0,s.length()-3) + "]");
    }
}
