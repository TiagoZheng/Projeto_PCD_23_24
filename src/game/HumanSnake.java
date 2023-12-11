package game;

import java.util.LinkedList;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import remote.Direction;
 /** Class for a remote snake, controlled by a human 
  * 
  * @author luismota
  *
  */
public class HumanSnake extends Snake {
  
	private Direction lastPressedDirection;

	public HumanSnake(int id,Board board) {
		super(id,board);
	}

  
  @Override
  public void run() {
      doInitialPositioning();

     while (!board.isGameFinished()) {
        System.out.println("ANTES LAST DIRECTION: " + lastPressedDirection);
        if(lastPressedDirection != null){
          Cell nextMove;
          Cell headPos = cells.getLast();
          BoardPosition headBP = headPos.getPosition();
          System.out.println("LAST DIRECTION: " + lastPressedDirection);
          switch (lastPressedDirection) {
            case UP:
              headBP = headBP.getCellAbove();
              lastPressedDirection= null;
              break;
          
            case DOWN:
              headBP = headBP.getCellBelow();
              lastPressedDirection= null;
              break;

            case LEFT:
              headBP = headBP.getCellLeft();
              lastPressedDirection= null;
              break;

            case RIGHT:
              headBP = headBP.getCellRight();
              lastPressedDirection= null;
              break;
          }

          nextMove = board.getCell(headBP);
          try {
            move(nextMove);
            System.out.println("MOVI ME" + nextMove.getPosition());
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
     }
  }

  	public void setLastPressedDirection(Direction d) {
		  lastPressedDirection = d;
	}

  @Override
  public boolean isHumanPlayer() {
    return true;
  }

}
