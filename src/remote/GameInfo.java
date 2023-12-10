package remote;

import java.io.Serializable;
import java.util.LinkedList;

import environment.BoardPosition;
import environment.Cell;
import game.Goal;
import game.Obstacle;
import game.Snake;

public class GameInfo implements Serializable{

    private int id;
    LinkedList<BoardPosition> obstaclePos = new LinkedList<>();
    int remainingMoves;

    public GameInfo(LinkedList<BoardPosition> obstaclePos, int remainingMoves){
        this.obstaclePos = obstaclePos;
        this.remainingMoves = remainingMoves;
    }

    public int getId(){
        return id;
    }

    public LinkedList<BoardPosition> getObstaclePos(){
        return obstaclePos;
    }

    public int getRemainingMoves(){
        return remainingMoves;
    } 


}
