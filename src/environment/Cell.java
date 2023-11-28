package environment;

import java.io.Serializable;

import javax.sound.midi.SysexMessage;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.Snake;
import game.AutomaticSnake;
/** Main class for game representation. 
 * 
 * @author luismota
 *
 */
public class Cell {
	private BoardPosition position;
	private Snake ocuppyingSnake = null;
	private GameElement gameElement=null;
	public BoardPosition isOcupiedByGoal;
	private Board board;
	
	public GameElement getGameElement() {
		return gameElement;
	}


	public Cell(BoardPosition position) {
		super();
		this.position = position;
	}

	public BoardPosition getPosition() {
		return position;
	}

	public synchronized void request(Snake snake, Board board)
			throws InterruptedException {

		while (isOcupied()) {
			System.out.println("this is something");
			wait();
		}

		if (isOcupiedByGoal()) {
			Goal currentGoal = getGoal();
			currentGoal.incrementValue();
			removeGoal();
			notifyAll();;

			board.addGameElement(currentGoal);
		}

		ocuppyingSnake=snake;
	}

	public synchronized void release() {
		//TODO
		ocuppyingSnake=null;
		notifyAll();
	}

	public boolean isOcupiedBySnake() {
		return ocuppyingSnake!=null;
	}


	public  synchronized void setGameElement(GameElement element) {
		// TODO coordination and mutual exclusion
		gameElement=element;

	}

	public boolean isOcupied() {
		return isOcupiedBySnake() || (gameElement!=null && gameElement instanceof Obstacle);
	}


	public Snake getOcuppyingSnake() {
		return ocuppyingSnake;
	}


	public  Goal removeGoal() {
		// TODO
		return (Goal)(gameElement = null);
	}
	
	public void removeObstacle() {
		gameElement = null;

	}


	public Goal getGoal() {
		return (Goal)gameElement;
	}


	public boolean isOcupiedByGoal() {
		return (gameElement!=null && gameElement instanceof Goal);
	}
	
	

}
