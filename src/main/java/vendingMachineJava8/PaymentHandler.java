package vendingMachineJava8;

public class PaymentHandler {

	private double totalAvailablePayments;

	public boolean verifyPayment(double cost) {
		boolean enoughPayment = false;
		
		if (totalAvailablePayments >= cost) {
			enoughPayment = true;
			reduceAvailablePayment(cost);
		}
		
		return enoughPayment;
	}

	public void receivePayment(Payment payment) {
		totalAvailablePayments += payment.value();
	}

	public double returnBalanceOfPayment() {
		return totalAvailablePayments;
	}
	
	private void reduceAvailablePayment(double cost) {
		totalAvailablePayments -= cost;
	}

}
