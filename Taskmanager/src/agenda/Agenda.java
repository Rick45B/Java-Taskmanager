package agenda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import exceptions.AgendaException;
import exceptions.AppuntamentoException;

/**
 * Classe pubblica che crea un oggetto di tipo <b>Agenda</b>
 * 
 * <p>L'agenda si compone di:</p>
 * <ul>
 * <li>un array di oggetti di tipo {@link Appuntamento}
 * <li>Il nome dell'agenda
 * <li>la dimesione dell'agenda
 * </ul>
 * @author Pietro Balossino, Riccardo Giovanni Gualiumi
 *
 */
public class Agenda implements Iterable<Agenda>, Serializable{
	private static final long serialVersionUID = 1L;
	private List<Appuntamento> agenda;
	private String nomeAgenda;
	final int MAXDIM;
	
	
	/**
	 * Costruisce una nuova agenda per nome e per dimensione
	 * @param nomeAgenda il nome dell'agenda da creare
	 * @param MAXDIM la dimensione dell'agenda da creare
	 */
	public Agenda(String nomeAgenda, int MAXDIM) {
		super();
		agenda = new ArrayList<Appuntamento>();
		this.nomeAgenda = nomeAgenda;
		this.MAXDIM = MAXDIM;
	}
	
	/**
	 * Costruisce una nuova agenda per nome
	 * @param nomeAgenda il nome dell'agenda da creare
	 */
	public Agenda(String nomeAgenda) {
		this(nomeAgenda, 20);
	}
	
	/**
	 * Restituisce una agenda completa, creata a partire da un file
	 * @param fileName il nome del file da cui creare l'agenda
	 * @param nomeAgenda il nome dell'agenda che si sta creando
	 * @param MAXDIM la dimensione dell'agenda
	 * @return una nuova {@link Agenda}
	 * @throws AgendaException se non è stato possibile creare una nuova agenda dal file
	 */
	static public Agenda createAgendaFromFile(String fileName, String nomeAgenda, int MAXDIM) throws AgendaException
	{
		Agenda out = new Agenda(nomeAgenda, MAXDIM);
		if (fileName.contains(".txt"))  //file testuale con appuntamenti
		{
			List<Appuntamento> temp;
			try {
				temp = readAppuntamentiTxt(fileName);
				out.agenda = temp;
			} catch (IOException  e) {
				throw new AgendaException("Impossibile creare agenda da file di testo!", e);
			}
		}
		else  //file binario con agenda
		{
			Agenda temp;
			try {
				temp = readAppuntamentiObj(fileName);
				out.agenda = temp.agenda;
			} catch (ClassNotFoundException | IOException e) {
				throw new AgendaException("Impossibile creare agenda da file binario!", e);
			}
		}
		
		return out;
	}
	
	
	/**
	 * Metodo per scrivere una agenda su un file.
	 * Se il file avrà estensione '.txt', l'agenda verrà scritta
	 * in forma testuale, altrimenti in forma binaria.
	 * @param fileName il nome del file su cui scrivere l'agenda
	 * @throws AgendaException se non e' stato possibile scrivere l'agenda su file
	 */
	public void dumpAgendaToFile(String fileName) throws AgendaException
	{
		try
		{
			if (fileName.contains(".txt"))
				dumpAgendaTxt(fileName);
			else
				dumpAgendaObj(fileName);
		}
		catch(IOException | IllegalArgumentException | NullPointerException e)
		{throw new AgendaException("Impossibile salvare agenda su file!", e);}
	}
	
	
	/**
	 * @return il nome dell'agenda
	 *
	 */
	public String getNome() {
		return nomeAgenda;
	}
	
	
	/**
	 * @return la dimensione massima dell'agenda
	 */
	public int getMaxDim() {
		return MAXDIM;
	}
	
	
	/**
	 * Restituisce un Appuntamento in base alla data inserita
	 * 
	 * @param date la data in cui cercare l'appuntamento
	 * @return l'appuntamento corrispondente alla data inserita.
	 */
	public Appuntamento getAppuntamenti(Calendar date)
	{
		return searchAppuntamento(date);
	}
	
	
	/**
	 * @return una deep copy di tutti gli appuntamenti nell'agenda
	 */
	public ArrayList<Appuntamento> getAllAppuntamenti() {
		agenda.sort(Comparator.comparing(Appuntamento::getData));
		ArrayList<Appuntamento> out = new ArrayList<Appuntamento>(agenda);
		return out;
	}
	
	
	/**
	 * Restituisce una lista di tutti gli appuntamenti con una persona
	 * 
	 * @param il nome della persona con cui si avrà l'appuntamento
	 * @return un <code>ArrayList</code> di appuntamenti
	 * @throws IllegalArgumentException se il nome inserito della persona non e' valido
	 */
	public ArrayList<Appuntamento> getAppuntamenti(String persona)
	{
		if (Appuntamento.invalidArgString(persona))
		{
			throw new IllegalArgumentException("Nome persona non valido!");
		}
		ArrayList<Appuntamento> output = new ArrayList<Appuntamento>();
		for (Appuntamento temp:agenda)
		{
			if (temp.getPersona().equalsIgnoreCase(persona))  //ho deciso di ignorare il maiusc
				output.add(temp);
		}
		return output;
	}
	
