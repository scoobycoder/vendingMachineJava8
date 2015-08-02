package vendingMachineJava8;

public class Vending {

	public static final String INSERTCOIN = "INSERT COIN"; 
	private double sum = 0;
	private double coinTray = 0;
	
	public String display() {
		if (sum == 0) {
			return INSERTCOIN;
		}
		return String.format("%.2f",sum);
	}

	public void insertCoin(Coin coin) {
		sum += coin.value();
	}

	public String coinTray() {
		return String.format("%.2f",coinTray);
	}

	public void returnCoin() {
		coinTray = sum;
		sum = 0;
	}

}
