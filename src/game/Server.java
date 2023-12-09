package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketPermission;

import environment.LocalBoard;
import gui.SnakeGui;

public class Server {
	// TODO
	private ServerSocket ss;  
	public LocalBoard board = new LocalBoard();
	private SnakeGui game = new SnakeGui(board, 600, 0);

	public static final int PORT = 8080;

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
		while(true) {
				try {
					waitForConnection();
					game.init();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private void waitForConnection() throws IOException {
		System.out.println("Wainting for connection...");
		Socket connection = ss.accept();
		ServerHandler serverHandler = new ServerHandler(connection, board);
		serverHandler.start();
	}



}
