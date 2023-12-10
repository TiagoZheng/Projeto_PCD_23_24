package game;

import java.util.LinkedList;

import environment.Board;
import environment.Cell;
 /** Class for a remote snake, controlled by a human 
  * 
  * @author luismota
  *
  */
public class HumanSnake extends Snake {
  
	
	public HumanSnake(int id,Board board) {
		super(id,board);
	}

  
  @Override
  public void run() {
      doInitialPositioning();
  }

}
