package agenda;

import java.util.ArrayList;
import java.util.Calendar;

import exceptions.AgendaException;
import exceptions.AppException;


/**
 * Classe utilizzata per la gestione delle agende, dalla creazione alla rimozione
 * 
 * @author  Pietro Balossino, Riccardo Giovanni Gualiumi
 */
public class App {
	private AgendaIterator<Agenda> agende;
	private String errorMsg;
	
	/**
	 * Costruisce una nuova interfaccia per la gestione delle agende
	 */
	public App() {
		agende = new AgendaIterator<Agenda>();
	}
	
	/**
	 * @return il numero di agende
	 */
	public Integer getSize() {
		return agende.size();
	}
	
	/**
	 * @return un messaggio di errore
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	
	/**
	 * Metodo per cercare una agenda tramite il suo nome
	 * 
	 * @param name il nome dell'agenda da cercare
	 * @return un'agenda
	 */
	public Agenda getAgenda(String name) {
		
		try  //controllo se è una stringa o un intero.
		{
			Appuntamento.invalidArg(name);
			return agende.get(Integer.parseInt(name));
		}
		catch (IllegalArgumentException e)
		{
			Appuntamento.invalidArgString(name);
			for (Agenda temp:agende)
			{
				if (temp.getNome().equals(name))
					return temp;
			}
			return null;
		}
	}

	
	/**
	 * Crea una nuova agenda e la aggiunge alla lista di agende
	 * 
	 * @param nomeAgenda il nome dell'agenda da aggiungere
	 * @param MAXDIM la dimensione dell'agenda da aggiungere
	 * @return <tt>true</tt> se l'agenda e' stato aggiunta, <tt>false</tt> altrimenti.
	 */
	public boolean addAgenda(String nomeAgenda, Integer MAXDIM)
	{
		return addAgenda(null, nomeAgenda, MAXDIM);
	}
	
	
	/**
	 * Aggiunge una agenda alla lista di agende
	 * 
	 * @param agenda l'agenda da aggiungere alla lista di agende
	 * @param nomeAgenda il nome dell'agenda da aggiungere
	 * @param MAXDIM la dimensione dell'agenda da aggiungere
	 * @return <tt>true</tt> se l'agenda e' stato aggiunta, <tt>false</tt> altrimenti.
	 */
	public boolean addAgenda(Agenda agenda, String nomeAgenda, Integer MAXDIM) {
		
		if (!(Appuntamento.invalidArgString(nomeAgenda) || Appuntamento.invalidArg((MAXDIM.toString()))))
		{
			if (agende.contains(getAgenda(nomeAgenda)))
			{
				errorMsg = getError(new AppException("Agenda con lo stesso nome già esistente!"));
				return false;
			}
			if (agenda!=null)
			{
				agende.add(agenda);
				return true;
			}
			Agenda temp = new Agenda(nomeAgenda, MAXDIM);
			agende.add(temp);
			return true;
		}
		return false;
	}
	
	/**
	 * Crea una nuova agenda con valori Standard e la aggiunge alla lista di agende
	 * @param nomeAgenda il nome dell'agenda da aggiungere
	 * @return <tt>true</tt> se l'agenda e' stato aggiunta, <tt>false</tt> altrimenti.
	 */
	public boolean addAgenda(String nomeAgenda)
	{
		return addAgenda(nomeAgenda, 20);   //valore standard di MAXDIM
	}
	
