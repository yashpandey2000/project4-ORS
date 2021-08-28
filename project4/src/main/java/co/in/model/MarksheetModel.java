package co.in.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mchange.util.DuplicateElementException;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.MarksheetBean;
import co.in.bean.StudentBean;
import co.in.util.JDBCDataSource;

/**
 * @author Yash Pandey
 *
 */
public class MarksheetModel {
	
	public static Logger log = Logger.getLogger(MarksheetModel.class);
	
	
public Integer nextpk() throws SQLException{
	log.debug("Model nextpk start");
	
	int pk =0;
	Connection conn =null;
	
	try{
	     conn =JDBCDataSource.getConnection();
	     conn.setAutoCommit(false);
	     PreparedStatement ps = conn.prepareStatement("select max(id) from st_marksheet");
	    ResultSet rs = ps.executeQuery();
	    
	    while(rs.next()){
	    	
	    	pk = rs.getInt(1);	
	    	
	    }
	    conn.commit();
	     ps.close();
	    		
	}catch(Exception e){
		log.error(e);
		e.printStackTrace();
	}
	
	finally {
		conn.close();
	}
	
	log.debug("Model nextpk end");
	return pk = pk+1;	
	
}



public long add(MarksheetBean bean) throws ApplicationException{
	log.debug("Model add Begin");
	Connection conn =null;
	int pk=0;
	
	Studentmodel sm = new Studentmodel();
	StudentBean sb = sm.findBypk(bean.getStudentid());
	bean.setName(sb.getFirstname() + " " + sb.getLastname());
	
	
	MarksheetBean duplicatereord = findByRollNo(bean.getRollno());
	
	if(duplicatereord !=null && duplicatereord.getRollno()!=null){
		
		throw new DuplicateElementException("roll no already exist");
	}
	
	
	try{
		
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("insert into st_marksheet values(?,?,?,?,?,?,?,?,?,?,?)");
		pk=nextpk();
		
		ps.setLong(1, pk);
		ps.setString(2, bean.getRollno());
		ps.setLong(3, bean.getStudentid());
		ps.setString(4, bean.getName());
		ps.setInt(5, bean.getPhysics());
		ps.setInt(6, bean.getChemistry());
		ps.setInt(7, bean.getMaths());
		ps.setString(8, bean.getCreatedby());
		ps.setString(9, bean.getModifiedby());
		ps.setTimestamp(10, bean.getCreateddatetime());
		ps.setTimestamp(11, bean.getModifieddatetime());
		ps.executeUpdate();
		conn.commit();
		
		ps.close();
	}catch(Exception e){
		e.printStackTrace();
		log.error("Database exception " + e);
		throw new ApplicationException("Exception : exception in adding markshhet");
	}
	finally {
		JDBCDataSource.closeConnection(conn);
	}
		
	log.debug("Model add end");
	return pk;
}




public MarksheetBean findByRollNo(String rollno) throws ApplicationException{
	log.debug("Model findByRollNo Begin");
	MarksheetBean bean =  new MarksheetBean();;
	Connection conn = null;
	StringBuffer sql = new StringBuffer("select * from st_marksheet where roll_no=?");
	try{
		conn =JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setString(1, rollno);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			
			
			bean.setId(rs.getLong(1));
			bean.setRollno(rs.getString(2));
			bean.setStudentid(rs.getLong(3));
			bean.setName(rs.getString(4));
			bean.setPhysics(rs.getInt(5));
			bean.setChemistry(rs.getInt(6));
			bean.setMaths(rs.getInt(7));
			bean.setCreatedby(rs.getString(8));
			bean.setModifiedby(rs.getString(9));
			bean.setCreateddatetime(rs.getTimestamp(10));
			bean.setModifieddatetime(rs.getTimestamp(11));
				
		}
		
		ps.close();
		rs.close();
		
	}catch(Exception e){
		log.error("Database Exception" + e);
		throw new ApplicationException("Exception: exception in getting marksheet by roll_no ");
	}
	finally {
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model findByRollNo end");
	return bean;
	}




public void delete(MarksheetBean bean) throws SQLException, ApplicationException{
	log.debug("Model delete begin");
	Connection conn = null;
	try{
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);	
		PreparedStatement ps = conn.prepareStatement("delete from st_marksheet where id=?");
		ps.setLong(1, bean.getId());
		ps.executeUpdate();
		conn.commit();
		ps.close();
			
	}catch(Exception e){
		e.printStackTrace();
		log.error("Database exception" + e);
		conn.rollback();
		throw new ApplicationException("Exception : exception in deleting record");
	}
finally{
	JDBCDataSource.closeConnection(conn);
}	
	log.debug("Model delete End");
}


