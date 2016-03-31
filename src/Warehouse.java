import java.util.HashMap;
/**
 * Physical, Single site warehouse. 
 * It implements interface InventoryManagementSystem to allow picking and restocking products 
 * into warehouse in multiple-threads environment.
 * @author xhao
 *
 */
public class Warehouse implements InventoryManagementSystem{
	// Maximum categories of products the warehouse can store
	static final int PRODUCT_MAXIMUM_CATEGORYS = 3;
	// Maximum quantities of each product the warehouse can store
	static final int PRODUCT_MAXIMUM_SIZE = 6;
	//Each kind of product stored in a specific index of buffer, 
	// the index is therefore assumed to be the location of that kind of product.
	private Product[] buffer = new Product[PRODUCT_MAXIMUM_CATEGORYS];
	// Map between productId with its corresponding location
	HashMap<String, Integer> inventory = new HashMap<String, Integer>();
	// Help to kill the locked waiting thread
	boolean flag = true;

	@Override
	public synchronized PickingResult pickProduct(String productId, int amountToPick) {
		// TODO Auto-generated method stub
		// Warehouse doesn't stock product with this Id
		if (!inventory.containsKey(productId)) {
			System.out.println("No record of product:" + productId + 
					" in warehouse, picking failed!");
			Product p = new Product(productId, 0);
			PickingResult pr = new PickingResult(p, amountToPick, false);
			return pr;
		}
		
		// Warehouse stock product with this id, get its location.
		int location = inventory.get(productId);
		// Warehouse cannot pick all the products, then pick all of them current in the 
		// warehouse and wait to pick the remaining ones,
		// Unless flag == false, which means kill the locked waiting thread
		int remaining = 0;
		while (buffer[location].getLevel() - amountToPick < 0 && flag) {
			try {
				remaining = amountToPick - buffer[location].getLevel();
				System.out.println(Thread.currentThread().getName() + 
						" wants to pick " + amountToPick +
						" of product:" + productId +
						" , however, it can only pick " + buffer[location].getLevel() +
						" of them and waiting to pick another " + remaining);
				buffer[location].setLevel(0);
				amountToPick = remaining;
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Notify other threads to pick or restock
		this.notifyAll();
		
		if (flag) {
			// Update the product's information, print out the result and return the PickingResult
			Product p = new Product(productId, buffer[location].getLevel() - amountToPick);
			PickingResult pr = new PickingResult(p, amountToPick, true);
			buffer[location] = p;
			System.out.println(pr);
			return pr;
		} else {
			if (remaining != 0) {
				// Record mission that have not been completed yet.
				System.out.println(Thread.currentThread().getName() + 
						" didn't pick remaining " + remaining +
						" of product:" + productId);
			}
			return null;
		}
		
	}

	@Override
	public synchronized RestockingResult restockProduct(String productId, int amountToRestock) {
		// TODO Auto-generated method stub
		int location = 0;
		// Warehouse hasn't have product with this id yet.
		if (!inventory.containsKey(productId)) { 
			Product p = new Product(productId, 0);
			// Cannot store product with new id
			if (inventory.size() == PRODUCT_MAXIMUM_CATEGORYS) {
				RestockingResult rr = new RestockingResult(p, amountToRestock, false);
				System.out.println("Warehouse cannot accept new kind of product.");
				return rr;
			} else {
				// Put products with this id into buffer's first free place.
				location = inventory.size();
				inventory.put(productId, location);
				buffer[location] = p;
			}
		}
		
		// Warehouse has products with this id, get its location
		location = inventory.get(productId);
		
		// Warehouse cannot restock all the products, then restock some of them to fill the 
		// warehouse and wait to restock the remaining ones
		int remaining = 0;
		while (buffer[location].getLevel() + amountToRestock > PRODUCT_MAXIMUM_SIZE && flag) {
			try {
				remaining = buffer[location].getLevel() + amountToRestock - PRODUCT_MAXIMUM_SIZE;
				System.out.println(Thread.currentThread().getName() + 
						" wants to restock " + amountToRestock +
						" of product:" + productId +
						" , however, it can only restock " + (PRODUCT_MAXIMUM_SIZE - buffer[location].getLevel()) +
						" of them and waiting to restock another " + remaining);
				buffer[location].setLevel(PRODUCT_MAXIMUM_SIZE);
				amountToRestock = remaining;
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Notify other threads to pick or restock
		this.notifyAll();
		
		if (flag) {
			// Update the product's information, print out the result and return the RestockingResult
			Product p = new Product(productId, buffer[location].getLevel() + amountToRestock);
			RestockingResult rr = new RestockingResult(p, amountToRestock, true);
			buffer[location] = p;
			System.out.println(rr);
			return rr;
		} else {
			if (remaining != 0) {
				// Record mission that have not been completed yet.
				System.out.println(Thread.currentThread().getName() + 
						" didn't restock remaining" + remaining +
						" of product:" + productId);
			}
			return null;
		}
		
	}
}
