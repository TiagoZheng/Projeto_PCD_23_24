package remote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

import environment.Board;
import environment.BoardPosition;
import environment.LocalBoard;
import gui.BoardComponent;
import gui.SnakeGui;

/** Remore client, only for part II
 * 
 * @author luismota
 *
 */

public class Client {
	private Socket socket;
	private String hostName;

	private ObjectInputStream in;
    private PrintWriter out;

	private GameInfo gameInfo;
	private RemoteBoard board;
	private SnakeGui gui;
	private BoardComponent boardGui;

	LinkedList<BoardPosition> bp = new LinkedList<>();

	

	private int port;

	public Client(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}

	public static void main(String[] args) {
	// TODO
		Client app = new Client("localhost", 8080);
		app.runClient();
	}

	private void runClient() {
		try {
			socket = new Socket(InetAddress.getByName(hostName), Server.PORT);
			System.out.println("CLIENT CONNECTED!");
			
		} catch (IOException e) {
			System.err.println("[CLIENT: ] Error connecting to server... ABORTING!");
			System.exit(1);
		}

		new ClientInputHandler(socket).start();
	}

	//////////////////////////////////////////////
	//////////////////////////////////////////////

	public class ClientInputHandler extends Thread{
    	private Socket connection;

		public ClientInputHandler(Socket connection) {
			this.connection = connection;
		}

		@Override
		public void run(){
			try {
				in = new ObjectInputStream(connection.getInputStream());
				gameInfo = (GameInfo) in.readObject();
				board = new RemoteBoard(gameInfo);
				gui = new SnakeGui(board, 600, 0);
				gui.init();


				new ClientOutputHandler(socket, boardGui).start();

				processConnection();

			} catch (IOException | ClassNotFoundException | InterruptedException e) {
					e.printStackTrace();
			} finally {
				closeConnection();
			}

		}

		private void processConnection() throws ClassNotFoundException, IOException, InterruptedException {
			while (true) {
				gameInfo = (GameInfo) in.readObject();
				board.update(gameInfo);
			}
		}

		private void closeConnection() {
			try {
				in.close();
				socket.close();
			} catch (IOException e) {
				System.err.println("ERROR! Terminating connections!");
			}

		}
	}

	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////

	public class ClientOutputHandler extends Thread {
		private Socket connection;
		private BoardComponent boardGui;
		private Direction lastPressedDirection;

		public ClientOutputHandler(Socket connection, BoardComponent boardGui) {
			this.connection = connection;
			this.boardGui = boardGui;
		}

		@Override
		public void run() {
			try {
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())),true);

				while (true) {

				lastPressedDirection = board.getLastPressedDirection();

					if (lastPressedDirection != null) {
						//System.out.println("Sending direction: " + lastPressedDirection.toString());

						out.println(lastPressedDirection.toString());
						board.clearLastPressedDirection();
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
					connection.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
