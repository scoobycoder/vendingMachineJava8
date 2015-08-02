package vendingMachineJava8;

public class Chips extends VendingItem {
	private double cost = 1.25;
	
	@Override
	public double getCost() {
		return cost;
	}
	
}
