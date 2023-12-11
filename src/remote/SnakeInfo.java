package remote;

import java.io.Serializable;
import java.util.LinkedList;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;

public class SnakeInfo implements Serializable {
    private int id;
    private LinkedList<BoardPosition> cells;
    private boolean humanSnake;

    public SnakeInfo(int id, LinkedList<BoardPosition> cells , boolean humanSnake){
        this.id = id;
        this.cells = cells;
        this.humanSnake = humanSnake;
    }
    
    public int getIdSnakeInfo(){
        return id;
    }

    public LinkedList<BoardPosition> getSnakePosSnakeInfo(){
        return cells;
    }

    public boolean isHumanSnake(){
        return humanSnake;
    }
}
