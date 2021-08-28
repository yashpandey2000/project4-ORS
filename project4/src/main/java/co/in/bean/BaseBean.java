package co.in.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Yash Pandey
 *
 */
public abstract class BaseBean implements Serializable , Comparable<BaseBean>,DropdownListBean{

public long id;
protected String createdby;
protected String modifiedby;
protected Timestamp	createddatetime;
protected Timestamp	modifieddatetime;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getCreatedby() {
	return createdby;
}
public void setCreatedby(String createdby) {
	this.createdby = createdby;
}
public String getModifiedby() {
	return modifiedby;
}
public void setModifiedby(String modifiedby) {
	this.modifiedby = modifiedby;
}
public Timestamp getCreateddatetime() {
	return createddatetime;
}
public void setCreateddatetime(Timestamp createddatetime) {
	this.createddatetime = createddatetime;
}
public Timestamp getModifieddatetime() {
	return modifieddatetime;
}
public void setModifieddatetime(Timestamp modifieddatetime) {
	this.modifieddatetime = modifieddatetime;
}


public int compareTo(BaseBean o) {
	
	return getvalue().compareTo(o.getvalue());
}
	

	
}
