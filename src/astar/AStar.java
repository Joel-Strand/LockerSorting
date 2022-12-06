package astar;

import astar.node.Node;
import astar.node.NodeArray2D;


import java.util.ArrayList;

public class AStar {
    public static void main(String[] args) {
        String path = "/Users/strandj23/Documents/Coding/AStar/src/astar/node/map1";
        NodeArray2D n = new NodeArray2D(path);

        ArrayList<Node> open = new ArrayList<>();
        ArrayList<Node> closed = new ArrayList<>();

        float hx = 0f; // calculate heuristic distance here (needs updating)
        float gx = 0f; // Distance from start
        float fx = hx + gx;

        Node vertex = n.getByIndex(1,1);
        System.out.println(n.getAdjacentNodes(vertex));

//        while (vertex != destination)

    }
}