	/**Rimuove un'agenda
	 * 
	 * @param name il nome dell'agenda da rimuovere
	 * @return <tt>true</tt> se l'agenda e' stata rimossa, <tt>false</tt> altrimenti.
	 */
	public boolean removeAgenda(String name) {
		
		if (!Appuntamento.invalidArgString(name))
		{
			Agenda temp = getAgenda(name);
			
			if (temp!=null)
			{
				agende.remove(getAgenda(name));
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Aggiunge una agenda creata da file
	 * 
	 * @param fileName il nome del file usato per creare l'agenda
	 * @param nomeAgenda il nome dell'agenda da aggiungere
	 * @param MAXDIM la dimensione dell'agenda da aggiungere
	 * @return <tt>true</tt> se l'agenda e' stato aggiunta, <tt>false</tt> altrimenti.
	 */
	public boolean agendaFromFile(String fileName, String nomeAgenda, int MAXDIM)
	{
		try {
			for (Agenda temp : agende)
			{
				if (temp.getNome().equals(nomeAgenda))
				{
					errorMsg = "Agenda con lo stesso nome già esistente!";
					return false;
				}
			}
			Agenda temp = Agenda.createAgendaFromFile(fileName, nomeAgenda, MAXDIM);
			agende.add(temp);
			return true;
		} catch (AgendaException | IllegalArgumentException e) {
			errorMsg = getError(e);
			return false;
		}
	}
	
	/**
	 * Scrive un'agenda su un file
	 * 
	 * @param fileName il nome del file su cui scrivere l'agenda
	 * @param agenda l'agenda da scrivere sul file
	 * @return <tt>true</tt> se l'agenda e' stato scritta correttamente su file, <tt>false</tt> altrimenti.
	 */
	public boolean agendaToFile(String fileName, Agenda agenda)
	{
		try {
			agenda.dumpAgendaToFile(fileName);
		} catch (AgendaException | IllegalArgumentException e) {
			errorMsg = getError(e);
			return false;
		}
		return true;
	}
	
	
	/**
	 * Aggiunge un'appuntamento ad un'agenda
	 * 
	 * @param date la data dell'appuntamento
	 * @param nomePersona il nome della persone con cui si avra' l'appuntamento
	 * @param luogoAppuntamento il luogo dove si avra' l'appuntamento
	 * @param durata la durata dell'appuntamento
	 * @param agenda l'agenda in cui inserire l'appuntamento
	 * @return <tt>true</tt> se l'appuntamento e' stato aggiunto, <tt>false</tt> altrimenti.
	 */
	public boolean addAppuntamentoToAgenda(Calendar date,
			String nomePersona, String luogoAppuntamento, Integer durata, Agenda agenda)
	{
		try {
			if (luogoAppuntamento == null)
				agenda.add(date, nomePersona, durata);
			if (luogoAppuntamento == null && nomePersona == null)
				agenda.add(date, durata);
			if (luogoAppuntamento == null && nomePersona == null && durata == null)
				agenda.add(date);
			agenda.add(date, nomePersona, luogoAppuntamento, durata);
		} catch (AgendaException | IllegalArgumentException e) {
			errorMsg = getError(e);
			return false;
		}
		return true;
	}

	/**
	 * Rimuove un appuntamento da un'agenda in base alla data
	 * 
	 * @param agenda l'agenda da cui togliere l'appuntamento
	 * @param date la data in cui eliminare l'appuntamento
	 * @return <tt>true</tt> se l'appuntamento e' stato rimosso, <tt>false</tt> altrimenti.
	 */
	public boolean removeAppuntamenFromAgenda(Agenda agenda, Calendar date)
	{
		try
		{
			agenda.remove(date);
		}
		catch (IllegalArgumentException e)
		{
			errorMsg = getError(e);
			return false;
		}
		return true;
	}
	
	/**
	 * Rimuove un appuntamento da un'agenda
	 * 
	 * @param agenda l'agenda da cui togliere l'appuntamento
	 * @param appuntamento l'appuntamento da rimuovere
	 * @return <tt>true</tt> se l'appuntamento e' stato rimosso, <tt>false</tt> altrimenti.
	 */
	public boolean removeAppuntamentoFromAgenda(Agenda agenda, Appuntamento appuntamento)
	{
		try
		{
			agenda.remove(appuntamento);
		}
		catch (IllegalArgumentException e)
		{
			errorMsg = getError(e);
			return false;
		}
		return true;
	}
	
	/**
	 * Modifica un appuntamento di un'agenda
	 * 
	 * @param agenda l'agenda in cui modificare l'appuntamento
	 * @param appuntamento l'appuntamento da modificare
	 * @param args i dati da modificare
	 * @return <tt>true</tt> se l'appuntamento e' stato modificato, <tt>false</tt> altrimenti.
	 */
	public boolean modifyAgenda(Agenda agenda, Appuntamento appuntamento, String...args)
	{
		try {
			agenda.modify(appuntamento, args);
		} catch (AgendaException | IllegalArgumentException e) {
			errorMsg = getError(e);
			return false;
		}
		return true;
	}
	
	/**
	 * Ricerca un appuntamento in base alla data
	 * 
	 * @param date la data in cui cercare l'appuntamento
	 * @return un {@link Appuntamento}
	 */
	public Appuntamento searchAppuntamento(Calendar date) {
		for (Agenda temp:agende)
		{
			Appuntamento out = temp.getAppuntamenti(date);
			if (out!=null)
				return out;
		}
		errorMsg = "Appuntamento non trovato!";
		return null;
	}
	
	/**
	 * Cerca una lista di appuntamenti con una persona
	 * @param persona il nome della persona con cui si ha l'appuntamento
	 * @param agenda l'agenda in cui cercare gli appuntamenti
	 * @return un ArrayList di Appuntamenti
	 */
	public ArrayList<Appuntamento> searchAppuntamenti(String persona, Agenda agenda) {
		try{return agenda.getAppuntamenti(persona);}
		catch(IllegalArgumentException e) {
			errorMsg = getError(e);
			return null;}
	}
	
	/**
	 * Ritorna l'errore estrapolato da qualsiasi eccezione lanciata
	 * @param e l'eccezione
	 * @return il messaggio dell'ultima eccezione lanciata
	 */
	private String getError(Throwable e)
	{
		if (e.getCause() != null)
		{
			if (e.getCause().getCause() != null)
				return e.getCause().getCause().getMessage();
			else
				return e.getCause().getMessage();
		}
		return e.getMessage();
	}

}
