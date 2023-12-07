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
		//System.err.println("initial size:"+cells.size());
		//cells.getLast().request(this); //Put in boardcell

		// Automatic movement
		while (!Thread.interrupted()) {
			try {
				if(board.isGameFinished()){
					System.out.println("Game End!");
					break;
				}	
				// System.out.println("FINITO? " + board.isGameFinished());
				move(nextCell());
				Thread.sleep(Board.PLAYER_PLAY_INTERVAL);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// Where to go next
	public Cell nextCell(){
		LinkedList<BoardPosition> nextMove = chooseDirection();
		int i = randomValue(nextMove.size());

		return board.getCell(nextMove.get(i));
	}
	
	private int distanceToGoal(BoardPosition position1, BoardPosition position2){
		return Math.abs(position1.x - position2.x) + Math.abs(position1.y - position2.y);
	}

	private LinkedList<BoardPosition> chooseDirection(){
		BoardPosition goalPosition = board.getGoalPosition();
		BoardPosition headPosition = cells.getLast().getPosition();
		List<BoardPosition> validNeighboringPositions = board.getNeighboringPositions(board.getCell(headPosition));
		// System.out.println("VALID: " + validNeighboringPositions);
		LinkedList<BoardPosition> nextGoodMove = new LinkedList<BoardPosition>();

		if (this.getLength() > 1){
			LinkedList<BoardPosition> ListBP = cellToBoardPosition(this.cells);
			// System.out.println("PREV: " + bp);
			for(BoardPosition bp : ListBP)
				validNeighboringPositions.remove(bp);
			// System.out.println("NEW_VALID: " + validNeighboringPositions);
		}

		int distance = distanceToGoal(headPosition, goalPosition);

		for(BoardPosition neighbor : validNeighboringPositions) {
			// System.out.println("NEIGHBOR: " + neighbor);
			int newDistance = distanceToGoal(goalPosition, neighbor);
			if(newDistance < distance){
				nextGoodMove.add(neighbor);
			}
		}
		return nextGoodMove;
	}

	private int randomValue (int i) {
		return (int) (Math.random() * i);
	}

	public LinkedList<BoardPosition> cellToBoardPosition (LinkedList<Cell> cells) {
		LinkedList<BoardPosition> bp = new LinkedList<BoardPosition>();
		for(Cell c : cells){
			bp.add(c.getPosition());
		}
		return bp;
	}
}

