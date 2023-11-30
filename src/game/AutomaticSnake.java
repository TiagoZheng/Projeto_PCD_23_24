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
		
	}
		

	public void moveTowardsGoal() throws InterruptedException {
		BoardPosition goalPosition = board.getGoalPosition();
		BoardPosition currentHeadPosition = cells.getLast().getPosition();
	
		List<BoardPosition> validNeighboringPositions = board.getNeighboringPositions(board.getCell(currentHeadPosition));
	
		if (!validNeighboringPositions.isEmpty()) {
			int minDistance = Integer.MAX_VALUE;
			BoardPosition nextMove = null;
	
			for (BoardPosition neighbor : validNeighboringPositions) {
				System.out.println("Checking where to go....");
				int distance = calculateDistance(neighbor, goalPosition);
				if (distance < minDistance) {
					minDistance = distance;
					nextMove = neighbor;
				}
			}
	
			if (nextMove != null) {
				Cell nextCell = board.getCell(nextMove);
	
				// Check if the next cell contains a goal
				if (nextCell.isOcupiedByGoal()) {
					Goal goal = nextCell.getGoal();
					int goalValue = goal.captureGoal();
	
					// Increase the size of the snake based on the goal value
					for (int i = 0; i < goalValue; i++) {
						// Add new cells to the snake
						Cell newCell = new Cell(nextMove);
						System.out.println("Got the goal going to get bigger");
						cells.addFirst(newCell);
						board.setChanged();
					}
	
					// Remove the goal from the cell
					nextCell.removeGoal();

					 // Check if the game should stop
					if (goalValue >= Goal.MAX_VALUE) {
						board.setFinished(true);
						System.out.println("Game End - Maximum Goal Value Reached!");
						return;
					}
					
				}
	
				// Move the snake to the next cell
				move(nextCell);
				System.out.println("MOVING!!");
			}
		}
	}

	public void resetDirection(){
		BoardPosition currentHeadPosition = cells.getLast().getPosition();
		List<BoardPosition> validNeighboringPositions = board.getNeighboringPositions(board.getCell(currentHeadPosition));

			try {
				System.out.println("CHANGED DIRECTION");
				if (!validNeighboringPositions.isEmpty()) {
				BoardPosition newDirection = chooseNextPosition(validNeighboringPositions);
				Cell nextMove = board.getCell(newDirection);
				move(nextMove);
				
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
	}	

	private int calculateDistance(BoardPosition position1, BoardPosition position2) {
		return Math.abs(position1.x - position2.x) + Math.abs(position1.y - position2.y);
	}

}
