package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class StartGUI {
/**
 * Classe che implementa una Graphical User Interface più sofisticata, grazie alle classi contenute nei package
 * java.awt e java.swing. Se si vuole usare questo tipo di interfacciamento, avviare SOLO ED ESCLUSIVAMENTE questa classe,
 * e seguire le istruzioni su schermo.
*/
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartGUI window = new StartGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				AgendaGUI temp = null;
				try {
					temp = new AgendaGUI();
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
				temp.frame.setVisible(true);
			}
		});
		btnStart.setBounds(165, 113, 103, 27);
		frame.getContentPane().add(btnStart);
	}
}
