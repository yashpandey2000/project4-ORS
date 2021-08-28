package co.in.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.Exception.DatabaseException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.CollegeBean;
import co.in.util.JDBCDataSource;


/**
 * @author Yash Pandey
 *
 */
public class CollegeModel {
	
	private static Logger log = Logger.getLogger(CollegeModel.class);

	public Integer nextPK() throws DatabaseException{
		log.debug("Model nextPK start");
	Connection conn = null;
	int pk=0;
	try{
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("select max(id) from st_college");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			pk=rs.getInt(1);
		}
		
		rs.close();
	}catch(Exception e){
		e.printStackTrace();
		log.error("Database Exception..", e);
		throw new DatabaseException ("Exception: Exception in getting PK");
	}finally{
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model nextPK End");
	return pk = pk+1;	
	}
	
	
	
	
	public long add(CollegeBean bean) throws ApplicationException,DuplicateRecordException {
			
		log.debug("Model add Started");
		Connection conn=null;
			int pk=0;
		
			CollegeBean duplicateCollegeName=findByName(bean.getName());
			
			if(duplicateCollegeName!=null){
				throw new DuplicateRecordException("College Name already exist");
			}	
		
		
		try{
			pk=nextPK();
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_college values(?,?,?,?,?,?,?,?,?,?)");
			
			ps.setInt(1, pk);
			ps.setString(2, bean.getName());
			ps.setString(3,bean.getAddress());
			ps.setString(4,bean.getState());
			ps.setString(5, bean.getCity());
			ps.setString(6, bean.getPhoneno());
			ps.setString(7, bean.getCreatedby());
			ps.setString(8, bean.getModifiedby());
			ps.setTimestamp(9, bean.getCreateddatetime());
			ps.setTimestamp(10, bean.getModifieddatetime());
			
			ps.executeUpdate();
			conn.commit();
			System.out.println("added");
			ps.close();
		}
		catch(Exception e){
			log.error("Database Exception.."+e);
		try{
			conn.rollback();
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		throw new ApplicationException("Exception: add rollback ecxeption"+e.getMessage());			
		
	}
		finally{
		JDBCDataSource.closeConnection(conn);
		
	}
		
		log.debug("Model add End");
		return pk;		
		
		}

	

	public CollegeBean findByName(String name) throws ApplicationException {
	
		log.debug("Model findByName Begin");
		
		StringBuffer sql = new StringBuffer("select * from st_college where name=?");
		CollegeBean bean= null;
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1,name);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
	
			 bean = new CollegeBean();
		
			 bean.setId(rs.getLong(1));
			 bean.setName(rs.getString(2));
			 bean.setAddress(rs.getString(3));			
			 bean.setState(rs.getString(4));
			 bean.setCity(rs.getString(5));			
			 bean.setPhoneno(rs.getString(6));			
			 bean.setCreatedby(rs.getString(7));			
			 bean.setModifiedby(rs.getString(8));
			 bean.setCreateddatetime(rs.getTimestamp(9));
			 bean.setModifieddatetime(rs.getTimestamp(10));	
			
				
			}
			System.out.println("inserted");
	     rs.close();				
		}catch(Exception e){
			e.printStackTrace();
			log.error("DataBase Exception" + e.getMessage());
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByName End");
		return bean;
	}


	
	
	public CollegeBean findByPK(long pk) throws ApplicationException{		
		
		 log.debug("Model findByPK Started");
		
		CollegeBean bean=null;
		StringBuffer sql = new StringBuffer("select * from st_college where id=?");
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1,pk);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				
				bean = new CollegeBean();
	
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
	            bean.setPhoneno(rs.getString(6));
				bean.setCreatedby(rs.getString(7));
				bean.setModifiedby(rs.getString(8));
				bean.setCreateddatetime(rs.getTimestamp(9));
				bean.setModifieddatetime(rs.getTimestamp(10));
				
				
				System.out.println("getting");	
				
			}
			
		rs.close();
		ps.close();
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Database Exception..", e);
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		 log.debug("Model findByPK End");
		return bean;
		
	}
	
	
	

	public void update(CollegeBean bean)throws DuplicateRecordException, ApplicationException, SQLException{
		log.debug("Model update Started");
		
		
		
			Connection conn=null;
			
			try{
				
				conn=JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement ps = conn.prepareStatement("update st_college set name=? , address=? , state=? , city=? , phone_no=? ,created_by=? , modified_by=? , created_datetime=? , modified_datetime=? where id=?");
			     ps.setString(1, bean.getName());
			     ps.setString(2, bean.getAddress());
			     ps.setString(3, bean.getState());
			     ps.setString(4, bean.getCity());
			     ps.setString(5, bean.getPhoneno());
			     ps.setString(6, bean.getCreatedby());
			     ps.setString(7, bean.getModifiedby());
			     ps.setTimestamp(8, bean.getCreateddatetime());
			     ps.setTimestamp(9, bean.getModifieddatetime());
			     ps.setLong(10, bean.getId());
			     
			     ps.executeUpdate();
			     conn.commit();
			     System.out.println("updated");
			     ps.close();
			
			}   
			
			
			catch(Exception ex){
				conn.rollback();
				ex.printStackTrace();
				throw new ApplicationException("Exception in updatting college"+ex.getMessage());
			}
			
		//	throw new ApplicationException("Exception in updating college");	
	       	finally{
			JDBCDataSource.closeConnection(conn);
		}
		
			log.debug("Model update end");
	}
	
	
	
	
	
	public void delete(CollegeBean bean) throws SQLException, ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		
		try{
			
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_college where id=?");
			ps.setLong(1,bean.getId());
			
			ps.executeUpdate();
			conn.commit();
			System.out.println("record deleted");	
			
		}catch(Exception e){
			
			e.printStackTrace();
			conn.rollback();
			log.error("Database Exception..", e);
			throw new ApplicationException("exception :Exception in deleting record"+e.getMessage());	
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete end");
	}
	
	

	
