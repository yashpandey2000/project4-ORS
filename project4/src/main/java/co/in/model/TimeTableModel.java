package co.in.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.SimpleAttributeSet;

import org.apache.log4j.Logger;

import com.mysql.fabric.xmlrpc.base.Data;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.CourseBean;
import co.in.bean.SubjectBean;
import co.in.bean.TimeTableBean;
import co.in.util.JDBCDataSource;

/**
 * @author Yash Pandey
 *
 */
public class TimeTableModel {
	
	Logger log = Logger.getLogger(TimeTableModel.class);

	
	
	public Integer nextpk() throws Exception{
		log.debug("Model nextPK Begin");
		int pk=0;
		
		Connection conn = null;
		
		try{
			
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_timetable");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				
				pk = rs.getInt(1);
			}
			conn.commit();
			rs.close();
			ps.close();
			
		}catch(Exception e){
			log.error("Database Exception" + e);
			e.getMessage();
		}finally{
			conn.close();
		}	
		
		log.debug("Model nextPK End");
		return pk = pk+1;	
		
	}
	
	
public TimeTableBean findbypk(long pk) throws SQLException{
	log.debug("Model findBypk Started");
	Connection conn = null;
	TimeTableBean bean = null;
	
	StringBuffer sql = new StringBuffer("select * from st_timetable where id=?");
	
	try{
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setLong(1, pk);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			
			bean = new TimeTableBean();
			bean.setId(rs.getLong(1));
			bean.setCourseid(rs.getInt(2));
			bean.setCoursename(rs.getString(3));
			bean.setSubjectid(rs.getInt(4));
			bean.setSubjectname(rs.getString(5));
			bean.setSemester(rs.getString(6));
			bean.setExamtime(rs.getString(7));
			bean.setExamdate(rs.getDate(8));
			bean.setCreatedby(rs.getString(9));
			bean.setModifiedby(rs.getString(10));
			bean.setCreateddatetime(rs.getTimestamp(11));
			bean.setModifieddatetime(rs.getTimestamp(12));
			
		}
		conn.commit();
		rs.close();
		ps.close();
			
	}catch(Exception e){
		e.printStackTrace();
	}
	
	finally{
		conn.close();
	}

	log.debug("Model findBypk end");
	return bean;	
}
	
	


public long add(TimeTableBean bean) throws Exception	{
	log.debug("Model add Started");
	int pk=0;
	Connection conn =null;
		
	CourseModel cm = new CourseModel();
	CourseBean cb = cm.findBypk(bean.getCourseid());
	bean.setCoursename(cb.getCname());
	
	SubjectModel sm = new SubjectModel();
	SubjectBean  sb = sm.findBypk(bean.getSubjectid());
	bean.setSubjectname(sb.getSubjectname());
	
	TimeTableBean bean1 = findByCSS(bean.getCoursename(),bean.getSubjectname(),bean.getSemester());
	TimeTableBean bean2 = findByCSE(bean.getCoursename() ,  bean.getSemester(), new java.sql.Date(bean.getExamdate().getTime()));
	
	if(bean1 !=null || bean2!=null){
		throw new Exception("exam time table already exist");
	}
	
	try{
		
		pk = nextpk();
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("insert into st_timetable values(?,?,?,?,?,?,?,?,?,?,?,?)");
		ps.setLong(1, pk);
		ps.setInt(2, bean.getCourseid());
		ps.setString(3, bean.getCoursename());
		ps.setInt(4, bean.getSubjectid());
		ps.setString(5,bean.getSubjectname());
		ps.setString(6, bean.getSemester());
		ps.setString(7, bean.getExamtime());
		ps.setDate(8, new java.sql.Date(bean.getExamdate().getTime()));
		ps.setString(9, bean.getCreatedby());
		ps.setString(10, bean.getModifiedby());
		ps.setTimestamp(11, bean.getCreateddatetime());
		ps.setTimestamp(12, bean.getModifieddatetime());
		
		ps.executeUpdate();
		conn.commit();
		ps.close();
	
	}catch(Exception e){
		e.printStackTrace();
		conn.rollback();
		throw new ApplicationException("exception : exception in adding timetable");
	}finally{
		conn.close();
	}
	
	log.debug("Model add ended");
	return pk;
	
	
}
	




