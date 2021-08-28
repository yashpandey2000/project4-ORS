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
import co.in.bean.CollegeBean;
import co.in.bean.CourseBean;
import co.in.bean.FacultyBean;
import co.in.bean.SubjectBean;
import co.in.util.JDBCDataSource;

/**
 * @author Yash Pandey
 *
 */



public class FacultyModel {
	
	
	private static Logger log = Logger.getLogger(FacultyModel.class);
		

	public Integer nextpk() throws ApplicationException, SQLException {
		log.debug("Model nextpk start");
		int pk = 0;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("select max(id) from st_faculty");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			conn.commit();
			ps.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("exception : exception in rollback" + e.getMessage());
		} finally {
			conn.close();
		}
		log.debug("Model nextpk end");
		return pk = pk + 1;
	}

	public long add(FacultyBean bean) throws SQLException, ApplicationException, DuplicateRecordException {
		log.debug("Model add start");
		int pk = 0;
		Connection conn = null;

		CollegeModel cm = new CollegeModel();
		CollegeBean cb = cm.findByPK(bean.getCollegeid());
		System.out.println("name of college"+ cb.getName());
		bean.setCollegename(cb.getName());

		CourseModel coursemodel = new CourseModel();
		CourseBean coursebean = coursemodel.findBypk(bean.getCourseid());
		System.out.println("name of course" + coursebean.getCname());
		bean.setCoursename(coursebean.getCname());

		SubjectModel sm = new SubjectModel();
		SubjectBean sb = sm.findBypk(bean.getSubjectid());
		System.out.println("name of subject "+ sb.getSubjectname());
		bean.setSubjectname(sb.getSubjectname());

		FacultyBean beanexist = findByEmail(bean.getLoginid());

		if (beanexist != null) {
			throw new DuplicateRecordException("record already exist");
		}
		try {
			pk = nextpk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("insert into st_faculty values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, bean.getFirstname());
			ps.setString(3, bean.getLastname());
			ps.setString(4, bean.getGender());
			ps.setString(5, bean.getLoginid());
			ps.setString(6, bean.getAddress());
			
			ps.setDate(7, new java.sql.Date(bean.getDateofjoining().getTime()));
			ps.setString(8, bean.getQualification());
			
			ps.setString(9, bean.getMobileno());
			ps.setInt(10, bean.getCollegeid());
			ps.setString(11, bean.getCollegename());
			ps.setInt(12, bean.getCourseid());
			ps.setString(13, bean.getCoursename());
			ps.setInt(14, bean.getSubjectid());
			ps.setString(15, bean.getSubjectname());
			ps.setString(16, bean.getCreatedby());
			ps.setString(17, bean.getModifiedby());
			ps.setTimestamp(18, bean.getCreateddatetime());
			ps.setTimestamp(19, bean.getModifieddatetime());

			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			throw new ApplicationException("exception : exception in adding faculty");
		} finally {
			conn.close();
		}
		log.debug("Model add end");
		return pk;
	}

	public void update(FacultyBean bean) throws SQLException, ApplicationException, DuplicateRecordException {
		log.debug("Model update start");
		Connection conn = null;

		CollegeModel cm = new CollegeModel();
		CollegeBean cb = cm.findByPK(bean.getCollegeid());
		System.out.println(cb.getName());
		bean.setCollegename(cb.getName());

		CourseModel cm1 = new CourseModel();
		CourseBean cb1 = cm1.findBypk(bean.getCourseid());
		System.out.println(cb1.getCname());
		bean.setCoursename(cb1.getCname());

		SubjectModel sm = new SubjectModel();
		SubjectBean sb = sm.findBypk(bean.getSubjectid());
		System.out.println(sb.getSubjectname());
		bean.setSubjectname(sb.getSubjectname());
		
		

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"update st_faculty set first_name=? , last_name=? , gender=? , login_id=? ,address=? , date_of_joining=? , qualification=? ,mobile_no=? , college_id=? , college_name=? ,course_id=? , course_name=? , subject_id=? , subject_name=? ,created_by=? , modified_by=? , created_datetime=? , modified_datetime=? where id=?");
			ps.setString(1, bean.getFirstname());
			ps.setString(2, bean.getLastname());
			ps.setString(3, bean.getGender());
			ps.setString(4, bean.getLoginid());
			ps.setString(5, bean.getAddress());
			ps.setDate(6, new java.sql.Date(bean.getDateofjoining().getTime()));
			ps.setString(7, bean.getQualification());
			ps.setString(8, bean.getMobileno());
			ps.setInt(9, bean.getCollegeid());
			ps.setString(10, bean.getCollegename());
			ps.setInt(11, bean.getCourseid());
			ps.setString(12, bean.getCoursename());
			ps.setInt(13, bean.getSubjectid());
			ps.setString(14, bean.getSubjectname());
			ps.setString(15, bean.getCreatedby());
			ps.setString(16, bean.getModifiedby());
			ps.setTimestamp(17, bean.getCreateddatetime());
			ps.setTimestamp(18, bean.getModifieddatetime());
			ps.setLong(19, bean.getId());

			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			throw new ApplicationException("exception : exception in update faculty");
		} finally {
			conn.close();
		}

		log.debug("Model update end");
	}

	public void delete(FacultyBean bean) throws SQLException {
		log.debug("Model delete start");
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("delete from st_faculty where id=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {

			e.printStackTrace();
			conn.rollback();

		} finally {
			conn.close();
		}
		log.debug("Model delete end");
	}

	public FacultyBean findBypk(long pk) throws ApplicationException, SQLException {
		log.debug("Model findBypk start");
		Connection conn = null;
		FacultyBean bean = null;
		StringBuffer sql = new StringBuffer("select * from st_faculty where id=?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			conn.setAutoCommit(false);
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				bean = new FacultyBean();

				bean.setId(rs.getLong(1));
				bean.setFirstname(rs.getString(2));
				bean.setLastname(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setLoginid(rs.getString(5));
				bean.setAddress(rs.getString(6));
				bean.setDateofjoining(rs.getDate(7));
				bean.setQualification(rs.getString(8));
				bean.setMobileno(rs.getString(9));
				bean.setCollegeid(rs.getInt(10));
				bean.setCollegename(rs.getString(11));
				bean.setCourseid(rs.getInt(12));
				bean.setCoursename(rs.getString(13));
				bean.setSubjectid(rs.getInt(14));
				bean.setSubjectname(rs.getString(15));
				bean.setCreatedby(rs.getString(16));
				bean.setModifiedby(rs.getString(17));
				bean.setCreateddatetime(rs.getTimestamp(18));
				bean.setModifieddatetime(rs.getTimestamp(19));

			}
			conn.commit();

			ps.close();
			rs.close();

		} catch (Exception e) {

			e.printStackTrace();
			conn.rollback();
			throw new ApplicationException("exception : exception in getting faculty by pk");
		} finally {
			conn.close();
		}
		log.debug("Model findBypk end");
		return bean;

	}

	public FacultyBean findByEmail(String loginid) throws SQLException {
		log.debug("Model findByEmail start");
		Connection conn = null;
		FacultyBean bean = null;
		StringBuffer sql = new StringBuffer("select * from st_faculty where login_id=?");
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, loginid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstname(rs.getString(2));
				bean.setLastname(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setLoginid(rs.getString(5));
				bean.setAddress(rs.getString(6));
				bean.setDateofjoining(rs.getDate(7));
				bean.setQualification(rs.getString(8));
				bean.setMobileno(rs.getString(9));
				bean.setCollegeid(rs.getInt(10));
				bean.setCollegename(rs.getString(11));
				bean.setCourseid(rs.getInt(12));
				bean.setCoursename(rs.getString(13));
				bean.setSubjectid(rs.getInt(14));
				bean.setSubjectname(rs.getString(15));
				bean.setCreatedby(rs.getString(16));
				bean.setModifiedby(rs.getString(17));
				bean.setCreateddatetime(rs.getTimestamp(18));
				bean.setModifieddatetime(rs.getTimestamp(19));

			}

			ps.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		log.debug("Model findByEmail end");
		return bean;
	}

	public List search(FacultyBean bean, int pageNo, int pageSize) throws SQLException {
		log.debug("Model search start");
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");

		if (bean.getId() > 0) {
			sql.append(" and id =" + bean.getId());
		}
		if (bean.getFirstname() != null && bean.getFirstname().length() > 0) {
			sql.append(" and first_name like '" + bean.getFirstname() + "%'");
		}
		if (bean.getLastname() != null && bean.getLastname().length() > 0) {
			sql.append(" and last_name like '" + bean.getLastname() + "%'");
		}
		if (bean.getGender() != null && bean.getGender().length() > 0) {
			sql.append(" and gender like '" + bean.getGender() + "%'");
		}
		if (bean.getLoginid() != null && bean.getLoginid().length() > 0) {
			sql.append(" and login_id like '" + bean.getLoginid() + "%'");
		}
		if (bean.getDateofjoining() != null && bean.getDateofjoining().getDate() > 0) {
			sql.append(" and date_of_joining =" + bean.getDateofjoining());
		}
		if (bean.getQualification() != null && bean.getQualification().length() > 0) {
			sql.append(" and qualification like '" + bean.getQualification() + "%'");
		}
		if (bean.getMobileno() != null && bean.getMobileno().length() > 0) {
			sql.append(" and mobile_no like '" + bean.getMobileno() + "%'");
		}
		if (bean.getCollegeid() > 0) {
			sql.append(" and college_id = '" + bean.getCollegeid() + "%'");
		}
		if (bean.getCollegename() != null && bean.getCollegename().length() > 0) {
			sql.append(" and college_name like '" + bean.getCollegename() + "%'");
		}
		if (bean.getCourseid() > 0) {
			sql.append(" and course_id = '" + bean.getCourseid() + "%'");
		}
		if (bean.getCoursename() != null && bean.getCoursename().length() > 0) {
			sql.append(" and course_name like '" + bean.getCoursename() + "%'");
		}
		if (bean.getSubjectid() > 0) {
			sql.append(" and subject_id = '" + bean.getSubjectid() + "%'");
		}
		if (bean.getSubjectname() != null && bean.getSubjectname().length() > 0) {
			sql.append(" and subject_name like '" + bean.getSubjectname() + "%'");
		}

		List<FacultyBean> list = new ArrayList<FacultyBean>();

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();

				bean.setId(rs.getLong(1));
				bean.setFirstname(rs.getString(2));
				bean.setLastname(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setLoginid(rs.getString(5));
				bean.setAddress(rs.getString(6));
				bean.setDateofjoining(rs.getDate(7));
				bean.setQualification(rs.getString(8));
				bean.setMobileno(rs.getString(9));
				bean.setCollegeid(rs.getInt(10));
				bean.setCollegename(rs.getString(11));
				bean.setCourseid(rs.getInt(12));
				bean.setCoursename(rs.getString(13));
				bean.setSubjectid(rs.getInt(14));
				bean.setSubjectname(rs.getString(15));
				bean.setCreatedby(rs.getString(16));
				bean.setModifiedby(rs.getString(17));
				bean.setCreateddatetime(rs.getTimestamp(18));
				bean.setModifieddatetime(rs.getTimestamp(19));

				list.add(bean);

			}
			conn.commit();
			rs.close();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();

		}
		log.debug("Model search end");
		return list;
	}

	public List list(int pageNo, int pageSize) throws SQLException {
		log.debug("Model list start");
		Connection conn = null;
		FacultyBean bean = null;
		StringBuffer sql = new StringBuffer("select * from st_faculty");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		List<FacultyBean> list = new ArrayList<FacultyBean>();
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				bean = new FacultyBean();

				bean.setId(rs.getLong(1));
				bean.setFirstname(rs.getString(2));
				bean.setLastname(rs.getString(3));
				bean.setGender(rs.getString(4));
				bean.setLoginid(rs.getString(5));
				bean.setAddress(rs.getString(6));
				bean.setDateofjoining(rs.getDate(7));
				bean.setQualification(rs.getString(8));
				bean.setMobileno(rs.getString(9));
				bean.setCollegeid(rs.getInt(10));
				bean.setCollegename(rs.getString(11));
				bean.setCourseid(rs.getInt(12));
				bean.setCoursename(rs.getString(13));
				bean.setSubjectid(rs.getInt(14));
				bean.setSubjectname(rs.getString(15));
				bean.setCreatedby(rs.getString(16));
				bean.setModifiedby(rs.getString(17));
				bean.setCreateddatetime(rs.getTimestamp(18));
				bean.setModifieddatetime(rs.getTimestamp(19));

				list.add(bean);
			}
			conn.commit();
			ps.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}

		log.debug("Model list end");
		return list;

	}

	public List list() throws Exception {
		return list(0, 0);
	}

}
