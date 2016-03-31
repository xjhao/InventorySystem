/**
 * Abstract class of the return class of picking and restocking products
 * It records:
 * 1. the product picked or restocked
 * 2. the amount of this product picked or restocked
 * 3. if the picking or restocking process successful, if true, then the process if successful,
 * if false, the process is failed.
 * @author xhao
 *
 */
public abstract class Result {
	private Product product;
	private int amount;
	private boolean success;
	
	public Result(Product product, int amount, boolean success) {
		this.product = product;
		this.amount = amount;
		this.success = success;
	}
	
	public void setProduct(Product p) {
		this.product = p;
	}
	public Product getProduct() {
		return this.product;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getAmount() {
		return this.amount;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public abstract String toString();
}
