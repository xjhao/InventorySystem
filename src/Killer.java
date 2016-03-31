import java.util.Random;

/**
 * This class used to kill locked waiting threads
 * @author xhao
 *
 */
public class Killer implements Runnable{
	// Warehouse the picker working on
	Warehouse warehouse = null;
	// Help to control the life period of the picker thread
	boolean flag = true;
	
	
	public Killer (Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public void run(){
		while (flag) {
			synchronized(warehouse) {
				warehouse.notifyAll();
				flag = false;
				System.out.println("All the threads were killed.");
			}	
		}
	}
}
