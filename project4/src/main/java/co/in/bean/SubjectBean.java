package co.in.bean;

/**
 * @author Yash Pandey
 *
 */
public class SubjectBean extends  BaseBean{
	
	private int courseid;
	private String coursename;
	private String subjectname;
	private String description;
	public int getCourseid() {
		return courseid;
	}
	public void setCourseid(int courseid) {
		this.courseid = courseid;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getSubjectname() {
		return subjectname;
	}
	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
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
		return subjectname;
	}
	
	

}
