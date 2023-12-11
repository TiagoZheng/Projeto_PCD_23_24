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

	public synchronized void request(Snake snake) throws InterruptedException {
		//TODO coordination and mutual exclusion
		while(isOcupied()) {
			System.out.println("There is something, i`ll wait...");
			wait();
		}
		ocuppyingSnake=snake;
	}

	public synchronized void release() {
		ocuppyingSnake=null;
		notifyAll();
	}

	public boolean isOcupiedBySnake() {
		return ocuppyingSnake!=null;
	}

	public synchronized void catchGoal() throws InterruptedException {
		Goal currentGoal = getGoal();
		currentGoal.incrementValue();

		if (isOcupiedByGoal()) {	
			removeGoal();
			board.addGameElement(currentGoal);
			notifyAll();
		}
	}


	public  synchronized void setGameElement(GameElement element) {
		// TODO coordination and mutual exclusion
		gameElement=element;
		notifyAll();

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
		//TODO
		gameElement = null;
		notifyAll();
	}


	public Goal getGoal() {
		return (Goal)gameElement;
	}


	public boolean isOcupiedByGoal() {
		return (gameElement!=null && gameElement instanceof Goal);
	}

	public boolean isOcupiedByObstacle(){
		return (gameElement!=null && gameElement instanceof Obstacle);
	}
	
	public void setObstacle(Obstacle o){
		this.gameElement = o;
	}


    public void setGoal(Goal g) {
		this.gameElement = g;
    }

	public void setSnake(Snake s){
		this.ocuppyingSnake =s;
	}

}
