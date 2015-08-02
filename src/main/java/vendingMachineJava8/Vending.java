package vendingMachineJava8;

public class Vending {

	private String sumOfChange = "INSERT COIN";
	
	public String display() {
		return sumOfChange;
	}

	public void insertCoin(Coin coin) {
		sumOfChange = String.valueOf(0.05);
	}

}
