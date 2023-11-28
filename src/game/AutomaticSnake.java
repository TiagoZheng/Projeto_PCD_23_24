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


		
		// System.out.println("GOAL POSITION" + goalPosition + "  [ID]" + getIdentification());
		// System.out.println("CURRENT POSITION" + currentHeadPosition + "   [ID]" + getIdentification());
	

	
 // TODO USE THE METHOD chooseNextPosition IN THE CLASS SNAKE
 /* 
		Cell currentHeadPosition = cells.getLast();
		ArrayList<BoardPosition> validCell = new ArrayList<BoardPosition>();
		validCell = (ArrayList<BoardPosition>) board.getNeighboringPositions(currentHeadPosition);

		BoardPosition nextMove = chooseNextPosition(validCell);
		Cell next = new Cell (new BoardPosition(nextMove.x,nextMove.y));
		System.out.println("CURRENT POSITION( x: " + currentHeadPosition.getPosition().x + " y: " + currentHeadPosition.getPosition().y + " ) [ID]" + getIdentification());
		move(next);
*/

  
		int dx = goalPosition.x - currentHeadPosition.x;
		int dy = goalPosition.y - currentHeadPosition.y;
		Cell nextCell;

		if (Math.abs(dx) > Math.abs(dy)) {
			if (dx > 0) {
				nextCell = board.getCell(currentHeadPosition.getCellRight());
			} else {
				nextCell = board.getCell(currentHeadPosition.getCellLeft());
			}
		} else {
			if (dy > 0) {
				nextCell = board.getCell(currentHeadPosition.getCellBelow());

			} else {
				nextCell = board.getCell(currentHeadPosition.getCellAbove());
			}
		}
		move(nextCell);


	}
}
