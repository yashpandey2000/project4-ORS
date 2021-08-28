package co.in.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.CollegeBean;
import co.in.bean.StudentBean;
import co.in.util.JDBCDataSource;

/**
 * @author Yash Pandey
 *
 */
public class Studentmodel {
	
    Logger log = Logger.getLogger(Studentmodel.class);

	
	public Integer nextpk() throws ApplicationException{
		log.debug("Model nextPK Started");
		
		int pk =0;
		Connection conn = null;
		try{
			conn =JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_student");
			ResultSet rs= ps.executeQuery();
			while(rs.next()){
				
				pk = rs.getInt(1);
				
			}
			
			conn.commit();
			ps.close();
			rs.close();
		}catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
			throw new ApplicationException("Exception : exception in getting pk");
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk = pk+1;
		}
	
	
	
	public long add(StudentBean bean) throws DuplicateRecordException, ApplicationException{
		log.debug("Model add Started");
		Connection conn= null;
		
		CollegeModel cm = new CollegeModel();
		CollegeBean cb = cm.findByPK(bean.getCollegeid());
		
		bean.setCollegename(cb.getName());
		
		StudentBean duplicatename = findByEmail(bean.getEmail());
		
		
		if(duplicatename!=null && duplicatename.getEmail()!=null){
			
			throw new DuplicateRecordException("record already exist");
		}
		int pk =0;
		
		try{
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		pk = nextpk();
		PreparedStatement ps = conn.prepareStatement("insert into st_student values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
		ps.setLong(1, pk);	
		ps.setLong(2, bean.getCollegeid());
		ps.setString(3, bean.getCollegename());
		ps.setString(4, bean.getFirstname());
		ps.setString(5, bean.getLastname());
		ps.setDate(6, new java.sql.Date(bean.getDob().getTime()));
		ps.setString(7, bean.getMobileno());
		ps.setString(8, bean.getAddress());
		ps.setString(9, bean.getEmail());
		ps.setString(10, bean.getCreatedby());
	    ps.setString(11, bean.getModifiedby());
	    ps.setTimestamp(12, bean.getCreateddatetime());
		ps.setTimestamp(13, bean.getModifieddatetime());
		
		ps.executeUpdate();
		conn.commit();
		ps.close();
	
		}catch(Exception e){
			
			log.error("Database Exception..", e);
			try{
				conn.rollback();
			}
			catch(Exception e1){
			e1.printStackTrace();
			
			}
			e.printStackTrace();
			throw new ApplicationException( "Exception : exception in adding student");
			}
		
		finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("Model add end");
	
		return pk ;
		
	}



	public StudentBean findByEmail(String email) throws ApplicationException {
	log.debug("Model findBy Email Begin");
	
	StudentBean bean = new StudentBean();
	
	StringBuffer sql = new StringBuffer("select * from st_student where email=?");
	Connection conn=null;
	try{
		conn=JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setString(1, email);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
		
			bean.setId(rs.getLong(1));
			bean.setCollegeid(rs.getLong(2));
			bean.setCollegename(rs.getString(3));
			bean.setFirstname(rs.getString(4));
			bean.setLastname(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileno(rs.getString(7));
			bean.setAddress(rs.getString(8));
			bean.setEmail(rs.getString(9));
			bean.setCreatedby(rs.getString(10));
			bean.setModifiedby(rs.getString(11));
			bean.setCreateddatetime(rs.getTimestamp(12));
			bean.setModifieddatetime(rs.getTimestamp(12));
		}
		rs.close();
		ps.close();
	}catch(Exception e){
		
		throw new ApplicationException("Exception : exception in getting user by email");
	}
		
	finally{
		JDBCDataSource.closeConnection(conn);
	}	
	log.debug("Model findBy Email End");	
	return bean;
	}
	
	
	public void delete (StudentBean bean) throws ApplicationException{
		log.debug("model delete begin");
		Connection conn =null;
		try{
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_student where id=?");
			ps.setLong(1,bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();	
			
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			throw new ApplicationException("Exception : exception in deleting record");
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}	
		log.debug("model delete end");
	}
	
	
	
	public StudentBean findBypk(long pk) throws ApplicationException{
		log.debug("Model findByPK Started");
		
		StringBuffer sql = new StringBuffer("select * from st_student where id=?");
		StudentBean bean =null;
		Connection conn=null;
		
		try{
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeid(rs.getLong(2));
				bean.setCollegename(rs.getString(3));
				bean.setFirstname(rs.getString(4));
				bean.setLastname(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileno(rs.getString(7));
				bean.setAddress(rs.getString(8));
				bean.setEmail(rs.getString(9));
				bean.setCreatedby(rs.getString(10));
				bean.setModifiedby(rs.getString(11));
				bean.setCreateddatetime(rs.getTimestamp(12));
				bean.setModifieddatetime(rs.getTimestamp(13));
			}
			
			rs.close();
			ps.close();
				
		}catch(Exception e){
			 log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("exception : exception in getting student by pk");
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
		}
	
	
	
	public void update(StudentBean bean) throws ApplicationException, DuplicateRecordException{
		log.debug("Model update Started");
		
		Connection conn=null;
		CollegeModel cm = new CollegeModel();
		CollegeBean cb = cm.findByPK(bean.getCollegeid());
		bean.setCollegename(cb.getName());
		

        
		
		try{
			conn= JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("update st_student set college_id=? , college_name=? , first_name=? , last_name=? , dob=? , mobile_no=?, address=? , email=? , created_by=? , modified_by=? , created_datetime=? , modified_datetime=? where id=?");
			ps.setLong(1, bean.getCollegeid());
			ps.setString(2, bean.getCollegename());
			ps.setString(3, bean.getFirstname());
			ps.setString(4, bean.getLastname());
			ps.setDate(5,new java.sql.Date(bean.getDob().getTime()));
			ps.setString(6, bean.getMobileno());
			ps.setString(7, bean.getAddress());
			ps.setString(8, bean.getEmail());
			ps.setString(9, bean.getCreatedby());
			ps.setString(10, bean.getModifiedby());
			ps.setTimestamp(11, bean.getCreateddatetime());
			ps.setTimestamp(12,bean.getModifieddatetime());
			ps.setLong(13, bean.getId());
			ps.executeUpdate();
			conn.commit();
			
			ps.close();
		}catch(Exception e){
			log.error("Database Exception" + e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in upating record");
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
		
	}
	
	
	public List search(StudentBean bean , int pageNo , int pageSize) throws ApplicationException{
		log.debug("Model search Begin");
		Connection conn =null;
		StringBuffer sql = new StringBuffer("select * from st_student where 1=1");
		if (bean != null) {
		
		if (bean.getEmail() != null && bean.getEmail().length() > 0) {
			sql.append(" and email like '" + bean.getEmail() + "%'");
		}
		if (bean.getFirstname() != null && bean.getFirstname().length() > 0) {
			sql.append(" AND FIRST_NAME like '" + bean.getFirstname() + "%'");
		}
		if (bean.getLastname() != null && bean.getLastname().length() > 0) {
			sql.append(" AND LAST_NAME like '" + bean.getLastname() + "%'");
		}
		
		}
		if(pageSize > 0){
			pageNo = (pageNo -1)*pageSize;
			sql.append(" Limit "+ pageNo +" , "+pageSize);
		}
		
		List<StudentBean> list = new ArrayList<StudentBean>();
		try{
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeid(rs.getLong(2));
				bean.setCollegename(rs.getString(3));
				bean.setFirstname(rs.getString(4));
				bean.setLastname(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileno(rs.getString(7));
				bean.setAddress(rs.getString(8));
				bean.setEmail(rs.getString(9));
				bean.setCreatedby(rs.getString(10));
				bean.setModifiedby(rs.getString(11));
				bean.setCreateddatetime(rs.getTimestamp(12));
				bean.setModifieddatetime(rs.getTimestamp(13));
				list.add(bean);
					
				
			}
		
		}catch(Exception e){
			log.error("Database ecxeption " + e);
			e.printStackTrace();
			throw new ApplicationException("Exception : exception in searching student ");
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model search End");	
		return list;
		
	}
	
	
	

	public List list(int pageNo , int pageSize) throws ApplicationException{
		log.debug("Model list Begin");
		Connection conn = null;
		List<StudentBean> list = new ArrayList();
		
		StringBuffer sql = new StringBuffer("select * from st_student");
		
		if(pageSize > 0){
			pageNo = (pageNo -1)*pageSize;
			sql.append(" limit " + pageNo +","+ pageSize);
			
		}
		
		try{
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				StudentBean bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeid(rs.getLong(2));
				bean.setCollegename(rs.getString(3));
				bean.setFirstname(rs.getString(4));
				bean.setLastname(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileno(rs.getString(7));
				bean.setAddress(rs.getString(8));
				bean.setEmail(rs.getString(9));
				bean.setCreatedby(rs.getString(10));
				bean.setModifiedby(rs.getString(11));
				bean.setCreateddatetime(rs.getTimestamp(12));
				bean.setModifieddatetime(rs.getTimestamp(13));
				list.add(bean);
						
			}
			rs.close();
			ps.close();	
			
		}catch(Exception e){
			log.error("database exception" + e);
			e.printStackTrace();
			
			throw new ApplicationException("Exception : exception in getting list of student ");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("Model list end");
		return list;
		}
	
	
	
	
	public List list() throws Exception{
		return list(0,0);
	}
	
	
	
	
}
