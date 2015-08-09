package vendingMachineJava8;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class paymentHandlerTest {
	
	@Mock
	private VendingItem mockedItem;
	@Mock
	private PaymentHandler paymentHandler;
	
	@InjectMocks private Vending underTest = new Vending();
	
    @Before public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void shouldCallPaymentProcessor() {
		underTest.purchase(mockedItem);
		 
		verify(paymentHandler).verifyPayment();
	}
	
}
