package game;

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
				System.out.println("Hellos");
            	moveTowardsGoal();
            	Thread.sleep(1000); // Adjust the delay as needed
        	}
    	} catch (InterruptedException e) {
        	e.printStackTrace();
    	}
		//TODO: automatic movement
		
	}
		

	public void moveTowardsGoal() throws InterruptedException {
		BoardPosition goalPosition = board.getGoalPosition();
		BoardPosition currentHeadPosition = cells.getLast().getPosition();
		System.out.println("GOAL POSITION" + goalPosition + "  [ID]" + getIdentification());
		System.out.println("CURRENT POSITION" + currentHeadPosition + "   [ID]" + getIdentification());
	
		int dx = goalPosition.x - currentHeadPosition.x;
		int dy = goalPosition.y - currentHeadPosition.y;
		Cell nextCell;
	
		if (Math.abs(dx) > Math.abs(dy)) {
			if (dx > 0) {
				// Move right
				nextCell = board.getCell(currentHeadPosition.getCellRight());
			} else {
				// Move left
				nextCell = board.getCell(currentHeadPosition.getCellLeft());
				
			}
		} else {
			if (dy > 0) {
				// Move down
				nextCell = board.getCell(currentHeadPosition.getCellBelow());

			} else {
				// Move up
				nextCell = board.getCell(currentHeadPosition.getCellAbove());

			}
		}
		move(nextCell);
	}



}
