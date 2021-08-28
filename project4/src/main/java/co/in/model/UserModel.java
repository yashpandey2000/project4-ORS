package co.in.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.Exception.RecordNotFoundException;
import co.in.bean.UserBean;
import co.in.util.EmailBuilder;
import co.in.util.EmailMessage;
import co.in.util.EmailUtility;
import co.in.util.JDBCDataSource;

/**
 * @author Yash Pandey
 *
 */
public class UserModel {
	
	private static Logger log = Logger.getLogger(UserModel.class);
	
	
	public int nextPK()throws ApplicationException{
		log.debug("Model nextpk started");
		int pk=0;
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_user");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				pk = rs.getInt(1);
			}
		
			rs.close();
		}catch(Exception e){
			log.error("Database Exception..", e);
			e.printStackTrace();
		throw new ApplicationException("exception:Exception in getting pk"+e.getMessage());
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextpk ended");
		return pk=pk+1;	
	}

		
		
	
	
	public long add(UserBean bean) throws DuplicateRecordException, ApplicationException, SQLException{
		log.debug("Model add started");
		int pk=0;
		Connection conn=null;
		
		UserBean beanexist = findByLogin(bean.getLoginid());
		
		if(beanexist !=null){
			throw new DuplicateRecordException("already exist");	
		} 
		
		try{

			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("insert into st_user values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		    
			ps.setInt(1, pk);
			ps.setString(2, bean.getFirstname());
			ps.setString(3, bean.getLastname());
			ps.setString(4, bean.getLoginid());
			ps.setString(5, bean.getPassword());
			//ps.setString(6, bean.getConfirmpassword());
			//ps.setString(7, bean.getAddress());
			ps.setDate(6,new java.sql.Date(bean.getDob().getTime()));
			ps.setString(7, bean.getMobileno());
			ps.setLong(8, bean.getRoleid());
		//	ps.setString(11, bean.getRollname());
			ps.setInt(9, bean.getUnsuccessfullogin());
			ps.setString(10, bean.getGender());
			ps.setTimestamp(11, bean.getLastlogin());
			ps.setString(12, bean.getLock());
			ps.setString(13, bean.getRegisteredip());
			ps.setString(14, bean.getLastloginip());
			ps.setString(15, bean.getCreatedby());
			ps.setString(16, bean.getModifiedby());
			ps.setTimestamp(17, bean.getCreateddatetime());
			ps.setTimestamp(18, bean.getModifieddatetime());
			ps.executeUpdate();
			conn.commit();
			System.out.println("record inserted");
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			throw new ApplicationException("exception : exception in add user"+e.getMessage());
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}	
		log.debug("Model end add");	
		return pk;	
	}
	
	
			
	public UserBean findByLogin(String loginid) throws ApplicationException {
		log.debug("Model findByLogin Begin");
		Connection conn = null;
		UserBean bean =null;
		
		StringBuffer sql = new StringBuffer("select * from st_user where login_id=?");
		
		try{
		conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setString(1, loginid);
		ResultSet rs =ps.executeQuery();
		while(rs.next()){
			System.out.println("rs");
			bean = new UserBean();
			bean.setId(rs.getLong(1));
			bean.setFirstname(rs.getString(2));
			bean.setLastname(rs.getString(3));
			bean.setLoginid(rs.getString(4));
			bean.setPassword(rs.getString(5));
			//bean.setConfirmpassword(rs.getString(6));
			//bean.setAddress(rs.getString(7));
			bean.setDob(rs.getDate(6));
			bean.setMobileno(rs.getString(7));
			bean.setRoleid(rs.getLong(8));
		//	bean.setRollname(rs.getString(11));
			bean.setUnsuccessfullogin(rs.getInt(9));
			bean.setGender(rs.getString(10));
			bean.setLastlogin(rs.getTimestamp(11));
			bean.setLock(rs.getString(12));
			bean.setRegisteredip(rs.getString(13));
			bean.setLastloginip(rs.getString(14));
		    bean.setCreatedby(rs.getString(15));
		    bean.setModifiedby(rs.getString(16));
		    bean.setCreateddatetime(rs.getTimestamp(17));
		    bean.setModifieddatetime(rs.getTimestamp(18));
			
		}
			
		rs.close();	
			
		}catch(Exception e){
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("exception: exception in getting user by login"+e.getMessage());
		}
		finally{
			JDBCDataSource.closeConnection(conn);
			
		}
		log.debug("Model findByLogin End");
		return bean;
	}

	
	
	public UserBean findByPk(long pk) throws ApplicationException{
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("select * from st_user where id=?");
		Connection conn = null;
		UserBean bean =null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1,pk);
			ResultSet rs =ps.executeQuery();
			while(rs.next()){
			bean = new UserBean();
			bean.setId(rs.getLong(1));
			bean.setFirstname(rs.getString(2));
			bean.setLastname(rs.getString(3));
			bean.setLoginid(rs.getString(4));
			bean.setPassword(rs.getString(5));
			//bean.setConfirmpassword(rs.getString(6));
			//bean.setAddress(rs.getString(7));
			bean.setDob(rs.getDate(6));
			bean.setMobileno(rs.getString(7));
			bean.setRoleid(rs.getLong(8));
				//bean.setRollname(rs.getString(11));
				bean.setUnsuccessfullogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastlogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredip(rs.getString(13));
				bean.setLastloginip(rs.getString(14));
				bean.setCreatedby(rs.getString(15));
				bean.setModifiedby(rs.getString(16));
				bean.setCreateddatetime(rs.getTimestamp(17));
				bean.setModifieddatetime(rs.getTimestamp(18));
			}
			
	rs.close();
		}catch(Exception e){
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting pk"+e.getMessage());
		}
		
		finally {
		JDBCDataSource.closeConnection(conn);	
		}
		log.debug("Model findByPK End");
		return bean;
		
	}
	
	
	
	public void delete(UserBean bean) throws SQLException, ApplicationException{
		
		Connection conn =null;
		
		try{
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_user where id=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			System.out.println("record deleted");
			ps.close();	
		}catch(Exception e){
			e.printStackTrace();
			//conn.rollback();
			throw new ApplicationException("exception:exception in deleting record");
		}
		finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	 
	
	
	
	public void update(UserBean bean) throws SQLException, ApplicationException, DuplicateRecordException{
		log.debug("model update started");
		Connection conn =null;
	
		
		try{
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("update st_user set first_name=? , last_name=? ,login_id=?,password=?,dob=?,mobile_no=?,role_id=?, unsuccessful_login=?,gender=?,last_login=?,user_lock=?,registered_ip=?,last_login_ip=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=? ");
			ps.setString(1, bean.getFirstname());
			ps.setString(2,bean.getLastname());
			ps.setString(3, bean.getLoginid());
			ps.setString(4, bean.getPassword());
		//	ps.setString(5, bean.getConfirmpassword());
			//ps.setString(6, bean.getAddress());
			ps.setDate(5, new Date (bean.getDob().getTime()));
			ps.setString(6, bean.getMobileno());
			ps.setLong(7, bean.getRoleid());
			//ps.setString(10, bean.getRollname());
			ps.setInt(8, bean.getUnsuccessfullogin());
			ps.setString(9,bean.getGender() );
			ps.setTimestamp(10, bean.getLastlogin());
			ps.setString(11, bean.getLock());
			ps.setString(12, bean.getRegisteredip());
			ps.setString(13, bean.getLastloginip());
			ps.setString(14, bean.getCreatedby());
			ps.setString(15, bean.getModifiedby());
			ps.setTimestamp(16,bean.getCreateddatetime());
			ps.setTimestamp(17, bean.getModifieddatetime());
			ps.setLong(18, bean.getId());
			ps.executeUpdate();

			conn.commit();	
			System.out.println("record updated");
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
			conn.rollback();
			throw new ApplicationException("exception : exception in updating record");
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("Model update end");
	}
	
	
	
	
	public List search(UserBean bean , int pageNo ,int pageSize) throws ApplicationException{
		log.debug("Model search Started"); 
		StringBuffer sql = new StringBuffer("select * from st_user where 1=1");
		
		if(bean!=null){
			if (bean.getId() > 0) {
				sql.append(" And ID = " + bean.getId());
			}
			if (bean.getFirstname() != null && bean.getFirstname().length() > 0) {
				sql.append(" And first_name like '" + bean.getFirstname()+"%'");
			}
			if (bean.getLastname() != null && bean.getLastname().length() > 0) {
				sql.append(" and last_name like '" + bean.getLastname() + "%'");
			}
			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + bean.getDob());
			}
			if (bean.getMobileno() != null && bean.getMobileno().length() > 0) {
				sql.append(" AND MOBILE_NO = " + bean.getMobileno());
			}
			if (bean.getRoleid() > 0) {
				sql.append(" AND ROLE_ID = " + bean.getRoleid());
			}
			
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + bean.getGender() + "%'");
			}
			
			if (bean.getLoginid() != null && bean.getLoginid().length() > 0) {
				sql.append(" and login_id like '" + bean.getLoginid() + "%'");
			}
			
			
		
}		
		
