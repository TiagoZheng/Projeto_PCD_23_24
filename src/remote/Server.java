package remote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketPermission;

import environment.Board;
import environment.LocalBoard;
import game.HumanSnake;
import game.Snake;
import gui.SnakeGui;

public class Server {
	// TODO
	private ServerSocket ss;  
    public static final int PORT = 8080;
    private GameInfo gameState;

	public LocalBoard board;
	private SnakeGui gui;

	public Server() {
		try {
			ss = new ServerSocket(PORT);
		} catch (IOException e) {
			System.err.println("Cannot init SERVER... aborting!");
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		Server app = new Server();
		app.runServer();
	}

	private void runServer(){
        board = new LocalBoard();
        gui = new SnakeGui(board, 600, 0);
		gui.init();

		while(true) {
				try {
					waitForConnection();
					System.out.println("GAME STARTS");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private void waitForConnection() throws IOException {
		System.out.println("Wainting for connection...");
		Socket connection = ss.accept();

        new ServerOutputHandler(connection).start();
	}

//////////////////////////////////////////////////
//////////////////////////////////////////////////

    // This class sends out the game state to the client and creates new snakes for each client that joins
	public class ServerOutputHandler extends Thread{

        private Socket connection;
        private ObjectOutputStream out;

        public ServerOutputHandler(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run(){
            try {
                createHumanSnake();  

                new ServerInputHandler(connection).start();
                out = new ObjectOutputStream(connection.getOutputStream());
                
                processConnection();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeConnection();
            }
        }

        private void processConnection() throws IOException {
            System.out.println("[SERVER] Connection Accepted... Processing...");
            
            while (true) {
                gameState = board.getGameInfo();
                out.writeObject(gameState);
                out.reset();

                try {
                    Thread.sleep(Board.REMOTE_REFRESH_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void createHumanSnake(){
            HumanSnake humanSnake = new HumanSnake(board.humanSnakes.size(), board);
            board.addHumanSnake(humanSnake); 
            board.addSnake(humanSnake);
            humanSnake.start();
        }

        private void closeConnection() {
            try {
                out.close();
                connection.close();
            } catch (IOException e) {
                System.err.println("ERROR! Terminating connections!");
            }

        }
    }

    // This class recieves the directions from the client and then puts the human snake moving
    public class ServerInputHandler extends Thread {

        private Socket connection;
        private BufferedReader in;
        private int id;
        private String clientMessage;

        public ServerInputHandler(Socket connection){
            this.connection = connection;
            this.id = board.humanSnakes.size() -1;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while (true) {
                    if ((clientMessage = in.readLine()) != null) {
                        System.out.println("MESSAGE RECIEVED:" + clientMessage);

                    Direction d = null;
						switch (clientMessage) {
						case "UP":
							d = Direction.UP;
							break;
						case "DOWN":
							d = Direction.DOWN;
							break;
						case "LEFT":
							d = Direction.LEFT;
							break;
						case "RIGHT":
							d = Direction.RIGHT;
							break;
						}
                        board.moveClient(d, id);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        }
    }
}

