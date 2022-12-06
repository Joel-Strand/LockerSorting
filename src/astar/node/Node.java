package astar.node;

public class Node {

    boolean hasLockers, stairs, room;
    int     xPos, yPos, zPos, score, state;

    public Node(int x, int y, int z, int state) {
        this.state = state;
        switch(state) {
            case 0:
                stairs = false;
                hasLockers = false;
                room = false;
                break;
            case 1:
                stairs = false;
                hasLockers = true;
                room = false;
                break;
            case 2:
                stairs = true;
                hasLockers = false;
                room = false;
                break;
            case 3:
                stairs = false;
                hasLockers = false;
                room = true;
                break;
            default:
                break;
        }

        xPos = x;
        yPos = y;
        zPos = z;
        score = 0;

    }

    public void updateScore() {
        this.score++;
    }
}
