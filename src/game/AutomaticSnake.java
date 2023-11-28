package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

public class AutomaticSnake extends Snake {

	public AutomaticSnake(int id, LocalBoard board) {
		super(id,board);
	}

	@Override
	public void run() {
		doInitialPositioning();
    	System.err.println("Initial size:" + cells.size());
    	try {
        	while (true) {
            	moveTowardsGoal();
            	Thread.sleep(200); 
        	}
    	} catch (InterruptedException e) {
        	e.printStackTrace();
    	}
		//TODO: automatic movement
		
	}
		

	public void moveTowardsGoal() throws InterruptedException {
		BoardPosition goalPosition = board.getGoalPosition();
		BoardPosition currentHeadPosition = cells.getLast().getPosition();

		List<BoardPosition> validNeighboringPositions = board.getNeighboringPositions(board.getCell(currentHeadPosition));
		

		if (!validNeighboringPositions.isEmpty()) {
			int minDistance = Integer.MAX_VALUE;
			BoardPosition nextMove = null;
	
			for (BoardPosition neighbor : validNeighboringPositions) {
				int distance = calculateDistance(neighbor, goalPosition);
				if (distance < minDistance) {
					minDistance = distance;
					nextMove = neighbor;
				}
			}
	
			if (nextMove != null) {
				Cell nextCell = board.getCell(nextMove);
				move(nextCell);
			}
		}
	}

	public void resetDirection(){
		BoardPosition currentHeadPosition = cells.getLast().getPosition();
		List<BoardPosition> validNeighboringPositions = board.getNeighboringPositions(board.getCell(currentHeadPosition));

			try {
				if (!validNeighboringPositions.isEmpty()) {
				BoardPosition newDirection = chooseNextPosition(validNeighboringPositions);
				Cell nextMove = board.getCell(newDirection);
				move(nextMove);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}	

	private int calculateDistance(BoardPosition position1, BoardPosition position2) {
		return Math.abs(position1.x - position2.x) + Math.abs(position1.y - position2.y);
	}

}
