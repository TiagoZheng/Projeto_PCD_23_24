package environment;

import java.io.Serializable;

/** Classe representing a position on the board, with some utilities
 * 
 * @author luismota
 *
 */

public class BoardPosition implements Serializable{
	public final int x;
	public final int y;

	public BoardPosition(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public boolean equals(Object obj) {
		BoardPosition other = (BoardPosition) obj;
		return other.x==x && other.y == y;
	}
	
	public double distanceTo(BoardPosition other) {
		double delta_x = y - other.y;
		double delta_y = x - other.x;
		return Math.sqrt(delta_x * delta_x + delta_y * delta_y);
	}


	public BoardPosition getCellAbove() {
		if(y>0)
			return new BoardPosition(x, y-1);
		return null;
	}
	public BoardPosition getCellBelow() {
		if(y<Board.NUM_ROWS-1)
			return new BoardPosition(x, y+1);
		return null;
	}
	public BoardPosition getCellLeft() {
		if(x>0)
			return new BoardPosition(x-1, y);
		return null;
	}
	public BoardPosition getCellRight() {
		if(x<Board.NUM_COLUMNS-1)
			return new BoardPosition(x+1, y);
		return null;
	}
}
