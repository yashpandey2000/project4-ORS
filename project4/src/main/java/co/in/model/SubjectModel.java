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
import co.in.bean.SubjectBean;
import co.in.util.JDBCDataSource;

/**
 * @author Yash Pandey
 *
 */
public class SubjectModel {
	private static Logger log = Logger.getLogger(SubjectModel.class);
	
	public Integer nextpk() throws SQLException, ApplicationException{
		log.debug("Model nextPK Started");
		int pk=0;
		Connection conn =null;
		
		try{
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_subject");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				
			pk = rs.getInt(1);	
				
			}
			conn.commit();
			ps.close();
			rs.close();
				
		}catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw new ApplicationException("exception : exception in getting next pk");
		}
		finally{
			conn.close();
		}
		log.debug("Model nextPK End");
		return pk = pk+1;
	
	}
	
	
	
	
	
	public long add(SubjectBean bean) throws ApplicationException, SQLException, DuplicateRecordException{
		log.debug("Model add Started");
		int pk=0;
		
		CourseModel cm = new CourseModel();
		CourseBean cb = cm.findBypk(bean.getCourseid());
		bean.setCoursename(cb.getCname());
		bean.setDescription(cb.getDescription());
		
		SubjectBean beanexist = findByName(bean.getSubjectname());
		
		if(beanexist!=null){
			throw new DuplicateRecordException("subject already exist");
		}
		
		Connection conn =null;
		
		try{
			
			pk=nextpk();
			conn =JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_subject values(?,?,?,?,?,?,?,?,?)");
			ps.setLong(1,pk);
			ps.setInt(2, bean.getCourseid());
			ps.setString(3, bean.getCoursename());
			ps.setString(4, bean.getSubjectname());
			ps.setString(5, bean.getDescription());
			ps.setString(6, bean.getCreatedby());
			ps.setString(7, bean.getModifiedby());
			ps.setTimestamp(8, bean.getCreateddatetime());
			ps.setTimestamp(9, bean.getModifieddatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();	
			
		}catch(Exception e){
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("exception : exception in adding subject");
		
		}finally {
			conn.close();
		}
		log.debug("Model add end");
		return pk;	
		
	}



	public SubjectBean findBypk(long pk) throws SQLException{	
		log.debug("Model findByPK Started");
		SubjectBean bean = null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_subject where id=?");
		try{
			 conn = JDBCDataSource.getConnection();
			 conn.setAutoCommit(false);
			 PreparedStatement ps = conn.prepareStatement(sql.toString());
			 ps.setLong(1, pk);
			 ResultSet rs = ps.executeQuery();
			 while(rs.next()){
				 
				 bean = new SubjectBean();
				 bean.setId(rs.getLong(1));
				 bean.setCourseid(rs.getInt(2));
				 bean.setCoursename(rs.getString(3));
				 bean.setSubjectname(rs.getString(4));
				 bean.setDescription(rs.getString(5));
				 bean.setCreatedby(rs.getString(6));
				 bean.setModifiedby(rs.getString(7));
				 bean.setCreateddatetime(rs.getTimestamp(8));
				 bean.setModifieddatetime(rs.getTimestamp(9)); 	 
			 }
			 conn.commit();
			 ps.close();
			 rs.close();
			 
			 
			 }catch(Exception e){
				  log.error("Database Exception..", e);
				 e.printStackTrace();
				 conn.rollback();	
		}
		 log.debug("Model findByPK End");
		return bean;
		}
	



	public SubjectBean findByName(String subjectname) throws SQLException {
		log.debug("Model findByName Started");
		SubjectBean bean = null;
		Connection conn =null;
		StringBuffer sql = new StringBuffer("select * from st_subject where subject_name=?");
		try{
			
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, subjectname);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setCourseid(rs.getInt(2));
				bean.setCoursename(rs.getString(3));
				bean.setSubjectname(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedby(rs.getString(6));
				bean.setModifiedby(rs.getString(7));
				bean.setCreateddatetime(rs.getTimestamp(8));
				bean.setModifieddatetime(rs.getTimestamp(9));
				
			}
			
			conn.commit();
			rs.close();
			ps.close();
			
		}catch(Exception e){
			log.error("Database Exception" + e);
			e.printStackTrace();
			conn.rollback();
		}finally{
			
			conn.close();
		}
		
		log.debug("Model add End");
		return bean;
	}
	
	
	
public void delete(SubjectBean bean) throws ApplicationException, SQLException{
	log.debug("model delete begin");
	Connection conn =null;
	
	try{
		
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("delete from st_subject where id=?");
		ps.setLong(1, bean.getId());
		ps.executeUpdate();
		conn.commit();
		ps.close();
		
	}catch(Exception e){
		e.printStackTrace();
		log.error("asdfghj");
		conn.rollback();
		throw new ApplicationException("exception : exception in deleting record");
	}finally {
		conn.close();
	}
	
	log.debug("model delete end");
}	
	
	
	
