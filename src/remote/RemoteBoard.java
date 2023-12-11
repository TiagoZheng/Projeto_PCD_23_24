package remote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.function.UnaryOperator;

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

	private int up, down, left, right;
	private Direction lastPressedDirection=null;
	private Board board;

	public RemoteBoard(Board board){
		this.board = board;
	}

	public RemoteBoard(GameInfo gameInfo) throws InterruptedException{
		//Obstacles
		for(BoardPosition bp : gameInfo.getObstaclePosGameInfo()){
			Obstacle o = new Obstacle(this);
			cells[bp.x][bp.y].setObstacle(o);
		}

		//Goal
		BoardPosition goalBP = gameInfo.getGoalPosGameInfo();
		Goal g = new Goal(this);
		cells[goalBP.x][goalBP.y].setGoal(g);

		//Snakes
		for(SnakeInfo snakeInfo : gameInfo.getSnakeInfoGameInfo()){
			if(snakeInfo.isHumanSnake()){
				Snake humanSnake = new HumanSnake(snakeInfo.getIdSnakeInfo(), this);

				for(BoardPosition snakeBP : snakeInfo.getSnakePosSnakeInfo()){
					//this.getCell(snakeBP).request(humanSnake);
					cells[snakeBP.x][snakeBP.y].setSnake(humanSnake);
					// humanSnake.cells.add(getCell(snakeBP));
				}		

			} else {
				Snake autoSnake = new AutomaticSnake(snakeInfo.getIdSnakeInfo(), this);	
				for(BoardPosition snakeBP : snakeInfo.getSnakePosSnakeInfo()){
					// this.getCell(snakeBP).request(autoSnake);
					cells[snakeBP.x][snakeBP.y].setSnake(autoSnake);
					// autoSnake.cells.add(getCell(snakeBP));
				}
			}
		
		}
	}

	@Override
	public void handleKeyPress(int keyCode) {
		// if(keyCode == left) {
		// 	lastPressedDirection=environment.Direction.LEFT;
		// }
		// else if(keyCode == right) {
		// 	lastPressedDirection=environment.Direction.RIGHT;
		// }
		// else if(keyCode == up) {
		// 	lastPressedDirection=environment.Direction.UP;
		// }
		// else if(keyCode == down) {
		// 	lastPressedDirection=environment.Direction.DOWN;
		// }
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
		}

		// Update on goals position 
		BoardPosition goalBP = gameInfo.getGoalPosGameInfo();
		Goal g = new Goal(this);
		g.setValue(gameInfo.getGoalValueGameInfo());
		cells[goalBP.x][goalBP.y].setGoal(g);

		//Update on Snake position
		for(SnakeInfo snakeInfo : gameInfo.getSnakeInfoGameInfo()){
			
			if(snakeInfo.isHumanSnake()){
				Snake humanSnake = new HumanSnake(snakeInfo.getIdSnakeInfo(), this);
				for(BoardPosition snakeBP : snakeInfo.getSnakePosSnakeInfo()){
					//this.getCell(snakeBP).request(humanSnake);
					cells[snakeBP.x][snakeBP.y].setSnake(humanSnake);
					System.out.println("Moving");
					// humanSnake.cells.add(getCell(snakeBP));
					
					// cells[humanSnake.cells.getFirst().getPosition().x][humanSnake.cells.getFirst().getPosition().y].setSnake(null);
				}

			} else {
				Snake autoSnake = new AutomaticSnake(snakeInfo.getIdSnakeInfo(), this);
				for(BoardPosition snakeBP : snakeInfo.getSnakePosSnakeInfo()){
					// this.getCell(snakeBP).request(autoSnake);
					cells[snakeBP.x][snakeBP.y].setSnake(autoSnake);
					// autoSnake.cells.add(getCell(snakeBP));

					// cells[autoSnake.cells.getFirst().getPosition().x][autoSnake.cells.getFirst().getPosition().y].setSnake(null);
				}
			}
		}
		setChanged();
		
    }

	public void addObstacle(BoardPosition bp){
		cells[bp.x][bp.y].setObstacle(new Obstacle(this));
	}
	

}
