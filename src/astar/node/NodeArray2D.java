package astar.node;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class NodeArray2D {

    Node[][] array;
    ArrayList<Integer> map;
    int magX, magY;

    public NodeArray2D(String filePath) {
        try {
            map = new ArrayList<>();
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            int fileLength  = 0;

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
            this.array = new Node[magX][magY];
            int x = 0, y = 0;

            // 2d Implementation;
            for (int i = 2; i < fileLength; i++) {

                if (map.get(i) == 5) {
                    y++;
                    x=0;
                    continue;
                }
                switch (map.get(i)) {
                    // Hallway
                    case 0:
                        this.array[x][y] = new Node(x, y, 0,0);
                        break;
                    // Locker
                    case 1:
                        this.array[x][y] = new Node(x, y, 0,1);
                        break;
                    // Stairs
                    case 2:
                        this.array[x][y] = new Node(x, y, 0,2);
                        break;
                    // Room
                    case 3:
                        this.array[x][y] = new Node(x, y, 0,3);
                        break;
                    // Empty
                    case 4:
                        this.array[x][y] = null;
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
    public static Node[][] ten() {
        Node[][] temp = new Node[10][10];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                temp[x][y] = new Node(x, y, 0, 0);
            }
        }
        return temp;
    }

    // Creates a 100x100 walkable array.
    public static Node[][] oneHundred() {
        Node[][] temp = new Node[100][100];
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                temp[x][y] = new Node(x, y, 0,0);
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
            for (int ind = 0; ind < array[row].length; ind++) {
                if (array[ind][row] == null) { s.append("   "); continue; }
                s.append(array[ind][row].state).append("  ");
            }
            s.append("\n  ");
        }
        System.out.println(s.substring(0,s.length()-3) + "]");
    }

    public Node getByIndex(int x, int y) {
        return array[x][y];
    }

    public ArrayList<Node> getAdjacentNodes(Node vertex) {
        ArrayList<Node> arrayToReturn = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            try {
                switch (i) {
                    case 0:
                        arrayToReturn.add(this.array[vertex.xPos - 1][vertex.yPos - 1]);
                        break;
                    case 1:
                        arrayToReturn.add(this.array[vertex.xPos][vertex.yPos - 1]);
                        break;
                    case 2:
                        arrayToReturn.add(this.array[vertex.xPos + 1][vertex.yPos - 1]);
                        break;
                    case 3:
                        arrayToReturn.add(this.array[vertex.xPos - 1][vertex.yPos]);
                        break;
                    case 4:
                        arrayToReturn.add(this.array[vertex.xPos + 1][vertex.yPos]);
                        break;
                    case 5:
                        arrayToReturn.add(this.array[vertex.xPos - 1][vertex.yPos + 1]);
                        break;
                    case 6:
                        arrayToReturn.add(this.array[vertex.xPos][vertex.yPos + 1]);
                        break;
                    case 7:
                        arrayToReturn.add(this.array[vertex.xPos + 1][vertex.yPos + 1]);
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                arrayToReturn.add(null);
            }
        }
        return arrayToReturn;
    }

    public static void main(String[] args) {
        NodeArray2D n = new NodeArray2D("/Users/strandj23/Documents/Coding/AStar/src/astar/node/map1");
        n.printRaw();
    }
}