private TimeTableBean findByCSE(String coursename, String semester, java.util.Date examdate) throws SQLException {
	log.debug("Model findByCSE Started");
	Connection conn =null;
	TimeTableBean bean =null;
	StringBuffer sql = new StringBuffer("select * from st_timetable were course_name=? and semester=? and exam_date=?");
	Date date = new Date(examdate.getTime());
	
	try{
		
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setString(1, coursename);
		ps.setString(2, semester);
		ps.setDate(3, date);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			bean = new TimeTableBean();
			bean.setId(rs.getLong(1));
			bean.setCourseid(rs.getInt(2));
			bean.setCoursename(rs.getString(3));
			bean.setSubjectid(rs.getInt(4));
			bean.setSubjectname(rs.getString(5));
			bean.setSemester(rs.getString(6));
			bean.setExamtime(rs.getString(7));
			bean.setExamdate(rs.getDate(8));
			bean.setCreatedby(rs.getString(9));
			bean.setModifiedby(rs.getString(10));
			bean.setCreateddatetime(rs.getTimestamp(11));
			bean.setModifieddatetime(rs.getTimestamp(12));	
		
	}
	conn.commit();
	rs.close();
	ps.close();
	
	}catch(Exception e){
		e.printStackTrace();
		conn.rollback();
	}finally {
		conn.close();
	}
		
	return bean;
}


public TimeTableBean findByCSS(String coursename, String subjectname, String semester) throws SQLException {
	
	Connection conn=null;
	TimeTableBean bean=null;
	StringBuffer sql = new StringBuffer("select * from st_timetable where course_name=? and subject_name=? and semester=? ");
	try{
		
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ps.setString(1, coursename);
		ps.setString(2, subjectname);
		ps.setString(3, semester);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			bean = new TimeTableBean();
			bean.setId(rs.getLong(1));
			bean.setCourseid(rs.getInt(2));
			bean.setCoursename(rs.getString(3));
			bean.setSubjectid(rs.getInt(4));
			bean.setSubjectname(rs.getString(5));
			bean.setSemester(rs.getString(6));
			bean.setExamtime(rs.getString(7));
			bean.setExamdate(rs.getDate(8));
			bean.setCreatedby(rs.getString(9));
			bean.setModifiedby(rs.getString(10));
			bean.setCreateddatetime(rs.getTimestamp(11));
			bean.setModifieddatetime(rs.getTimestamp(12));
			
		}
		conn.commit();
		ps.close();
		rs.close();
	}catch(Exception e){
		e.printStackTrace();
		conn.rollback();
	}
	log.debug("Model findByCSE ended");
	return bean;
	
}


public void delete(TimeTableBean bean) throws SQLException{
	log.debug("Model delete Started");
	Connection conn = null;
	
	try{
		
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("delete from st_timetable where id=?");
		ps.setLong(1,bean.getId());
		ps.executeUpdate();
		
		conn.commit();
		ps.close();
		
		
		
	}catch(Exception e){
		
		e.printStackTrace();
	}finally{
		conn.close();
	}	
	
	log.debug("Model delete ended");
}




public void update(TimeTableBean bean) throws ApplicationException, SQLException, DuplicateRecordException{
	log.debug("Model update Started");
Connection conn =null;

CourseModel cm = new CourseModel();
CourseBean cb = cm.findBypk(bean.getCourseid());
bean.setCoursename(cb.getCname());

SubjectModel sm = new SubjectModel();
SubjectBean sb = sm.findBypk(bean.getSubjectid());
bean.setSubjectname(sb.getSubjectname());


try{
	
	conn =JDBCDataSource.getConnection();
    conn.setAutoCommit(false);
    PreparedStatement ps = conn.prepareStatement("update st_timetable set course_id=? , course_name=? , subject_id=? , subject_name=? ,semester=? , exam_time=? , exam_date=? , created_by=? , modified_by=? , created_datetime=? , modified_datetime=? where id=?");
	ps.setInt(1, bean.getCourseid());
	ps.setString(2, bean.getCoursename());
	ps.setInt(3, bean.getSubjectid());
	ps.setString(4, bean.getSubjectname());
	ps.setString(5, bean.getSemester());
	ps.setString(6, bean.getExamtime());
	ps.setDate(7, new java.sql.Date(bean.getExamdate().getTime()));
	ps.setString(8, bean.getCreatedby());
	ps.setString(9, bean.getModifiedby());
	ps.setTimestamp(10, bean.getCreateddatetime());
	ps.setTimestamp(11, bean.getModifieddatetime());
	ps.setLong(12, bean.getId());
	ps.executeUpdate();
	conn.commit();
	ps.close();
	
}catch(Exception e){
	e.printStackTrace();
	conn.rollback();
}finally{
	conn.close();
}	
	
log.debug("Model update ended");
}	
	
	
	
