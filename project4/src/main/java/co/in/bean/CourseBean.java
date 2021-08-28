package co.in.bean;

/**
 * @author Yash Pandey
 *
 */
public class CourseBean extends BaseBean {
	
	private String cname;
	private String duration;
	private String description;
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
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
		return cname;
	}
	
	
	

}
