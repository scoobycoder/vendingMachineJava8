package vendingMachineJava8;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class paymentHandlerTest {

	@Mock
	private VendingItem mockedItem;
	@Mock
	private PaymentHandler mockPaymentHandler;
	@Mock
	private Coin mockedCoin;
	@Mock
	private Payment mockedPayment;

	@InjectMocks
	private Vending underTest = new Vending();
	private AbstractApplicationContext applicationContext;
	private PaymentHandler paymentHandler;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);

		applicationContext = new ClassPathXmlApplicationContext(
				"application-context.xml");
		paymentHandler = (PaymentHandler) applicationContext
				.getBean("paymentHandler");
	}

	@Test
	public void shouldCallPaymentProcessor() {
		underTest.purchase(mockedItem);

		verify(mockPaymentHandler).verifyPayment(mockedItem.getCost());
	}

	@Test
	public void shouldCallPaymentProcessorToAddCoins() {
		underTest.insertCoin(mockedCoin);

		verify(mockPaymentHandler).receivePayment(mockedCoin);
	}

	@Test
	public void shouldReturnBalanceOfPaymentWhenTransactionComplete() {
		underTest.returnCoins();

		verify(mockPaymentHandler, times(2)).returnBalanceOfPayment();
	}

	@Test
	public void shouldReturnTrueIfPaymentIsEnough() {
		when(mockedPayment.value()).thenReturn(0.1);

		paymentHandler.receivePayment(mockedPayment);

		assertEquals(true, paymentHandler.verifyPayment(0.1));
	}

	@Test
	public void shouldReturnFalseIfPaymentIsNotEnough() {
		when(mockedPayment.value()).thenReturn(0.1);

		paymentHandler.receivePayment(mockedPayment);

		assertEquals(false, paymentHandler.verifyPayment(0.2));
	}

	@Test
	public void paymentHandlerShouldSubtractAvailablePaymentWhenItemPurchased() {
		when(mockedPayment.value()).thenReturn(1.0);

		paymentHandler.receivePayment(mockedPayment);
		paymentHandler.verifyPayment(0.75);

		assertEquals(0.25, paymentHandler.returnBalanceOfPayment(), 0.00001);
	}
	
	@Test
	public void shouldSumAllPaymentsEntered() {
		when(mockedPayment.value()).thenReturn(0.25);

		paymentHandler.receivePayment(mockedPayment);
		paymentHandler.receivePayment(mockedPayment);
		paymentHandler.receivePayment(mockedPayment);

		assertEquals(0.75, paymentHandler.returnBalanceOfPayment(), 0.00001);
	}

}
