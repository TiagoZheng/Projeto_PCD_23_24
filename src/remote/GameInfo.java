package remote;

import java.io.Serializable;
import java.util.LinkedList;

import environment.BoardPosition;
import environment.Cell;
import game.Goal;
import game.Obstacle;
import game.Snake;

public class GameInfo implements Serializable{

    private int remainingMoves, goalValue;
    private BoardPosition goalPos;
    LinkedList<BoardPosition> obstaclePos = new LinkedList<>();
    LinkedList<SnakeInfo> snakeInfo = new LinkedList<>();

    public GameInfo(LinkedList<BoardPosition> obstaclePos, int remainingMoves, int goalValue, BoardPosition goalPos, LinkedList<SnakeInfo> snakeInfo){
        this.obstaclePos = obstaclePos;
        this.remainingMoves = remainingMoves;
        this.goalValue = goalValue;
        this.goalPos = goalPos;
        this.snakeInfo = snakeInfo;
    }

    public LinkedList<BoardPosition> getObstaclePosGameInfo(){
        return obstaclePos;
    }

    public int getRemainingMovesGameInfo(){
        return remainingMoves;
    } 

    public BoardPosition getGoalPosGameInfo(){
        return goalPos;
    }

    public int getGoalValueGameInfo(){
        return goalValue;
    }

    public LinkedList<SnakeInfo> getSnakeInfoGameInfo(){
        return snakeInfo;
    }

}
