package game;

import environment.Board;
import environment.BoardPosition;
import environment.LocalBoard;

public class Goal extends GameElement  {
	private int value=1;
	private Board board;
	public static final int MAX_VALUE=10;
	private BoardPosition currentPosition;

	public Goal( Board board2) {
		this.board = board2;
		currentPosition = board.getGoalPosition();
	}
	
	public int getValue() {
		return value;
	}
	public void incrementValue() throws InterruptedException {
		value++;
	}

	public int captureGoal() {
//		TODO
		return -1;
	}

	public BoardPosition getPosition() {
		return currentPosition;
	}
}
