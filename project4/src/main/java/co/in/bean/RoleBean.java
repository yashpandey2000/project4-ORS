package co.in.bean;

/**
 * @author Yash Pandey
 *
 */
public class RoleBean extends BaseBean  {

	
	public static final int admin=1;
	public static final int student=3;
	public static final int faculty=2;
	public static final int kiosk=4;
	public static final int college=5;
	
	private String name;
	private String description;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getkey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	public String getvalue() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
}
