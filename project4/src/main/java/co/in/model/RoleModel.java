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
import co.in.bean.RoleBean;
import co.in.util.JDBCDataSource;

/**
 * @author Yash Pandey
 *
 */
public class RoleModel {
	
	
	Logger log = Logger.getLogger(RoleModel.class);

public int nextPK() throws ApplicationException{
	log.debug("Model nextPK Begin");
		Connection conn=null;
		int pk=0;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_role");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				pk = rs.getInt(1);
			}
			rs.close();
		}catch(Exception e){
			log.error("Database Exception" + e);
			e.printStackTrace();
			throw new ApplicationException("Exception:Exception in getting pk");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk = pk+1;	
	}
	
	
public RoleBean findByName(String name) throws ApplicationException{
	log.debug("Model findByName Started");
	RoleBean bean = new RoleBean();
	
	StringBuffer sql = new StringBuffer("select * from st_role where name=?");
	Connection conn = null;
	try{
		
		conn = JDBCDataSource.getConnection();
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ps.setString(1,name);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			
			
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			//System.out.println(bean.getName()+"name is");
			bean.setDescription(rs.getString(3));
			bean.setCreatedby(rs.getString(4));
			bean.setModifiedby(rs.getString(5));
			bean.setCreateddatetime(rs.getTimestamp(6));
			bean.setModifieddatetime(rs.getTimestamp(7));
		}
		
	}catch(Exception e){
		log.error("Database Exception..", e);
	e.printStackTrace();	
	throw new ApplicationException("exception:exception in getting user by name");
	}finally{
		
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model findByName Started");
	return bean;
		
}


public long add(RoleBean bean) throws ApplicationException,DuplicateRecordException, SQLException{
	log.debug("Model add Begin");
	Connection conn=null;
	RoleBean duplicatebean = findByName(bean.getName());
	if(duplicatebean.getName()!=null){
		
	throw new DuplicateRecordException("already exist");	
	}
	int pk=0;
	try{
	conn = JDBCDataSource.getConnection();	
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
		
		pk= nextPK();
		ps.setInt(1, pk);
		ps.setString(2,bean.getName());
		ps.setString(3,bean.getDescription());
		ps.setString(4, bean.getCreatedby());
		ps.setString(5, bean.getModifiedby());
		ps.setTimestamp(6,  bean.getCreateddatetime());
		ps.setTimestamp(7, bean.getModifieddatetime());
		
		ps.executeUpdate();
		System.out.println("inserted");
		conn.commit();
		ps.close();
	}catch(Exception e){
		log.error("Database Exception" + e);
		e.printStackTrace();
		conn.rollback();
		throw new ApplicationException("exception: Exception in add role");
	}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
	log.debug("Model add End");
	return pk;	
}



public RoleBean findByPk(long pk) throws SQLException, ApplicationException{
	log.error("Model findByPK Started");
	StringBuffer sql = new StringBuffer("select * from st_role where id=?");
	RoleBean bean=null;
	Connection conn=null;
	try{
		
		conn=JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setLong(1, pk);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			bean = new RoleBean();
			
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setCreatedby(rs.getString(4));
			bean.setModifiedby(rs.getString(5));
			bean.setCreateddatetime(rs.getTimestamp(6));
			bean.setModifieddatetime(rs.getTimestamp(7));
		}
		
		rs.close();
		
	}catch(Exception e){
		log.error("Databade exception" + e);
		e.printStackTrace();
		conn.rollback();
		throw new ApplicationException("exception:exception in getting pk"+e.getMessage());		
	}finally{
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model findByPK end");
	return bean;
	
}



public void delete(RoleBean bean) throws SQLException, ApplicationException{
	log.debug("Model delete Started");
	Connection conn=null;
	
	try{
		
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("delete from st_role where id=?");
		ps.setLong(1,bean.getId());
		ps.executeUpdate();
		conn.commit();
		System.out.println("record deleted");
		ps.close();
	    }catch(Exception e){
	    	log.error("Database exception" + e);
		e.printStackTrace();
		conn.rollback();
		throw new ApplicationException("exception:exception in delete role"+e.getMessage());
		
	   }finally{
		 JDBCDataSource.closeConnection(conn); 
	   }
	log.debug("Model delete end");
		
}




