package co.in.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.BaseBean;
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
@WebServlet(name = "MyProfileCtl", urlPatterns = { "/ctl/MyProfileCtl" })
public class MyProfileCtl extends BaseCtl {
private static final long serialVersionUID = 1L;
private static Logger log = Logger.getLogger(MyProfileCtl.class);
public static final String OP_CHANGE_MY_PASWORD = "ChangePassword";
       
   protected boolean validate(HttpServletRequest request){
	   log.debug("validate start");
	   boolean pass =true;
	   
	   
	   String op = DataUtility.getString(request.getParameter("operation"));
	   
	   if(OP_CHANGE_MY_PASWORD.equalsIgnoreCase(op) || op== null){
		   
		   return pass;
	   }
	   
//	   if(DataValidator.isNull(request.getParameter("loginid"))){
//		   request.setAttribute("loginid", PropertyReader.getvalue("error.require","Loginid"));
//		   pass = false;
//	   }
//	   
//	   else if(!DataValidator.isEmail(request.getParameter("loginid"))){
//		   request.setAttribute("loginid", PropertyReader.getvalue("error.email", "Invalid"));
//	   pass = false;
//	   }
	   
	   
	   if(DataValidator.isNull(request.getParameter("firstname"))){
		   request.setAttribute("firstname", PropertyReader.getvalue("error.require", "First Name"));
		 pass = false;  
	   }
	   
	   else if(!DataValidator.isName(request.getParameter("firstname"))){
		   request.setAttribute("firstname", "Invalid  Name");
		   pass = false;
	   }
	   
	   if(DataValidator.isNull(request.getParameter("lastname"))){
		   request.setAttribute("lastname", PropertyReader.getvalue("error.require", "Last Name"));
		   pass = false;
	   }
	   else if(!DataValidator.isName(request.getParameter("lastname"))){
		   request.setAttribute("lastname", "Invalid  Name ");
		   pass= false;
	   }
	   if(DataValidator.isNull(request.getParameter("gender"))){
		   request.setAttribute("gender", PropertyReader.getvalue("error.require", "Gender"));
		   pass = false;
	   }
	   
	   if(DataValidator.isNull(request.getParameter("mobile"))){
		   request.setAttribute("mobile", PropertyReader.getvalue("error.require", "MobileNo"));
		   pass = false;
	   }
	   
	  else if( !DataValidator.isMobileNo(request.getParameter("mobile"))){
		   request.setAttribute("mobile", PropertyReader.getvalue("error.mobile", "Invalid"));
		  pass= false; 
	   }
	   
	if(DataValidator.isNull(request.getParameter("dob"))){
		request.setAttribute("dob", PropertyReader.getvalue("error.require", "DOB"));
		pass = false;
	}
	
	else if(!DataValidator.isValidAge(request.getParameter("dob"))){
		request.setAttribute("dob", PropertyReader.getvalue("error.date","Invalid" ));
		pass = false;
		
	}
	      
	log.debug("validate end");
	return pass;
	   
   }
	
	
	
	
   
   protected BaseBean populateBean(HttpServletRequest request){
	   
	   UserBean bean = new UserBean();
	   
	   bean.setId(DataUtility.getLong(request.getParameter("id")));
	   bean.setLoginid(DataUtility.getString(request.getParameter("loginid")));
	   bean.setFirstname(DataUtility.getString(request.getParameter("firstname")));
	   bean.setLastname(DataUtility.getString(request.getParameter("lastname"))); 
	   bean.setMobileno(DataUtility.getString(request.getParameter("mobile")));
	   bean.setGender(DataUtility.getString(request.getParameter("gender")));
	   bean.setDob(DataUtility.getDate(request.getParameter("dob")));
	   
	   populateDTO(bean,request);
	  
	return bean;
	
   }
   
   

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		HttpSession session = request.getSession(true);
		
		UserBean userbean = (UserBean) session.getAttribute("user");
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		long id = userbean.getId();
		
		UserModel model = new UserModel();
		
		if(id >0 || op!=null){
			
			
			
			try {
				UserBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
				
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
			
			
		}
		
		log.debug("doget start");
		ServletUtility.forward(getView(), request, response);
	}

	
	



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
		HttpSession session = request.getSession(true);
		
		UserBean userbean = (UserBean) session.getAttribute("user");
		
		long id = userbean.getId();
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		UserModel model = new UserModel();
		
		if(OP_SAVE.equalsIgnoreCase(op)){
			
			UserBean bean = (UserBean) populateBean(request);
			
			try {
				
			if(id>0){
				
				userbean.setFirstname(bean.getFirstname());
				userbean.setLastname(bean.getLastname());
				userbean.setGender(bean.getGender());
				userbean.setMobileno(bean.getMobileno());
				userbean.setDob(bean.getDob());
				
			 model.update(userbean);
					
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.setSuccessMessage("profile has been updated successful", request);
			
			
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ApplicationException e) {
					
					ServletUtility.handleException(e, request, response);
					e.printStackTrace();
					return;
				} catch (DuplicateRecordException e) {
					
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("login id already exist", request);
					
				}
				
			}else if(OP_CHANGE_MY_PASWORD.equalsIgnoreCase(op)){
				
				ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
				return;
			}
			
		ServletUtility.forward(getView(), request, response);
			
		log.debug("dopost end");
		}
		
		
		
protected String getView() {
		
		return ORSView.MY_PROFILE_VIEW;
	}


	
	
	
	

}
