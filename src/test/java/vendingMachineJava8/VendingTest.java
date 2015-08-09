package vendingMachineJava8;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class VendingTest {

	private ApplicationContext applicationContext;
	private Coin nickle;
	private Coin dime;
	private Coin quarter;
	private Coin penny;
	private VendingItem chips;
	private VendingItem candy;

	@Mock
	private PaymentHandler paymentHandler;
	
	@InjectMocks private Vending underTest;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void setup() {
		applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
		underTest = (Vending) applicationContext.getBean("vending");
		nickle = (Nickle) applicationContext.getBean("nickle");
		dime = (Dime) applicationContext.getBean("dime");
		quarter = (Quarter) applicationContext.getBean("quarter");
		penny = (Penny) applicationContext.getBean("penny");
		chips = (Chips) applicationContext.getBean("chips");
		candy = (Candy) applicationContext.getBean("candy");
	}
	
	private void insertMultipleCoins(Coin coin, int quantity) {
		for (int i = 0; i < quantity; i++) {
			underTest.insertCoin(quarter);
		}
	}
	
	@Test
	public void shouldDisplayInsertChangeWhenNoChangeExists() {
		assertEquals("INSERT COIN", underTest.display());
	}
	
	@Test
	public void shouldDisplayFiveCentsForNickle() {
		underTest.insertCoin(nickle);
		assertEquals("0.05", underTest.display());
	}
	
	@Test
	public void shouldDisplay10CentsforDime() {
		underTest.insertCoin(quarter);
		assertEquals("0.25", underTest.display());
	}
	
	@Test
	public void shouldDisplayInsertCoinforAnyOtherCoin() {
		underTest.insertCoin(penny);
		assertEquals("INSERT COIN", underTest.display());
	}
	
	@Test
	public void shouldSumAllChangeEntered() {
		insertMultipleCoins(quarter, 3);
		underTest.insertCoin(nickle);
		underTest.insertCoin(penny);
		underTest.insertCoin(dime);
		assertEquals("0.90", underTest.display());
	}
	
	@Test
	public void shouldReturnAllChangeWhenReturnChangeIsPressed() {
		underTest.insertCoin(quarter);
		underTest.insertCoin(dime);
		underTest.returnCoins();
		
		assertEquals("INSERT COIN", underTest.display());
		assertEquals("0.35", underTest.coinTray());
	}
	
	@Test
	public void shouldAllowPurchaseOfChipsForOneTwentyFiveWhenChipsIsPressed() {
		when(paymentHandler.verifyPayment()).thenReturn(true);
		
		insertMultipleCoins(quarter, 4);
		underTest.insertCoin(quarter);
		ArrayList<VendingItem> expectedItems = new ArrayList<VendingItem>();
		expectedItems.add(chips);

		underTest.purchase(chips);
		
		assertEquals(expectedItems, underTest.itemBin());
	}
	
	@Test
	public void shouldAllowForPurchaseOfCandyForFiftyWhenCandyIsPressed() {
		when(paymentHandler.verifyPayment()).thenReturn(true);
		insertMultipleCoins(quarter, 2);
		ArrayList<VendingItem> expectedItems = new ArrayList<VendingItem>();
		expectedItems.add(candy);
		
		underTest.purchase(candy);
		
		assertEquals(expectedItems, underTest.itemBin());
	}
	
	@Test
	public void shouldMakeChangeForItemsPurchased() {
		when(paymentHandler.verifyPayment()).thenReturn(true);
		
		insertMultipleCoins(quarter, 3);
		underTest.purchase(candy);
		
		assertEquals("0.25", underTest.coinTray());
	}
	
	@Test
	public void shouldAllowForPurchaseOfMultipleItemsWhenMoneyEnteredBetweenTransactions() {
		when(paymentHandler.verifyPayment()).thenReturn(true);
		
		insertMultipleCoins(quarter, 2);
		ArrayList<VendingItem> expectedItems = new ArrayList<VendingItem>();
		expectedItems.add(candy);
		expectedItems.add(chips);
		
		underTest.purchase(candy);
		insertMultipleCoins(quarter, 5);
		underTest.purchase(chips);
		
		assertEquals(expectedItems, underTest.itemBin());
	}
	
	@Test
	public void shouldReturnToInsertCoinDisplayAfterPurchaseComplete() {
		when(paymentHandler.verifyPayment()).thenReturn(true);
		
		insertMultipleCoins(quarter, 2);
		
		underTest.purchase(candy);
		
		assertEquals("INSERT COIN", underTest.display());
	}
	
}
