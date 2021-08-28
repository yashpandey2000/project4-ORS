package co.in.bean;

/**
 * @author Yash Pandey
 *
 */
public class CollegeBean extends BaseBean {
	
	private String name;
	private String address;
	private String state;
	private String city;
	private String phoneno;
	
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getkey() {
		
		return id + "";
	}
	public String getvalue() {
		
		return name;
	}
	
}
