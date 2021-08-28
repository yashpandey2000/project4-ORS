package co.in.bean;

import java.util.Date;

/**
 * @author Yash Pandey
 *
 */
public class StudentBean extends BaseBean{

	
	private String firstname;
	private String lastname;
	private Date dob;
	private String mobileno;
	private String address;
	private String email;
	private long collegeid;
	private String collegename;
	
	
	
	
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getCollegeid() {
		return collegeid;
	}
	public void setCollegeid(long collegeid) {
		this.collegeid = collegeid;
	}
	public String getCollegename() {
		return collegename;
	}
	public void setCollegename(String collegename) {
		this.collegename = collegename;
	}
	public String getkey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	public String getvalue() {
		// TODO Auto-generated method stub
		return firstname+" "+lastname;
	}
}
