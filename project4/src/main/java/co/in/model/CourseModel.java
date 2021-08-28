package co.in.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.CourseBean;
import co.in.util.JDBCDataSource;

/**
 * @author Yash Pandey
 *
 */
public class CourseModel {

	private static Logger log = Logger.getLogger(FacultyModel.class);
	
	
	public Integer nextpk(){
		
		log.debug("Model nextPK start");
		
		int pk=0;
		
		Connection conn=null;
		try{
			conn= JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_course");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				
			pk=rs.getInt(1)	;	
				
			}
			
			conn.commit();
			ps.close();
			rs.close();
		}catch(Exception e){
			e.getMessage();
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
			
		log.debug("Model nextPK end");
		return pk = pk+1;
		
		
	}



	public long add(CourseBean bean) throws ApplicationException,DuplicateRecordException{
		log.debug("Model add start");
		int pk=0;
CourseBean beanexist = null;
try{
	beanexist = findByName(bean.getCname());
	
}catch(Exception e){
	e.printStackTrace();
}
		
if(beanexist!=null){
	throw new DuplicateRecordException("Course name Already exist!");
}		
		
Connection conn = null;
try{
	pk=nextpk();
	conn=JDBCDataSource.getConnection();
	conn.setAutoCommit(false);
	PreparedStatement ps = conn.prepareStatement("insert into st_course values(?,?,?,?,?,?,?,?) ");
	ps.setInt(1, pk);
	ps.setString(2, bean.getCname());
	ps.setString(3, bean.getDuration());
	ps.setString(4, bean.getDescription());
	ps.setString(5, bean.getCreatedby());
	ps.setString(6, bean.getModifiedby());
	ps.setTimestamp(7, bean.getCreateddatetime());
	ps.setTimestamp(8, bean.getModifieddatetime());
	ps.executeUpdate();
	conn.commit();
	ps.close();
	
}catch(Exception e1){
	e1.printStackTrace();
	try{
		conn.rollback();
	}catch(Exception e2){
		e2.printStackTrace();
		throw new ApplicationException("Exception in roll back"+e2.getMessage());
	}
	
	throw new ApplicationException("exception : exception in adding course");
}
finally{
	JDBCDataSource.closeConnection(conn);		
}		
		
log.debug("Model add end");
		return pk;
		}
	
	
	
	
	
	public void delete(CourseBean bean) throws SQLException{
		log.debug("Model delete start");
		Connection conn = null;
		try{
			
			conn= JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_course where id=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();	
		
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			conn.close();
		}
		
		log.debug("Model delete end");
	}
	
	
	
	public void update(CourseBean bean) throws SQLException, ApplicationException, DuplicateRecordException{
		log.debug("Model update start");
		Connection conn = null;
		
		
		
		try{
			
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("update st_course set cname=? , duration=? , description=? , created_by=? , modified_by=? , created_datetime=? , modified_datetime=? where id=?");
			ps.setString(1, bean.getCname());
			ps.setString(2, bean.getDuration());
			ps.setString(3, bean.getDescription());
			ps.setString(4, bean.getCreatedby());
			ps.setString(5, bean.getModifiedby());
			ps.setTimestamp(6, bean.getCreateddatetime());
			ps.setTimestamp(7, bean.getModifieddatetime());
			ps.setLong(8, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
		}finally{
			
			conn.close();
		}
		
		log.debug("Model update end");
		
		
	}
	
		
	
	public CourseBean findByName(String cname) throws ApplicationException, SQLException {
		
		log.debug("Model findByName start");
		
		CourseBean bean =null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_course where cname=?");
		
		try{
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1,cname);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
			bean = new CourseBean();
			
			bean.setId(rs.getLong(1));
			bean.setCname(rs.getString(2));
			bean.setDuration(rs.getString(3));	
			bean.setDescription(rs.getString(4));
			bean.setCreatedby(rs.getString(5));
			bean.setModifiedby(rs.getString(6));
			bean.setCreateddatetime(rs.getTimestamp(7));
			bean.setModifieddatetime(rs.getTimestamp(8));
			
			}
			conn.commit();
			ps.close();
			rs.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			throw new ApplicationException("exception : exception in update course");
		}
		finally{
			conn.close();
		}

