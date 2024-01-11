package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import java.text.Format;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

/**
 * Classe utilizzata per la creazione di una Graphical User Interface per l'inserimento di una nuova {@code Agenda}
 * 
 * @author Pietro Balossino, Riccardo Giovanni Gualiumi
 *
 */
public class AddAgendaGUI {

	protected JFrame frame;
	private AgendaGUI ageGUI;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddAgendaGUI window = new AddAgendaGUI();
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
	public AddAgendaGUI() {
		this(null);
	}
	
	public AddAgendaGUI(AgendaGUI ageGUI)
	{
		this.ageGUI = ageGUI;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 268);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		String nomeObb = "<html>Nome Agenda<font color='red'>*</font></html>";
		String dimObb = "<html>Dimensione(default 20)<font color='red'>*</font></html>";
		
		JLabel lblCreaUnaNuova = new JLabel("Crea una nuova Agenda");
		lblCreaUnaNuova.setBounds(148, 12, 144, 17);
		frame.getContentPane().add(lblCreaUnaNuova);
		
		JLabel lblNomeAgenda = new JLabel("Nome Agenda");
		lblNomeAgenda.setBounds(12, 60, 103, 17);
		frame.getContentPane().add(lblNomeAgenda);
		
		JLabel lblDimensione = new JLabel("Dimensione(default 20)");
		lblDimensione.setBounds(12, 101, 169, 17);
		frame.getContentPane().add(lblDimensione);
		
		JLabel lblFileAppuntamentiopzionale = new JLabel("File Appuntamenti(opzionale)");
		lblFileAppuntamentiopzionale.setBounds(12, 147, 198, 17);
		frame.getContentPane().add(lblFileAppuntamentiopzionale);
		
		JFormattedTextField formattedTextFieldNome = new JFormattedTextField((Format) null);
		formattedTextFieldNome.setBounds(121, 58, 307, 19);
		frame.getContentPane().add(formattedTextFieldNome);
		
		JFormattedTextField formattedTextFieldDimensione = new JFormattedTextField((Format) null);
		formattedTextFieldDimensione.setBounds(174, 99, 254, 19);
		frame.getContentPane().add(formattedTextFieldDimensione);
		
		JFormattedTextField formattedTextFieldFile = new JFormattedTextField((Format) null);
		formattedTextFieldFile.setBounds(200, 146, 144, 19);
		frame.getContentPane().add(formattedTextFieldFile);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (formattedTextFieldNome.getText().equals(""))
					JOptionPane.showMessageDialog(btnNewButton, "Nome agenda obbligatorio!");
				else
				{
				
					if (!formattedTextFieldFile.getText().equalsIgnoreCase(""))
						if (!ageGUI.agende.agendaFromFile(formattedTextFieldFile.getText(), formattedTextFieldNome.getText(),
								formattedTextFieldDimensione.getText().equalsIgnoreCase("")?20:Integer.parseInt(formattedTextFieldDimensione.getText())))
						{
							JOptionPane.showMessageDialog(btnNewButton, ageGUI.agende.getErrorMsg());
						}
						else
							ageGUI.refreshPage();
					else
					{
						try{if (!ageGUI.agende.addAgenda(formattedTextFieldNome.getText() 
								,formattedTextFieldDimensione.getText().equalsIgnoreCase("")?20:Integer.parseInt(formattedTextFieldDimensione.getText())))
						{
							JOptionPane.showMessageDialog(btnNewButton, ageGUI.agende.getErrorMsg());
						}
						else
							ageGUI.refreshPage();}
						catch(IndexOutOfBoundsException ex) {JOptionPane.showMessageDialog(btnNewButton, "Nome agenda non valido!");}
					}
				}
				
				
				ageGUI.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(174, 194, 103, 27);
		frame.getContentPane().add(btnNewButton);
		
		lblNomeAgenda.setText(nomeObb);
		lblDimensione.setText(dimObb);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechoose = new JFileChooser();
				filechoose.showOpenDialog(btnSelect);
				File f = filechoose.getSelectedFile();
				if (f!=null)
					formattedTextFieldFile.setText(f.getAbsolutePath());
			}
		});
		btnSelect.setBounds(356, 147, 72, 17);
		frame.getContentPane().add(btnSelect);
	}
}
