package vendingMachineJava8;

public class Vending {

	public static final String INSERTCOIN = "INSERT COIN"; 
	private double sum = 0;
	
	public String display() {
		if (sum == 0) {
			return INSERTCOIN;
		}
		return String.valueOf(sum);
	}

	public void insertCoin(Coin coin) {
		sum += coin.value();
	}

}