public void update(RoleBean bean) throws SQLException, ApplicationException, DuplicateRecordException{
	log.debug("Model update Started");
	Connection conn=null;
	
	

	try{
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("update st_role set name=? , description=? , created_by=? , modified_by=? , created_datetime=? , modified_datetime=? where id=?");
		ps.setString(1,bean.getName());
		ps.setString(2, bean.getDescription());
		ps.setString(3, bean.getCreatedby());
		ps.setString(4, bean.getModifiedby());
		ps.setTimestamp(5, bean.getCreateddatetime());
		ps.setTimestamp(6, bean.getModifieddatetime());
		ps.setLong(7, bean.getId());
		ps.executeUpdate();
		conn.commit();
		System.out.println("updated");
		ps.close();
		
	}catch(Exception e){
		log.error(e);
		e.printStackTrace();
		conn.rollback();
		throw new ApplicationException("exception : exception in updating role"+e.getMessage());
	}
	finally{
		
		JDBCDataSource.closeConnection(conn);
		
	}	
	
	log.debug("Model update end");
}



public List search(int pageNo , RoleBean bean , int pageSize) throws ApplicationException{
	log.debug("Model search begin");

	 StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE 1=1");

     if (bean != null) {
         if (bean.getId() > 0) {
             sql.append(" AND id = " + bean.getId());
         }
         if (bean.getName() != null && bean.getName().length() > 0) {
             sql.append(" AND NAME like '" + bean.getName() + "%'");
         }
         if (bean.getDescription() != null
                 && bean.getDescription().length() > 0) {
             sql.append(" AND DESCRIPTION like '" + bean.getDescription()
                     + "%'");
         }

     }

     // if page size is greater than zero then apply pagination
     if (pageSize > 0) {
         // Calculate start record index
         pageNo = (pageNo - 1) * pageSize;

         sql.append(" Limit " + pageNo + ", " + pageSize);
         // sql.append(" limit " + pageNo + "," + pageSize);
     }

     ArrayList list = new ArrayList();
     Connection conn = null;
     try {
         conn = JDBCDataSource.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql.toString());
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
             bean = new RoleBean();
             bean.setId(rs.getLong(1));
             bean.setName(rs.getString(2));
             bean.setDescription(rs.getString(3));
             bean.setCreatedby(rs.getString(4));
             bean.setModifiedby(rs.getString(5));
             bean.setCreateddatetime(rs.getTimestamp(6));
             bean.setModifieddatetime(rs.getTimestamp(7));
             list.add(bean);
         }
         rs.close();
     } catch (Exception e) {
         log.error("Database Exception..", e);
         throw new ApplicationException(
                 "Exception : Exception in search Role");
     } finally {
         JDBCDataSource.closeConnection(conn);
     }

     log.debug("Model search End");
     return list;
 }	


public List list() throws ApplicationException, SQLException {
    return list(0, 0);
}


public List list(int pageSize , int pageNo) throws ApplicationException, SQLException{
	
	log.debug("Model list begin");
	
	List<RoleBean> list = new ArrayList();
	StringBuffer sql = new StringBuffer("select * from st_role");
	Connection conn = null;
	RoleBean bean=null;
	if(pageSize > 0 ){
		
		pageNo = (pageNo -1)*pageSize;
			
	}
	
	
	try{
		conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
		      bean = new RoleBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setCreatedby(rs.getString(4));
			bean.setModifiedby(rs.getString(5));
			
			list.add(bean);
					
		}
		
		
		
		rs.close();
		
	}catch(Exception e){
		log.error(e);
		e.printStackTrace();
		
		throw new ApplicationException("exception :exception in getting list"+e.getMessage());
	}
	finally{
		
		JDBCDataSource.closeConnection(conn);
	}
	
	log.debug("Model list End");
	return list ;	
		
}


	
}
