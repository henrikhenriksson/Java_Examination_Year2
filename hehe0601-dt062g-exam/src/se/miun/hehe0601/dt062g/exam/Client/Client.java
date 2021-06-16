package se.miun.hehe0601.dt062g.exam.Client;

import java.awt.BorderLayout;
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
 * This is a client class that presents a GUI to the user. Will be used to
 * communicate with a server that scrambles input messages.
 * 
 * @author Henrik Henriksson
 * @version 1.0
 * @since 2020-01-17
 *
 */
public class Client extends JFrame {

	private final int MIN_PORT_VALUE = 1;
	private final int MAX_PORT_VALUE = 65535;

	private JPanel SettingsPanel;
	private JTextField addressField;
	private JTextField portField;
	private JButton connectButton;
	private JButton disconnectButton;

	private JPanel textPanel;
	private JTextArea messageArea;

	private JPanel inputPanel;
	private JTextField inputField;
	private JButton sendButton;

	LocalDateTime currentTime;
	DateTimeFormatter formatter;

	private static final long serialVersionUID = 3652716737493158731L;

	// set title and create the panels:
	public Client() {
		super("Client");
		makeFrame();
		makeSettingsPanel();
		makeTextPanel();
		makeInputPanel();
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	}

	// create the main frame
	private void makeFrame() {

		setSize(800, 600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	// Create settingspanel that will be presented at the top of the frame.
	private void makeSettingsPanel() {

		// this container will be used to wrap the underlying panels.
		JPanel topPanel = new JPanel(new BorderLayout());
		// container panel:
		SettingsPanel = new JPanel();
		// Not optimal to use boxlayout, but it works.
		SettingsPanel.setLayout(new BoxLayout(SettingsPanel, BoxLayout.X_AXIS));
		JLabel addressLabel = new JLabel("Address ");
		JLabel portLabel = new JLabel("Port ");

		addressField = new JTextField("localhost");
		portField = new JTextField("");
		connectButton = new JButton("Connect");
		disconnectButton = new JButton("Disconnect");
		disconnectButton.setEnabled(false);

		// add the labels, buttons and textfields to the containerPanel
		SettingsPanel.add(addressLabel);
		SettingsPanel.add(addressField);
		SettingsPanel.add(portLabel);
		SettingsPanel.add(portField);
		SettingsPanel.add(connectButton);
		SettingsPanel.add(disconnectButton);

		// add event listeners
		connectButton.addActionListener(e -> attemptConnection());
		disconnectButton.addActionListener(e -> attemptDisconnect());

		// add the containerpanel to the wrapper
		topPanel.add(SettingsPanel, BorderLayout.CENTER);

		// add wrapper
		this.add(topPanel, BorderLayout.NORTH);

	}

	// set appropriate bools when connection is closed.
	private void attemptDisconnect() {

		connectButton.setEnabled(true);
		disconnectButton.setEnabled(false);
		sendButton.setEnabled(false);

	}

	// get the current time in a formatted string:
	private String getTime() {
		currentTime = LocalDateTime.now();

		return currentTime.format(formatter);

	}

	// This function will attempt a connection. Currently only checks for valid
	// port and manipulates buttons

	private void attemptConnection() {

		if (addressField.getText().equals("")) {
			messageArea.append(getTime() + " - " + "invalid Server Adress: "
					+ addressField.getText() + "\n");
		} else {

			String inputAddress = addressField.getText();
			int inputPort = -1;

			try {
				inputPort = Integer.parseInt(portField.getText());
				if (inputPort < MIN_PORT_VALUE || inputPort > MAX_PORT_VALUE) {
					throw new NumberFormatException();
				}
				connectButton.setEnabled(false);
				disconnectButton.setEnabled(true);
				sendButton.setEnabled(true);

				messageArea.append(getTime() + " - " + "Connected to "
						+ inputAddress + " on port: " + inputPort + "\n");

			} catch (NumberFormatException nfe) {
				messageArea.append(getTime() + " - " + "invalid port: "
						+ portField.getText() + "\n");
			}
		}

	}

	// creat the text panels, much the same as the top panel, only it utilizes a
	// textare nesteled within a vertical scrollpane that is nesteled within the
	// textPanel
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

		add(textPanel, BorderLayout.CENTER);
	}

	// bottom panel, much the same as Settingspanel.
	private void makeInputPanel() {
		JPanel bottomPanel = new JPanel(new BorderLayout());
		inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));

		inputField = new JTextField();
		sendButton = new JButton("Send");
		sendButton.setEnabled(false);

		inputPanel.add(inputField);
		inputPanel.add(sendButton);

		bottomPanel.add(inputPanel, BorderLayout.CENTER);

		this.add(bottomPanel, BorderLayout.SOUTH);

	}

}
