package astar.node;

public class New2DNode {
    int     xPos,   yPos,   // top left coordinates
            xScale, yScale,
            score, state;
    boolean stairs, locker, walkable, room;
    String name;


    public New2DNode(int _xPos, int _yPos, int _xScale,
                   int _yScale, int _state, String _name) {
        this.state = _state;
        switch(state) {
            case 0:
                stairs = false;
                locker = false;
                room = false;
                break;
            case 1:
                stairs = false;
                locker = true;
                room = false;
                break;
            case 2:
                stairs = true;
                locker = false;
                room = false;
                break;
            case 3:
                stairs = false;
                locker = false;
                room = true;
                break;
            default:
                break;
        }

        xPos = _xPos;
        yPos = _yPos;
        xScale = _xScale;
        yScale = _yScale;
        score = 0;
        name = _name;
        walkable = !locker && !stairs && !room;

    }

    public void updateScore() {
        this.score++;
    }

    public void setWalkable(boolean state)  { this.walkable = state;  }

    public void setStairs(boolean state) { this.stairs = state; }
}
