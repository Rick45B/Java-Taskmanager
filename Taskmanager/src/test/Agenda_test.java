package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import agenda.Agenda;
import agenda.Appuntamento;
import exceptions.AgendaException;
import exceptions.AppuntamentoException;

class Agenda_test {

	@Test
	void test_addRemove() {
		Calendar date1 = Calendar.getInstance();
		date1.set(2025, 03, 9, 11, 5);
		Calendar date2 = Calendar.getInstance();
		date2.set(2023, 11, 23, 15, 22);
		Calendar date3 = Calendar.getInstance();
		date3.set(2020, 8, 22, 18, 30);
		Agenda temp1 = new Agenda("Importante", 150);
		assertEquals("Importante", temp1.getNome());
		assertEquals(temp1.size(), 0);
		Assertions.assertDoesNotThrow (()-> temp1.add(date3, "Giacomo", 45));
		Assertions.assertDoesNotThrow (()->temp1.add(date1, "Marco", "Piazza Sardegna", 90));
		Assertions.assertDoesNotThrow (()->temp1.add(date2));
		assertEquals(temp1.size(), 3);
		assertEquals(temp1.toString(), "Nome Agenda = Importante, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45}"
				+ " {2023-11-23-15:22, Persona = , Luogo = , Durata = 60}"
				+ " {2025-3-9-11:5, Persona = Marco, Luogo = Piazza Sardegna, Durata = 90} ");
		
		//provo a rimuovere appuntamento
		temp1.remove(date1);
		assertEquals(temp1.size(), 2);
		assertEquals(temp1.toString(), "Nome Agenda = Importante, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45}"
				+" {2023-11-23-15:22, Persona = , Luogo = , Durata = 60} ");
		temp1.remove(date2);
		temp1.remove(date3);
		assertEquals(temp1.size(), 0);
		assertEquals(temp1.toString(), "Nome Agenda = Importante, Appuntamenti = ");
		
		Assertions.assertDoesNotThrow (()-> temp1.add(date3, "Giacomo", 45));
		Assertions.assertDoesNotThrow (()->temp1.add(date1, "Marco", "Piazza Sardegna", 90));
		Assertions.assertDoesNotThrow (()->temp1.add(date2));
		
		//provo ad aggiungere appuntamento con stesso orario dell'appuntamento numero 2 (in ordine di data):
		Calendar date_temp = Calendar.getInstance();
		date_temp.set(2023, 11, 23, 15, 22);
		Calendar date_temp2 = Calendar.getInstance();
		date_temp2.set(2023, 11, 23, 15, 20);
		
		AgendaException ex1 = Assertions.assertThrows(AgendaException.class, () -> temp1.add(date_temp, 0));
		AgendaException ex2 = Assertions.assertThrows(AgendaException.class, () -> temp1.add(date_temp2, 2));
		assertEquals(ex1.getMessage(), "Errore: appuntamenti coincidenti!");
		assertEquals(ex2.getMessage(), "Errore: appuntamenti coincidenti!");
	}
	
	@Test
	void test_GetAppuntamento() {
		//creo agenda con appuntamenti
		Calendar date1 = Calendar.getInstance();
		date1.set(2029, 03, 9, 11, 5);
		Calendar date2 = Calendar.getInstance();
		date2.set(2021, 11, 23, 15, 22);
		Calendar date3 = Calendar.getInstance();
		date3.set(2016, 8, 22, 18, 30);
		Agenda temp1 = new Agenda("Importante", 150);
		assertEquals("Importante", temp1.getNome());
		assertEquals(temp1.size(), 0);
		Assertions.assertDoesNotThrow (()->temp1.add(date3, "Giacomo", 45));
		Assertions.assertDoesNotThrow (()->temp1.add(date1, "Giacomo", "Piazza Sardegna", 90));
		Assertions.assertDoesNotThrow (()->temp1.add(date2));
		assertEquals(temp1.toString(), "Nome Agenda = Importante, Appuntamenti = {2016-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45}"
				+ " {2021-11-23-15:22, Persona = , Luogo = , Durata = 60}"
				+ " {2029-3-9-11:5, Persona = Giacomo, Luogo = Piazza Sardegna, Durata = 90} ");
		
		//testo le funzioni search
		assertEquals(temp1.getAppuntamenti("Giacomo").toString(), "[2016-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45"
				+ ", 2029-3-9-11:5, Persona = Giacomo, Luogo = Piazza Sardegna, Durata = 90]");
		assertEquals(temp1.getAppuntamenti("").toString(), "[2021-11-23-15:22, Persona = , Luogo = , Durata = 60]");
		
		//modifico un appuntamento
		Assertions.assertDoesNotThrow(()->temp1.modify(temp1.getAppuntamenti(date2), null,null,null,null,null,"Pippo",null,null));
		assertEquals(temp1.getAppuntamenti("").toString(), "[]");
		assertEquals(temp1.getAppuntamenti("pippo").toString(), "[2021-11-23-15:22, Persona = Pippo, Luogo = , Durata = 60]");
		assertEquals(temp1.getAppuntamenti(date1).toString(), "2029-3-9-11:5, Persona = Giacomo, Luogo = Piazza Sardegna, Durata = 90");
	}
	
