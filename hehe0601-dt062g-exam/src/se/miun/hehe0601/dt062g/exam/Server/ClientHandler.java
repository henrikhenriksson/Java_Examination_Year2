package se.miun.hehe0601.dt062g.exam.Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.Socket;

import se.miun.hehe0601.dt062g.exam.WordRandomizerFilterWriter;

/**
 * This class will handle and create new threads for each new client connecting
 * to the server.
 * 
 * @author Henrik Henriksson
 * @version 1.0
 * @since 2020-01-17
 *
 */
public class ClientHandler extends Thread {

	private Server server;
	private Socket socket;
	private String address;

	private DataInputStream in = null;
	private DataOutputStream out = null;
	private WordRandomizerFilterWriter fout = null;

//---------------------------------------------------------------------------
	// initiate the clienthandler with a server object.
	public ClientHandler(Socket s, Server server) {
		super();
		this.server = server;
		this.socket = s;
		this.address = socket.getInetAddress().getHostAddress() + ":"
				+ s.getPort();

	}

//---------------------------------------------------------------------------
	/**
	 * Attempt to create input and output streams. Close connection if either
	 * fails. Wrap the streams in buffers to increase performance.
	 */
	private void createStreams() {

		try {
			this.out = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));

			this.in = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e) {
			server.appendText(
					server.getTime() + "Error: Could not create streams\n" + e);
			closeConnection();
			return;
		}

	}

//---------------------------------------------------------------------------
	/**
	 * Close the streams. The close commands are separated into two try/catch
	 * blocks as to ensure that both are run, even if one fails.
	 */
	private void closeStreams() {
		try {

			this.in.close();
		} catch (IOException e) {
			server.appendText(server.getTime()
					+ "Error: Could not close the input stream\n" + e);

		}

		try {
			this.out.close();
		} catch (IOException e) {
			server.appendText(server.getTime()
					+ "Error: Could not close the output stream\n" + e);

		}
	}

//---------------------------------------------------------------------------
	/**
	 * Close the socket.
	 */
	private void closeSocket() {
		try {
			this.socket.close();
		} catch (IOException e) {
			server.appendText(server.getTime()
					+ "Error: Could not close the connection\n" + e);
			return;
		}
	}

//---------------------------------------------------------------------------
	/**
	 * Both close methods gathered into one, with print to the server indicating
	 * the disconnect..
	 */
	private void closeConnection() {
		closeStreams();
		closeSocket();
		server.appendText(server.getTime() + "CLOSING CONNECTION ON PORT "
				+ Server.port + " WITH " + address);
	}

//---------------------------------------------------------------------------
	// This function will be invoked when a message is sent to the server
	@Override
	public void run() {

		// open the streams
		createStreams();

		String message = null;

		try {
			// attempt to read the message.
			message = in.readUTF();
			server.appendText(server.getTime() + "Recieved message from "
					+ address + "\n>>" + message);
			// invoke method to manipulate the message string:
			sendMessageToClient(message);
		} catch (IOException e) {
			server.appendText(
					server.getTime() + "Error retrieving message from Client");
			closeConnection();
			return;
		}
		// always close the connection after each request.
		closeConnection();

	}

//---------------------------------------------------------------------------
	// Manipulate incomming string and return it to client in scrambeled form.
	private void sendMessageToClient(String str) throws IOException {

		Writer w = null;
		String returnString = null;

		w = new StringWriter();

		fout = new WordRandomizerFilterWriter(w);

		try {
			fout.write(str);
			fout.flush();
			returnString = w.toString();
			server.appendText(
					server.getTime() + "Sending Message to all Clients: ");

			out.writeUTF(returnString);
		} catch (IOException e) {
			server.appendText(
					server.getTime() + "Error Sending Message to Clients: ");
			return;
		} finally {
			if (w != null) {
				w.close();
			}
			if (fout != null) {
				fout.close();
			}

		}

	}

//---------------------------------------------------------------------------
}
