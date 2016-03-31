import java.util.Random;

/**
 * This class simulate a worker who pick products from warehouse.
 * In order to simulate multiply workers pick products, it implements Interface Runnable.
 * @author xhao
 *
 */
public class Picker implements Runnable{
	// Warehouse the picker working on
	Warehouse warehouse = null;
	// Help to control the life period of the picker thread
	boolean flag = true;
	static final int PRODUCT_MAXIMUM_PICKING = 3;
	
	public Picker (Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public void run(){
		while (flag) {
			try {
				// To help show result in console more clear
				Thread.sleep(1000); 
				
				// Generate random ProductId and amountToPick values
				int randomId = (new Random()).nextInt(warehouse.PRODUCT_MAXIMUM_CATEGORYS);
				String productId = Integer.toString(randomId);
				int amountToPick = (new Random()).nextInt(PRODUCT_MAXIMUM_PICKING) + 1;
				
				// Pick amountToPick products with productId in warehouse
				warehouse.pickProduct(productId, amountToPick);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
