package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import environment.LocalBoard;

public class ServerHandler extends Thread{
    private Socket connection;
    private HumanSnake humanSnake;
    private LocalBoard board;

    private ObjectInputStream in;
    private ObjectOutputStream out;


    public ServerHandler(Socket connection, LocalBoard board) {
        this.connection = connection;
        this.board = board;
    }

    @Override
    public void run(){
        try {
            getStream();
            processConnection();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void processConnection() {
        System.out.println("[SERVER] Connection Accepted... Processing...");
        humanSnake = new HumanSnake(board.humanSnakes.size(), board);
        System.out.println("[SERVER] Human Snake Created...");
    }


    public void getStream() throws IOException {
        in = new ObjectInputStream(connection.getInputStream());
		out = new ObjectOutputStream(connection.getOutputStream());
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            connection.close();
        } catch (IOException e) {
            System.err.println("ERROR! Terminating connections!");
        }

    }
}
