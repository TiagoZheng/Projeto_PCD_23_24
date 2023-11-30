package game;

import java.util.List;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;

public class ObstacleMover extends Thread {
	private Obstacle obstacle;
	private LocalBoard board;
	
	public ObstacleMover(Obstacle obstacle, LocalBoard board) {
		super();
		this.obstacle = obstacle;
		this.board = board;
	}

	@Override
	public void run() {

		for(int i = 0 ; i < obstacle.NUM_MOVES; i++) {
			moveObstacleToRandomCell();

		}
		// System.out.println("OBSTACULOS");
		// while (obstacle.getRemainingMoves() > 0 ) {
		// 	BoardPosition pos = board.getGoalPosition();
		// 	Cell nextCell = new Cell(pos);
		// 	obstacle.setCurrentCell(nextCell);
			
		// }

	}

	private void moveObstacleToRandomCell() {
		BoardPosition randomPosition = board.getRandomPosition();
		Cell nextCell = board.getCell(randomPosition);
		obstacle.setCurrentCell(nextCell);
		obstacle.decrementMoves();
	}
}
