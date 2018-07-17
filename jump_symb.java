
public class jump_symb {
	private String name;
	private String value;
	
	public jump_symb() {}
	public jump_symb(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	
	public void setName(String name) {
	   this.name= name;
	}
	public void setValue(String value) {
		   this.value= value;
		}
	
	public String toString() {
		return "Symbol:  " + name + "  value: " + value;
	 }

}
