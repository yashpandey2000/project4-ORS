package co.in.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.BaseBean;
import co.in.bean.StudentBean;
import co.in.model.CollegeModel;
import co.in.model.Studentmodel;
import co.in.util.DataUtility;
import co.in.util.DataValidator;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;

/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "StudentCtl", urlPatterns = { "/ctl/StudentCtl" })
public class StudentCtl extends BaseCtl {
private static final long serialVersionUID = 1L;
private static Logger log = Logger.getLogger(StudentCtl.class);
   protected void preload(HttpServletRequest request){
	   
	   CollegeModel model = new CollegeModel();
	   
	try  {
	List list = model.list();
	request.setAttribute("collegelist", list);
	}catch(ApplicationException e){
		
		e.printStackTrace();
	}
	   
   }
	
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		
		StudentBean bean = new StudentBean();
		
		String dob = request.getParameter("dob");
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstname(DataUtility.getString(request.getParameter("firstname")));
		bean.setLastname(DataUtility.getString(request.getParameter("lastname")));
		bean.setDob(DataUtility.getDate(dob));
		bean.setMobileno(DataUtility.getString(request.getParameter("mobile")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setCollegeid(DataUtility.getLong(request.getParameter("college")));
		
		populateDTO(bean, request);
		
		return bean;
		
	}
	
	
	
	protected boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("firstname"))){
			request.setAttribute("firstname",PropertyReader.getvalue("error.require","First Name"));
			pass = false;
		}
		else if(! DataValidator.isName(request.getParameter("firstname"))){
			request.setAttribute("firstname","Invalid First Name");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("lastname"))){
			request.setAttribute("lastname", PropertyReader.getvalue("error.require","Last Name"));
			pass = false;
		}
		
		else if(!DataValidator.isName(request.getParameter("lastname"))){
			request.setAttribute("lastname", "Invalid Last Name");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("college"))){
			request.setAttribute("college",PropertyReader.getvalue("error.require", "College Name"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("dob"))){
			request.setAttribute("dob", PropertyReader.getvalue("error.require", "DOB"));
			pass = false;
		}
		
		else if(!DataValidator.isValidAge(request.getParameter("dob"))){
			request.setAttribute("dob", PropertyReader.getvalue("error.date", " Invalid DOB,"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("mobile"))){
			request.setAttribute("mobile", PropertyReader.getvalue("error.require", "MobileNo"));
			pass = false;
		}
		else if(!DataValidator.isMobileNo(request.getParameter("mobile"))){
			request.setAttribute("mobile", "Invalid Mobile No");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("email"))){
			request.setAttribute("email", PropertyReader.getvalue("error.require", "Emailid") );
			pass = false;
		}
		else if(!DataValidator.isEmail(request.getParameter("email"))){
			request.setAttribute("email", PropertyReader.getvalue("error.email", "Invalid"));
			pass = false;
		}
		
		log.debug("validate end");
		return pass;
		
	
	}
	
	
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		long id = DataUtility.getLong(request.getParameter("id"));
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		Studentmodel model = new Studentmodel();
		if(id>0){
			
			StudentBean bean;
			
			try{
				
				bean = model.findBypk(id);
				ServletUtility.setBean(bean, request);
				
			}catch(Exception e){
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				
			}
			
		}
		log.debug("doget end");
		ServletUtility.forward(getView(), request, response);	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
	long id = DataUtility.getLong(request.getParameter("id"));
	
	String op = DataUtility.getString(request.getParameter("operation"));
		
	Studentmodel model = new Studentmodel();
	
	if(OP_SAVE.equalsIgnoreCase(op)){
		
		StudentBean bean1 = (StudentBean) populateBean(request);
		long pk = 0;
		
		
		try {
		pk = model.add(bean1);
		bean1.setId(pk);
		
		
		ServletUtility.setSuccessMessage("Student Added Successfully", request);
		ServletUtility.setBean(bean1, request);
		} catch (DuplicateRecordException e) {
			ServletUtility.setErrorMessage("Student Already Exist", request);
			ServletUtility.setBean(bean1, request);
			e.printStackTrace();
			
		} catch (ApplicationException e) {
		    ServletUtility.handleException(e, request, response);
			e.printStackTrace();
		}
		ServletUtility.setBean(bean1, request);
		ServletUtility.forward(getView(), request, response);
	
	}
	else if(OP_UPDATE.equalsIgnoreCase(op)){
		
		StudentBean bean2 = (StudentBean) populateBean(request);
		if(id>0){
			
					try {
				    model.update(bean2);
					ServletUtility.setSuccessMessage("Student Updated Successfully", request);
				} catch (ApplicationException e) {
					
					e.printStackTrace();
					ServletUtility.handleException(e, request, response);
				}catch (DuplicateRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
		
		}
		
		
		
		ServletUtility.setBean(bean2 , request);
		ServletUtility.forward(getView(), request, response);
		
	}
		
	else if(OP_CANCEL.equalsIgnoreCase(op))	{
		ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
	}
		
	else if(OP_RESET.equalsIgnoreCase(op)){
		ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
	return;
	}	
	log.debug("dopost end");
		
}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STUDENT_VIEW;
	}

}
