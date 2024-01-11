package test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import agenda.Agenda;
import agenda.App;

class App_test {

	@Test
	void InsertRemove_test() {
		App temp = new App();
		Calendar dataColloquio = Calendar.getInstance();
		dataColloquio.set(2023, 9, 24, 12, 0);
		
		assertEquals(temp.getSize(),0);
		
		temp.addAgenda("Lavoro");
		assertEquals(temp.getSize(),1);
		
		temp.addAgenda("Università", 20);
		assertEquals(temp.getSize(),2);
		assertEquals(temp.getAgenda("Università").getMaxDim(), 20);
		
		Agenda agenda = new Agenda("figli");
		temp.addAgenda(agenda, "figli", 30);
		assertEquals(temp.getSize(),3);
		
		temp.removeAgenda("Università");
		assertEquals(temp.getSize(),2);
		
		Agenda agenda1 = temp.getAgenda("Lavoro");
		Assertions.assertDoesNotThrow(()->agenda1.add(dataColloquio, "Dott. Rossi Mario", 15));
		assertEquals(agenda1.toString(), "Nome Agenda = Lavoro, Appuntamenti = {2023-9-24-12:0, Persona = Dott. Rossi Mario, Luogo = , Durata = 15} ");
		
		//rimozione agenda
		temp.removeAgenda("Lavoro");
		assertEquals(temp.getSize(), 1);
		assertEquals(temp.getAgenda("Lavoro"), null);
		
		assertTrue(temp.agendaFromFile("agenda.dat", "Lavoro", 20));
		assertEquals(temp.getSize(),2);
		assertEquals(temp.getAgenda("Lavoro").size(), 3);
		
		assertTrue(temp.agendaToFile("agenda.dat", temp.getAgenda("Lavoro")));
		
		assertTrue(temp.addAppuntamentoToAgenda(dataColloquio, "Luca", "Casale M.to", 125, temp.getAgenda("Lavoro")));
		assertEquals(temp.getAgenda("Lavoro").size(), 4);
		assertEquals(temp.getAgenda("Lavoro").toString(), "Nome Agenda = Lavoro, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45} {2023-9-24-12:0, Persona = Luca, Luogo = Casale M.to, Durata = 125} {2023-11-23-15:22, Persona = , Luogo = , Durata = 60} {2025-3-9-11:5, Persona = Marco, Luogo = Piazza Sardegna, Durata = 90} ");

		assertTrue(temp.removeAppuntamenFromAgenda(temp.getAgenda("Lavoro"), dataColloquio));
		assertEquals(temp.getAgenda("Lavoro").size(), 3);
		assertEquals(temp.getAgenda("Lavoro").toString(), "Nome Agenda = Lavoro, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45} {2023-11-23-15:22, Persona = , Luogo = , Durata = 60} {2025-3-9-11:5, Persona = Marco, Luogo = Piazza Sardegna, Durata = 90} ");

		assertTrue(temp.removeAppuntamentoFromAgenda(temp.getAgenda("Lavoro"), temp.getAgenda("Lavoro").getAppuntamenti("Giacomo").get(0)));
		assertEquals(temp.getAgenda("Lavoro").size(), 2);
		assertEquals(temp.getAgenda("Lavoro").toString(), "Nome Agenda = Lavoro, Appuntamenti = {2023-11-23-15:22, Persona = , Luogo = , Durata = 60} {2025-3-9-11:5, Persona = Marco, Luogo = Piazza Sardegna, Durata = 90} ");

	}

