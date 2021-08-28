package co.in.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.BaseBean;
import co.in.bean.CourseBean;
import co.in.model.CourseModel;
import co.in.util.DataUtility;
import co.in.util.DataValidator;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;


/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl" })
public class CourseCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
  
	private static Logger log = Logger.getLogger(CourseCtl.class);
	
	protected boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("cname"))){
			request.setAttribute("cname", PropertyReader.getvalue("error.require","Course Name"));
			pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("cname"))){
			request.setAttribute("cname", "Invalid Course Name ");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("duration"))){
			request.setAttribute("duration", PropertyReader.getvalue("error.require", "Course Duration"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("desc"))){
			request.setAttribute("desc", PropertyReader.getvalue("error.require", "Course Description"));
			pass = false;
		}
		
		else if(!DataValidator.isName(request.getParameter("desc"))){
			request.setAttribute("desc", "Invalid Description");
			pass = false;
		}
		 log.debug("validate end");
		return pass;
		
	}
	
	
	
	protected BaseBean populateBean(HttpServletRequest request){
		CourseBean bean = new CourseBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		System.out.println(request.getParameter("id"+"00000000000000000000000000000000"));
		bean.setCname(DataUtility.getString(request.getParameter("cname")));
		bean.setDuration(DataUtility.getString(request.getParameter("duration")));
		bean.setDescription(DataUtility.getString(request.getParameter("desc")));
		
		populateDTO(bean, request);
		
		
		return bean;
		}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		long id = DataUtility.getLong(request.getParameter("id"));
	    String op = DataUtility.getString(request.getParameter("operation"));
	    
	    CourseBean bean = null;
	    CourseModel model = new CourseModel();
	    
	    if(id>0 || op!=null){
	    	
	    	
	    	
	    	try {
			bean = 	model.findBypk(id);
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServletUtility.setErrorMessage("no record exist", request);
			}
	    	
	    }
	    log.debug("doget end");
	    ServletUtility.setBean(bean, request);
	   ServletUtility.forward(getView(), request, response);
	}

	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("dopost start");
		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));
		
		CourseModel model = new CourseModel();
		
		if(OP_SAVE.equalsIgnoreCase(op)){
			
			CourseBean bean ;
			bean = (CourseBean) populateBean(request);
			
			
			 try {
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Course Added Successfuly", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				
			} catch (DuplicateRecordException e) {
				
				e.printStackTrace();
				ServletUtility.setErrorMessage("Course Already Exist", request);
				
			}
			
			
			ServletUtility.forward(getView(), request, response);
			
		}
		else if(OP_UPDATE.equalsIgnoreCase(op)){
			
			CourseBean bean1;
			bean1 = (CourseBean) populateBean(request);
			System.out.println("in post update method");
			System.out.println("id =================== "+id);
			if(id>0){
				try {
					model.update(bean1);
					ServletUtility.setSuccessMessage("Course Updated Successfully", request);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DuplicateRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
			  }
			
			ServletUtility.setBean(bean1, request);
			ServletUtility.forward(getView(), request, response);
			
		}else if(OP_RESET.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		}else if(OP_CANCEL.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		}
				
		log.debug("dopost end");
	}



	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COURSE_VIEW;
	}

}
