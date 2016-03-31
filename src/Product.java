/**
 * Product with productId and level in the ware house.
 * @author xhao
 *
 */
public class Product {
	private String id;
	private int level;
	
	public Product(String id, int level) {
		this.id = id;
		this.level = level;
	}
	
	public String getProductId() {
        return this.id;
    }
    
    public void setProductId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
	
	public String toString() {
		return id;
	}
}
