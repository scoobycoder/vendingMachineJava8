package vendingMachineJava8;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class VendingTest {

	private ApplicationContext applicationContext;
	private Vending vending;
	private Nickle nickle;
	private Dime dime;

	@Before
	public void setup() {
		applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
		vending = (Vending) applicationContext.getBean("vending");
		nickle = (Nickle) applicationContext.getBean("nickle");
		dime = (Dime) applicationContext.getBean("dime");
	}
	
	@Test
	public void shouldDisplayInsertChangeWhenNoChangeExists() {
		assertEquals("INSERT COIN", vending.display());
	}
	
	@Test
	public void shouldDisplayFiveCentsForNickle() {
		vending.insertCoin(nickle);
		assertEquals("0.05", vending.display());
	}
	
	@Test
	public void shouldDisplay10CentsforDime() {
		vending.insertCoin(dime);
		assertEquals("0.10", vending.display());
	}
	
}
