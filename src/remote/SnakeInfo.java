package remote;

import java.io.Serializable;

import environment.BoardPosition;

public class SnakeInfo implements Serializable {
    private int id;
    private BoardPosition snakePos;
    private boolean humanSnake;

    public SnakeInfo(int id, BoardPosition snakePos, boolean humanSnake){
        this.id = id;
        this.snakePos = snakePos;
        this.humanSnake = humanSnake;
    }
    
    public int getIdSnakeInfo(){
        return id;
    }

    public BoardPosition getSnakePosSnakeInfo(){
        return snakePos;
    }

    public boolean isHumanSnake(){
        return humanSnake;
    }
}
