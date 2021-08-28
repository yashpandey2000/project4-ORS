package co.in.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mysql.fabric.xmlrpc.base.Data;

import co.in.Exception.ApplicationException;
import co.in.bean.BaseBean;
import co.in.bean.StudentBean;
import co.in.model.Studentmodel;
import co.in.util.DataUtility;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;
 
/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "StudentListCtl", urlPatterns = { "/ctl/StudentListCtl" })
public class StudentListCtl extends BaseCtl {
private static final long serialVersionUID = 1L;
private static Logger log = Logger.getLogger(StudentListCtl.class);
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		StudentBean bean = new StudentBean();
		
	
	bean.setFirstname(DataUtility.getString(request.getParameter("firstname")));
	bean.setLastname(DataUtility.getString(request.getParameter("lastname")));
	bean.setEmail(DataUtility.getString(request.getParameter("email")));
	
	
	populateDTO(bean, request);	
	
		return bean;
		
		
	}
	
	
	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));
		
		Studentmodel model = new Studentmodel();
		
		StudentBean bean = (StudentBean) populateBean(request);
		
		List list  = new ArrayList();
		
		try{
			
		list  =	model.search(bean, pageNo, pageSize);
			
			if(list == null || list.size()==0){
				
				ServletUtility.setErrorMessage("no record found", request);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setpageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("doget end");
	}

	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		String[] ids = request.getParameterValues("ids");
		
		int pageNo = DataUtility.getInt(request.getParameter("pageno"));
		int pageSize = DataUtility.getInt(request.getParameter("pagesize"));
		
		pageNo = (pageNo==0)?1:pageNo;
		
		pageSize = (pageSize==0)?DataUtility.getInt(PropertyReader.getvalue("page.size")):pageSize;
		
		if(OP_SEARCH.equalsIgnoreCase(op)){
			
			pageNo=1;
		}
		else if(OP_PREVIOUS.equalsIgnoreCase(op)){
			if(pageNo > 1){
	    		pageNo--;
	    	}
	    	
	    	else{
	    		ServletUtility.setErrorMessage("No Previous Page", request);
	    		pageNo =1;
	    		
	    	}
		}
		else if(OP_NEXT.equalsIgnoreCase(op)){
			pageNo++;
		}
		else if (OP_NEW.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
		return;
		}
		
		else if(OP_RESET.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;
		}
		else if(OP_BACK.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
		return;
		}
		
		else if(OP_DELETE.equalsIgnoreCase(op)){
			
			Studentmodel mod = new Studentmodel();
			StudentBean be = new StudentBean();
			
			if(ids!=null && ids.length>0){
				
				for (String id2 : ids) {
					
					int idnew = DataUtility.getInt(id2);
					
					be.setId(idnew);
					
					ServletUtility.setSuccessMessage("Student Deleted Successfully", request);
					try {
						mod.delete(be);
					} catch (ApplicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
			}else{
				
				ServletUtility.setErrorMessage("Select Atleast one Record", request);
			}
				
		}
		
		Studentmodel model = new Studentmodel();
		StudentBean bean = (StudentBean) populateBean(request);
		List list = new ArrayList();
		
		
		try {
			
		list= model.search(bean, pageNo, pageSize);
		
		if(list==null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
			ServletUtility.setErrorMessage("No Student Found", request);
		}
		
		
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setpageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("dopost end");	
	}




	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STUDENT_LIST_VIEW;
	}

}
