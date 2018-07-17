
public class comp_symb {
	// it's better to use hashmaps over lists to represent the symbols and their value
	private String name;
	private String value;

	public comp_symb() {}

	public comp_symb(String name, String value) {
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
		this.name = name;
	}
	public void setValue(String value) {
		this.value  = value;
	}
	 
	public String toString() {
		return "Symbol:  " + name + "  value: " + value;
	 }


}