public List search(CollegeBean bean,int pageNo,int pageSize) throws ApplicationException{
		
		
		log.debug("Model search Begin");
		
		StringBuffer sql = new StringBuffer
				("select * from st_college where 1=1");
		
		if(bean!=null){
			if(bean.getId()>0){
			sql.append(" And id "+bean.getId());
			}
			if(bean.getName()!=null && bean.getName().length()>0){
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if(bean.getAddress()!=null && bean.getAddress().length()>0){
				sql.append(" And Address like '"+bean.getAddress()+"%'");
			}
			if(bean.getCity()!=null && bean.getCity().length()>0){
				sql.append(" And city like '"+bean.getCity()+"%'");
			}
			if(bean.getState()!=null && bean.getState().length()>0){
				sql.append(" And state like '"+bean.getState()+"%'");
			}
			if(bean.getPhoneno()!=null && bean.getPhoneno().length()>0){
				sql.append(" And phone_no like "+bean.getPhoneno());
			}
			
		}
		
		if(pageSize>0){
			pageNo= (pageNo-1)*pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}
		ArrayList<CollegeBean> list = new ArrayList<CollegeBean>();
		Connection conn= null;
		try{
			
			conn= JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		    ResultSet rs=pstmt.executeQuery();
		   // CollegeBean bean1;
		    while(rs.next()){
		    	bean= new CollegeBean();
		    	bean.setId(rs.getLong(1));
		    	bean.setName(rs.getString(2));
		    	bean.setAddress(rs.getString(3));
		    	bean.setCity(rs.getString(4));
		    	bean.setState(rs.getString(5));
		    	bean.setPhoneno(rs.getString(6));
		    	bean.setCreatedby(rs.getString(7));
		    	bean.setModifiedby(rs.getString(8));
		    	bean.setCreateddatetime(rs.getTimestamp(9));
		    	bean.setCreateddatetime(rs.getTimestamp(10));
		    	list.add(bean);
		    }
		    pstmt.close();
		}
		catch(Exception e){
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException
			    ("Exception : Exception in search college");
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model search End");
		return list;
	}	
	
	
	
	
public 	List list(int pageNo,int pageSize){
	log.debug("model list begin");
	List<CollegeBean> list = new ArrayList<CollegeBean>();
	StringBuffer sql = new StringBuffer("select * from st_college");
	
	Connection conn=null;
	CollegeBean bean = null;
	
	if(pageSize>0){
		pageNo = (pageNo-1)*pageSize;
		
	}
	
	try{
		conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			bean = new CollegeBean();
		bean.setId(rs.getLong(1));
		bean.setName(rs.getString(2));
		bean.setAddress(rs.getString(3));
		bean.setState(rs.getString(4));
		bean.setCity(rs.getString(5));
		bean.setPhoneno(rs.getString(6));
		bean.setCreatedby(rs.getString(7));
		bean.setModifiedby(rs.getString(8));
		bean.setCreateddatetime(rs.getTimestamp(9));
		bean.setModifieddatetime(rs.getTimestamp(10));
		list.add(bean);		
		}
		}
	
	catch(Exception e){
		e.printStackTrace();
		
	}finally{
		JDBCDataSource.closeConnection(conn);
	}
	
	log.debug("Model list End");
	return list;
}


public List list()throws ApplicationException{
	return list(0,0);
}	
	
	
	
	
	}
