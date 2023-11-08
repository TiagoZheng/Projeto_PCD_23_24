package game;

import java.io.Serializable;
import java.util.LinkedList;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
/** Base class for representing Snakes.
 * Will be extended by HumanSnake and AutomaticSnake.
 * Common methods will be defined here.
 * @author luismota
 *
 */
public abstract class Snake extends Thread implements Serializable{
	private static final int DELTA_SIZE = 10;
	protected LinkedList<Cell> cells = new LinkedList<Cell>();
	protected int size = 5;
	private int id;
	protected Board board;
	         
	public Snake(int id,Board board) {
		this.id = id;
		this.board=board;
	}

	public int getSize() {
		return size;
	}

	public int getIdentification() {
		return id;
	}

	public int getLength() {
		return cells.size();
	}
	
	public LinkedList<Cell> getCells() {
		return cells;
	}
	protected void move(Cell nextCell) throws InterruptedException {
		//TODO
		if(nextCell.isOcupiedByGoal()) {
			Goal currentGoal = nextCell.getGoal();
			nextCell.removeGoal();
			this.cells.add(nextCell);
			currentGoal.incrementValue();
			board.addGameElement(currentGoal);

		// } else if(nextCell.isOcupied()) {

		} else {
			nextCell.request(this);
			cells.removeFirst().release();
			cells.add(nextCell);
			
		}
		SnakeGui.updatePosition();	
		
	}
	
	private BoardPosition chooseNextPosition(LinkedList<BoardPosition> neighboringPositions) {
        // Implement the logic to choose the next position based on neighboring positions
        // For example, you can randomly select one of the neighboring positions
        int randomIndex = (int) (Math.random() * neighboringPositions.size());
        return neighboringPositions.get(randomIndex);
    }

	public LinkedList<BoardPosition> getPath() {
		LinkedList<BoardPosition> coordinates = new LinkedList<BoardPosition>();
		for (Cell cell : cells) {
			coordinates.add(cell.getPosition());
		}
		return coordinates;
	}	
	protected void doInitialPositioning() {
		// Random position on the first column. 
		// At startup, snake occupies a single cell
		int posX = 0;
		int posY = (int) (Math.random() * Board.NUM_ROWS);
		BoardPosition at = new BoardPosition(posX, posY);
		
		try {
			board.getCell(at).request(this);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		cells.add(board.getCell(at));
		System.err.println("Snake "+getIdentification()+" starting at:"+getCells().getLast().toString());		
	}
	
	public Board getBoard() {
		return board;
	}
	
}
