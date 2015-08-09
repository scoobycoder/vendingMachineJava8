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
	@Mock
	private VendingItem mockedItem;
	
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
	public void shouldDisplay25CentsforQuarter() {
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
		when(paymentHandler.returnBalanceOfPayment()).thenReturn(0.35);
		
		underTest.returnCoins();
		
		assertEquals("INSERT COIN", underTest.display());
		assertEquals("0.35", underTest.coinTray());
	}
	
	@Test
	public void shouldAllowPurchaseOfChipsForOneTwentyFiveWhenChipsIsPressed() {
		when(mockedItem.getCost()).thenReturn(1.25);
		when(paymentHandler.verifyPayment(mockedItem.getCost())).thenReturn(true);
		
		insertMultipleCoins(quarter, 4);
		underTest.insertCoin(quarter);
		ArrayList<VendingItem> expectedItems = new ArrayList<VendingItem>();
		expectedItems.add(chips);

		underTest.purchase(chips);
		
		assertEquals(expectedItems, underTest.itemBin());
	}
	
	@Test
	public void shouldAllowForPurchaseOfCandyForFiftyWhenCandyIsPressed() {
		when(mockedItem.getCost()).thenReturn(0.50);
		when(paymentHandler.verifyPayment(mockedItem.getCost())).thenReturn(true);
		insertMultipleCoins(quarter, 2);
		ArrayList<VendingItem> expectedItems = new ArrayList<VendingItem>();
		expectedItems.add(candy);
		
		underTest.purchase(candy);
		
		assertEquals(expectedItems, underTest.itemBin());
	}
	
	@Test
	public void shouldMakeChangeForItemsPurchased() {
		when(mockedItem.getCost()).thenReturn(0.50);
		when(paymentHandler.verifyPayment(mockedItem.getCost())).thenReturn(true);
		when(paymentHandler.returnBalanceOfPayment()).thenReturn(0.25);
		
		underTest.purchase(mockedItem);
		
		assertEquals("0.25", underTest.coinTray());
	}
	
	@Test
	public void shouldAllowForPurchaseOfMultipleItemsWhenMoneyEnteredBetweenTransactions() {
		when(mockedItem.getCost()).thenReturn(0.50);
		when(paymentHandler.verifyPayment(mockedItem.getCost())).thenReturn(true);
		when(mockedItem.getCost()).thenReturn(1.25);
		when(paymentHandler.verifyPayment(mockedItem.getCost())).thenReturn(true);
		
		ArrayList<VendingItem> expectedItems = new ArrayList<VendingItem>();
		expectedItems.add(candy);
		expectedItems.add(chips);
		
		insertMultipleCoins(quarter, 2);
		underTest.purchase(candy);
		
		insertMultipleCoins(quarter, 5);
		underTest.purchase(chips);
		
		assertEquals(expectedItems, underTest.itemBin());
	}
	
	@Test
	public void shouldReturnToInsertCoinDisplayAfterPurchaseComplete() {
		when(mockedItem.getCost()).thenReturn(0.50);
		when(paymentHandler.verifyPayment(mockedItem.getCost())).thenReturn(true);
		
		insertMultipleCoins(quarter, 2);
		
		underTest.purchase(candy);
		
		assertEquals("INSERT COIN", underTest.display());
	}
	
}
