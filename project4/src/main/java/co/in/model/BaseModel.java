package co.in.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.Exception.DatabaseException;
import co.in.util.DataUtility;
import co.in.util.JDBCDataSource;


/**
 * @author Yash Pandey
 *
 */
public abstract class BaseModel implements Comparable<BaseModel> {
	
private static Logger log = Logger.getLogger(BaseModel.class);	

protected long id;
protected String createdby;
protected String modifiedby;
protected Timestamp createddatetime;
protected Timestamp modifieddatetime;
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


public int compareto(BaseModel next){
	int n = (int) (id - next.getId());
	return 0;
	
}	
	
public long nextpk() throws DatabaseException{
	log.debug("Model nextPK Started");
	long pk =0;
	Connection conn = null;
	
	try{
		
		conn =JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("select max(id) from ");
	    ResultSet rs = ps.executeQuery();
	    while(rs.next()){
	    	pk = rs.getInt(1);
	    }
	
	}catch(Exception e){
		e.printStackTrace();
		throw new DatabaseException("Exception : exception in getting pk");
	}
	log.debug("Model nextPK Started");
	return pk=pk+1;	
}



public abstract String getTableName();



public void UpdateCreatedInfo() throws ApplicationException, SQLException{
	log.debug("model update info started");
	Connection conn =null;
	String sql = "update"+getTableName() +"set created_by=? , created_datetime=? where id=?";
	
	try{
		conn =JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, createdby);
		ps.setTimestamp(2, DataUtility.getCurrentTimestamp());
		ps.setLong(3, id);
		
		ps.executeUpdate();
		conn.commit();
		ps.close();
		
	}catch(Exception e){
		e.printStackTrace();
		throw new ApplicationException(e.getMessage());
	}finally{
		conn.close();
	}
	
	log.debug("Model update info end");
}



public void updatemodifiedinfo() throws SQLException, ApplicationException{
	
	log.debug("Update modified info started");
	
	Connection conn =null;
	String sql = "update" +getTableName()+"set modified_by=?,modified_datetime=? where id=?";
	
	try{
	conn.setAutoCommit(false);
	PreparedStatement ps = conn.prepareStatement(sql.toString());
	ps.setString(1, modifiedby);
	ps.setTimestamp(2, DataUtility.getCurrentTimestamp());
    ps.setLong(3, id);
    
    ps.executeUpdate();
    conn.commit();
    ps.close();
	}catch(Exception e){
		e.printStackTrace();
		throw new ApplicationException(e.getMessage());
	}finally {
		conn.close();
	}
	log.debug("Update modified info end");
	}



protected <T extends BaseModel> T populateModel(ResultSet rs , T model) throws SQLException{
	
	model.setId(rs.getLong("id"));
	model.setCreatedby(rs.getString("created_by"));
	model.setModifiedby(rs.getString("modified_by"));
	model.setCreateddatetime(rs.getTimestamp("created_datetime"));
	model.setModifieddatetime(rs.getTimestamp("modified_datetime"));
	
	
	return model;
	
}


	
}
