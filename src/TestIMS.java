/**
 * This class is used to test the functionality of IMS.
 * I didn't use JUnit for making my files clean.
 * 
 * Notice:
 * In my test case, there're four threads: 
 * two of them are picker and two of them are stocker.
 * 
 * They randomly choose one product with id "0" or "1" or "2", random amount from 1 to 3, 
 * to pick or stock to the warehouse. 
 * 
 * The system with run for 10 seconds, after that, all the threads will be killed and
 * print out the status before been killed if they're abnormal.
 *
 * @author xhao
 *
 */
public class TestIMS {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Warehouse warehouse = new Warehouse();
		Restocker r1 = new Restocker(warehouse);
		Restocker r2 = new Restocker(warehouse);
		Picker p1 = new Picker(warehouse);
		Picker p2 = new Picker(warehouse);
		Thread tr1 = new Thread(r1);
		Thread tr2 = new Thread(r2);
		Thread tp1 = new Thread(p1);
		Thread tp2 = new Thread(p2);
		tr1.start();
		tr2.start();
		tp1.start();
		tp2.start();
		try {
			// Execute the IMS for 10 seconds and then quit
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Don't create new missions
		r1.flag = false;
		r2.flag = false;
		p1.flag = false;
		p2.flag = false;
		
		// Help to kill locked waiting threads
		warehouse.flag = false;
		Killer k = new Killer(warehouse);
		Thread tk = new Thread(k);
		tk.start();
	}

}
