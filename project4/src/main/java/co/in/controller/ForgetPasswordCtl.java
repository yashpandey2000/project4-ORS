package co.in.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

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
@WebServlet(name="ForgetPasswordCtl",urlPatterns={"/ForgetPasswordCtl"})
public class ForgetPasswordCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ForgetPasswordCtl.class);
	protected boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass = true;
		
		
		
		if(DataValidator.isNull(request.getParameter("login"))){
		request.setAttribute("login", PropertyReader.getvalue("error.require","Emailid"));		
           pass = false;		
         
		}
		
		else if(!DataValidator.isEmail(request.getParameter("login"))){
			request.setAttribute("login", "Invalid Emailid");
		pass= false;
		}	
		 log.debug("validate end");
		return pass;
		
	}
	
	protected BaseBean populateBean(HttpServletRequest request){
	
		UserBean bean = new UserBean();
		
		bean.setLoginid(DataUtility.getString(request.getParameter("login")));
		
		return bean;
		}
	
	
	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletUtility.forward(getView(), request, response);
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		if(OP_GO.equalsIgnoreCase(op)){
			System.out.println("in controller GO operation");
			UserBean bean = (UserBean) populateBean(request);
			
			UserModel model = new UserModel();
			
			try{
				
				System.out.println("in controller model operation");
				model.forgetpassword(bean.getLoginid());
				
				
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Password has been sent to your email id !", request);
				//System.out.println("in controller success message");
			    }catch(RecordNotFoundException e){
				System.out.println("in controller  record not found");
				ServletUtility.setErrorMessage("Email does not exist", request);
			    } catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("in controller application exception");
				ServletUtility.handleException(e, request, response);
				return;
			}
			
			ServletUtility.forward(getView(), request, response);
			
		}else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.FORGET_PASSWORD_CTL, request, response);
			return;
		}
	
		log.debug("dopost end");
		
	}



	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FORGET_PASSWORD_VIEW;
	}

}
