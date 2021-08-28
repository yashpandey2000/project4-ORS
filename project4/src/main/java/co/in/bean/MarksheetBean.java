package co.in.bean;

/**
 * @author Yash Pandey
 *
 */
public class MarksheetBean extends BaseBean {
	
	private String rollno;
	private long studentid;
	private String name;
	private int physics;
	private int chemistry;
	private int maths;
	public String getRollno() {
		return rollno;
	}
	public void setRollno(String rollno) {
		this.rollno = rollno;
	}
	public long getStudentid() {
		return studentid;
	}
	public void setStudentid(long studentid) {
		this.studentid = studentid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPhysics() {
		return physics;
	}
	public void setPhysics(int physics) {
		this.physics = physics;
	}
	public int getChemistry() {
		return chemistry;
	}
	public void setChemistry(int chemistry) {
		this.chemistry = chemistry;
	}
	public int getMaths() {
		return maths;
	}
	public void setMaths(int maths) {
		this.maths = maths;
	}
	public String getkey() {
		// TODO Auto-generated method stub
		return id+ "";
	}
	public String getvalue() {
		// TODO Auto-generated method stub
		return rollno;
	}
	
	
	
	

}
