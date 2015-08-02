package vendingMachineJava8;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class VendingTest {

	private ApplicationContext applicationContext;
	private Vending vending;

	@Before
	public void setup() {
		applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
		vending = (Vending) applicationContext.getBean("vending");
	}
	
	@Test
	public void shouldDisplayInsertChangeWhenNoChangeExists() {
		assertEquals("INSERT COIN", vending.display());
	}
	
	@Test
	public void shouldDisplayFiveCentsForNickle() {
		vending.insertCoin(new Nickle());
		assertEquals("0.05", vending.display());
	}
	
	@Test
	public void shouldDisplay10CentsforDime() {
		vending.insertCoin(new Dime());
		assertEquals("0.10", vending.display());
	}
	
}
