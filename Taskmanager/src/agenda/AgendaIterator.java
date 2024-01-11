package agenda;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Classe che, implementando la classe Iterator, rende iterabile un'agenda
 * @param <T> l'oggetto che viene reso iterabile
 * @author Pietro Balossino, Riccardo Giovanni Gualiumi
 */
public class AgendaIterator<T> extends ArrayList<Agenda> implements Iterator<Agenda>{

	private static final long serialVersionUID = 1L;
	private ArrayList<Agenda> arrayList;
    private int currentSize;
    
    /**
     * inizializza la classe. Non richiede argomenti.
     */
    public AgendaIterator() {
        this.arrayList = new ArrayList<Agenda>();
        this.currentSize = arrayList.size();
    }

    private int currentIndex = 0;

    @Override
    public boolean hasNext() {
        return currentIndex < currentSize && arrayList.get(currentIndex) != null;
    }

    @Override
    public Agenda next() {
        return arrayList.get(currentIndex++);
    }

    @Override
    public void remove() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Operazione non supportata!");
    }

}
