package vendingMachineJava8;

import java.util.ArrayList;

public class Vending {

	public static final String INSERTCOIN = "INSERT COIN"; 
	private double sum = 0;
	private double coinTray = 0;
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
		return makeChangeFormat(sum);
	}

	public void insertCoin(Coin coin) {
		paymentHandler.receivePayment(coin);
		sum += coin.value();
	}

	public String coinTray() {
		return makeChangeFormat(coinTray);
	}

	public void returnCoins() {
		coinTray = sum;
		sum = 0;
	}

	public ArrayList<VendingItem> itemBin() {
		return itemsInBin;
	}

	public void purchase(VendingItem item) {
		
		if (paymentHandler.verifyPayment()){
			completePurchase(item);
			chargeCustomer(item);
			returnCoins();
		}
		
 	}
	
	private boolean noMoney() {
		return sum == 0;
	}
	
	private String makeChangeFormat(double sums) {
		return String.format("%.2f",sums);
	}

	private void chargeCustomer(VendingItem item) {
		sum -= item.getCost();
	}

	private void completePurchase(VendingItem item) {
		itemsInBin.add(item);
	}

	private boolean canAfford(VendingItem item) {
		return item.getCost() <= sum;
	}

}
