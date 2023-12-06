package game;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;

public class Obstacle extends GameElement {
	
	private static final int NUM_MOVES=9;
	static final int OBSTACLE_MOVE_INTERVAL = 700;
	private int remainingMoves=NUM_MOVES;
	private Board board;
	private BoardPosition currentPosition;

	public Obstacle(Board board) {
		super();
		this.board = board;
		this.currentPosition = board.getRandomPosition();
		board.getCell(currentPosition).setGameElement(this);
	}
	
	public int getRemainingMoves() {
		return remainingMoves;
	}

	public void move(BoardPosition newPosition) {
		Cell currentCell = board.getCell(currentPosition);
		Cell newCell = board.getCell(newPosition);

		currentCell.setGameElement(null);
		newCell.setGameElement(this);
		currentPosition = newPosition;
		remainingMoves--;
	}
	
	public BoardPosition getInitialPosition(BoardPosition pos){
		return pos;
	}

}
