package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import agenda.Appuntamento;
import exceptions.AppuntamentoException;

class Appuntamento_test {

	@Test
	void test_constructor() {
		Calendar date1 = Calendar.getInstance();
		date1.set(2023, 11, 14, 12, 30);
		Calendar date2 = Calendar.getInstance();
		date2.set(2023, 11, 14, 0, 0);
		Calendar date3 = Calendar.getInstance();
		date3.set(2023, 11, 14, 25, 0);
		
		Appuntamento temp1 = new Appuntamento(date1, "Rick", "UPO", 90);
		assertEquals("2023-11-14-12:30, Persona = Rick, Luogo = UPO, Durata = 90", temp1.toString());
		assertEquals("Rick", temp1.getPersona());
		assertEquals("UPO", temp1.getLuogo());
		Appuntamento temp2 = new Appuntamento(date1, "Rick", 90);
		assertEquals("2023-11-14-12:30, Persona = Rick, Luogo = , Durata = 90", temp2.toString());
		assertEquals("Rick", temp2.getPersona());
		assertEquals("", temp2.getLuogo());
		Appuntamento temp3 = new Appuntamento(date1, 90);
		assertEquals("2023-11-14-12:30, Persona = , Luogo = , Durata = 90", temp3.toString());
		assertEquals("", temp3.getPersona());
		assertEquals("", temp3.getLuogo());
		Appuntamento temp4 = new Appuntamento(date1);
		assertEquals("2023-11-14-12:30, Persona = , Luogo = , Durata = 60", temp4.toString());
		assertEquals("", temp4.getPersona());
		assertEquals("", temp4.getLuogo());
		Appuntamento temp5 = new Appuntamento(date2);
		assertEquals("2023-11-14-0:0, Persona = , Luogo = , Durata = 60", temp5.toString());
		assertEquals("", temp5.getPersona());
		assertEquals("", temp5.getLuogo());
		Appuntamento temp6 = new Appuntamento(date3);
		assertEquals("2023-11-15-1:0, Persona = , Luogo = , Durata = 60", temp6.toString()); 
		assertEquals("", temp6.getPersona());
		assertEquals("", temp6.getLuogo());
		
		assertEquals(temp1.getAnno(), "2023");
		assertEquals(temp1.getMese(), "11");
		assertEquals(temp1.getGiorno(), "14");
		assertEquals(temp1.getOra(), "12");
		assertEquals(temp1.getMinuto(), "30");
		assertEquals(temp1.getDurata(), "90");
		assertEquals(temp4.getDurata(), "60");
		//assertEquals(temp1.getFormat(), "2023-11-14-12:30");
		assertEquals(temp5.getOra(), "0");
	}
	
	@Test
	void setter_Test() {
		Calendar date1 = Calendar.getInstance();
		date1.set(2023, 11, 14, 12, 30);
		
		Appuntamento temp1 = new Appuntamento(date1, "Rick", "UPO", 90);
		assertEquals("2023-11-14-12:30, Persona = Rick, Luogo = UPO, Durata = 90", temp1.toString());
		assertEquals("Rick", temp1.getPersona());
		assertEquals("UPO", temp1.getLuogo());
		
		assertEquals(Assertions.assertThrows(AppuntamentoException.class, ()->temp1.setDurata(-5)).getMessage(), "Impossibile settare "
				+ "durata appuntamento!");
		assertEquals(Assertions.assertThrows(AppuntamentoException.class, ()->temp1.setHour(-12)).getMessage(), "Impossibile settare "
				+ "orario appuntamento(ora)!");
		assertEquals(Assertions.assertThrows(AppuntamentoException.class, ()->temp1.setMinute(-500)).getMessage(), "Impossibile settare "
				+ "orario appuntamento(minuti)!");
		assertEquals(Assertions.assertThrows(AppuntamentoException.class, ()->temp1.setDay(-9)).getMessage(), "Impossibile settare "
				+ "data appuntamento(giorno)!");
		assertEquals(Assertions.assertThrows(AppuntamentoException.class, ()->temp1.setYear(-50000)).getMessage(), "Impossibile settare "
				+ "data appuntamento(anno)!");
		assertEquals(Assertions.assertThrows(AppuntamentoException.class, ()->temp1.setPersona(null)).getMessage(), "Impossibile settare "
				+ "persona appuntamento!");
		assertEquals(Assertions.assertThrows(AppuntamentoException.class, ()->temp1.setLuogo(null)).getMessage(), "Impossibile settare "
				+ "luogo appuntamento!");
		
		//controllo concatenazione eccezioni
		AppuntamentoException ex1 = Assertions.assertThrows(AppuntamentoException.class, ()->temp1.setLuogo(null));
		assertEquals(ex1.getCause().getMessage(), "Argomento non valido!");
		assertEquals(ex1.getCause().getClass(), IllegalArgumentException.class);
	}

}
