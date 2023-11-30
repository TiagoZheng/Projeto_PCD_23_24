package game;

import environment.Board;
import environment.Cell;
import environment.LocalBoard;

public class Obstacle extends GameElement {
	
	
	static final int NUM_MOVES=3;
	static final int OBSTACLE_MOVE_INTERVAL = 400;
	private int remainingMoves=NUM_MOVES;
	private Board board;
	private Cell currentCell;

	public Obstacle(Board board) {
		super();
		this.currentCell = null;
		this.board = board;
		startMoverThread();
	}

	private void startMoverThread() {
        ObstacleMover mover = new ObstacleMover(this, (LocalBoard) board);
		System.out.println("MOVING OBJECTS");
        mover.start();
    }
	
	public int getRemainingMoves() {
		return remainingMoves;
	}

	public void decrementMoves(){
		remainingMoves--;
	}

	public void setCurrentCell(Cell cell){
		this.currentCell = cell;
	}

	public Cell getCurrentCell(){
		return currentCell;
	}

}
