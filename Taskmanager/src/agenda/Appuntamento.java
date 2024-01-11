package agenda;
import java.io.Serializable;
import java.util.*;

import exceptions.AppuntamentoException;
/**
 * Classe pubblica che va a creare un oggetto di tipo Appuntamento.
 * 
 *<p> L'appuntamento si compone di:</p>
 * <ul>
 *  <li>una data nel formato gg-mm-aaaa,</li>
 *  <li>un'ora, nel formato hh-mm,</li>
 *  <li>una durata espressa in minuti,</li>
 *  <li>il nome della persona con cui si avra' l'appuntamento,</li>
 *  <li>e il luogo dove avverra' l'appuntamento.</li>
 *  </ul>
 *  
 * @author Pietro Balossino, Riccardo Giovanni Gualiumi
 *
 */
public class Appuntamento implements Serializable {
	private static final long serialVersionUID = 1L;
	private Calendar data;
	private String nomePersona;
	private String luogoAppuntamento;
	private int durata;
	
	/**
	 * Costruisce un nuovo {@code Appuntamento} con i valori specificati
	 * 
	 * @param date la data dell'appuntamento
	 * @param nomePersona il nome della persona con cui si avrà l'appuntamento
	 * @param luogoAppuntamento il luogo in cui si avrà l'appuntamento
	 * @param durata la durata dell'appuntamento
	 */
	public Appuntamento(Calendar date, String nomePersona, String luogoAppuntamento, int durata) {
		this.data = Calendar.getInstance();
		this.data.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE), date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
		this.nomePersona = nomePersona;
		this.luogoAppuntamento = luogoAppuntamento;
		this.durata = durata;
	}
	
	/**
	 * Costruisce un nuovo {@code Appuntamento} con i valori specificati
	 * 
	 * @param date la data dell'appuntamento
	 * @param nomePersona il nome della persona con cui si avrà l'appuntamento
	 * @param durata la durata dell'appuntamento
	 */
	public Appuntamento(Calendar date, String nomePersona, int durata) {
		this(date, nomePersona, "", durata);
	}
	
	/**
	 * Costruisce un nuovo {@code Appuntamento} con i valori specificati
	 * 
	 * @param date la data dell'appuntamento
	 * @param durata la durata dell'appuntamento
	 */
	public Appuntamento(Calendar date, int durata) {
		this(date, "", durata);
	}
	
	/**
	 * Costruisce un nuovo {@code Appuntamento} con i valori specificati
	 * 
	 * @param date la data dell'appuntamento
	 */
	public Appuntamento(Calendar date) {
		this(date, 60);
	}

	@Override
	public String toString() {
		int year = data.get(Calendar.YEAR);
		int month = data.get(Calendar.MONTH);
		int day = data.get(Calendar.DATE);
		int hour = data.get(Calendar.HOUR_OF_DAY);
		int minute = data.get(Calendar.MINUTE);
		return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day) + "-" +
				Integer.toString(hour) + ":" +Integer.toString(minute) + ", Persona = " +
		nomePersona + ", Luogo = " + luogoAppuntamento + ", Durata = " + durata;
	}
	
	/** 
	 * @return il nome della persona con cui si avra' l'appuntamento
	 */
	public String getPersona() {
		return nomePersona;
	}
	

	/**
	 * @return il luogo dell'appuntamento
	 */
	public String getLuogo() {
		return luogoAppuntamento;
	}
	
	/**
	 * @return l'anno dell'appuntamento
	 */
	public String getAnno() {
		return Integer.toString(data.get(Calendar.YEAR));
	}
	
	/**
	 * @return il mese dell'appuntamento
	 */
	public String getMese() {
		return Integer.toString(data.get(Calendar.MONTH));
	}
	
	/**
	 * @return il giorno dell'appuntamento
	 */
	public String getGiorno() {
		return Integer.toString(data.get(Calendar.DATE));
	}
	
	/** 
	 * @return l'ora dell'appuntamento
	 */
	public String getOra() {
		return Integer.toString(data.get(Calendar.HOUR_OF_DAY));
	}
	
	/**
	 * @return i minuti dell'appuntamento
	 */
	public String getMinuto() {
		return Integer.toString(data.get(Calendar.MINUTE));
	}
	
	/**
	 * @return la durata dell'appuntamento
	 */
	public String getDurata() {
		return Integer.toString(durata);
	}
	
	/**
	 * @return la data intera in formato stringa
	 */
	public String getFormat() {
		int year = data.get(Calendar.YEAR);
		int month = data.get(Calendar.MONTH);
		int day = data.get(Calendar.DATE);
		int hour = data.get(Calendar.HOUR_OF_DAY);
		int minute = data.get(Calendar.MINUTE);
		return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day) + "-" +
				Integer.toString(hour) + ":" +Integer.toString(minute);
	}
	
	
	/**
	 * setta l'ora dell'appuntamento
	 * 
	 * @param hour la nuova ora dell'appuntamento
	 * @throws AppuntamentoException se non e' stato possibile settare l'ora
	 */
	public void setHour(Integer hour) throws AppuntamentoException{
		try
		{
			if (!invalidArg(hour.toString()))
				data.set(Calendar.HOUR_OF_DAY, hour);
		}
		catch(IllegalArgumentException e)
		{
			throw new AppuntamentoException("Impossibile settare orario appuntamento(ora)!", e);
		}
	}
	
	
	/**
	 * setta i minuti all'appuntamento
	 * 
	 * @param minute i nuovi minuti dell'appuntamento
	 * @throws AppuntamentoException se non e' stato possibile settare i minuti
	 */
	public void setMinute(Integer minute) throws AppuntamentoException {
		try
		{
			if (!invalidArg(minute.toString()))
				data.set(Calendar.MINUTE, minute);
		}
		catch(IllegalArgumentException e)
		{
			throw new AppuntamentoException("Impossibile settare orario appuntamento(minuti)!", e);
		}
	}
	
	/**
	 * setta l'anno all'appuntamento
	 * 
	 * @param year il nuovo anno dell'appuntamento
	 * @throws AppuntamentoException se non e' stato possibile settare l'anno
	 */
	public void setYear(Integer year) throws AppuntamentoException {
		try
		{
			if (!invalidArg(year.toString()))
				data.set(Calendar.YEAR, year);
		}
		catch(IllegalArgumentException e)
		{
			throw new AppuntamentoException("Impossibile settare data appuntamento(anno)!", e);
		}
	}
	
	/**
	 * setta il mese all'appuntamento
	 * 
	 * @param month il nuovo mese dell'appuntamento
	 * @throws AppuntamentoException se non e' stato possibile settare il mese
	 */
	public void setMonth(Integer month) throws AppuntamentoException {
		try
		{
			if (!invalidArg(month.toString()))
				data.set(Calendar.MONTH, month);
		}
		catch(IllegalArgumentException e)
		{
			throw new AppuntamentoException("Impossibile settare data appuntamento(mese)!", e);
		}
	}
	
	/**
	 * setta il giorno all'appuntamento
	 * 
	 * @param day il nuovo giorno dell'appuntamento
	 * @throws AppuntamentoException se non e' stato possibile settare il giorno
	 */
	public void setDay(Integer day) throws AppuntamentoException {
		try
		{
			if (!invalidArg(day.toString()))
				data.set(Calendar.DATE, day);
		}
		catch(IllegalArgumentException e)
		{
			throw new AppuntamentoException("Impossibile settare data appuntamento(giorno)!", e);
		}
	}
	
	/**
	 * setta la persona con cui si avra' l'appuntamento
	 * 
	 * @param Persona il nuovo nome della persona con cui si ha l'appuntamento
	 * @throws AppuntamentoException se non e' stato possibile settare la persona
	 */
	public void setPersona(String Persona) throws AppuntamentoException  {
		try
		{
			if (!invalidArgString(Persona))
				nomePersona = Persona;
		}
		catch(IllegalArgumentException e)
		{
			throw new AppuntamentoException("Impossibile settare persona appuntamento!", e);
		}
	}
	
	/**
	 * setta il luogo dell'appuntamento
	 * 
	 * @param Luogo il nuovo luogo dove avverra' l'appuntamento
	 * @throws AppuntamentoException se non e' stato possibile settare il luogo
	 */
	public void setLuogo(String Luogo) throws AppuntamentoException {
		try
		{
			if (!invalidArgString(Luogo))
				luogoAppuntamento = Luogo;
		}
		catch(IllegalArgumentException e)
		{
			throw new AppuntamentoException("Impossibile settare luogo appuntamento!", e);
		}
	}
	
	/**
	 * setta la durata dell'appuntamento
	 * 
	 * @param durata la nuova durata dell'appuntamento
	 * @throws AppuntamentoException se non e' stato possibile settare la durata
	 */
	public void setDurata(Integer durata) throws AppuntamentoException{
		try
		{
			if (!invalidArg(durata.toString()))
				this.durata = durata;
		}
		catch(IllegalArgumentException e)
		{
			throw new AppuntamentoException("Impossibile settare durata appuntamento!", e);
		}
	}
	
	@Override
	public Appuntamento clone() {
		return new Appuntamento(data, this.nomePersona, this.luogoAppuntamento, this.durata);
	}
	
	/**
	 * @return la data dell'appuntamento
	 */
	protected Calendar getData() {
		return this.data;
	}
	
	/**
	 * Controlla la validità dell'argomento
	 * 
	 * @param arg l'argomento da controllare
	 * @return <tt>true</tt> se l'argomento è valido, <tt>false</tt> altrimenti.
	 * @throws IllegalArgumentException se viene passato un argomento illegale.
	 */
	static protected boolean invalidArg(String arg)
	{
		if (arg == null)
			throw new IllegalArgumentException("Argomento non valido!");
		if (Integer.parseInt(arg) < 0)
			throw new IllegalArgumentException("Argomento non valido: immesso un valore negativo!");
		return false;
	}
	
	/**
	 * Controlla la validità dell'argomento come stringa
	 * 
	 * @param arg la stringa da controllare
	 * @return <tt>true</tt> se l'argomento è valido, <tt>false</tt> altrimenti.
	 * @throws IllegalArgumentException se viene passato un argomento illegale.
	 */
	static protected boolean invalidArgString(String arg)
	{
		if (arg == null)
			throw new IllegalArgumentException("Argomento non valido!");
		return false;
	}
}
