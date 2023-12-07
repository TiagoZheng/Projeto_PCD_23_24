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
	private boolean isIncreasing = false;
	
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
	
	public void move(Cell cell) throws InterruptedException {
		// System.out.println("MOVING MOVING MOVING");
		// System.out.println(cells.size());
		if (cell.isOcupiedByGoal()) {		
			// cell.catchGoal();	
			Goal currentGoal = cell.getGoal();
			cell.request(this);
			cells.add(cell);

			cell.removeGoal();
			currentGoal.incrementValue();
			board.addGameElement(currentGoal);

		} else {
			cell.request(this);
			cells.removeFirst().release();
			cells.add(cell);
		}
		board.setChanged();	
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
			//Placement of players, if occupied choose a new position
			if(board.getCell(at).isOcupiedBySnake()) {
				//System.out.println("Snake in this position, going to choose a new one...");
				doInitialPositioning();
			} else {
				board.getCell(at).request(this);
				cells.add(board.getCell(at));
			}
				
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//System.err.println("Snake "+getIdentification()+" starting at:"+getCells().getLast());		
	}
	
	public Board getBoard() {
		return board;
	}

	public Cell nextCell() {
		return null;
	}

	/*Criar uma metodo
		Se boolean tiver a true snake aumenta se nao snake anda normal
	*/

	// public void movement(Cell cell, int value) throws InterruptedException{
	// 	// int value = goal.getValue();
	// 	if(!isIncreasing){
	// 		cell.request(this);
	// 		cells.removeFirst().release();
	// 		cells.add(cell);
	// 	}else {
	// 		for(int i = 0; i < value; i++){
	// 			cell.request(this);
	// 			cells.add(cell);
	// 		}
	// 		isIncreasing=false;
	// 	}
	// }
}