	/**
	 * @return la dimensione dell'agenda 
	 */
	public int size() {
		return agenda.size();
	}
	
	
	/**
	 * Aggiunge un nuovo Appuntamento all'agenda
	 * 
	 * @param date la data dell'appuntamento
	 * @param nomePersona il nome della persona con cui si avra' l'appuntamento
	 * @param luogoAppuntamento il luogo in cui si avra' l'appuntamento
	 * @param durata la durata dell'appuntamento
	 * @throws AgendaException se il nome della perona, il luogo o la durata non sono argomenti validi
	 */
	public void add(Calendar date,
			String nomePersona, String luogoAppuntamento, int durata) throws AgendaException {
		
		if (nomePersona == null || luogoAppuntamento == null || durata < 0)
			throw new IllegalArgumentException("Argomenti non validi!");
		
		add(new Appuntamento(date, nomePersona, luogoAppuntamento, durata));
	}
	
	/**
	 * Aggiunge un nuovo Appuntamento all'agenda
	 * 
	 * @param date la data dell'appuntamento
	 * @param durata la durata dell'appuntamento
	 * @throws AgendaException se non e' stato possibile aggiungere il nuovo appuntamento
	 */
	public void add(Calendar date, int durata) throws AgendaException {
		
		add(new Appuntamento(date, durata));
	}
	
	
	/**
	 * Aggiunge un nuovo Appuntamento all'agenda.
	 * 
	 * @param date la data dell'appuntamento
	 * @throws AgendaException se non e' stato possibile aggiungere il nuovo appuntamento
	 */
	public void add(Calendar date) throws AgendaException {
			
		add(new Appuntamento(date));
	}
	
	
	/**
	 * Aggiunge un nuovo Appuntamento all'agenda.
	 * 
	 * @param date la data dell'appuntamento
	 * @param nomePersona il nome della persona con cui si avra' l'appuntamento
	 * @param durata la durata dell'appuntamento
	 * @throws AgendaException se non e' stato possibile aggiungere il nuovo appuntamento
	 */
	public void add(Calendar date,
			String nomePersona, int durata) throws AgendaException {
		
		add(new Appuntamento(date, nomePersona, durata));
	}
	
	/**
	 * Elimina gli appuntamenti in base alla data
	 * @param date la data in cui eliminare l'appuntamento
	 * @throws IllegalArgumentException se la data non e' valida
	 */
	public void remove(Calendar date){
		if (date==null)
			throw new IllegalArgumentException("Argomento non valido: riferimento vacuo!");
		remove(this.getAppuntamenti(date));
	}
	
	
	/**
	 * Elimina un'appuntamento specifico
	 * @param appuntamento l'appuntamento da rimuovere
	 * @throws IllegalArgumentException se l'appuntamento non e' valido
	 */
	public void remove(Appuntamento appuntamento){
		if (appuntamento==null)
			throw new IllegalArgumentException("Argomento non valido: riferimento vacuo!");
		agenda.remove(appuntamento);
	}
	
	/**
	 * Modifica i dati di un'appuntamento
	 * <p>Inserire in ordine cosa si vuole modificare (sfruttando l'argomento arg). Porre a null ciò che NON si vuole modificare,
	 *  sfruttando il seguente pattern: anno, mese, giorno, ora, minuto, persona, luogo, durata</p>
	 * 
	 * @param appuntamento l'appuntamento da modificare
	 * @param arg lista dei valori da modificare
	 * @throws AgendaException se non è possibile modificare l'appuntamento
	 * @throws IllegalArgumentException se i dati forniti non sono validi
	 */
	public void modify(Appuntamento appuntamento, String...arg) throws AgendaException {
		int cnt = 0;
		
		if (appuntamento != null && arg.length==8)
		{
			Appuntamento tempCpy = appuntamento.clone();
			try
			{
				for (String temp:arg)
				{
					if (temp!=null)
					{
						switch(cnt)
						{
						case 0 -> tempCpy.setYear(Integer.parseInt(arg[0]));
						case 1 -> tempCpy.setMonth(Integer.parseInt(arg[1]));
						case 2 -> tempCpy.setDay(Integer.parseInt(arg[2]));
						case 3 -> tempCpy.setHour(Integer.parseInt(arg[3]));
						case 4 -> tempCpy.setMinute(Integer.parseInt(arg[4]));
						case 5 -> tempCpy.setPersona(arg[5]);
						case 6 -> tempCpy.setLuogo(arg[6]);
						case 7 -> tempCpy.setDurata(Integer.parseInt(arg[7]));
						}		
					}
					cnt++;
				}
				for (Appuntamento temp:agenda)
				{
					if (!checkOrari(tempCpy, temp) && arg[0]!=null && arg[1]!=null && arg[2]!=null && arg[3]!=null && arg[4]!=null)
					{
						throw new AgendaException("Impossibile modificare l'appuntamento: appuntamento si sovrappone con un altro già esistente!");
					}
				}
				this.agenda.remove(appuntamento);
				this.agenda.add(tempCpy);
			}
			catch(AppuntamentoException e) {throw new AgendaException("Impossibile modificare l'appuntamento!", e);}
		}
		else
			throw new IllegalArgumentException("Argomenti non validi!");
	}
	
