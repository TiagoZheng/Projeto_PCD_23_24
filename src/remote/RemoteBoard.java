package remote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

import environment.LocalBoard;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import game.AutomaticSnake;
import game.Goal;
import game.HumanSnake;
import game.Obstacle;
import game.Snake;

/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Srver.
 * Only for part II of the project.
 * @author luismota
 *
 */
public class RemoteBoard extends Board{
	
	public RemoteBoard(GameInfo gameInfo) throws InterruptedException{
		//Obstacles
		for(BoardPosition bp : gameInfo.getObstaclePosGameInfo()){
			Obstacle o = new Obstacle(this);
			cells[bp.x][bp.y].setObstacle(o);
		}
		System.out.println("OBSTACLE PLACE CHECK!");

		//Goal
		BoardPosition goalBP = gameInfo.getGoalPosGameInfo();
		Goal g = new Goal(this);
		cells[goalBP.x][goalBP.y].setGoal(g);
		System.out.println("GOAL PLACE CHECK!");

		//Snakes
		// for(SnakeInfo snakeInfo : gameInfo.getSnakeInfoGameInfo()){
		// 	if(snakeInfo.isHumanSnake()){
		// 		Snake humanSnake = new HumanSnake(snakeInfo.getIdSnakeInfo(), this);
		// 		cells[snakeInfo.getSnakePosSnakeInfo().x][snakeInfo.getSnakePosSnakeInfo().y].request(humanSnake);
		// 	} else {
		// 		Snake autoSnake = new AutomaticSnake(snakeInfo.getIdSnakeInfo(), this);
		// 		cells[snakeInfo.getSnakePosSnakeInfo().x][snakeInfo.getSnakePosSnakeInfo().y].request(autoSnake);
		// 	}
		// }
		// System.out.println("SNAKES PLACE CHECK!");
	}

	@Override
	public void handleKeyPress(int keyCode) {
		//TODO
	}

	@Override
	public void handleKeyRelease() {
		// TODO
	}

	@Override
	public void init() {
			
	}
    public void update(GameInfo gameInfo) throws InterruptedException {
		// Update client GUI
		// Criar grid no remote
		for (int x = 0; x < NUM_COLUMNS; x++) {
			for (int y = 0; y < NUM_ROWS; y++) {
				cells[x][y].setObstacle(null);;
			}
		}
		
		// Update on obstacle position 
		for(BoardPosition bp : gameInfo.getObstaclePosGameInfo()){
			Obstacle o = new Obstacle(this);
			o.setRemainingMoves(gameInfo.getRemainingMovesGameInfo());
			cells[bp.x][bp.y].setObstacle(o);
			System.out.println("OBSTACLE UPDATE CHECK!");
		}

		// Update on goals position 
		BoardPosition goalBP = gameInfo.getGoalPosGameInfo();
		Goal g = new Goal(this);
		g.setValue(gameInfo.getGoalValueGameInfo());
		cells[goalBP.x][goalBP.y].setGoal(g);
		System.out.println("GOAL UPDATE CHECK!");

		//Update on Snake position
		// for(SnakeInfo snakeInfo : gameInfo.getSnakeInfoGameInfo()){
		// 	if(snakeInfo.isHumanSnake()){
		// 		Snake humanSnake = new HumanSnake(snakeInfo.getIdSnakeInfo(), this);
		// 		humanSnake.move(getCell(new BoardPosition(snakeInfo.getSnakePosSnakeInfo().x, snakeInfo.getSnakePosSnakeInfo().y)));
		// 		// cells[snakeInfo.getSnakePosSnakeInfo().x][snakeInfo.getSnakePosSnakeInfo().y].request(humanSnake);

		// 	} else {
		// 		Snake autoSnake = new AutomaticSnake(snakeInfo.getIdSnakeInfo(), this);
		// 		autoSnake.move(getCell(new BoardPosition(snakeInfo.getSnakePosSnakeInfo().x, snakeInfo.getSnakePosSnakeInfo().y)));
		// 		// cells[snakeInfo.getSnakePosSnakeInfo().x][snakeInfo.getSnakePosSnakeInfo().y].request(autoSnake);
	
		// 	}
		// }
		// System.out.println("SNAKES UPDATE CHECK!");
		setChanged();
		
    }

	public void addObstacle(BoardPosition bp){
		cells[bp.x][bp.y].setObstacle(new Obstacle(this));
	}
	

}