public void update(MarksheetBean bean) throws ApplicationException, SQLException, DuplicateRecordException{
	log.debug("Model update End");
	Connection conn = null;
	
	Studentmodel sm = new Studentmodel();
	StudentBean sb = sm.findBypk(bean.getStudentid());
	System.out.println(sb.getFirstname());
	System.out.println(sb.getLastname());
	bean.setName(sb.getFirstname() + " " + sb.getLastname()); 
	

	
	try{
		
		conn  = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("update st_marksheet set roll_no=? ,student_id=? , name=? , physics=? , chemistry=? , maths=? , created_by=? , modified_by=? , created_datetime=? , modified_datetime=? where id=?");
		ps.setString(1, bean.getRollno());
		ps.setLong(2, bean.getStudentid());
		ps.setString(3, bean.getName());
		ps.setInt(4, bean.getPhysics());
		ps.setInt(5, bean.getChemistry());
		ps.setInt(6, bean.getMaths());
		ps.setString(7, bean.getCreatedby());
		ps.setString(8, bean.getModifiedby());
		ps.setTimestamp(9,bean.getCreateddatetime());
		ps.setTimestamp(10,bean.getModifieddatetime());
		ps.setLong(11, bean.getId());
		ps.executeUpdate();
		System.out.println("record updated");
		conn.commit();
		ps.close();
		
	}catch(Exception e){
		conn.rollback();
		log.error("Database exc"+e);
		
		e.printStackTrace();
		throw new ApplicationException("Exception : exception in updating record");
	}
	finally{
		JDBCDataSource.closeConnection(conn);
	}
	
	log.debug("Model update End");
}



public MarksheetBean findBypk(long pk) throws ApplicationException{
	
	log.debug("Model findByPK begin");
	
	Connection conn =null;
	MarksheetBean bean =null;
	StringBuffer sql = new StringBuffer("select * from st_marksheet where id=?");
	
	try{
		conn= JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
	    ps.setLong(1,pk);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			
			bean = new MarksheetBean();
			bean.setId(rs.getLong(1));
			bean.setRollno(rs.getString(2));
			bean.setStudentid(rs.getLong(3));
			bean.setName(rs.getString(4));
			bean.setPhysics(rs.getInt(5));
			bean.setChemistry(rs.getInt(6));
			bean.setMaths(rs.getInt(7));
			bean.setCreatedby(rs.getString(8));
			bean.setModifiedby(rs.getString(9));
			bean.setCreateddatetime(rs.getTimestamp(10));
			bean.setModifieddatetime(rs.getTimestamp(11));
			
		}
		
		ps.close();
		rs.close();	
		
	}catch(Exception e){
		e.printStackTrace();
		log.error("Database exception" + e);
		throw new ApplicationException("exception : excpetion in getting marksheet");
	}
	finally{
		JDBCDataSource.closeConnection(conn);	
	}
	log.debug("Model findByPK end");
	return bean;
	}