	/**
	 * Controlla che gli orari di due appuntamenti non si sovrappongano
	 * @param nuovo il primo appuntamento
	 * @param old il secondo appuntamento
	 * @return <tt>true</tt> se i due appuntamenti non si sovrappongono, <tt>false</tt> altrimenti.
	 */
	protected boolean checkOrari(Appuntamento nuovo, Appuntamento old)
	{
		if (nuovo.getAnno().equals(old.getAnno()) && nuovo.getMese().equals(old.getMese()) && 
				nuovo.getGiorno().equals(old.getGiorno()))
		{
			Integer inizioOld = Integer.parseInt(old.getOra()+old.getMinuto());
			Integer fineOld = inizioOld+Integer.parseInt(old.getDurata());
			Integer inizioNew = Integer.parseInt(nuovo.getOra()+nuovo.getMinuto());
			Integer fineNew = inizioNew+Integer.parseInt(nuovo.getDurata());
			
			if (nuovo.getFormat().equals(old.getFormat()))
				return false;
			if (inizioNew == inizioOld || inizioNew == fineOld || fineNew == fineOld)
				return false;
			if (inizioOld <= inizioNew && fineOld >= inizioNew)
				return false;
			if (inizioOld >= inizioNew && inizioOld<=fineNew)
				return false;
		}
		return true;
	}
	
	//funzioni private
	
	/**
	 * Metodo privato per eseguire la scrittura dell'agenda su un file di testo
	 * @param fileName il nome del file di testo su cui effettuare la scrittura
	 * @throws IOException se degli errori sull' input/output si sono verificati
	 */
	private void dumpAgendaTxt(String fileName) throws IOException
	{
		if (fileName==null)
			throw new IllegalArgumentException("fileName non valido!");
		PrintWriter out = new PrintWriter(new File(fileName));
		
		try
		{
			for (Appuntamento temp:agenda)
			{
				String date = temp.getFormat();
				String persona = temp.getPersona();
				String luogo = temp.getLuogo();
				String durata = temp.getDurata();
			    out.printf("%s | %s | %s | %s\n", date, persona, luogo, durata);
			}
		}
		finally
		{
			out.close();
		}
	}
	
	
	/**
	 * Metodo privato per scrivere l'agenda come oggetto su un file
	 * @param fileName il nome del file in cui eseguire la scrittura
	 * @throws IOException se degli errori sull' input/output si sono verificati.
	 */
	private void dumpAgendaObj(String fileName) throws IOException {
		
		if (fileName == null)
			throw new IllegalArgumentException("fileName non valido!");
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
		try
		{
			out.writeObject(this);
			out.flush();
		}
		finally 
		{
			out.close();
		}
	}
	
	
	/**
	 * Metodo privato che ritorna un'agenda creata leggendo gli appuntamenti scritti su file in formato binario, come oggetti. 
	 * @param fileName il nome del file da cui leggere gli appuntamenti
	 * @return una nuova {@link Agenda}
	 * @throws IOException se degli errori sull' input/output si sono verificati
	 * @throws ClassNotFoundException se vengono letti oggetti di classi non note
	 * @throws FileNotFoundException se nessun file con quel nome è stato trovato.
	 */
	private static Agenda readAppuntamentiObj(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		ObjectInputStream fin = new ObjectInputStream(new FileInputStream(fileName));
		try
		{
			Agenda out = (Agenda) fin.readObject();
			return out;
		}
		finally
		{
			fin.close();
		}
	}

	
	/**
	 * Metodo privato usato creare una lista di appuntamenti da un file di testo
	 * @param fileName il nome del file da cui leggere gli appuntamenti
	 * @return una lista di {@link Appuntamento}
	 * @throws IOException se degli errori sull' input/output si sono verificati
	 * @throws AgendaException se non è stato possibile leggere i dati dal file
	 * @throws FileNotFoundException se nessun file con quel nome è stato trovato.
	 */
	private static List<Appuntamento> readAppuntamentiTxt(String fileName) throws FileNotFoundException, IOException, AgendaException {
		
		if (fileName==null)
			throw new IllegalArgumentException("Argomento non valido!");
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		
		try
		{
			String line = in.readLine();
			List<Appuntamento> out = new ArrayList<Appuntamento>();
			
			while (line!=null)
			{
				String[] lineData = line.split("\\|");
				if (lineData.length==4)
				{
					String dateStr = lineData[0].trim();
					String[] dateStrInfo = dateStr.split("-");
					if (dateStrInfo.length != 4)
					{
						throw new AgendaException("Impossibile leggere dati da File: format sconosciuto!");
					}
					String[] hourStrInfo = dateStrInfo[3].split(":");
					if (hourStrInfo.length != 2)
					{
						throw new AgendaException("Impossibile leggere dati da File: format sconosciuto!");
					}
					Calendar date = Calendar.getInstance();
					date.set(Integer.parseInt(dateStrInfo[0]), Integer.parseInt(dateStrInfo[1]), Integer.parseInt(dateStrInfo[2]),
							Integer.parseInt(hourStrInfo[0]), Integer.parseInt(hourStrInfo[1]));
					
					String persona = lineData[1].trim();
					String luogo = lineData[2].trim();
					String durata = lineData[3].trim();
					out.add(new Appuntamento(date, persona, luogo, Integer.parseInt(durata)));
				}
				else
				{
					throw new AgendaException("Impossibile leggere dati da File: format sconosciuto!");
				}
				line = in.readLine();
			}
			return out;
		}
		finally
		{
			in.close();
		}
	}
		
