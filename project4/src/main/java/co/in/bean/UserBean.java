package co.in.bean;


import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Yash Pandey
 *
 */
public class UserBean extends BaseBean {
	
	
	public static final String active ="active";
	public static final String inactive ="inactive";

	
	private String firstname;
	private String lastname;
	private String loginid;
	private String password;
	private String confirmpassword;
	//private String address;
	private Date dob;
	private String mobileno;
	private long roleid;
	//private String rollname;
	private int unsuccessfullogin;
	private String gender;
	private Timestamp lastlogin;
	private String lock;
	private String registeredip;
	private String lastloginip;
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
	
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getPassword() {
		return password;
	}
	public String setPassword(String password) {
		return this.password = password;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
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
	public long getRoleid() {
		return roleid;
	}
	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}
	public int getUnsuccessfullogin() {
		return unsuccessfullogin;
	}
	public void setUnsuccessfullogin(int unsuccessfullogin) {
		this.unsuccessfullogin = unsuccessfullogin;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Timestamp getLastlogin() {
		return lastlogin;
	}
	public void setLastlogin(Timestamp lastlogin) {
		this.lastlogin = lastlogin;
	}
	public String getLock() {
		return lock;
	}
	public void setLock(String lock) {
		this.lock = lock;
	}
	public String getRegisteredip() {
		return registeredip;
	}
	public void setRegisteredip(String registeredip) {
		this.registeredip = registeredip;
	}
	public String getLastloginip() {
		return lastloginip;
	}
	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;	
	}

	
	public String getkey() {
		return id+"";
	}
	public String getvalue() {
		return firstname+" "+lastname;
	}
	

	
}
