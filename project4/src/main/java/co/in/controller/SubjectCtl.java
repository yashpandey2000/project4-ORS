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
import co.in.bean.SubjectBean;
import co.in.model.CourseModel;
import co.in.model.SubjectModel;
import co.in.util.DataUtility;
import co.in.util.DataValidator;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;


/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "SubjectCtl", urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SubjectCtl.class);
   
	protected void preload(HttpServletRequest request){
		
		CourseModel model = new CourseModel();
		
		List list;
		
		try {
		list = 	model.list();
			request.setAttribute("courselist", list);
			System.out.println(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	protected boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("courseid"))){
			request.setAttribute("courseid", PropertyReader.getvalue("error.require", "Course Name"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("subjectname"))){
			request.setAttribute("subjectname", PropertyReader.getvalue("error.require", "Subject Name"));
			pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("subjectname"))){
			request.setAttribute("subjectname", "Invalid Subject Name");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("desc"))){
			request.setAttribute("desc", PropertyReader.getvalue("error.require", "Description"));
			pass = false;
		}
	
		else if(! DataValidator.isName(request.getParameter("desc"))){
			request.setAttribute("desc", "Invalid Description");
			pass = false;
		}
		log.debug("validate end");
		return pass;
		
		}
	
	
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		SubjectBean bean = new SubjectBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCourseid(DataUtility.getInt(request.getParameter("courseid")));
		bean.setSubjectname(DataUtility.getString(request.getParameter("subjectname")));
		bean.setDescription(DataUtility.getString(request.getParameter("desc")));
		
		
		populateDTO(bean, request);
			
		return bean;
		
	}
	
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));
		
		SubjectModel model = new SubjectModel();
		
		SubjectBean bean;
		if(id>0){
			
			try {
				bean = model.findBypk(id);
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
		System.out.println(id+"--------------------");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		SubjectModel model = new SubjectModel();
		
		if(OP_SAVE.equalsIgnoreCase(op)){
			
			SubjectBean bean = (SubjectBean) populateBean(request);
			
			
			try {
				model.add(bean);
				bean.setId(id);
				
				ServletUtility.setSuccessMessage("Subject Added Successfully", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.setErrorMessage("Subject Name Already Exist", request);
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.forward(getView(), request, response);
			
		}
		else if(OP_UPDATE.equalsIgnoreCase(op)){
			
			SubjectBean bean1 = (SubjectBean) populateBean(request);
			
			       try {
					model.update(bean1);
					ServletUtility.setSuccessMessage("Subject Updated Successfully", request);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DuplicateRecordException e) {
					
					e.printStackTrace();
					
				}
			ServletUtility.setBean(bean1, request);
			ServletUtility.forward(getView(), request, response);
			
		}
		else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		}
		
		else if(OP_CANCEL.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		}
		
		log.debug("dopost end");
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUBJECT_VIEW;
	}

}
