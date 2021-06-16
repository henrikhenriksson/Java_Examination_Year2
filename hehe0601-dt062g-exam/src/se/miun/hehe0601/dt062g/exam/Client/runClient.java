package se.miun.hehe0601.dt062g.exam.Client;

import javax.swing.SwingUtilities;

/**
 * This class will start the Client program
 * 
 * @author Henrik Henriksson
 * @version 1.0
 * @since 2020-01-17
 *
 */
public class runClient {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> new Client().setVisible(true));

	}

}
