package vendingMachineJava8;

import java.util.ArrayList;

public class Vending {

	public static final String INSERTCOIN = "INSERT COIN"; 
	private double coinsInTray = 0;
	private ArrayList<VendingItem> itemsInBin;
	private PaymentHandler paymentHandler;
	
	public void setPaymentHandler(PaymentHandler paymentHandler) {
		this.paymentHandler = paymentHandler;
	}

	public Vending() {
		itemsInBin = new ArrayList<VendingItem>();
	}
	
	public String display() {
		if (noMoney()) {
			return INSERTCOIN;
		}
		return makeChangeFormat(paymentHandler.returnBalanceOfPayment());
	}

	public void insertCoin(Coin coin) {
		paymentHandler.receivePayment(coin);
	}

	public String coinTray() {
		return makeChangeFormat(coinsInTray);
	}

	public void returnCoins() {
		coinsInTray = paymentHandler.returnBalanceOfPayment();
		paymentHandler.verifyPayment(paymentHandler.returnBalanceOfPayment());
	}

	public ArrayList<VendingItem> itemBin() {
		return itemsInBin;
	}

	public void purchase(VendingItem item) {
		
		if (paymentHandler.verifyPayment(item.getCost())){
			completePurchase(item);
			chargeCustomer(item);
			returnCoins();
		}
		
 	}
	
	private boolean noMoney() {
		return paymentHandler.returnBalanceOfPayment() == 0;
	}
	
	private String makeChangeFormat(double sums) {
		return String.format("%.2f",sums);
	}

	private void chargeCustomer(VendingItem item) {
		paymentHandler.verifyPayment(item.getCost());
	}

	private void completePurchase(VendingItem item) {
		itemsInBin.add(item);
	}

}
