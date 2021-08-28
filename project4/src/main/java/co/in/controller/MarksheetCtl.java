package co.in.controller;

import java.io.IOException;
import java.sql.SQLException;
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
import co.in.bean.MarksheetBean;
import co.in.model.MarksheetModel;
import co.in.model.Studentmodel;
import co.in.util.DataUtility;
import co.in.util.DataValidator;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;



/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "MarksheetCtl", urlPatterns = "/ctl/MarksheetCtl")
public class MarksheetCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MarksheetCtl.class);
   protected void preload(HttpServletRequest request){
	   
	   Studentmodel model = new Studentmodel();
	   
	   try {
		List list = model.list();
		request.setAttribute("studentlist", list);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	      
   }
	
   
   protected boolean validate(HttpServletRequest request){
	   log.debug("validate start");
	boolean pass = true;
	
	
	if(DataValidator.isNull(request.getParameter("rollno"))){
		request.setAttribute("rollno",PropertyReader.getvalue("error.require","RollNo"));
		pass = false;
	}
	else if(DataValidator.isRollNo(request.getParameter("rollno"))){
		request.setAttribute("rollno", "Invalid Roll No (Enter Roll no in format as  Ex. 01cs01)");
		pass = false;
	}
	
	if(DataValidator.isNull(request.getParameter("studentid"))){
		request.setAttribute("studentid", PropertyReader.getvalue("error.require","Student Name" ));
		pass = false;
	}
	if(DataValidator.isNull(request.getParameter("physics"))){
		request.setAttribute("physics", PropertyReader.getvalue("error.require","Physics"));
		pass = false;
	}   
	else if(DataUtility.getInt(request.getParameter("physics"))>100) {
		request.setAttribute("physics", "Marks Can Not Be More Than 100");
		pass = false;
	}  
	else if(DataUtility.getInt(request.getParameter("physics"))<0){
		request.setAttribute("physics", "Marks Can Not Be Negative");
		pass = false;
	}  
	  
	else if(!DataValidator.isInteger(request.getParameter("physics")) && DataValidator.isNotNull(request.getParameter("physics"))){
		request.setAttribute("physics", "Enter Number Only");
		pass = false;
	}
	
	
	
	if(DataValidator.isNull(request.getParameter("chemistry"))){
		request.setAttribute("chemistry", PropertyReader.getvalue("error.require","chemistry"));
		pass = false;
	}   
	else if(DataUtility.getInt(request.getParameter("chemistry"))>100) {
		request.setAttribute("chemistry", "Marks Can Not Be More Than 100");
		pass = false;
	}  
	else if(DataUtility.getInt(request.getParameter("chemistry"))<0){
		request.setAttribute("chemistry", "Marks Can Not Be Negative");
		pass = false;
	}  
	  
	else if(!DataValidator.isInteger(request.getParameter("chemistry")) && DataValidator.isNotNull(request.getParameter("physics"))){
		request.setAttribute("chemistry", "Enter Number Only");
		pass = false;
	}
	
	

	if(DataValidator.isNull(request.getParameter("math"))){
		request.setAttribute("math", PropertyReader.getvalue("error.require","math"));
		pass = false;
	}   
	else if(DataUtility.getInt(request.getParameter("math"))>100) {
		request.setAttribute("math", "Marks Can Not Be More Than 100");
		pass = false;
	}  
	else if(DataUtility.getInt(request.getParameter("math"))<0){
		request.setAttribute("math", "Marks Can Not Be Negative");
		pass = false;
	}  
	  
	else if(!DataValidator.isInteger(request.getParameter("math")) && DataValidator.isNotNull(request.getParameter("physics"))){
		request.setAttribute("math", "Enter Number Only");
		pass = false;
	}
	
	
	 log.debug("validate end");
   
	   return pass;
	}
	
   
   
   protected BaseBean populateBean(HttpServletRequest request){
	
	   MarksheetBean bean = new MarksheetBean();
	   
	   bean.setId(DataUtility.getLong(request.getParameter("id")));
	   bean.setRollno(DataUtility.getString(request.getParameter("rollno")));
	   bean.setStudentid(DataUtility.getLong(request.getParameter("studentid")));
	   bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
	   bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
	   bean.setMaths(DataUtility.getInt(request.getParameter("math")));
	   
	   populateDTO(bean, request);
	   
	   
	   return bean;
	}
   
   
   
   
   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));
		
		MarksheetModel model = new MarksheetModel();
		
		if(id>0){
			MarksheetBean bean;
			try {
				bean = model.findBypk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			}
			
		}
		log.debug("doget end");
		ServletUtility.forward(getView(), request, response);
		return;
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
		MarksheetModel model = new MarksheetModel();
		String op = DataUtility.getString(request.getParameter("operation"));
		
		if(OP_SAVE.equalsIgnoreCase(op)){
			
		MarksheetBean bean = (MarksheetBean) populateBean(request);
			
			try {
				model.add(bean);
				//ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Marksheet Added Successfully", request);
			} catch (Exception e) {
				e.printStackTrace();
				
				ServletUtility.setErrorMessage("Roll Number Already Exist", request);
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.forward(getView(), request, response);
		}
		else if(OP_UPDATE.equalsIgnoreCase(op)){
			
			MarksheetBean bean1 = (MarksheetBean) populateBean(request);
			
			
			long id = DataUtility.getLong(request.getParameter("id"));
			
			if(id>0){
				
				
				try {
					MarksheetModel model1 = new MarksheetModel();
					model1.update(bean1);
					
					ServletUtility.setSuccessMessage("Marksheet Updated Successfully", request);
				} catch (ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DuplicateRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
				
			}
			
			ServletUtility.setBean(bean1, request);
			ServletUtility.forward(getView(), request, response);
			
		}else if(OP_CANCEL.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
		     return;
		     
		}
		log.debug("dopost end");
		
	}


	@Override
	protected String getView() {
		
		return ORSView.MARKSHEET_VIEW;
	}

}