public void update(SubjectBean bean) throws SQLException, ApplicationException, DuplicateRecordException{
	log.debug("Model update Started");
	Connection conn =null;
	
	CourseModel cm = new CourseModel();
	CourseBean cb = cm.findBypk(bean.getCourseid());
	bean.setCoursename(cb.getCname());
	bean.setDescription(cb.getDescription());
	
	
	
	try{
		
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("update st_subject set course_id=? , course_name=? , subject_name=? , description=? , created_by=? , modified_by=? , created_datetime=? , modified_datetime=? where id=?");
		ps.setInt(1, bean.getCourseid());
		ps.setString(2, bean.getCoursename());
		ps.setString(3, bean.getSubjectname());
		ps.setString(4, bean.getDescription());
		ps.setString(5, bean.getCreatedby());
		ps.setString(6, bean.getModifiedby());
		ps.setTimestamp(7, bean.getCreateddatetime());
		ps.setTimestamp(8, bean.getModifieddatetime());
		ps.setLong(9, bean.getId());
		ps.executeUpdate();
		
		conn.commit();
		ps.close();
		
	}catch(Exception e){
		log.error("Database Exception" + e);
		e.printStackTrace();
		conn.rollback();
		throw new ApplicationException("exception : exception in updating subject");
	}finally {
		conn.close();
	}
	
	log.debug("Model update End");
}	
	
	
	
public List search(SubjectBean bean , int pageNo , int pageSize) throws ApplicationException, SQLException{
	log.debug("Model search Begin");
	Connection conn =null;
	List<SubjectBean> list = new ArrayList<SubjectBean>();
	StringBuffer sql = new StringBuffer("select * from st_subject where 1=1");
	
	if(bean.getId()>0){
		sql.append(" and id ="+bean.getId());
	}
	if(bean.getCourseid()>0){
		sql.append(" and course_id ="+bean.getCourseid());
	}
	if(bean.getCoursename()!=null && bean.getCoursename().length()>0){
		sql.append(" and course_name like'"+bean.getCoursename()+"%'");
	}
	
	if(bean.getSubjectname()!=null && bean.getSubjectname().length()>0){
		sql.append(" and subject_name like'"+bean.getSubjectname()+"%'");
	}
	if(bean.getDescription()!=null && bean.getDescription().length()>0){
		sql.append(" and description like'"+bean.getDescription()+"%'");
	}
	
	
	if(pageSize>0){
		pageNo = (pageNo-1)*pageSize;
		sql.append(" limit "+pageNo+" , "+pageSize);
	}
	
	try{
		
		conn= JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
	    ResultSet rs = ps.executeQuery();
	    
	    while(rs.next()){
	    	
	    	bean = new SubjectBean();
	    	
	    	bean.setId(rs.getLong(1));
	    	bean.setCourseid(rs.getInt(2));
	    	bean.setCoursename(rs.getString(3));
	    	bean.setSubjectname(rs.getString(4));
	    	bean.setDescription(rs.getString(5));
	    	bean.setCreatedby(rs.getString(6));
	    	bean.setModifiedby(rs.getString(7));
	    	bean.setCreateddatetime(rs.getTimestamp(8));
	    	bean.setModifieddatetime(rs.getTimestamp(9));
	    
	    	list.add(bean);
	    }
		
		conn.commit();
		ps.close();
		rs.close();	
		
	}catch(Exception e){
		log.error("Database ecxeption " + e);
		e.printStackTrace();
		conn.rollback();
		throw new ApplicationException("exception : exception in search list");
	}
	log.debug("Model search End");
	return list;
	
}



public List list(int pageNo , int pageSize) throws ApplicationException, SQLException{
	log.debug("Model list Begin");
	Connection conn =null;
	SubjectBean bean=null;
	StringBuffer sql = new StringBuffer("select * from st_subject");
	List<SubjectBean> list = new ArrayList<SubjectBean>();
	
	if(pageSize>0){
		pageNo = (pageNo-1)*pageSize;
		sql.append(" limit "+pageNo+" , "+pageSize);
	}
	
	try{
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			bean = new SubjectBean();
			
			bean.setId(rs.getLong(1));
	    	bean.setCourseid(rs.getInt(2));
	    	bean.setCoursename(rs.getString(3));
	    	bean.setSubjectname(rs.getString(4));
	    	bean.setDescription(rs.getString(5));
	    	bean.setCreatedby(rs.getString(6));
	    	bean.setModifiedby(rs.getString(7));
	    	bean.setCreateddatetime(rs.getTimestamp(8));
	    	bean.setModifieddatetime(rs.getTimestamp(9));
	    
	    	list.add(bean);
			
			
		}
		
		conn.commit();
		ps.close();
		rs.close();
	}catch(Exception e){
		log.error("database exception" + e);
		e.printStackTrace();
		conn.rollback();
		throw new ApplicationException("exception : exception in getting list");
	}
	log.debug("Model list end");
	return list;
	}





public List list() throws Exception{
	return list(0,0);
}








}
