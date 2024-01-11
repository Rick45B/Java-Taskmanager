package gui;

import java.util.ArrayList;
import java.util.Calendar;

import agenda.Agenda;
import agenda.App;
import agenda.Appuntamento;
import jbook.util.Input;

/**
 * Classe che implementa una Graphical User Interface da terminale
 * 
 * @author Pietro Balossino, Riccardo Giovanni Gualiumi
 *
 */
public class TerminalGUI {

	private static App agende;
	public static void main(String[] args) {
		
		
		agende = new App();
		int quit = 0;		//variabile d'uscita
		Agenda agendaCurrent;
		
		System.out.println("***Avvio del programma in corso***\n");
		while (quit!=1)
		{
			System.out.println("Agende presenti nel sistema: "+agende.getSize()+"\n");
			for (int i = 0; i<agende.getSize(); i++)
			{
				Agenda temp = agende.getAgenda(((Integer)i).toString());
				System.out.println(i+") "+temp.toString());
				System.out.println("Dimensione agenda: "+temp.size()+"\\"+temp.getMaxDim()+"\n-------------------");
			}
			switch(Input.readInt("\nInserire 0 per uscire dal programma, 1 per aggiungere un'agenda, 2 per selezionare un'agenda: ")) 
			{
			case 0 -> quit = 1;
			case 1 -> {
				
				Integer dim = 0;
				String fileName = Input.readString("\nDigitare file da cui creare l'agenda. Se non si vuole generare un'agenda"
						+ " da file, non inserire niente: ");
				String name = Input.readString("\nDigitare nome della nuova agenda: ");
				try {dim = Input.readInt("\nDigitare dimensione massima della nuova agenda, o un numero <=0 per la dimensione di default: ");}
				catch(NumberFormatException e) {}  //do nothing
				if (dim<=0)
					dim = 20;
				if (fileName.length()==0)
				{
					if (agende.addAgenda(name, dim))
						System.out.println("Agenda inserita con successo!");
					else
						System.out.println(agende.getErrorMsg());
				}
				else
				{
					if (agende.agendaFromFile(fileName, name, dim))
						System.out.println("\nAgenda creata con successo");
					else
						System.out.println(agende.getErrorMsg());
				}
			}
			case 2 -> {
				int x = 0;
				String agendaSel = "";
				do
				{
					agendaSel = Input.readString("\nDigitare nome agenda o indice agenda da selezionare(-1 per uscire): ");
					try {x = Integer.parseInt(agendaSel);
					if (x >= agende.getSize() || (x<0 && x!=-1))
						System.out.println("\nInserire un indice valido!");}
					catch(IndexOutOfBoundsException | IllegalArgumentException e) {}
					
						
				}
				while (x>=agende.getSize() || (x<0 && x!=-1));
				
				if (x!=-1)
				{
					int varSwitch = 0;  //ipotizzo vero
					do
					{
						varSwitch = 0;
						agendaCurrent = agende.getAgenda(agendaSel);
						
						switch (Input.readInt("\nSelezionare una delle seguenti funzionalità:\n"
								+ "0) -> torna al menu principale.\n"
								+ "1) -> aggiungi appuntamento all'agenda.\n"
								+ "2) -> rimuovi appuntamento dall'agenda.\n"
								+ "3) -> cancella agenda.\n"
								+ "4) -> scrivi agenda su file.\n"
								+ "5) -> modifica un appuntamento esistente.\n"
								+ "6) -> cerca un appuntamento esistente.\n"))
						{
						case 0 -> {}  //do nothing
						case 1 -> {	//aggiungi appuntamento
							
							Calendar date = askDate();
							String nomePersona = Input.readString("\nInserire nome persona appuntamento(opzionale): ");
							String luogo = Input.readString("\nInserire luogo appuntamento(opzionale): ");
							Integer durata = 0;
							do
								try
								{
										durata = Input.readInt("\nInserire durata appuntamento in minuti(obbligatoria): ");
								}
								catch (NumberFormatException e)
								{System.out.println("\nErrore! Perfavore inserire un numero!");}
							while(durata<=0);
							
							if (agende.addAppuntamentoToAgenda(date, nomePersona, luogo, durata, agendaCurrent)==false)
								System.out.println(agende.getErrorMsg());
						}
						case 2 -> { //rimuovi appuntamento
							
							Calendar date = askDate();
							if (agende.removeAppuntamenFromAgenda(agendaCurrent, date) == false)
								System.out.println("\nNessuna corrispondenza per la data immessa!");
						}
						case 3 -> {agende.removeAgenda(agendaSel);}
						case 4 -> {
							String nomeFile = Input.readString("\nInserire nome file dove salvare l'agenda: ");
							if (agende.agendaToFile(nomeFile, agendaCurrent)==false)
								System.out.println(agende.getErrorMsg());
							else
								System.out.println("\nAgenda salvata con successo!");
						}
						case 5 -> {
							Calendar date = askDate();
							Appuntamento temp = agende.searchAppuntamento(date);
							System.out.println("\nAppuntamento selezionato: "+temp.toString());
							String[] arg = getArgs();
							if (agende.modifyAgenda(agendaCurrent, temp, arg))
								System.out.println("\nModifica eseguita con successo!");
							else
								System.out.println(agende.getErrorMsg());
						}
						case 6 -> {
							Calendar date = Calendar.getInstance();
							String[] dataArg;
							
							do{
								String data = Input.readString("\nIndicare data oppure persona appuntamento: ");
								dataArg = data.split(":");
								if (dataArg.length==1)
								{
									ArrayList<Appuntamento> temp = agende.searchAppuntamenti(data, agendaCurrent);
									if (temp!=null)
										System.out.println("\n"+temp.size()+" appuntamenti corrispondono alla data fornita: "+temp.toString());
									else
										System.out.println(agende.getErrorMsg());
								}
								else
								{
									if (dataArg.length==5)
									{
										date.set (Integer.parseInt(dataArg[0].trim()), Integer.parseInt(dataArg[1].trim())
												, Integer.parseInt(dataArg[2].trim()), Integer.parseInt(dataArg[3].trim()), Integer.parseInt(dataArg[4].trim()));
										Appuntamento temp = agende.searchAppuntamento(date);
										System.out.println("\nUn appuntamento corrisponde alla data fornita: "+temp.toString());
									}
								}
							}while(dataArg.length!=1 && dataArg.length!=5);
						}
						default -> {System.out.println("Comando non valido, riprovare.");
							varSwitch = -1;}
						}
					}while(varSwitch==-1);
				}
				
			}
			}
		}
		System.out.println("\n***Uscita dal programma in corso***");
	}
	
