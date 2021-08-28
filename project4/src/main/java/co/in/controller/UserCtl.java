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
import co.in.bean.RoleBean;
import co.in.bean.UserBean;
import co.in.model.RoleModel;
import co.in.model.UserModel;
import co.in.util.DataUtility;
import co.in.util.DataValidator;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;

/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UserCtl.class);
	protected void preload(HttpServletRequest request){
		
		RoleModel model = new RoleModel();
		
		try{
			
			List list = model.list();
			request.setAttribute("roleList", list);
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	
	}
	
	
	protected boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass =true;
		
		long id = DataUtility.getInt(request.getParameter("id"));
		System.out.println("1"+id);
		if(DataValidator.isNull(request.getParameter("firstname"))){
			request.setAttribute("firstname", PropertyReader.getvalue("error.require", "First Name"));
		pass=false;
		System.out.println("2");
		}
		
		else if(!DataValidator.isName(request.getParameter("firstname"))){
			request.setAttribute("firstname", "Invalid First Name");
			pass = false;
			System.out.println("2");
		}
		
		if(DataValidator.isNull(request.getParameter("lastname"))){
			request.setAttribute("lastname", PropertyReader.getvalue("error.require","Last Name"));
			pass=false;
			System.out.println("3");
		}
		else if(!DataValidator.isName(request.getParameter("lastname"))){
			request.setAttribute("lastname", "Invalid Last Name");
			pass=false;
			System.out.println("4");
		}
		
		if(DataValidator.isNull(request.getParameter("loginid"))){
			request.setAttribute("loginid", PropertyReader.getvalue("error.require","Loginid"));
			pass = false;
			System.out.println("5");
		}
		
		else if(!DataValidator.isEmail(request.getParameter("loginid"))){
			request.setAttribute("loginid",PropertyReader.getvalue("error.email","Invalid" ) );
			pass = false;
			System.out.println("6");
		}
		
		if(DataValidator.isNull(request.getParameter("password"))){
			request.setAttribute("password", PropertyReader.getvalue("error.require", "Password"));
			pass = false;
			System.out.println("7");
		}
		else if(!DataValidator.isPassword(request.getParameter("password"))){
			request.setAttribute("password",PropertyReader.getvalue("error.password", "Invalid "));
			pass=false;
			System.out.println("8");
		}if(id==0){
			if(DataValidator.isNull(request.getParameter("confirmpassword"))){
				System.out.println(request.getParameter("confirmpassword")+"-----------------------");
				request.setAttribute("confirmpassword", PropertyReader.getvalue("error.require", "Confirm Password"));
				pass = false;
				System.out.println("---->>>>confirmpass");
			}
		}
		
		
//		if(DataValidator.isNull(request.getParameter("address"))){
//			request.setAttribute("address",PropertyReader.getvalue("error.require", "Address") );
//			pass= false;
//		}
		
		/*else if(!DataValidator.isAddress(request.getParameter("address"))){
			request.setAttribute("address", "Invalid Address");
			pass=false;
		}*/
		
		if(DataValidator.isNull(request.getParameter("gender"))){
			request.setAttribute("gender", PropertyReader.getvalue("error.require", "Gender"));
			pass= false;
			System.out.println("9 error");
		}
		
		if(DataValidator.isNull(request.getParameter("dob"))){
			request.setAttribute("dob",PropertyReader.getvalue("error.require", "DOB"));
			pass = false;
			System.out.println("10");
		}
		else if(!DataValidator.isDate(request.getParameter("dob"))){
			request.setAttribute("dob", PropertyReader.getvalue("error.date","DOB"));
			pass= false;System.out.println("11");
			
			
		}
		else if(!DataValidator.isValidAge(request.getParameter("dob"))){
			request.setAttribute("dob", PropertyReader.getvalue("error.date", "DOB"));
			pass = false;
			System.out.println("12");
		}
		if(id==0){
			if(!request.getParameter("password").equals(request.getParameter("confirmpassword")) && !"".equals(request.getParameter("confirmpassword"))){
				request.setAttribute("confirmpassword", "Confirm Password & Password should be matched");
				pass = false;
				System.out.println("13");
			}
		}
		
		
		
		if(DataValidator.isNull(request.getParameter("mobile"))){
			request.setAttribute("mobile", PropertyReader.getvalue("error.require", "Mobile No"));
			pass = false;
			System.out.println("14");
		}
		else if(!DataValidator.isMobileNo(request.getParameter("mobile"))){
			request.setAttribute("mobile", PropertyReader.getvalue("error.mobile", "Invalid"));
			pass= false;
			System.out.println("15");
		}
		
		if(DataValidator.isNull(request.getParameter("roleid"))){
			request.setAttribute("roleid", PropertyReader.getvalue("error.require", "Roll Name"));
			pass = false;
			System.out.println("16");
		}
		
		log.debug("validate end");
		return pass;
		
	}

	
	
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		
		UserBean bean = new UserBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRoleid(DataUtility.getLong(request.getParameter("roleid")));
		bean.setFirstname(DataUtility.getString(request.getParameter("firstname")));
		bean.setLastname(DataUtility.getString(request.getParameter("lastname")));
		bean.setLoginid(DataUtility.getString(request.getParameter("loginid")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmpassword(DataUtility.getString(request.getParameter("confirmpassword")));
		//bean.setAddress(DataUtility.getString(request.getParameter("address")));
		//bean.setRollname(DataUtility.getString(request.getParameter("rolename")));
		bean.setMobileno(DataUtility.getString(request.getParameter("mobile")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		
		populateDTO(bean, request);
		
		return bean;
		
	}
	
	

  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));
		
		UserModel model = new UserModel();
		UserBean bean = null;
		
		if(id>0){
			
			try{
				bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			}catch(Exception e){
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			}
			
			
		}
		
		
		ServletUtility.forward(getView(), request, response);
		log.debug("doget end");
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
		UserModel model = new UserModel();
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		
		long id = DataUtility.getLong(request.getParameter("id"));
		
		
		if(OP_SAVE.equalsIgnoreCase(op)){

			try{
				
				UserBean bean = (UserBean) populateBean(request);
				model.add(bean);
				ServletUtility.setBean(bean, request);	
				ServletUtility.setSuccessMessage("user added successfully", request);
				
				
			}catch(DuplicateRecordException e){
				e.printStackTrace();
				ServletUtility.setErrorMessage("User Already Exist", request);
				
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ServletUtility.forward(getView(), request, response);
			System.out.println("=========================================================");
		}
		
		else if(OP_UPDATE.equalsIgnoreCase(op)){
			System.out.println("--------------------------------------------------------");
			UserBean bean1 = (UserBean) populateBean(request);
			
			
			try {
				if(id>0){
				model.update(bean1);
				}
				ServletUtility.setBean(bean1, request);
				ServletUtility.setSuccessMessage("User Updated Successfully", request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ServletUtility.forward(getView(), request, response);	
			
	
		}
		else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
			
		}
		
		else if(OP_CANCEL.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
		 return;
		}
		
		log.debug("dopost end");
	}


	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_VIEW;
	}
	
	

}
