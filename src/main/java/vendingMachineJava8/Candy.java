package vendingMachineJava8;

public class Candy extends VendingItem {
	private double cost = 0.50;
	
	@Override
	public double getCost() {
		return cost;
	}
	
}