if(pageSize>0){
	pageNo =(pageNo-1)*pageSize;
	sql.append(" limit "+pageNo+" , "+pageSize);
}		
		
		
List<UserBean> list = new ArrayList();		
	Connection conn=null;	
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstname(rs.getString(2));
				bean.setLastname(rs.getString(3));
				bean.setLoginid(rs.getString(4));
				bean.setPassword(rs.getString(5));
			//	bean.setConfirmpassword(rs.getString(6));
			//	bean.setAddress(rs.getString(7));
				bean.setDob(rs.getDate(6));
				bean.setMobileno(rs.getString(7));
				bean.setRoleid(rs.getLong(8));
			//	bean.setRollname(rs.getString(11));
				bean.setUnsuccessfullogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastlogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredip(rs.getString(13));
				bean.setLastloginip(rs.getString(14));
				bean.setCreatedby(rs.getString(15));
				bean.setModifiedby(rs.getString(16));
				bean.setCreateddatetime(rs.getTimestamp(17));
				bean.setModifieddatetime(rs.getTimestamp(18));
				
				list.add(bean);
			}
			
			rs.close();	
		
		}catch(Exception e){
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("exception:exception in search user"+e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model search End");
		return list;	
	}
	
	
	
		
	

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("model list started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_USER where 1=1");
		// if page size is greater than zero then apply pagination
		
		
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit "+pageNo+","+pageSize);
		}
		
		
		Connection conn = null;
		UserBean bean=null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				 bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstname(rs.getString(2));
				bean.setLastname(rs.getString(3));
				bean.setLoginid(rs.getString(4));
				bean.setPassword(rs.getString(5));
				//bean.setConfirmpassword(rs.getString(6));
				//bean.setAddress(rs.getString(7));
				bean.setDob(rs.getDate(6));
				bean.setMobileno(rs.getString(7));
				bean.setRoleid(rs.getLong(8));
				//bean.setRollname(rs.getString(11));
				bean.setUnsuccessfullogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastlogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredip(rs.getString(13));
				bean.setLastloginip(rs.getString(14));
				bean.setCreatedby(rs.getString(15));
				bean.setModifiedby(rs.getString(16));
				bean.setCreateddatetime(rs.getTimestamp(17));
				bean.setModifieddatetime(rs.getTimestamp(18));
				
				list.add(bean);
				System.out.println("outside rs");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model list end");
		return list;
	}
	
	
	
	public UserBean authenticate(String login,String password) throws ApplicationException{
		log.debug("Model authenticate begin");
	UserBean bean =null;
	Connection conn=null;
	StringBuffer sql = new StringBuffer("select * from st_user where login_id=? and password=?");
		
	try{
		conn=JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setString(1, login);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			bean = new UserBean();
			bean.setId(rs.getLong(1));
			bean.setFirstname(rs.getString(2));
			bean.setLastname(rs.getString(3));
			bean.setLoginid(rs.getString(4));
			bean.setPassword(rs.getString(5));
			//bean.setConfirmpassword(rs.getString(6));
			//bean.setAddress(rs.getString(7));
			bean.setDob(rs.getDate(6));
			bean.setMobileno(rs.getString(7));
			bean.setRoleid(rs.getLong(8));
			//bean.setRollname(rs.getString(11));
			bean.setUnsuccessfullogin(rs.getInt(9));
			bean.setGender(rs.getString(10));
			bean.setLastlogin(rs.getTimestamp(11));
			bean.setLock(rs.getString(12));
			bean.setRegisteredip(rs.getString(13));
			bean.setLastloginip(rs.getString(14));
			bean.setCreatedby(rs.getString(15));
			bean.setModifiedby(rs.getString(16));
			bean.setCreateddatetime(rs.getTimestamp(17));
			bean.setModifieddatetime(rs.getTimestamp(18));
		}
		
	rs.close();		
		
	}catch(Exception e){
		log.error("Database Exception.." + e.getMessage());
		e.printStackTrace();
		throw new ApplicationException( "exception:exception in authenticate user"+e.getMessage());	
	}	
		
	finally {
		JDBCDataSource.closeConnection(conn);
	}	
	log.debug("Model authenticate end");
		return bean;	
	}
	
	
	
	
	
	public boolean lock(String login) throws RecordNotFoundException, SQLException, ApplicationException{
		
		boolean flag=false;
		UserBean beanexist =null;
		try{
			beanexist = findByLogin(login);
			
			if(beanexist!=null){
				beanexist.setLock(UserBean.active);
				update(beanexist);
				flag=true;
			}else{
				throw new RecordNotFoundException("loginid not exist");
			}
			
		}catch(DuplicateRecordException e){
			throw new ApplicationException("database exception");
		}
		return flag;	
	}
	
	
	
	
	public List getRoles(UserBean bean) throws ApplicationException{
		log.debug("Model roles started");
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		StringBuffer sql = new StringBuffer("select * from st_user where role_id=?");
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, bean.getRoleid());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
			bean=new UserBean();
			
			bean.setId(rs.getInt(1));
			bean.setFirstname(rs.getString(2));
			bean.setLastname(rs.getString(3));
			bean.setLoginid(rs.getString(4));
			//System.out.println(bean.getLoginid()+"-------------");
			bean.setPassword(rs.getString(5));
			//bean.setConfirmpassword(rs.getString(6));
			//bean.setAddress(rs.getString(7));
			bean.setDob(rs.getDate(6));
			bean.setMobileno(rs.getString(7));
			bean.setRoleid(rs.getLong(8));
			//bean.setRollname(rs.getString(11));
			bean.setUnsuccessfullogin(rs.getInt(9));
			bean.setGender(rs.getString(10));
			bean.setLastlogin(rs.getTimestamp(11));
			bean.setLock(rs.getString(12));
			bean.setRegisteredip(rs.getString(13));
			bean.setLastloginip(rs.getString(14));
			bean.setCreatedby(rs.getString(15));
			bean.setModifiedby(rs.getString(16));
			bean.setCreateddatetime(rs.getTimestamp(17));
			bean.setModifieddatetime(rs.getTimestamp(18));
				
				list.add(bean);		
			}	
			rs.close();
		}catch(Exception e){
			log.error("Database Exception..", e);
			e.printStackTrace();
			throw new ApplicationException("Exception:exception in getting role");
		}
		log.debug("Model Role end");
			return list;	
		
	}
	
	
	
	
	 public boolean changePassword(Long id, String oldPassword,String newPassword) throws RecordNotFoundException,ApplicationException, SQLException {

        log.debug("model changePassword Started");
	        boolean flag = false;
	        UserBean beanExist = null;

	        beanExist = findByPk(id);
	        if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {
	            beanExist.setPassword(newPassword);
	            try {
	                update(beanExist);
	            } catch (DuplicateRecordException e) {
                log.error(e);
	                throw new ApplicationException("LoginId is already exist");
	            }
	            
	            flag = true;
	            
	        	} else {
	            throw new RecordNotFoundException("Loginid not exist");
	        	}

	        HashMap<String, String> map = new HashMap<String, String>();

	        map.put("login", beanExist.getLoginid());
	        map.put("password", beanExist.getPassword());
	        map.put("firstName", beanExist.getFirstname());
	        map.put("lastName", beanExist.getLastname());

	        String message = EmailBuilder.getChangePasswordMessage(map);

	        EmailMessage msg = new EmailMessage();

	        msg.setTo(beanExist.getLoginid());
	        msg.setSubject("Rays Ors Password has been changed Successfully.");
	        msg.setMessage(message);
	        msg.setMessageType(EmailMessage.HTML_MSG);

	        EmailUtility.sendMail(msg);

	        log.debug("Model changePassword End");
	        return flag;

	    }



