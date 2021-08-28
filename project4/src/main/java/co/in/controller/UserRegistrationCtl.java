package co.in.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.BaseBean;
import co.in.bean.RoleBean;
import co.in.bean.UserBean;
import co.in.model.UserModel;
import co.in.util.DataUtility;
import co.in.util.DataValidator;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;


/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/UserRegistrationCtl" })
public class UserRegistrationCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UserRegistrationCtl.class);
	public static final String OP_SIGN_UP = "SignUp";
   
  //  private static Logger log = Logger.getLogger(UserRegistrationCtl.class);
	
	protected boolean validate(HttpServletRequest request){
	
		
		boolean pass = true;
		
		//System.out.println("inside validate method");
		//System.out.println(request.getParameter("dob"));
		if(DataValidator.isNull(request.getParameter("firstname"))){
			request.setAttribute("firstname", PropertyReader.getvalue("error.require","First Name") );
			pass=false;
		}
		else if(!DataValidator.isName(request.getParameter("firstname"))){
			request.setAttribute("firstname", "invalid First Name");
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("lastname"))){
			request.setAttribute("lastname", PropertyReader.getvalue("error.require" , "Last Name"));
			pass=false;
		}
		else if(!DataValidator.isName(request.getParameter("lastname"))){
			request.setAttribute("lastname", "invalid Last Name");
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("loginid"))){
			request.setAttribute("loginid", PropertyReader.getvalue("error.require" , "Emailid"));
			pass=false;
		}
		else if(!DataValidator.isEmail(request.getParameter("loginid"))){
			request.setAttribute("loginid", PropertyReader.getvalue("error.email","invalid Emailid"));;
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("password"))){
			request.setAttribute("password",PropertyReader.getvalue("error.require" , "Password"));
			pass=false;
		}
		
		else if(!DataValidator.isPassword(request.getParameter("password"))){
			request.setAttribute("password", PropertyReader.getvalue("error.password" , "invalid"));
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("confirmpassword"))){
			request.setAttribute("confirmpassword", PropertyReader.getvalue("error.require" , "Confirm Password"));
			pass=false;
		}
	
		
//		if(DataValidator.isNull(request.getParameter("address"))){
//			request.setAttribute("address1", PropertyReader.getvalue("error.require" , "Address" ));
//		
//			pass=false;
//		}
//		
//		else if(!DataValidator.isAddress(request.getParameter("address"))){
//			request.setAttribute("address1", "invalid Address");
//		
//			pass=false;
//		}
		
		if(DataValidator.isNull(request.getParameter("gender"))){
			request.setAttribute("gender", PropertyReader.getvalue("error.require","Gender"));
			pass=false;
		}
		
		
		if(DataValidator.isNull(request.getParameter("dob"))){
			request.setAttribute("dob1", PropertyReader.getvalue("error.require" ,"DOB"));
			pass=false;
		}
		
		
		else if(!DataValidator.isValidAge(request.getParameter("dob"))){
			request.setAttribute("dob1", PropertyReader.getvalue("error.date" , "DOB"));
		
			pass=false;
		}
		
		if(!request.getParameter("password").equals(request.getParameter("confirmpassword")) && !"".equals(request.getParameter("confirmpassword"))){
			request.setAttribute("confirmpassword","Password & Confirm Password should me matched");
			pass=false;
		}
		
		if(DataValidator.isNull(request.getParameter("mobile"))){
			request.setAttribute("mobile", PropertyReader.getvalue("error.require" ,"MobileNo"));
		
			pass=false;
		}
		else if(!DataValidator.isMobileNo(request.getParameter("mobile"))){
			request.setAttribute("mobile", PropertyReader.getvalue("error.mobile","Invalid"));
			pass=false;
		}
		
		log.debug("validate end");
		return pass;
		
	}
	
	
	
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		UserBean bean = new UserBean();
	   //  System.out.println("userbean in populatebean");
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRoleid(RoleBean.student);
		bean.setFirstname(DataUtility.getString(request.getParameter("firstname")));
		bean.setLastname(DataUtility.getString(request.getParameter("lastname")));
		bean.setLoginid(DataUtility.getString(request.getParameter("loginid")));
	//	System.out.println(request.getParameter("loginid")+"----");
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmpassword(DataUtility.getString(request.getParameter("confirmpassword")));
	//	bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileno(DataUtility.getString(request.getParameter("mobile")));
		//System.out.println("populatedBean method is ended");
		populateDTO(bean,request);	
		
		return bean	;
	}
	
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//System.out.println("inside doget");
		ServletUtility.forward(getView(), request, response);
		//System.out.println("doget method of user regis ended");
		
	}

	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
		String op = DataUtility.getString(request.getParameter("operation"));
	  
	    UserModel model = new UserModel();
	    
	if(OP_SIGN_UP.equalsIgnoreCase(op)){
		
	long id = DataUtility.getLong(request.getParameter("id"));
	
	
	UserBean bean = (UserBean) populateBean(request);
	
	try{
		
		long pk = model.registerUser(bean);
		bean.setId(pk);
		//request.getSession().setAttribute("UserBean", bean);
		ServletUtility.setBean(bean, request);
		ServletUtility.setSuccessMessage("Registration Successful", request);
	    ServletUtility.forward(getView(), request, response);
	return;
	}catch(ApplicationException e){
		e.printStackTrace();
		ServletUtility.handleException(e, request, response);
		return;
	}catch(DuplicateRecordException e){
	
	ServletUtility.setBean(bean, request);
	ServletUtility.setErrorMessage("login id already exist !", request);
	ServletUtility.forward(getView(), request, response);
	
	
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	//System.out.println("if block of post method in regis");
	
	}else if(OP_RESET.equalsIgnoreCase(op)){
		
		ServletUtility.forward(getView(), request, response);
		return;
	}
	
	log.debug("dopost end");
	
	}
	
	
protected String getView() {
		//System.out.println("view method");
		return ORSView.USER_REGISTRATION_VIEW;
	}



}
