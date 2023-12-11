package environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.ObstacleMover;
import game.Snake;
import remote.GameInfo;
import remote.Server;
import remote.SnakeInfo;
import game.AutomaticSnake;

/** Class representing the state of a game running locally
 * 
 * @author luismota
 *
 */
public class LocalBoard extends Board{
	
	private static final int NUM_SNAKES = 1;
	private static final int NUM_OBSTACLES = 20;
	public static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 1;
	private ObstacleMover obstacleMover;

	LinkedList<SnakeInfo> snakeInfos = new LinkedList<>();

	public LocalBoard() {
		
		for (int i = 0; i < NUM_SNAKES; i++) {
			AutomaticSnake snake = new AutomaticSnake(i, this);
			snakes.add(snake);
		}

		addObstacles(NUM_OBSTACLES);
		Goal goal=addGoal();
//		System.err.println("All elements placed");
	}

	public void init() {
		for(Snake s : snakes)
			s.start();

		// TODO: launch other threads
		for(Obstacle o : getObstacles()){
			o.setRandomPos();
			obstacleMover = new ObstacleMover(o, this);
			obstacleMover.start();
			
		}
			
		setChanged();
	}

	

	@Override
	public void handleKeyPress(int keyCode) {
		// do nothing... No keys relevant in local game
	}

	@Override
	public void handleKeyRelease() {
		// do nothing... No keys relevant in local game
	}


	public LinkedList<BoardPosition> getObstaclePos(){
		LinkedList<BoardPosition> pos = new LinkedList<BoardPosition>();
		for (int x = 0; x < NUM_COLUMNS; x++) {
			for (int y = 0; y < NUM_ROWS; y++) {
				if(cells[x][y].isOcupiedByObstacle()){
					pos.add(cells[x][y].getPosition());
				}
			}
		}
		return pos;
	}

	public int getRemainingMoves(){
		return obstacles.getLast().getRemainingMoves();
	}

	public BoardPosition getGoalPos(){
		return getGoalPosition();
	}

	public int getGoalValue(){
		return goal.getValue();
	}

	public LinkedList<SnakeInfo> getSnakeInfos(){
		snakeInfos.clear();
		for (int x = 0; x < NUM_COLUMNS; x++) {
			for (int y = 0; y < NUM_ROWS; y++) {
				if(cells[x][y].isOcupiedBySnake()){
					Snake s = cells[x][y].getOcuppyingSnake();
					int id = s.getIdentification();
					BoardPosition bp = new BoardPosition(x,y);
					snakeInfos.add(new SnakeInfo(id, bp, s.isHumanPlayer()));
				}
			}
		}
		return snakeInfos;
		
	}

    public GameInfo getGameInfo() {
		GameInfo gameInfo = new GameInfo(getObstaclePos(), getRemainingMoves(),getGoalValue(), getGoalPos(), getSnakeInfos());
        return gameInfo;
    }
}