		log.debug("Model findByName end");
		
		return bean;
	}




	
	public CourseBean findBypk(long pk) throws ApplicationException, SQLException{

		log.debug("Model findBypk start");
		
		CourseBean bean = null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_course where id =?");
		try{
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				bean = new CourseBean();
				
				bean.setId(rs.getLong(1));
				bean.setCname(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedby(rs.getString(5));
				bean.setModifiedby(rs.getString(6));
				bean.setCreateddatetime(rs.getTimestamp(7));
				bean.setModifieddatetime(rs.getTimestamp(8));
				
			}
			
			conn.commit();
			ps.close();
			rs.close();
			
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			throw new ApplicationException("exception : exception in getting record by pk");
		}
		finally{
			conn.close();
		}
		

		log.debug("Model findBypk end");
		
		return bean;
		
	}




public List search(CourseBean bean , int pageNo , int pageSize) throws ApplicationException, SQLException{

	log.debug("Model search start");
	
	Connection conn = null;
	StringBuffer sql = new StringBuffer("select * from st_course where 1=1"); 
	
	if(bean!=null){
		
		if(bean.getId()>0){
			sql.append(" and id "+bean.getId());
		}
		
		if(bean.getCname()!=null && bean.getCname().length()>0){
			sql.append(" and cname like '"+bean.getCname()+"%'");
		}
		
		if(bean.getDuration()!=null && bean.getDuration().length()>0){
			sql.append(" and duration like '"+bean.getDuration()+"%'");
		}
		if(bean.getDescription()!=null && bean.getDescription().length()>0){
			sql.append(" and description like '"+bean.getDescription()+"%'");
		}
		
		
	}
	
	if(pageSize>0){
		pageNo = (pageNo-1)*pageSize;
		sql.append(" limit "+pageNo+ "," +pageSize);
	}
	
	List <CourseBean> list = new ArrayList<CourseBean>();
	
	try{
		
		conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ResultSet rs =ps.executeQuery();
		while(rs.next()){
			bean  = new CourseBean();
			
			bean.setId(rs.getLong(1));
			bean.setCname(rs.getString(2));
			bean.setDuration(rs.getString(3));
			bean.setDescription(rs.getString(4));
			bean.setCreatedby(rs.getString(5));
			bean.setModifiedby(rs.getString(6));
			bean.setCreateddatetime(rs.getTimestamp(7));
			bean.setModifieddatetime(rs.getTimestamp(8));
		
			list.add(bean);
		}
		
		ps.close();
		rs.close();
			
	}catch(Exception e){
		e.printStackTrace();
		throw new ApplicationException("exception : exception in search list");
	}
	finally{
		conn.close();
	}
	
	log.debug("Model search end");
	return list;	
	
}




public List list (int pageNo , int pageSize) throws ApplicationException, SQLException{
	log.debug("Model list start");
	Connection conn = null;
	CourseBean bean = null;
	StringBuffer sql = new StringBuffer("select * from st_course");
	
	if(pageSize>0){
		pageNo = (pageNo-1)*pageSize;
		sql.append(" limit "+pageNo+" , "+pageSize);
	}
	
	List<CourseBean> list = new ArrayList<CourseBean>();
	
	try{
		
	conn= JDBCDataSource.getConnection();
	PreparedStatement ps = conn.prepareStatement(sql.toString());
	ResultSet rs = ps.executeQuery();
	while(rs.next()){
	
		bean = new CourseBean();
		bean.setId(rs.getLong(1));
		bean.setCname(rs.getString(2));
		bean.setDuration(rs.getString(3));
		bean.setDescription(rs.getString(4));
		bean.setCreatedby(rs.getString(5));
		bean.setModifiedby(rs.getString(6));
		bean.setCreateddatetime(rs.getTimestamp(7));
		bean.setModifieddatetime(rs.getTimestamp(8));	
		
		list.add(bean);
	}
	ps.close();
	rs.close();
		
	}catch(Exception e){
		e.printStackTrace();
		throw new ApplicationException("exception : exception in getting list");
	}
	finally{
		conn.close();
	}
	log.debug("Model list end");
	return list;
	
}


public List list() throws Exception{
	return list(0,0);
}



}
