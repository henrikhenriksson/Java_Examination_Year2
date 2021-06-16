package se.miun.hehe0601.dt062g.exam.Server;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This is a Server GUI class that listens to ports and handles incomming
 * messages from the client.
 * 
 * @author Henrik Henriksson
 * @version 1.0
 * @since 2020-01-17
 *
 */
public class Server extends JFrame {

	private static final long serialVersionUID = 142595684038040292L;

	static int port;
	private final int MIN_PORT_VALUE = 0;
	private final int MAX_PORT_VALUE = 65535;

	private JPanel listenPanel;
	private JTextField portField;
	private JButton startButton;
	private JPanel textPanel;
	private static JTextArea messageArea;

	// static so they can be called in static method.
	private LocalDateTime currentTime;
	private DateTimeFormatter formatter;

	private Thread newThread;

//---------------------------------------------------------------------------
	// constructor initiates the Frame
	public Server() {
		super("Server");

		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		makeFrame();
		makeListenPanel();
		makeTextPanel();

	}

	// get the current time in a formatted string:
	public String getTime() {
		currentTime = LocalDateTime.now();

		return currentTime.format(formatter);

	}

//---------------------------------------------------------------------------
	// Static method to add text to the Server GUI textArea with current
	// timestamp."
	public void appendText(String str) {

		messageArea.append(getTime() + " - " + str + "\n");

	}

//---------------------------------------------------------------------------
	// initialize the frame.
	private void makeFrame() {
		setSize(800, 600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

//---------------------------------------------------------------------------
	// Create the server GUI textArea
	private void makeTextPanel() {
		textPanel = new JPanel(new BorderLayout());

		messageArea = new JTextArea();
		messageArea.setText("");

		messageArea.setWrapStyleWord(true);
		messageArea.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(messageArea);
		scrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		textPanel.add(scrollPane, BorderLayout.CENTER);

		this.add(textPanel, BorderLayout.CENTER);

	}

//---------------------------------------------------------------------------
	// Create the ServerArea top Panel
	private void makeListenPanel() {

		JPanel topPanel = new JPanel(new BorderLayout());
		listenPanel = new JPanel();
		listenPanel.setLayout(new BoxLayout(listenPanel, BoxLayout.X_AXIS));
		JLabel portLabel = new JLabel("Port: ");
		portField = new JTextField();

		listenPanel.add(portLabel);
		listenPanel.add(portField);

		startButton = new JButton("Start");
		startButton.addActionListener(e -> attemptConnection());

		listenPanel.add(startButton);
		topPanel.add(listenPanel, BorderLayout.CENTER);

		add(topPanel, BorderLayout.NORTH);

	}

//---------------------------------------------------------------------------
	// invoked when user presses "start" in the GUI, attempts to start a new
	// socket connection.
	private void attemptConnection() {

		try {
			port = Integer.parseInt(portField.getText());
			if (port < MIN_PORT_VALUE || port > MAX_PORT_VALUE) {
				throw new NumberFormatException();
			}

			newSocket();
			messageArea.append(getTime() + " - " + "Listening on Port : "
					+ portField.getText() + "\n");

		} catch (NumberFormatException nfe) {
			messageArea.append(getTime() + " - " + "invalid port: "
					+ portField.getText() + "\n");
		}

	}

//---------------------------------------------------------------------------
	// initialize a new socket within a new thread, as to not lock the
	// eventthread while listening to new connections.
	private void newSocket() {

		if (newThread != null) {
			messageArea.append(getTime()
					+ " Server is already listening to a different port");
		} else {

			newThread = new Thread(() -> {
				ServerSocket ss = null;
				try {
					ss = new ServerSocket(port);
				} catch (IOException e) {
					messageArea.append(getTime()
							+ "Service interrupted. Unable to establish serverSocket");
					e.printStackTrace();
				}
				newThread.start();
				while (true) {
					Socket s = null;
					try {
						s = ss.accept();

						String clientAdress = s.getInetAddress()
								.getHostAddress() + ":" + s.getPort();

						messageArea.append(getTime()
								+ " - CLIENT CONNECTED ON PORT " + s.getPort()
								+ " FROM " + clientAdress);
						new ClientHandler(s, this).start();
					} catch (IOException e) {
						messageArea.append(
								getTime() + "Error connecting to client");
					}
				}
			});
		}

	}

//---------------------------------------------------------------------------

}
