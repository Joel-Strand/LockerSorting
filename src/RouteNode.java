public class RouteNode {
    private final Node current;
    private Node previous;
    private double routeScore;
    private double estimatedScore;

    public RouteNode(Node current) {
        this(current,null,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
    }

    public RouteNode(Node current, Node previous, double routeScore, double estimatedScore) {
        this.current = current;
        this.previous = previous;
        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

}
