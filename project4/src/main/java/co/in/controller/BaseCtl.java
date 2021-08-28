package co.in.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.in.bean.BaseBean;
import co.in.bean.UserBean;
import co.in.util.DataUtility;
import co.in.util.DataValidator;
import co.in.util.ServletUtility;

/**
 * @author Yash Pandey
 *
 */
public abstract class BaseCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	
	public static final String OP_SAVE = "Save";
	public static final String OP_UPDATE = "Update";
	public static final String OP_CANCEL = "Cancel";
	public static final String OP_DELETE = "Delete";
	public static final String op_LIST = "List";
	public static final String OP_SEARCH = "Search";
	public static final String OP_VIEW = "View";
	public static final String OP_NEXT = "NEXT";
	public static final String OP_PREVIOUS = "Previous";
	public static final String OP_NEW = "New";
	public static final String OP_GO = "Go";
	public static final String OP_BACK = "Back";
	public static final String OP_RESET = "Reset";
	
	public static final String MSG_SUCCESS = "Success";
	
	public static final String MSG_ERROR = "Error";
	

	
	
	protected boolean validate(HttpServletRequest request){
	
	//	System.out.println("validate method in basectl");
		
		return true;
		
	}
	
	
    protected void preload(HttpServletRequest request){
		
   // 	System.out.println("preload method in basectl");
   
    }
	
	
	protected BaseBean populateDTO(BaseBean dto , HttpServletRequest request){
		//System.out.println("populateDTo");
		String createdby = request.getParameter("createdby");
		String modifiedby = null;
		
		UserBean userbean = (UserBean) request.getSession().getAttribute("user");
		if(userbean == null){
			
			createdby = "root";
			modifiedby = "root";
		}else{
			
			modifiedby = userbean.getLoginid();
			
			if("null".equalsIgnoreCase(createdby) || DataValidator.isNull(createdby)){
				createdby = modifiedby;
			}
			
			
		}
		
		dto.setCreatedby(createdby);
		dto.setModifiedby(modifiedby);
		
		long ctd = DataUtility.getLong(request.getParameter("createddatetime"));
		
		if(ctd>0){
			dto.setCreateddatetime(DataUtility.getTimestamp(ctd));
			
		}
		
		else{
			dto.setCreateddatetime(DataUtility.getCurrentTimestamp());
			
		}
		
		
		dto.setModifieddatetime(DataUtility.getCurrentTimestamp());
		
		return dto;
		
	}
	
	
	protected void service(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException{
	//	System.out.println("service method in basectl");
	//	System.out.println(request.getParameter("dob")+"----->>");
		preload(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println(op);
		
		if(DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op) && !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)){
			//System.out.println("2");
			if(!validate(request)){
				BaseBean bean = (BaseBean)populateBean(request);
			//	System.out.println("3");
				ServletUtility.setBean(bean, request);
				//System.out.println("4");
				ServletUtility.forward(getView(), request, response);
		System.out.println("5");
			return;
			}
		}
		
		super.service(request, response);
		System.out.println("123");
		
		System.out.println("2323");
		
	}
	


	protected BaseBean populateBean(HttpServletRequest request) {
//	System.out.println("populate method in basectl");
		return null;
	
	}


	
	
	protected abstract String getView(); 
	
  

}