public List search(MarksheetBean bean , int pageNo , int pageSize) throws ApplicationException{
	
	log.debug("Model  search begin");
	StringBuffer sql = new StringBuffer("select * from st_marksheet where 1=1");
	
	if(bean!=null){
		
		if(bean.getId()>0){
			sql.append(" and id = '"+bean.getId()+"'");
		}
		if(bean.getRollno()!=null && bean.getRollno().length()>0){
			
			sql.append(" and roll_no = '"+bean.getRollno()+"'");
		}
		if(bean.getName()!=null && bean.getName().length()>0){
			
			sql.append(" and name like '"+bean.getName() +"%'");
		}
		if(bean.getPhysics()>0){
			sql.append(" and physics = '"+bean.getPhysics()+"'");
		}
		if(bean.getChemistry()>0){
			sql.append(" and chemistry = '"+bean.getChemistry()+"'");
		}
		
		if(bean.getMaths()>0){
			sql.append(" and maths ='"+bean.getMaths()+"'");
	}	
	}
	
	
	if(pageSize>0){
		pageNo = (pageNo-1)*pageSize;
		sql.append(" limit "+pageNo+","+pageSize);
		
	}
	
	List<MarksheetBean> list = new ArrayList();
	Connection conn= null;
	
	try{
		conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			bean = new MarksheetBean();
			bean.setId(rs.getLong(1));
			bean.setRollno(rs.getString(2));
			bean.setStudentid(rs.getLong(3));
			bean.setName(rs.getString(4));
			bean.setPhysics(rs.getInt(5));
			bean.setChemistry(rs.getInt(6));
			bean.setMaths(rs.getInt(7));
			bean.setCreatedby(rs.getString(8));
			bean.setModifiedby(rs.getString(9));
			bean.setCreateddatetime(rs.getTimestamp(10));
			bean.setModifieddatetime(rs.getTimestamp(11));
			list.add(bean);
		}
		
		ps.close();
		rs.close();
	}catch(Exception e){
		e.printStackTrace();
	
		throw new ApplicationException("Exception : exception in getting list");
	}
	log.debug("Model  search End");
	return list;

}


public List list(int pageNo , int pageSize) throws ApplicationException{
	
	StringBuffer sql = new StringBuffer("select * from st_marksheet");
	if(pageSize>0){
		
		pageNo = (pageNo-1)*pageSize;
		sql.append(" limit "+pageNo+","+pageSize);
		
	}
	
	List<MarksheetBean> list = new ArrayList<MarksheetBean>();
	
	Connection conn =null;
	try{
		
		conn=JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			
			MarksheetBean bean = new MarksheetBean();
			bean.setId(rs.getLong(1));
			bean.setRollno(rs.getString(2));
			bean.setStudentid(rs.getLong(3));
			bean.setName(rs.getString(4));
			bean.setPhysics(rs.getInt(5));
			bean.setChemistry(rs.getInt(6));
			bean.setMaths(rs.getInt(7));
			bean.setCreatedby(rs.getString(8));
			bean.setModifiedby(rs.getString(9));
			bean.setCreateddatetime(rs.getTimestamp(10));
			bean.setModifieddatetime(rs.getTimestamp(11));
			list.add(bean);
		}
		
		ps.close();
		rs.close();
	}catch(Exception e){
		log.error(e);
		e.printStackTrace();
		throw new ApplicationException("Exception : exception in getting list");
	}
	
	
	return list;
	
}


public List getMeritList(int pageNo , int pageSize) throws ApplicationException{
	log.debug("Model  list begin");
	StringBuffer sql = new StringBuffer("select id,roll_no,name,physics,chemistry,maths"+",(physics+chemistry+maths)as total from st_marksheet where not (physics<33 or maths<33 or chemistry<33)order by"+" total desc");
	
	if(pageSize>0){
		
		pageNo = (pageNo-1)*pageSize;
		sql.append(" limit "+pageNo+","+pageSize);
			
	}
	
	Connection conn = null;
	MarksheetBean bean = null;
	List<MarksheetBean> list = new ArrayList<MarksheetBean>();
	try{
		
		conn=JDBCDataSource.getConnection();
		
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
		bean = new MarksheetBean();
		bean.setId(rs.getLong(1));
		bean.setRollno(rs.getString(2));
		bean.setName(rs.getString(3));
		bean.setPhysics(rs.getInt(4));
		bean.setChemistry(rs.getInt(5));
		bean.setMaths(rs.getInt(6));
		list.add(bean);
		}	
		
		
		ps.close();
		
	}catch(Exception e){
		log.error(e.getMessage());
		e.printStackTrace();
		throw new ApplicationException("exception : exception in getting merit list of marksheet");
	}
	finally{
		JDBCDataSource.closeConnection(conn);
		
	}
	log.debug("Model  list End");
	return list;
	}


public List list() throws Exception{
	return list(0,0);
}


}
