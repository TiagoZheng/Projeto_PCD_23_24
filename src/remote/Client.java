package remote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import game.Server;

/** Remore client, only for part II
 * 
 * @author luismota
 *
 */

public class Client {
	private Socket socket;
	private String host;
	private int port;
	private ClientHandler handler;

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) {
	// TODO
		Client app = new Client("localhost", 8080);
		app.runClient();
	}

	private void runClient() {
		try {
			socket = new Socket(InetAddress.getByName(host), port);
			handler = new ClientHandler(socket,this);
			handler.start();
			
		} catch (IOException e) {
			System.err.println("[CLIENT: ] Error connecting to server... ABORTING!");
			System.exit(1);
		}
	}

}
