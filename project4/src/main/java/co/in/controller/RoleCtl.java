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
import co.in.model.RoleModel;
import co.in.util.DataUtility;
import co.in.util.DataValidator;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;

/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "RoleCtl", urlPatterns = { "/ctl/RoleCtl" })
public class RoleCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(RoleCtl.class);
   protected boolean validate(HttpServletRequest request){
	   log.debug("validate start");
	   boolean pass = true;
	   
	   if(DataValidator.isNull(request.getParameter("name"))){ 
		   request.setAttribute("name", PropertyReader.getvalue("error.require", "Name"));
		  pass = false; 
		 
	   }
	   
	   
	   else if(!DataValidator.isName(request.getParameter("name"))){
		   request.setAttribute("name","Invalid Role Name");
		   pass = false;
	   }
	   
	   if(DataValidator.isNull(request.getParameter("desc"))){
		   request.setAttribute("desc",PropertyReader.getvalue("error.require","Description"));
		   pass = false;
	   } else if(!DataValidator.isName(request.getParameter("desc"))){
		   request.setAttribute("desc","Invalid Description");
		   pass = false;
	   }
	   
	   log.debug("validate end");
	     
	   return pass;
   
   }
    

   protected BaseBean populateBean(HttpServletRequest request){
	System.out.println("populatebean start");
	   
	   RoleBean bean = new RoleBean();
	   
	   bean.setId(DataUtility.getLong(request.getParameter("id")));
	   
	   bean.setName(DataUtility.getString(request.getParameter("name")));
	   
	   bean.setDescription(DataUtility.getString(request.getParameter("desc")));
	 //  System.out.println("after populateBean");
	   
	   populateDTO(bean, request);
	   
	   
	   
	   return bean;
	   
	   
	   
   }
   
 	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		long id = DataUtility.getLong(request.getParameter("id"));	
	
	String op = DataUtility.getString(request.getParameter("operation"));
	
	RoleModel model = new RoleModel();
	
	RoleBean bean = null;
	System.out.println("after roll bean");
	if(id>0 || op!=null){
		
		
		try {
			bean = model.findByPk(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServletUtility.setBean(bean, request);
		
	}

	ServletUtility.forward(getView(), request, response);
	log.debug("doget end");
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
	
	long id = DataUtility.getLong(request.getParameter("id"));
	
	String op = DataUtility.getString(request.getParameter("operation"));
	
	RoleModel model = new RoleModel();
	
	if(OP_SAVE.equalsIgnoreCase(op)){
		
		RoleBean bean = (RoleBean) populateBean(request);
		
		
			try {
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Role Added Successfully ", request);	
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
				ServletUtility.setErrorMessage("Role Already Exist", request);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ServletUtility.forward(getView(), request, response);
			
		}
	
	
	else if(OP_UPDATE.equalsIgnoreCase(op)){
		
		RoleBean bean1 = (RoleBean) populateBean(request);
		
		if(id>0){
			
			try {
				model.update(bean1);
				ServletUtility.setSuccessMessage("Role Updated Successfully", request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
				
			}
			
		}
		ServletUtility.setBean(bean1, request);
		ServletUtility.forward(getView(), request, response);
		
	}
	
	
	else if(OP_CANCEL.equalsIgnoreCase(op)){
		
		ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
		
		return;
	}
	
	
	else if(OP_RESET.equalsIgnoreCase(op)){
		
		ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
		return;
	}
	
	log.debug("dopost end");
		
	}
	
		
	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ROLE_VIEW;
	}
	
}
