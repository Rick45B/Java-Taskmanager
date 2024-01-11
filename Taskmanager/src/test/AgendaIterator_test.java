package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import agenda.Agenda;
import agenda.AgendaIterator;

class AgendaIterator_test {

	@Test
	void test() {
		AgendaIterator<Agenda> temp = new AgendaIterator<Agenda>();
		assertEquals(temp.size(), 0);
		
		//aggiungo agende e ne testo consistenza:
		Agenda agenda1 = new Agenda("Importante", 100);
		Agenda agenda2 = new Agenda("Università", 100);
		Agenda agenda3 = new Agenda("Lavoro", 100);
		temp.add(agenda1);
		assertEquals(temp.size(), 1);
		assertEquals(temp.toString(), "[Nome Agenda = Importante, Appuntamenti = ]");
		
		temp.add(agenda3);
		assertEquals(temp.size(), 2);
		assertEquals(temp.toString(), "[Nome Agenda = Importante, Appuntamenti = , Nome Agenda = Lavoro, Appuntamenti = ]");
		
		temp.add(agenda2);
		assertEquals(temp.size(), 3);
		assertEquals(temp.toString(), "[Nome Agenda = Importante, Appuntamenti = , Nome Agenda = Lavoro, Appuntamenti = , "
				+ "Nome Agenda = Università, Appuntamenti = ]");
		
		//testo la rimozione iterativa:
		Exception exc1 = assertThrows(Exception.class, () ->
		{temp.remove();});
		assertEquals("Operazione non supportata!", exc1.getMessage());
		
		assertEquals(temp.size(), 3);
		
		Calendar date = Calendar.getInstance();
		date.set(2023, 07, 26, 12, 30);
		Assertions.assertDoesNotThrow(()->agenda2.add(date, "Marco", "Vercelli, Via Laviny", 30));
		
		assertEquals(temp.toString(), "[Nome Agenda = Importante, Appuntamenti = , Nome Agenda = Lavoro, Appuntamenti = , "
				+ "Nome Agenda = Università, Appuntamenti = {2023-7-26-12:30, Persona = Marco, Luogo = Vercelli, Via Laviny, Durata = 30} ]");
		
		//Testo la rimozione non iterativa:
		temp.remove(2);
		assertEquals(temp.size(), 2);
		assertEquals(temp.toString(), "[Nome Agenda = Importante, Appuntamenti = , Nome Agenda = Lavoro, Appuntamenti = ]");
		
		//Testo "l'iterabilità":
		int cnt = 0;
		for (Agenda a:temp)
		{
			assertEquals(a, temp.get(cnt));
			cnt++;
		}
		assertEquals(cnt, 2);
	}

}