	@Test
	void test_modify() {
		//definisco delle variabili calendar per i test
		Calendar date4 = Calendar.getInstance();
		date4.set(2025, 03, 9, 11, 5);
		Calendar date5 = Calendar.getInstance();
		date5.set(1980, 11, 5, 11, 5);
		Calendar date1 = Calendar.getInstance();
		date1.set(2025, 03, 9, 11, 5);
		Calendar date2 = Calendar.getInstance();
		date2.set(2023, 11, 23, 15, 22);
		Calendar date3 = Calendar.getInstance();
		date3.set(2020, 8, 22, 18, 30);
		Agenda temp1 = new Agenda("Avvocato", 97);
		assertEquals("Avvocato", temp1.getNome());
		assertEquals(temp1.size(), 0);
		Assertions.assertDoesNotThrow (()->temp1.add(date3, "Giacomo", 45));
		Assertions.assertDoesNotThrow (()->temp1.add(date1, "Marco", "Piazza Sardegna", 90));
		Assertions.assertDoesNotThrow (()->temp1.add(date2));
		
		//testo metodi di modifica
		Assertions.assertDoesNotThrow(()->temp1.modify(temp1.getAppuntamenti(date4), null,null,null,null,null,"Luigi",null,"7"));
		assertEquals(temp1.toString(), "Nome Agenda = Avvocato, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45}"
				+ " {2023-11-23-15:22, Persona = , Luogo = , Durata = 60}"
				+ " {2025-3-9-11:5, Persona = Luigi, Luogo = Piazza Sardegna, Durata = 7} ");
		
		Assertions.assertDoesNotThrow(()->temp1.modify(temp1.getAppuntamenti(date4), null,null,null,null,null,null,null,null));
		assertEquals(temp1.toString(), "Nome Agenda = Avvocato, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45}"
				+ " {2023-11-23-15:22, Persona = , Luogo = , Durata = 60}"
				+ " {2025-3-9-11:5, Persona = Luigi, Luogo = Piazza Sardegna, Durata = 7} ");
		
		Assertions.assertDoesNotThrow(()->temp1.modify(temp1.getAppuntamenti(date4), "1980","11","5",null,null,"Luigi",null,"7"));
		assertEquals(temp1.toString(), "Nome Agenda = Avvocato, Appuntamenti = {1980-11-5-11:5, Persona = Luigi, Luogo = Piazza Sardegna, Durata = 7}" 
				+ " {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45}"
				+ " {2023-11-23-15:22, Persona = , Luogo = , Durata = 60} ");
		
		Assertions.assertDoesNotThrow(()->temp1.modify(temp1.getAppuntamenti(date5), "2025","03","9",null,null,null,null,null));
		assertEquals(temp1.toString(), "Nome Agenda = Avvocato, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45}"
				+ " {2023-11-23-15:22, Persona = , Luogo = , Durata = 60}"
				+ " {2025-3-9-11:5, Persona = Luigi, Luogo = Piazza Sardegna, Durata = 7} ");
		
		//controllo che argomenti non validi NON modifichino gli appuntamenti
		AgendaException ex1 = Assertions.assertThrows(AgendaException.class, () -> temp1.modify(temp1.getAppuntamenti(date4), "-2674","9",null,"-23","-14","Luigi",null,"-1"));
		assertEquals(temp1.toString(), "Nome Agenda = Avvocato, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45}"
				+ " {2023-11-23-15:22, Persona = , Luogo = , Durata = 60}"
				+ " {2025-3-9-11:5, Persona = Luigi, Luogo = Piazza Sardegna, Durata = 7} ");
		assertEquals(ex1.getCause().toString(), new AppuntamentoException("Impossibile settare data appuntamento(anno)!").toString());
		assertEquals(ex1.getMessage(), "Impossibile modificare l'appuntamento!");
		assertEquals(ex1.getCause().getCause().getClass(), IllegalArgumentException.class);
		
	}