	/**
	 * Restituisce un Appuntamento in base alla data
	 * 
	 * @param date la data con cui cercare l'appuntamento
	 * @return l'appuntamento nella data passata come parametro.
	 */
	private Appuntamento searchAppuntamento(Calendar date) {
		for (Appuntamento temp:agenda)
		{
			if (Integer.parseInt(temp.getAnno()) == date.get(Calendar.YEAR) && Integer.parseInt(temp.getMese()) == date.get(Calendar.MONTH)
					&& Integer.parseInt(temp.getGiorno()) == date.get(Calendar.DATE) && Integer.parseInt(temp.getOra()) == date.get(Calendar.HOUR_OF_DAY) 
					&& Integer.parseInt(temp.getMinuto()) == date.get(Calendar.MINUTE))
				return temp;
		}
		return null;
	}
	
	/**
	 * Metodo privato usato per creare un nuovo appuntamento,
	 * 
	 * @param appuntamento l'appuntamento da aggiungere all'agenda
	 * @return <tt>true</tt> se l'appuntamento e' stato creato, <tt>false</tt> altrimenti.
	 * @throws AgendaException se i due appuntamenti sono coincidenti o se si e' superata la dimensione massima dell'agenda
	 */
	private void add(Appuntamento appuntamento) throws AgendaException {
		
		//controllo orario appuntamento
		for (Appuntamento temp:agenda)
		{
			Appuntamento appuntamento_cp1 = appuntamento.clone();
			Appuntamento appuntamento_cp2 = temp.clone();
			//dopo aver fatto le deep copies, evinco, dalla durata, l'orario di fine dell'appuntamento più piccolo, e lo confronto
			//con l'altro. -> oltre a confrontare gli appuntamenti così come sono, senza durata.
			if (!checkOrari(appuntamento_cp1, appuntamento_cp2))
			{
				throw new AgendaException("Errore: appuntamenti coincidenti!");
			}
			
			if (!checkOrari(appuntamento_cp1, appuntamento_cp2))
				throw new AgendaException("Errore: appuntamenti coincidenti!");
		}
		if (agenda.size()>=MAXDIM)
			throw new AgendaException("Ecceduta dimensione massima agenda!");
		agenda.add(appuntamento);
	}

	//overrides
	
	@Override
	public String toString() {
		String out = "Nome Agenda = " + nomeAgenda + ", Appuntamenti = ";
		
		agenda.sort(Comparator.comparing(Appuntamento::getData));
		
		for (Appuntamento c:agenda) {
			out = out.concat("{");
			out = out.concat(c.toString());
			out = out.concat("} ");
		}
		return out;
	}

	@Override
	public Iterator<Agenda> iterator() {
		return new AgendaIterator<Agenda>();
	}

}
