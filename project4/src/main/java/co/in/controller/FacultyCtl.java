package co.in.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mysql.fabric.xmlrpc.base.Data;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.BaseBean;
import co.in.bean.FacultyBean;
import co.in.model.CollegeModel;
import co.in.model.CourseModel;
import co.in.model.FacultyModel;
import co.in.model.SubjectModel;
import co.in.util.DataUtility;
import co.in.util.DataValidator;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;


/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "FacultyCtl", urlPatterns = { "/ctl/FacultyCtl" })
public class FacultyCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(FacultyCtl.class);
	
	protected void preload(HttpServletRequest request){
		
		CollegeModel model = new CollegeModel();
		
	 try {
	  List list1 = model.list();
	  request.setAttribute("collegelist", list1);
	} catch (ApplicationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	
	 CourseModel model1 = new CourseModel();
	 
	 try {
		List list2 = model1.list();
		request.setAttribute("courselist", list2);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	 SubjectModel model2 = new SubjectModel();
	
	 try {
		List list3 = model2.list();
		request.setAttribute("subjectlist", list3);
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 	
		
	}
	
	
protected boolean validate(HttpServletRequest request){
	log.debug("validate start");
	boolean pass = true;
	
	if(DataValidator.isNull(request.getParameter("fname"))){
		request.setAttribute("fname", PropertyReader.getvalue( "error.require" , "First Name"));
	pass = false;
	}
	else if(!DataValidator.isName(request.getParameter("fname"))){
		request.setAttribute("lname", "Invalid First Name");
		pass= false;
	}
	if(DataValidator.isNull(request.getParameter("lname"))){
		request.setAttribute("lname", PropertyReader.getvalue("error.require", "Last Name"));
		pass = false;
	}
	else if(!DataValidator.isName(request.getParameter("lname"))){
		request.setAttribute("lname", "Invalid Last Name");
	  pass = false;	
	}
	if(DataValidator.isNull(request.getParameter("loginid"))){
		request.setAttribute("loginid", PropertyReader.getvalue("error.require", "Emailid"));
		pass = false;
	}
	else if(!DataValidator.isEmail(request.getParameter("loginid"))){
		request.setAttribute("loginid", "Invalid Emailid");
		pass = false;
	}
	
	if(DataValidator.isNull(request.getParameter("doj"))){
		request.setAttribute("doj", PropertyReader.getvalue("error.require", "Date Of Joining"));
		pass = false;
	}
	if(DataValidator.isNull(request.getParameter("mobile"))){
		request.setAttribute("mobile", PropertyReader.getvalue("error.require", "Mobile"));
		pass = false;
	}
	else if(!DataValidator.isMobileNo(request.getParameter("mobile"))){
		request.setAttribute("mobile", "Invalid Mobile No");
		pass = false;
	}
	if(DataValidator.isNull(request.getParameter("qual"))){
		request.setAttribute("qual", PropertyReader.getvalue("error.require", "Qualification"));
		pass = false;
	}
	if(DataValidator.isNull(request.getParameter("collegeid"))){
		request.setAttribute("collegename", PropertyReader.getvalue("error.require", "College Name"));
		pass = false;
	}
	if(DataValidator.isNull(request.getParameter("courseid"))){
		request.setAttribute("coursename", PropertyReader.getvalue("error.require", "Course Name"));
		pass = false;
	}
	if(DataValidator.isNull(request.getParameter("subjectid"))){
		request.setAttribute("subjectname", PropertyReader.getvalue("error.require", "Subject Name"));
	pass = false;
	}if(DataValidator.isNull(request.getParameter("gender"))){
		request.setAttribute("gender", PropertyReader.getvalue("error.require", "Gender"));
		pass = false;
	}

	 log.debug("validate end");
	return pass;
	
}
	
   
    protected BaseBean populateBean(HttpServletRequest request){
    	
    	FacultyBean bean = new FacultyBean();
    	
    	bean.setId(DataUtility.getLong(request.getParameter("id")));
    	bean.setFirstname(DataUtility.getString(request.getParameter("fname")));
    	bean.setLastname(DataUtility.getString(request.getParameter("lname")));
    	bean.setGender(DataUtility.getString(request.getParameter("gender")));
    	bean.setLoginid(DataUtility.getString(request.getParameter("loginid")));
    	bean.setDateofjoining(DataUtility.getDate(request.getParameter("doj")));
    	bean.setQualification(DataUtility.getString(request.getParameter("qual")));
    	bean.setMobileno(DataUtility.getString(request.getParameter("mobile")));
    	
    	bean.setCollegeid(DataUtility.getInt(request.getParameter("collegeid")));
    	//System.out.println(request.getParameter("collegeid 111111111111111111111"));
    	bean.setCourseid(DataUtility.getInt(request.getParameter("courseid")));
    	//System.out.println(request.getParameter("courseid111111111111111111111111111111111"));
    	bean.setSubjectid(DataUtility.getInt(request.getParameter("subjectid")));
    	//System.out.println(request.getParameter("subjectid111111111111111111111111111111111111111111"));
    	
    	populateDTO(bean, request);
    	
		return bean;
		
    
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
	long id = DataUtility.getLong(request.getParameter("id"));	
	String op = DataUtility.getString(request.getParameter("operation"));
	
	FacultyModel model = new FacultyModel();
	FacultyBean bean = null;
	if(id>0){
		
		try {
			bean = model.findBypk(id);
			ServletUtility.setBean(bean, request);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	ServletUtility.forward(getView(), request, response);
	log.debug("doget end");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));
		
		FacultyModel model = new FacultyModel();
		FacultyBean bean = null;
		
		if(OP_SAVE.equalsIgnoreCase(op)){
			
			bean = (FacultyBean) populateBean(request);
			
			try {
				model.add(bean);
				
				ServletUtility.setSuccessMessage("Faculty Added Successfully", request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
				
				ServletUtility.setErrorMessage("Faculty already exist", request);
				
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.forward(getView(), request, response);
		}
		
		
		else if(OP_UPDATE.equalsIgnoreCase(op)){
			
			FacultyBean bean1 = (FacultyBean) populateBean(request);
			
			if(id>0){
			try {
				
				model.update(bean1);
				
				ServletUtility.setSuccessMessage("Faculty Updated Successfully", request);
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(DuplicateRecordException e){
				
			}
			}
			ServletUtility.setBean(bean1, request);
			ServletUtility.forward(getView(), request, response);
			
			
		}else if(OP_RESET.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		}else if(OP_CANCEL.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		}
		
		
		
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FACULTY_VIEW;
	}

}
