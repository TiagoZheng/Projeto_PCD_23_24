package remote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

import environment.LocalBoard;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import game.Goal;
import game.Obstacle;
import game.Snake;

/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Srver.
 * Only for part II of the project.
 * @author luismota
 *
 */
public class RemoteBoard extends Board{
	
	public RemoteBoard(GameInfo gameInfo){
		LinkedList<BoardPosition> bpObs = gameInfo.getObstaclePos();
		for(BoardPosition bp : bpObs){
			Obstacle o = new Obstacle(this);
			cells[bp.x][bp.y].setObstacle(o);
		}
		
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
    public void update(GameInfo gameInfo) {
		//TODO
		for (int x = 0; x < NUM_COLUMNS; x++) {
			for (int y = 0; y < NUM_ROWS; y++) {
				cells[x][y].setObstacle(null);;
			}
		}
		
		for(BoardPosition bp : gameInfo.getObstaclePos()){
			Obstacle o = new Obstacle(this);
			o.setRemainingMoves(gameInfo.getRemainingMoves());
			cells[bp.x][bp.y].setObstacle(o);
		}
		setChanged();
		
    }

	public void addObstacle(BoardPosition bp){
		cells[bp.x][bp.y].setObstacle(new Obstacle(this));
	}
	

}
