import java.util.Random;

/**
 * This class simulate a worker who restocks products  warehouse.
 * In order to simulate multiply workers pick products, it implements Interface Runnable.
 * @author xhao
 */
public class Restocker implements Runnable{
	// Warehouse the restocker working on
	Warehouse warehouse = null;
	// Help to control the life period of the picker thread
	boolean flag = true;
	static final int PRODUCT_MAXIMUM_RESTOCKING = 3;
	
	public Restocker (Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	public void run(){
		while (flag) {
			try {
				// To help show result in console more clear
				Thread.sleep(500);
				
				// Generate random ProductId and amountToRestock values
				int randomId = (new Random()).nextInt(warehouse.PRODUCT_MAXIMUM_CATEGORYS);
				String productId = Integer.toString(randomId);
				int amountToRestock = (new Random()).nextInt(PRODUCT_MAXIMUM_RESTOCKING) + 1;
				
				// Pick amountToPick products with productId in warehouse
				warehouse.restockProduct(productId, amountToRestock);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
