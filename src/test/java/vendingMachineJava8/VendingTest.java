package vendingMachineJava8;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
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
	private VendingItem chips;
	private VendingItem candy;

	@Before
	public void setup() {
		applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
		vending = (Vending) applicationContext.getBean("vending");
		nickle = (Nickle) applicationContext.getBean("nickle");
		dime = (Dime) applicationContext.getBean("dime");
		quarter = (Quarter) applicationContext.getBean("quarter");
		penny = (Penny) applicationContext.getBean("penny");
		chips = (Chips) applicationContext.getBean("chips");
		candy = (Candy) applicationContext.getBean("candy");
		
	}
	
	private void insertMultipleCoins(Coin coin, int quantity) {
		for (int i = 0; i < quantity; i++) {
			vending.insertCoin(quarter);
		}
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
	
	@Test
	public void shouldSumAllChangeEntered() {
		insertMultipleCoins(quarter, 3);
		vending.insertCoin(nickle);
		vending.insertCoin(penny);
		vending.insertCoin(dime);
		assertEquals("0.90", vending.display());
	}
	
	@Test
	public void shouldReturnAllChangeWhenReturnChangeIsPressed() {
		vending.insertCoin(quarter);
		vending.insertCoin(dime);
		vending.returnCoin();
		
		assertEquals("INSERT COIN", vending.display());
		assertEquals("0.35", vending.coinTray());
	}
	
	@Test
	public void shouldAllowPurchaseOfChipsForOneTwentyFiveWhenChipsIsPressed() {
		insertMultipleCoins(quarter, 4);
		vending.insertCoin(quarter);
		ArrayList<VendingItem> expectedItems = new ArrayList<VendingItem>();
		expectedItems.add(chips);

		vending.purchase(chips);
		
		assertEquals(expectedItems, vending.itemBin());
	}
	
	@Test
	public void shouldAllowPurchaseOfChipsForOneTwentyFiveAndCandyForFifty() {
		insertMultipleCoins(quarter, 7);
		ArrayList<VendingItem> expectedItems = new ArrayList<VendingItem>();
		expectedItems.add(chips);
		expectedItems.add(candy);
		
		vending.purchase(chips);
		vending.purchase(candy);
		
		assertEquals(expectedItems, vending.itemBin());
	}
	
}