public List search(TimeTableBean bean , int pageNo , int pageSize) throws SQLException	{
	
	log.debug("Model search Started");
	Connection conn =null;

	StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1");
	
	if(bean!= null){
		
		if (bean.getCourseid() > 0) {
			sql.append(" And course_id= " + bean.getCourseid());
		}
		if (bean.getSubjectid() > 0) {
			sql.append(" And subject_id= " + bean.getSubjectid());
		}
		if (bean.getExamdate() !=null) {
		//	System.out.println(d);
			sql.append(" AND EXAM_DATE= '" + new java.sql.Date(bean.getExamdate().getTime()) + "'");
		}
		if (bean.getExamtime() != null && bean.getExamtime().length()>0) {
			sql.append(" AND EXAM_TIME= '" + bean.getExamtime() + "'");
		}
		
		
		if(pageSize>0){
			pageNo = (pageNo-1)*pageSize;
			sql.append(" limit "+pageNo+" , "+pageSize);
		}	
		
	}
	
	List list = new ArrayList();
	
	try{
		
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			
			bean = new TimeTableBean();
			bean.setId(rs.getLong(1));
			bean.setCourseid(rs.getInt(2));
			bean.setCoursename(rs.getString(3));
			bean.setSubjectid(rs.getInt(4));
			bean.setSubjectname(rs.getString(5));
			bean.setSemester(rs.getString(6));
			bean.setExamtime(rs.getString(7));
			bean.setExamdate(rs.getDate(8));
			bean.setCreatedby(rs.getString(9));
			bean.setModifiedby(rs.getString(10));
			bean.setCreateddatetime(rs.getTimestamp(11));
			bean.setModifieddatetime(rs.getTimestamp(12));	
		    
			list.add(bean);
		}
		
		conn.commit();
		ps.close();
		rs.close();
		
		
	}catch(Exception e){
		e.printStackTrace();
		conn.rollback();
	}finally {
		conn.close();
	}
	log.debug("Model search ended");
	return list;
	
}
	
	
	
public List list (int pageNo , int pageSize) throws SQLException{
	log.debug("Model list Started");
	List list = new ArrayList();
	
	TimeTableBean bean =null;
	Connection conn= null;
	StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1");
	
	if(pageSize>0){
		pageNo = (pageNo-1)*pageSize ;
		
		sql.append(" limit " + pageNo +" , "+pageSize);
			
	}
	
	try{
		conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			
			bean = new TimeTableBean();
			
			bean.setId(rs.getLong(1));
			bean.setCourseid(rs.getInt(2));
			bean.setCoursename(rs.getString(3));
			bean.setSubjectid(rs.getInt(4));
			bean.setSubjectname(rs.getString(5));
			bean.setSemester(rs.getString(6));
			bean.setExamtime(rs.getString(7));
			bean.setExamdate(rs.getDate(8));
			bean.setCreatedby(rs.getString(9));
			bean.setModifiedby(rs.getString(10));
			bean.setCreateddatetime(rs.getTimestamp(11));
			bean.setModifieddatetime(rs.getTimestamp(12));
			
			list.add(bean);
			
		}
		rs.close();
		ps.close();
			
	}catch(Exception e){
		e.printStackTrace();
	}
	finally{
		
		conn.close();
	}
	
	log.debug("Model list ended");
	return list;
	
}	
	
	
public TimeTableBean findByCourseName(long courseid , java.util.Date examdate) throws SQLException{
	log.debug("Model findByCourseName Started");
	Connection conn =null;
	TimeTableBean bean =null;
	
	Date date = new Date(examdate.getTime());
	try{
		
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement("select * from st_timetable where course_id=? and exam_date=?");
		System.out.println("examdate"+date+"cid"+courseid);
		ps.setLong(1, courseid);
		ps.setDate(2, date);
		long id = nextpk();
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			
			bean = new TimeTableBean();
			bean.setId(rs.getLong(1));
			bean.setCourseid(rs.getInt(2));
			bean.setCoursename(rs.getString(3));
			bean.setSubjectid(rs.getInt(4));
			bean.setSubjectname(rs.getString(5));
			bean.setSemester(rs.getString(6));
			bean.setExamtime(rs.getString(7));
			bean.setExamdate(rs.getDate(8));
			bean.setCreatedby(rs.getString(9));
			bean.setModifiedby(rs.getString(10));
			bean.setCreateddatetime(rs.getTimestamp(11));
			bean.setModifieddatetime(rs.getTimestamp(12));	
			
			
		}
		
		conn.commit();
		ps.close();
		rs.close();
		
		
	}catch(Exception e){
		e.printStackTrace();
		conn.rollback();
	}
	
	log.debug("Model findByCourseName ended");
	return bean;
	}	
	
	
public List list() throws Exception{
	return list(0,0);
}
	
	
	
	

}
