package co.in.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mysql.fabric.xmlrpc.base.Data;

import co.in.Exception.ApplicationException;
import co.in.Exception.RecordNotFoundException;
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
@WebServlet(name="ChangePasswordCtl",urlPatterns={"/ctl/ChangePasswordCtl"})
public class ChangePasswordCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ChangePasswordCtl.class);
   public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";
	
   
   
   protected boolean validate(HttpServletRequest request){
	   log.debug("validate start");
	
	   boolean pass = true;
	   
	   
	   String op = DataUtility.getString(request.getParameter("operation"));
	   
	   String pass1 = request.getParameter("newpassword");
	   
	   if(OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)){
		   
		 
		   return pass;
	   }
	   
	   
	   
	  if(DataValidator.isNull(request.getParameter("oldpassword"))) {
		  request.setAttribute("oldpassword", PropertyReader.getvalue("error.require","Old Password"));
		  pass = false;
	  }
	   
	  else if(!DataValidator.isPassword(pass1)){
		  request.setAttribute("oldpassword", PropertyReader.getvalue("error.password","Invalid"));
		  pass = false;
	  } 
	  
	  
	 if(DataValidator.isNull(request.getParameter("newpassword"))){
		 request.setAttribute("newpassword",PropertyReader.getvalue("error.require", "New Password") );
		pass = false; 
	 }  
	   
	 else if(!DataValidator.isPassword(pass1)){
		 request.setAttribute("newpassword",PropertyReader.getvalue("error.password", "Invalid") );
	    pass =false;
	 } 
	 
	 else  if(request.getParameter("oldpassword").equals(request.getParameter("newpassword"))){
		  request.setAttribute("newpassword", "Old and New password should be different");
		  pass = false;
	  }
	   
	  if(DataValidator.isNull(request.getParameter("confirmpassword"))){
		  request.setAttribute("confirmpassword", PropertyReader.getvalue("error.require", "Confirm Password"));
		 pass = false; 
	  } 
	   
	  else if(!request.getParameter("newpassword").equals(request.getParameter("confirmpassword")) && !"".equals(request.getParameter("confirmpassword"))){
		  request.setAttribute("confirmpassword", "new and confirm password should be same");
		  pass = false;
	  }  
	  
	  log.debug("validate end");
	   return pass;
	   
   }
   
   
   protected BaseBean populateBean(HttpServletRequest request){
	
	   
	   UserBean bean = new UserBean();
	   bean.setPassword(DataUtility.getString(request.getParameter("oldpassword")));
	   bean.setConfirmpassword(DataUtility.getString(request.getParameter("confirmpassword")));
	   
	   populateDTO(bean, request);
	   
	  
	   return bean;
	   
   }
   
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletUtility.forward(getView(), request, response);
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
		HttpSession session = request.getSession(true);
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		UserModel model = new UserModel();
		
		UserBean userbean = (UserBean) session.getAttribute("user");
		
		UserBean bean = (UserBean) populateBean(request);
		
		long id = userbean.getId();
		
		String newpassword = request.getParameter("newpassword");
	
		
		if(OP_SAVE.equalsIgnoreCase(op)){
		
			try{
				
				boolean flag = true;
				flag=model.changePassword(id, bean.getPassword(), newpassword);
				
				
					
				if(flag==true ){
					
					
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("password changed successful", request);
				    ServletUtility.forward(getView(), request, response);
				return;
				}
				
				
				
				
		}catch(ApplicationException e){
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ServletUtility.setErrorMessage("Old password is invalid", request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
				
		}else if(OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			return;
		}
		
		
	ServletUtility.forward(getView(), request, response);	
		
		
	log.debug("dopost end");
		
	}



	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}
