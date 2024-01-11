package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import agenda.Agenda;
import agenda.App;
import agenda.Appuntamento;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

public class AgendaGUI {

	protected JFrame frame;
	protected static Integer index = 0;
	protected App agende;
	protected static JLabel lblNomeAgenda;
	private JTable table;
	private DefaultTableModel model;
	private AddAppuntamentoGUI appGUI;
	private AddAgendaGUI ageGUI;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgendaGUI window = new AgendaGUI();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException per errori di I/O
	 * @throws ClassNotFoundException se non è stata trovata la classe
	 * @throws FileNotFoundException se l'apertura di un file non e' andata a buon fine
	 */
	public AgendaGUI() throws FileNotFoundException, ClassNotFoundException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException per errori di I/O
	 * @throws ClassNotFoundException se non è stata trovata la classe
	 * @throws FileNotFoundException se l'apertura di un file non e' andata a buon fine
	 */
	private void initialize() throws FileNotFoundException, ClassNotFoundException, IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 327);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblNomeAgenda = new JLabel();
		lblNomeAgenda.setBounds(158, 12, 128, 17);
		lblNomeAgenda.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNomeAgenda);
		
		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (agende.getSize()==1)
				{
					refreshPage();
				}
				if (index-1>=0)
				{
					model = (DefaultTableModel)table.getModel();
					lblNomeAgenda.setText(agende.getAgenda((--index).toString()).getNome());
					Page(agende.getAgenda(index.toString()), model);
				}
			}
		});
		btnPrevious.setBounds(12, 231, 103, 27);
		frame.getContentPane().add(btnPrevious);
		
		//API agenda
		agende = new App();
		
		//initializing all the other app's windows
		appGUI = new AddAppuntamentoGUI(this);
		ageGUI = new AddAgendaGUI(this);
		appGUI.frame.setVisible(false);
		ageGUI.frame.setVisible(false);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (agende.getSize()==1)
				{
					refreshPage();
				}
				if (index+1<agende.getSize())
				{
					model = (DefaultTableModel)table.getModel();
					lblNomeAgenda.setText(agende.getAgenda((++index).toString()).getNome());
					Page(agende.getAgenda(index.toString()), model);
				}
			}
		});
		btnNext.setBounds(325, 231, 103, 27);
		frame.getContentPane().add(btnNext);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appGUI.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnAdd.setBounds(169, 231, 103, 27);
		frame.getContentPane().add(btnAdd);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 40, 416, 165);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Data", "Persona", "Luogo", "Durata"
			}
		));
		table.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				
				if (e.getColumn()!=-1)
				{
					String []args = new String[] {null, null, null, null, null, null, null, null};
					
					switch (e.getColumn())
					{
					case 0 -> {
						String cell = table.getModel().getValueAt(e.getFirstRow(), e.getColumn()).toString();
						String []tempDate = cell.split("-");
						if (tempDate.length==4)
						{
							String []tempHour = tempDate[3].split(":");
							if (tempHour.length==2)
							{
								args[0] = tempDate[0].trim();
								args[1] = tempDate[1].trim();
								args[2] = tempDate[2].trim();
								args[3] = tempHour[0].trim();
								args[4] = tempHour[1].trim();
							}
						}
						
					}	//data
					case 1 -> {args[5] = table.getModel().getValueAt(e.getFirstRow(), e.getColumn()).toString();}	//persona
					case 2 -> {args[6] = table.getModel().getValueAt(e.getFirstRow(), e.getColumn()).toString();}	//luogo
					case 3 -> {args[7] = table.getModel().getValueAt(e.getFirstRow(), e.getColumn()).toString();}	//durata
					}
					
					agende.modifyAgenda(agende.getAgenda(index.toString()), 
							agende.getAgenda(index.toString()).getAllAppuntamenti().get(e.getFirstRow()), args);
					refreshPage();
					}
			}
			
		});
		scrollPane.setViewportView(table);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnAgenda = new JMenu("Agenda");
		menuBar.add(mnAgenda);
		
		JMenuItem mntmAggiungiAgenda = new JMenuItem("Aggiungi agenda...");
		mntmAggiungiAgenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ageGUI.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		mnAgenda.add(mntmAggiungiAgenda);
		
		JMenuItem mntmRimuoviAgenda = new JMenuItem("Rimuovi agenda...");
		mntmRimuoviAgenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agende.removeAgenda(lblNomeAgenda.getText());
				refreshPage(0);
			}
		});
		mnAgenda.add(mntmRimuoviAgenda);
		
		JMenuItem mntmSalvaSuFile = new JMenuItem("Salva su file...");
		mntmSalvaSuFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lblNomeAgenda.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(mntmSalvaSuFile, "Nessuna agenda selezionata!");
				}
				else
				{
					JFileChooser filechoose = new JFileChooser();
					filechoose.showSaveDialog(mntmSalvaSuFile);
					File f = filechoose.getSelectedFile();
					if (!agende.agendaToFile(f.getAbsolutePath(), agende.getAgenda(index.toString())))
					{
						JOptionPane.showMessageDialog(mntmSalvaSuFile, agende.getErrorMsg());
					}
				}
			}
		});
		mnAgenda.add(mntmSalvaSuFile);
		
		JMenu mnSearch = new JMenu("Search...");
		menuBar.add(mnSearch);
		
		JMenuItem mntmCercaPerPersona = new JMenuItem("Cerca per persona");
		mntmCercaPerPersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String persona = JOptionPane.showInputDialog("Digitare persona appuntamento: ");
				if (persona!=null)
				{
					try {ArrayList<Appuntamento> temp = agende.searchAppuntamenti(persona, agende.getAgenda(index.toString()));
					refreshPage(-1, temp);}
					catch(IndexOutOfBoundsException ex) {JOptionPane.showMessageDialog(mntmCercaPerPersona, "Nessuna corrispondenza!");}
				}
				else
					JOptionPane.showInputDialog("Errore: digitare un input valido!");
			}
		});
		mnSearch.add(mntmCercaPerPersona);
		
		JMenuItem mntmCercaPerData = new JMenuItem("Cerca per data");
		mntmCercaPerData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = JOptionPane.showInputDialog("Digitare data appuntamento (yyyy-mm-dd-hh:mm): ");
				if (data!=null)
				{
					if (!data.isEmpty())
					{
						String []tempDate = data.split("-");
						if (tempDate.length==4)
						{
							String []tempHour = tempDate[3].split(":");
							if (tempHour.length==2)
							{
								Calendar date = Calendar.getInstance();
								date.set(Integer.parseInt(tempDate[0].trim()), Integer.parseInt(tempDate[1].trim()),
										Integer.parseInt(tempDate[2].trim()), Integer.parseInt(tempHour[0].trim()),
										Integer.parseInt(tempHour[1].trim()));
								ArrayList<Appuntamento> temp = new ArrayList<Appuntamento>();
								Appuntamento temp2 = agende.searchAppuntamento(date);
								if (temp2 == null)
								{
									JOptionPane.showMessageDialog(mntmCercaPerPersona, "Nessuna corrispondenza!");
								}
								else
									temp.add(temp2);
								refreshPage(-1, temp);
							}
							else
								JOptionPane.showInputDialog("Errore: digitare un input valido!");
						}
						else
							JOptionPane.showInputDialog("Errore: digitare un input valido!");
					}
					else
						JOptionPane.showInputDialog("Errore: digitare un input valido!");
				}
			}
		});
		mnSearch.add(mntmCercaPerData);
	}
	
	/**
	 * Funzione usata per aggiornare la tabella
	*/
	protected void refreshPage()
	{
		refreshPage(-1);
	}
	
	/**
	 * Funzione privata e generica sfruttata per aggiornare la tabella
	 * con un qualsiasi tipo di dato.
	 * @param input elemento di tipo generico da inserire nella tabella
	 * @param model tabella in cui inserire il dato.
	 */
	@SuppressWarnings("unchecked")
	private <T> void Page(T input, DefaultTableModel model) {
		
		model.setRowCount(0);   //per pulire tabella
		ArrayList<Appuntamento> agenda;
		if (input.getClass()==Agenda.class)
			agenda = ((Agenda)input).getAllAppuntamenti();
		else
			agenda = (ArrayList<Appuntamento>)input;
		for (Appuntamento temp:agenda)
		{
			String data = temp.getFormat();
			String persona = temp.getPersona();
			String luogo = temp.getLuogo();
			String durata = temp.getDurata();
			model.addRow(new Object[] {data, persona, luogo, durata});
		}
	}
	
	/**
	 * Funzione privata, chiamata da refreshPage, per aggiornare la tabella corrente.
	 * @param i indice il cui valore mi indica se resettare o meno l'index delle agende.
	*/
	private void refreshPage(int i) {
		
		if (agende.getSize()==0)
		{
			lblNomeAgenda.setText("");
			model = (DefaultTableModel)table.getModel();
			model.setRowCount(0);
		}
		else
		{
			if (i!=-1)
				index = 0;
			model = (DefaultTableModel)table.getModel();
			lblNomeAgenda.setText(agende.getAgenda((index).toString()).getNome());
			Page(agende.getAgenda(index.toString()), model);
		}
	}
	
	/**
	 * Funzione privata, chiamata da refreshPage, per aggiornare la tabella corrente.
	 * @param i indice il cui valore mi indica se resettare o meno l'index delle agende.
	 * @param agenda agenda da mostrare nella tabella. Richiamerà la funzione Page con questa agenda.
	*/
	private void refreshPage(int i, ArrayList<Appuntamento> agenda) {
		
		if (agende.getSize()==0)
		{
			lblNomeAgenda.setText("");
			model = (DefaultTableModel)table.getModel();
			model.setRowCount(0);
		}
		else
		{
			if (i!=-1)
				index = 0;
			model = (DefaultTableModel)table.getModel();
			lblNomeAgenda.setText(agende.getAgenda((index).toString()).getNome());
			if (agenda!=null)
				Page(agenda, model);
			else
				Page(agende.getAgenda(index.toString()), model);
		}
	}
}
