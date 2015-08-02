package vendingMachineJava8;

public class Coins {

	private Coin coinType;

	public Coins(Coin coinType) {
		this.coinType = coinType;
	}

	public String value() {
		if (coinType == Coin.NICKLE) {
			return "0.05";
		}
		else
			return "0.10";
	}

}
