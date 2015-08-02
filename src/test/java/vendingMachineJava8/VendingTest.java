package vendingMachineJava8;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class VendingTest {

	private ApplicationContext applicationContext;
	private Vending vending;
	private Coin nickle;
	private Coin dime;
	private Coin quarter;
	private Coin penny;

	@Before
	public void setup() {
		applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
		vending = (Vending) applicationContext.getBean("vending");
		nickle = (Nickle) applicationContext.getBean("nickle");
		dime = (Dime) applicationContext.getBean("dime");
		quarter = (Quarter) applicationContext.getBean("quarter");
		penny = (Penny) applicationContext.getBean("penny");
		
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
		vending.insertCoin(quarter);
		assertEquals("0.25", vending.display());
	}
	
	@Test
	public void shouldDisplayInsertCoinforAnyOtherCoin() {
		vending.insertCoin(penny);
		assertEquals("INSERT COIN", vending.display());
	}
	
}