public long registerUser(UserBean bean) throws DuplicateRecordException, ApplicationException, SQLException{
	log.debug("model registeruser started");
	long pk = add(bean);
	HashMap<String, String> map = new HashMap<String, String>();
	
	map.put("login", bean.getLoginid());
	map.put("password",bean.getPassword());
	
	String message = EmailBuilder.getUserRegistrationMessage(map);
	
	EmailMessage msg = new EmailMessage();
	
	msg.setTo(bean.getLoginid());
	msg.setSubject("registration is successful for ORS project");
	msg.setMessage(message);
	msg.setMessageType(EmailMessage.HTML_MSG);
	
	
	EmailUtility.sendMail(msg);
	
	log.debug("model registeruser ended");
	return pk ;
	
	
}
	
	
//public boolean resetPassword(UserBean bean) throws ApplicationException, SQLException{
//	boolean flag = false;
//	
//	String newPassword = String.valueOf(new java.util.Date().getTime()).substring(9,13);
//	UserBean userData = findByPk(bean.getId());
//	
//	userData.setPassword(newPassword);
//	try{
//		update(userData);
//	}
//	catch(DuplicateRecordException e){
//		e.printStackTrace();
//		
//	}
//	
//	flag =true;
//	HashMap<String, String> map = new HashMap<String, String>();
//	
//	map.put("login", bean.getLoginid());
//	map.put("password", bean.getPassword());
//	map.put("firstname",bean.getFirstname());
//	map.put("lastname", bean.getLastname());
//	
//	String message = EmailBuilder.getForgetPasswordMessage(map);
//	
//	EmailMessage msg = new EmailMessage();
//	
//	msg.setTo(bean.getLoginid());
//    msg.setSubject("password has been reset");
//    msg.setMessage(message);
//    msg.setMessageType(EmailMessage.HTML_MSG);
//    
//    EmailUtility.sendMail(msg);
//    
//    return flag;
//		
//}


public boolean forgetpassword(String login) throws ApplicationException, RecordNotFoundException{
	
	
	log.debug("model forgetpassword started");
	boolean flag =false;
	UserBean userData = findByLogin(login);
	
	if(userData == null){
	
		throw new RecordNotFoundException("email does not exist");
	}
	
	flag = true;
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("login" , userData.getLoginid());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFirstname());
		map.put("lastName", userData.getLastname());
		
		String message = EmailBuilder.getForgetPasswordMessage(map);
		
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("ors Forget password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		
		EmailUtility.sendMail(msg);
		
		log.debug("model forgetpassword ended");
	return flag;	
	
}

	

public List list() throws Exception{
	return list(0,0);
}	
	
	
}
