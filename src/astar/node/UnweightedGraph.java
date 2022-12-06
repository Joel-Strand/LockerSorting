package astar.node;

import java.util.LinkedList;

public class UnweightedGraph {
    public static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    public static class Graph {
        int vertices;
        LinkedList<Edge> [] adjacencylist;

        public Graph(int vertices) {
            this.vertices = vertices;
            adjacencylist = new LinkedList[vertices];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i < vertices ; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }

        public void addEdge(int source, int destination, int weight) {
            // One for Directed Graph
            Edge edge = new Edge(source, destination, weight);
            Edge edge1 = new Edge(destination, source, weight);
            adjacencylist[source].addFirst(edge);
            adjacencylist[destination].addFirst(edge1);
        }

        public void printGraph(){
            for (int i = 0; i < vertices ; i++) {
                LinkedList<Edge> list = adjacencylist[i];
                for (Edge edge : list) {
                    System.out.println("vertex-" + i + " is connected to " +
                            edge.destination + " with weight " + edge.weight);
                }
            }
        }

        public void trio(int source, int[] dest1, int[] dest2) {
            Edge edge1 = new Edge(source, dest1[0], dest1[1]);
            Edge edge2 = new Edge(source, dest2[0], dest2[1]);
            Edge edge3 = new Edge(dest1[0], source, dest1[1]);
            Edge edge4 = new Edge(dest2[0], source, dest2[1]);
            adjacencylist[source].addFirst(edge1);
            adjacencylist[source].addFirst(edge2);
            adjacencylist[dest1[0]].addFirst(edge3);
            adjacencylist[dest2[0]].addFirst(edge4);
        }
    }

    public static void main(String[] args) {
        int vertices = 6;
        Graph graph = new Graph(vertices);
        int node1 = 1;
        int[] node2 = new int[2];
        node2[0] = 2;
        node2[1] = 10;
        int[] node3 = new int[2];
        node3[0] = 5;
        node3[1] = 110;

        graph.trio(node1,node2,node3);




        graph.printGraph();
    }
}