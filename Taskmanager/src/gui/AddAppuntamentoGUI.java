package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import javax.swing.JFormattedTextField$AbstractFormatter;

/**
 * Classe utilizzata per la creazione di una Graphical User Interface per l'inserimento di un nuovo {@code Appuntamento}
 * 
 * @author Pietro Balossino, Riccardo Giovanni Gualiumi
 *
 */
public class AddAppuntamentoGUI {

	protected JFrame frame;
	private AgendaGUI ageGUI;
	private JTextField textField_5;
	private JTextField textField_6;
	private JFormattedTextField formattedTextFieldAnno;
	private JFormattedTextField formattedTextFieldOra;
	private JFormattedTextField formattedTextFieldDurata;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddAppuntamentoGUI window = new AddAppuntamentoGUI();
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
	public AddAppuntamentoGUI() {
		this(null);
	}
	
	public AddAppuntamentoGUI(AgendaGUI ageGUI)
	{
		this.ageGUI = ageGUI;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	DateFormat format2 = new SimpleDateFormat("HH:mm");
	String anno = "yyyy-mm-dd";
	String annoObb = "<html>yyyy-mm-dd<font color='red'>*</font></html>";
	String ora = "hh:mm";
	String oraObb = "<html>hh:mm<font color='red'>*</font></html>";
	String durata = "Durata(in minuti)";
	String durataObb = "<html>Durata(in minuti)<font color='red'>*</font></html>";
	String file = "File";
	String fileObb = "<html>File<font color='red'>*</font></html>";
	String grey = "";
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 429, 259);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblAnno = new JLabel(annoObb);
		lblAnno.setBounds(12, 22, 88, 17);
		frame.getContentPane().add(lblAnno);
		
		JLabel lblOra = new JLabel(oraObb);
		lblOra.setBounds(12, 55, 47, 17);
		frame.getContentPane().add(lblOra);
		
		JLabel lblPersonaAppuntamento = new JLabel("Persona appuntamento");
		lblPersonaAppuntamento.setBounds(12, 115, 148, 17);
		frame.getContentPane().add(lblPersonaAppuntamento);
		
		JLabel lblLuogoAppuntamento = new JLabel("Luogo appuntamento");
		lblLuogoAppuntamento.setBounds(12, 144, 139, 17);
		frame.getContentPane().add(lblLuogoAppuntamento);
		
		JLabel lblDurata = new JLabel(durataObb);
		lblDurata.setBounds(12, 85, 119, 17);
		frame.getContentPane().add(lblDurata);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					Calendar date = Calendar.getInstance();
					String[] annoString = formattedTextFieldAnno.getText().split("-");
					String[] oraString = formattedTextFieldOra.getText().split(":");
					date.set(Integer.parseInt(annoString[0].trim()), Integer.parseInt(annoString[1].trim()), Integer.parseInt(annoString[2].trim()),
							Integer.parseInt(oraString[0].trim()), Integer.parseInt(oraString[1].trim()));
					
					if (!ageGUI.agende.addAppuntamentoToAgenda(date, textField_5.getText(), textField_6.getText(),
							Integer.parseInt(formattedTextFieldDurata.getText().trim()), ageGUI.agende.getAgenda(AgendaGUI.index.toString())))
					{
						JOptionPane.showMessageDialog(btnSubmit, ageGUI.agende.getErrorMsg());
					}
				
				ageGUI.refreshPage();}
				catch(NumberFormatException ex) {JOptionPane.showMessageDialog(btnSubmit, "Digitare tutti i campi obbligatori!");}
				catch(IndexOutOfBoundsException ex) {JOptionPane.showMessageDialog(btnSubmit, "Nessuna agenda selezionata!");}
				ageGUI.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnSubmit.setBounds(167, 190, 103, 27);
		frame.getContentPane().add(btnSubmit);
		
		textField_5 = new JTextField();
		textField_5.setBounds(169, 113, 227, 21);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);
		
		textField_6 = new JTextField();
		textField_6.setBounds(169, 144, 227, 21);
		frame.getContentPane().add(textField_6);
		textField_6.setColumns(10);
		
		formattedTextFieldAnno = new JFormattedTextField(format1);
		formattedTextFieldAnno.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAnno.setText(annoObb);
				lblOra.setText(oraObb);
				lblDurata.setText(durataObb);
			}
		});
		formattedTextFieldAnno.setBounds(105, 20, 291, 19);
		frame.getContentPane().add(formattedTextFieldAnno);
		
		formattedTextFieldOra = new JFormattedTextField(format2);
		formattedTextFieldOra.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAnno.setText(annoObb);
				lblOra.setText(oraObb);
				lblDurata.setText(durataObb);
			}
		});
		formattedTextFieldOra.setBounds(67, 53, 329, 19);
		frame.getContentPane().add(formattedTextFieldOra);
		
		formattedTextFieldDurata = new JFormattedTextField();
		formattedTextFieldDurata.setBackground(new Color(255, 255, 255));
		formattedTextFieldDurata.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblAnno.setText(annoObb);
				lblOra.setText(oraObb);
				lblDurata.setText(durataObb);
			}
		});
		formattedTextFieldDurata.setBounds(135, 84, 261, 19);
		frame.getContentPane().add(formattedTextFieldDurata);
	}

}
