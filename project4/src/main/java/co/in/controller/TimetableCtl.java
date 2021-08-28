package co.in.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.BaseBean;
import co.in.bean.TimeTableBean;
import co.in.model.CollegeModel;
import co.in.model.CourseModel;
import co.in.model.SubjectModel;
import co.in.model.TimeTableModel;
import co.in.util.DataUtility;
import co.in.util.DataValidator;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;

/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "TimetableCtl", urlPatterns = { "/ctl/TimetableCtl" })
public class TimetableCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TimetableCtl.class);
	protected void preload(HttpServletRequest request){
		
		CourseModel cm = new CourseModel();
		
	
		try {
			List list1 = cm.list();
			request.setAttribute("courselist", list1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		SubjectModel sm = new SubjectModel();
		
		
		try {
		List list2 = sm.list();
		request.setAttribute("subjectlist", list2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("courseid"))){
			request.setAttribute("coursename", PropertyReader.getvalue("error.require", "Course Name"));
		pass = false;
		}
		if(DataValidator.isNull(request.getParameter("subjectid"))){
			request.setAttribute("subjectname", PropertyReader.getvalue("error.require", "Subject Name"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("sem"))){
			request.setAttribute("sem",PropertyReader.getvalue("error.require", "Semester"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("examtime"))){
			request.setAttribute("examtime", PropertyReader.getvalue("error.require", "Examtime"));
		pass = false;
		}
		if(DataValidator.isNull(request.getParameter("examdate"))){
			request.setAttribute("examdate", PropertyReader.getvalue("error.require", "Examdate"));
			pass = false;
		}
		log.debug("validate end");
		return pass;
		
	}
	
	
	
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		TimeTableBean bean = new TimeTableBean();
		
		bean.setId(DataUtility.getInt(request.getParameter("id")));
		bean.setCourseid(DataUtility.getInt(request.getParameter("courseid")));
		bean.setCoursename(DataUtility.getString(request.getParameter("coursename")));
		bean.setSubjectid(DataUtility.getInt(request.getParameter("subjectid")));
		bean.setSubjectname(DataUtility.getString(request.getParameter("subjectname")));
		bean.setSemester(DataUtility.getString(request.getParameter("sem")));
		bean.setExamtime(DataUtility.getString(request.getParameter("examtime")));
		bean.setExamdate(DataUtility.getDate(request.getParameter("examdate")));
		
		populateDTO(bean, request);
		
		return bean;
		
	}
	
	
	
	
	
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		long id = DataUtility.getLong(request.getParameter("id"));
		
		TimeTableModel model = new TimeTableModel();
		TimeTableBean bean;
		if(id>0){
			try {
				bean = model.findbypk(id);
				ServletUtility.setBean(bean, request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		log.debug("doget end");
		ServletUtility.forward(getView(), request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));
		
		TimeTableModel model = new TimeTableModel();
		
		if(OP_SAVE.equalsIgnoreCase(op)){
			TimeTableBean bean = (TimeTableBean) populateBean(request);
			 try {
				id = model.add(bean);
				
				ServletUtility.setSuccessMessage("Timetable Added Successfully", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServletUtility.setErrorMessage("Timetable Already Exist", request);
			    
			}
			 ServletUtility.setBean(bean, request);
			ServletUtility.forward(getView(), request, response);
		}
		
		else if(OP_UPDATE.equalsIgnoreCase(op)){
			TimeTableBean bean2 = (TimeTableBean) populateBean(request);
			
			if(id>0){
				
				try {
					model.update(bean2);
					ServletUtility.setSuccessMessage("Timetable Updated Successfully", request);
				} catch (ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DuplicateRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
			}
			ServletUtility.setBean(bean2, request);
			ServletUtility.forward(getView(), request, response);
		}
		else if(OP_RESET.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
		return;
		}
		else if(OP_CANCEL.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		}
		
		
		log.debug("dopost end");
		
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIMETABLE_VIEW;
	}

}
