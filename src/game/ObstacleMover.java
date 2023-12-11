package game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;

public class ObstacleMover extends Thread {
	private Obstacle obstacle;
	private LocalBoard board;
	
	public ObstacleMover(Obstacle obstacle, LocalBoard board) {
		super();
		this.obstacle = obstacle;
		this.board = board;
    }	

	@Override
	public void run() {
		// TODO
		moveObstacles(obstacle.getRemainingMoves());
	}

	public void moveObstacles(int numMoves){
		ExecutorService threadPool = Executors.newFixedThreadPool(board.NUM_SIMULTANEOUS_MOVING_OBSTACLES);

		for (int i = 0; i < numMoves; i++) {
			threadPool.submit( () -> {
				moveObstacle();
			});
		}
		threadPool.shutdown();
	}

	public void moveObstacle(){

		BoardPosition bp = board.getRandomPosition();
		while(board.getCell(bp).isOcupiedByGoal() || board.getCell(bp).isOcupied() ){
			bp = board.getRandomPosition();
		}
		
		try {
			obstacle.move(bp);
			Thread.sleep(obstacle.OBSTACLE_MOVE_INTERVAL);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
