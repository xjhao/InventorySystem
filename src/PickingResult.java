/**
 * Picking result which extends Result
 * @author xhao
 *
 */
public class PickingResult extends Result{
	public PickingResult(Product product, int amount, boolean success) {
		super(product, amount, success);
	}
	
	@Override
	public String toString() {
		if (isSuccess()) {
			return Thread.currentThread().getName() + 
					" successfully picked " + this.getAmount() + 
					" of Product:" + this.getProduct().getProductId() +
					", which current level is " + (this.getProduct().getLevel());
		} else {
			return Thread.currentThread().getName() + 
					" successfully picked " + this.getAmount() + 
					" of Product:" + this.getProduct().getProductId() +
					", which current level is already " + (this.getProduct().getLevel());
		}
					
	}
}