	@Test
	void agendaIO_test() {
		Calendar date4 = Calendar.getInstance();
		date4.set(2025, 03, 9, 11, 5);
		Calendar date5 = Calendar.getInstance();
		date5.set(1980, 11, 5, 11, 5);
		Calendar date1 = Calendar.getInstance();
		date1.set(2025, 03, 9, 11, 5);
		Calendar date2 = Calendar.getInstance();
		date2.set(2023, 11, 23, 15, 22);
		Calendar date3 = Calendar.getInstance();
		date3.set(2020, 8, 22, 18, 30);
		Agenda temp1 = new Agenda("Avvocato", 97);
		assertEquals("Avvocato", temp1.getNome());
		assertEquals(temp1.size(), 0);
		Assertions.assertDoesNotThrow (()->temp1.add(date3, "Giacomo", 45));
		Assertions.assertDoesNotThrow (()->temp1.add(date1, "Marco", "Piazza Sardegna", 90));
		Assertions.assertDoesNotThrow (()->temp1.add(date2));
		
		//testo IO
		Assertions.assertDoesNotThrow(()->temp1.dumpAgendaToFile("calendario.txt"));
		Assertions.assertDoesNotThrow(()->temp1.dumpAgendaToFile("agenda.dat"));
		
		Assertions.assertDoesNotThrow(()->{Agenda temp2 = Agenda.createAgendaFromFile("agenda.dat", "NuovaAgendaFile", 100);
		assertEquals(temp2.size(), 3);
		assertEquals(temp2.toString(),"Nome Agenda = NuovaAgendaFile, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45}"
				+ " {2023-11-23-15:22, Persona = , Luogo = , Durata = 60}"
				+ " {2025-3-9-11:5, Persona = Marco, Luogo = Piazza Sardegna, Durata = 90} ");});
		
		Assertions.assertDoesNotThrow(()->{Agenda temp3 = Agenda.createAgendaFromFile("calendario.txt", "NuovaAgendaTesto", 100);
		assertEquals(temp3.size(), 3);
		assertEquals(temp3.toString(),"Nome Agenda = NuovaAgendaTesto, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45}"
				+ " {2023-11-23-15:22, Persona = , Luogo = , Durata = 60}"
				+ " {2025-3-9-11:5, Persona = Marco, Luogo = Piazza Sardegna, Durata = 90} ");});
	}
	
	@Test
	void exception_Test() {
		Calendar date = Calendar.getInstance();
		date.set(2021, 11, 23, 6, 50);
		Calendar date2 = Calendar.getInstance();
		date2.set(2023, 9, 11, 8, 20);
		Calendar date3 = Calendar.getInstance();
		date3.set(2025, 7, 2, 23, 20);
		Calendar date4 = Calendar.getInstance();
		date4.set(2025, 8, 11, 10, 20);
		//file non esistente in crerateAgendaFromFile
		AgendaException ex1 = Assertions.assertThrows(AgendaException.class, ()->Agenda.createAgendaFromFile("pippo.txt", "Prova", 10));
		assertEquals(ex1.getMessage(), "Impossibile creare agenda da file di testo!");
		assertEquals(ex1.getCause().getMessage(), "pippo.txt (No such file or directory)");
		assertEquals(ex1.getCause().getClass(), FileNotFoundException.class);
		AgendaException ex1Temp = Assertions.assertThrows(AgendaException.class, ()->Agenda.createAgendaFromFile("pippo.dat", "Prova", 10));
		assertEquals(ex1Temp.getMessage(), "Impossibile creare agenda da file binario!");
		
		//file corrotto in createAgendaFromFile
		AgendaException ex2 = Assertions.assertThrows(AgendaException.class, ()->Agenda.createAgendaFromFile("calendario_errato.txt", "Prova", 10));
		assertEquals(ex2.getMessage(), "Impossibile leggere dati da File: format sconosciuto!");
		
		//test dump agenda to file
		Agenda temp = new Agenda("Importante", 3);
		Assertions.assertDoesNotThrow (()->temp.add(date));
		Assertions.assertDoesNotThrow (()->temp.add(date2));
		Assertions.assertDoesNotThrow (()->temp.add(date3));
		AgendaException ex3 = Assertions.assertThrows(AgendaException.class, ()->temp.dumpAgendaToFile(null));
		assertEquals(ex3.getMessage(), "Impossibile salvare agenda su file!");
		assertEquals(ex3.getCause().getClass(), NullPointerException.class);
		
		//test su add
		assertEquals(temp.size(), 3);
		AgendaException ex4 = Assertions.assertThrows(AgendaException.class, ()->{
			temp.add(date4);
			assertEquals(temp.size(), 3);
			});
		assertEquals(ex4.getMessage(), "Ecceduta dimensione massima agenda!");
		assertEquals(temp.toString(), "Nome Agenda = Importante, Appuntamenti = "
				+ "{2021-11-23-6:50, Persona = , Luogo = , Durata = 60} "
				+ "{2023-9-11-8:20, Persona = , Luogo = , Durata = 60}"
				+ " {2025-7-2-23:20, Persona = , Luogo = , Durata = 60} ");
		temp.remove(date3);
		assertEquals(temp.size(), 2);
		
		ex4 = Assertions.assertThrows(AgendaException.class, ()->{
			temp.add(date2);});
		assertEquals(ex4.getMessage(), "Errore: appuntamenti coincidenti!");
		
		//remove
		assertEquals(Assertions.assertThrows(IllegalArgumentException.class, ()->temp.remove((Calendar)null)).getMessage(), "Argomento non valido: riferimento vacuo!");
		assertEquals(Assertions.assertThrows(IllegalArgumentException.class, ()->temp.remove((Appuntamento)null)).getMessage(), "Argomento non valido: riferimento vacuo!");
		
		//modify
		AgendaException ex5 = Assertions.assertThrows(AgendaException.class, ()->temp.modify(temp.getAppuntamenti(date2), "-1999", "-90", null, null, "Pippo", "SanGiovanni", "-2", null));
		assertEquals(ex5.getMessage(), "Impossibile modificare l'appuntamento!");
		assertEquals(ex5.getCause().getClass(), AppuntamentoException.class);
		assertEquals(ex5.getCause().getCause().getClass(), IllegalArgumentException.class);
	}
}