	@Test
	void ModifySearch_test() {
		
		App temp = new App();
		Calendar dataColloquio = Calendar.getInstance();
		dataColloquio.set(2023, 9, 24, 12, 0);
		
		assertEquals(temp.getSize(),0);
		
		temp.addAgenda("Lavoro");
		assertEquals(temp.getSize(),1);
		
		temp.addAgenda("Università", 20);
		assertEquals(temp.getSize(),2);
		assertEquals(temp.getAgenda("Università").getMaxDim(), 20);
		
		Agenda agenda = new Agenda("figli");
		temp.addAgenda(agenda, "figli", 30);
		assertEquals(temp.getSize(),3);
		
		temp.removeAgenda("Università");
		assertEquals(temp.getSize(),2);
		
		Agenda agenda1 = temp.getAgenda("Lavoro");
		Assertions.assertDoesNotThrow(()->agenda1.add(dataColloquio, "Dott. Rossi Mario", 15));
		assertEquals(agenda1.toString(), "Nome Agenda = Lavoro, Appuntamenti = {2023-9-24-12:0, Persona = Dott. Rossi Mario, Luogo = , Durata = 15} ");
		
		//rimozione agenda
		temp.removeAgenda("Lavoro");
		assertEquals(temp.getSize(), 1);
		assertEquals(temp.getAgenda("Lavoro"), null);
		
		assertTrue(temp.agendaFromFile("agenda.dat", "Lavoro", 20));
		assertEquals(temp.getSize(),2);
		assertEquals(temp.getAgenda("Lavoro").size(), 3);
		
		assertTrue(temp.agendaToFile("agenda.dat", temp.getAgenda("Lavoro")));
		
		assertTrue(temp.addAppuntamentoToAgenda(dataColloquio, "Luca", "Casale M.to", 125, temp.getAgenda("Lavoro")));
		assertEquals(temp.getAgenda("Lavoro").size(), 4);
		assertEquals(temp.getAgenda("Lavoro").toString(), "Nome Agenda = Lavoro, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45} {2023-9-24-12:0, Persona = Luca, Luogo = Casale M.to, Durata = 125} {2023-11-23-15:22, Persona = , Luogo = , Durata = 60} {2025-3-9-11:5, Persona = Marco, Luogo = Piazza Sardegna, Durata = 90} ");

		//prova della modifica di un appuntamento
		assertTrue(temp.modifyAgenda(temp.getAgenda("Lavoro"), temp.getAgenda("Lavoro").getAppuntamenti("Giacomo").get(0), null, null, null, null , null, null, "Vercelli", null));
		assertEquals(temp.getAgenda("Lavoro").toString(), "Nome Agenda = Lavoro, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = Vercelli, Durata = 45} {2023-9-24-12:0, Persona = Luca, Luogo = Casale M.to, Durata = 125} {2023-11-23-15:22, Persona = , Luogo = , Durata = 60} {2025-3-9-11:5, Persona = Marco, Luogo = Piazza Sardegna, Durata = 90} ");

		Calendar dataColloquio1 = Calendar.getInstance();
		dataColloquio1.set(2023, 11, 23, 15, 22);
		assertTrue(temp.modifyAgenda(temp.getAgenda("Lavoro"), temp.getAgenda("Lavoro").getAppuntamenti(dataColloquio1), null, null, null, null, null, "Riccardo", null, null));
		assertEquals(temp.getAgenda("Lavoro").toString(), "Nome Agenda = Lavoro, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = Vercelli, Durata = 45} {2023-9-24-12:0, Persona = Luca, Luogo = Casale M.to, Durata = 125} {2023-11-23-15:22, Persona = Riccardo, Luogo = , Durata = 60} {2025-3-9-11:5, Persona = Marco, Luogo = Piazza Sardegna, Durata = 90} ");

		assertEquals(temp.searchAppuntamento(dataColloquio1).toString(), "2023-11-23-15:22, Persona = Riccardo, Luogo = , Durata = 60");
		assertEquals(temp.searchAppuntamenti("Giacomo", temp.getAgenda("Lavoro")).toString(), "[2020-8-22-18:30, Persona = Giacomo, Luogo = Vercelli, Durata = 45]");

	}
	
	@Test
	void ErrorMsg_Test() {
		
		App temp = new App();
		
		
		assertEquals(temp.getSize(),0);
		
		temp.addAgenda("Lavoro");
		assertEquals(temp.getSize(),1);
		
		temp.addAgenda("Università", 20);
		assertEquals(temp.getSize(),2);
		assertEquals(temp.getAgenda("Università").getMaxDim(), 20);
		
		Agenda agenda = new Agenda("figli");
		temp.addAgenda(agenda, "figli", 30);
		assertEquals(temp.getSize(),3);
		
		temp.removeAgenda("Università");
		assertEquals(temp.getSize(),2);
		
		//provo ad aggiungere una agenda con un nome già presente
		assertFalse(temp.addAgenda("figli", 30));
		assertEquals(temp.getErrorMsg(), "Agenda con lo stesso nome già esistente!");
		assertEquals(temp.getSize(),2);
		
		assertTrue(temp.agendaFromFile("agenda.dat", "Personale", 20));
		assertEquals(temp.getSize(),3);
		assertEquals(temp.getAgenda("Personale").size(), 3);
		assertEquals(temp.getAgenda("Personale").toString(), "Nome Agenda = Personale, Appuntamenti = {2020-8-22-18:30, Persona = Giacomo, Luogo = , Durata = 45} {2023-11-23-15:22, Persona = , Luogo = , Durata = 60} {2025-3-9-11:5, Persona = Marco, Luogo = Piazza Sardegna, Durata = 90} ");

		//provo a cercare un appuntamento con una data non presente
		Calendar dataColloquio = Calendar.getInstance();
		dataColloquio.set(2023, 9, 24, 12, 0);
		
		assertEquals(temp.searchAppuntamento(dataColloquio), null);
		assertEquals(temp.getErrorMsg(), "Appuntamento non trovato!");
	}
}