	//funzioni private
	/**
	 * Metodo privato per richiedere l'inserimento di una data, nel mentre ne controlla la correttezza.
	 * @return di una data di Calendar
	 */
	private static Calendar askDate()
	{
		int quit2 = 0;
		Calendar date = Calendar.getInstance();
		do
			{		
				try
				{
					String data = Input.readString("\nDigitare, separarti da degli spazi, "
							+ "'anno : mese : giorno : ora : minuto' dell'appuntamento: ");
					String[] dataArg = data.split(":");
					if (dataArg.length != 5)
						throw new IllegalArgumentException("Formato di data non valido!");
					date.set (Integer.parseInt(dataArg[0].trim()), Integer.parseInt(dataArg[1].trim())
							, Integer.parseInt(dataArg[2].trim()), Integer.parseInt(dataArg[3].trim()), Integer.parseInt(dataArg[4].trim()));
					quit2 = 1;
				}
				catch(IllegalArgumentException e)
				{
					System.out.println(e.getMessage());
				}
				
			}while (quit2==0);
		return date;
	}
	
	/**
	 * Metodo privato per richiedere gli argomenti di un appuntamento
	 * @return di un array di stringhe con gli argomenti
	 */
	private static String[] getArgs()
	{
		String[] arg = null;
		do
		{
			String argRaw = Input.readString("\nDigitare, separati da degli spazi,"
					+ " anno : mese : giorno : ora : minuto : persona : luogo : durata ,"
					+ "le nuove specifiche dell'appuntamento: ");
			arg = argRaw.split(":");
			if (arg.length==8)
			{
				for (int cnt = 0; cnt<arg.length; cnt++)
					arg[cnt] = arg[cnt].trim();
				return arg;
			}
			else
				System.out.println("\nArgomento inserito non valido, riprovare!");
		}while(arg.length!=8);
		return arg;
	
	}

}
