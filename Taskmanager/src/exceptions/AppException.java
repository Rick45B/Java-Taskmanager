package exceptions;

/**
 *Segnala un eccezione lanciata da {@code App} in caso di errore
 *
 */
public class AppException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Costruisce una nuova eccezione con messaggio e causa dettagliati
	 * @param msg il messaggio di errore
	 * @param e la causa dell'errore
	 */
	public AppException(String msg, Throwable e)
	{
		super(msg, e);
	}
	
	/**
	 * Costruisce un eccezione con un messaggio dettagliato
	 * @param msg il messaggio di errore
	 */
	public AppException(String msg)
	{
		super(msg);
	}
}
