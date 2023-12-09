package remote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket socket;
    private Client client;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }

    @Override
    public void run(){
        try {
            getStreams();
            processConnection();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void processConnection() {
        System.out.println("[CLIENT:] I joined the game...");
    }

    public void getStreams() throws IOException{
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("ERROR! Terminating connections!");
        }

    }
}
