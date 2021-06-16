package se.miun.hehe0601.dt062g.exam.Server;

import javax.swing.SwingUtilities;

/**
 * This class will start the Server program
 * 
 * @author Henrik Henriksson
 * @version 1.0
 * @since 2020-01-17
 *
 */
public class runServer {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> new Server().setVisible(true));

	}

}
