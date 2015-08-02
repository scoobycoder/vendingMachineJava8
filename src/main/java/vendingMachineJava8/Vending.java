package vendingMachineJava8;

public class Vending {

	private String sumOfChange = "INSERT COIN";
	
	public String display() {
		return sumOfChange;
	}

	public void insertCoin(Coins coin) {
		sumOfChange = String.valueOf(coin.value());
	}

}
