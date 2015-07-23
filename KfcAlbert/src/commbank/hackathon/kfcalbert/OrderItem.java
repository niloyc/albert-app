package commbank.hackathon.kfcalbert;

public class OrderItem {

	String mName;
	double mPrice;
	int mQty;
	
	public OrderItem(String name, int qty, double price){
		mName = name;
		mQty = qty;
		mPrice = price;
	}
	
	public String getName(){
		return mName;
	}
	
	public int getQty(){
		return mQty;
	}
	
	/**
	 * Get the price in dollars
	 * @return returns the price in dollars
	 */
	public double getPrice(){
		return mPrice;
	}
}
