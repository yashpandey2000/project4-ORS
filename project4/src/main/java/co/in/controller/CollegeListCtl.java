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
import co.in.bean.BaseBean;
import co.in.bean.CollegeBean;
import co.in.model.CollegeModel;
import co.in.util.DataUtility;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;

/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "CollegeListCtl", urlPatterns = "/ctl/CollegeListCtl")
public class CollegeListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(CollegeListCtl.class);
	
	protected BaseBean populateBean(HttpServletRequest request){
	
	CollegeBean bean = new CollegeBean();
	
	bean.setName(DataUtility.getString(request.getParameter("cname")));
	bean.setCity(DataUtility.getString(request.getParameter("ccity")));	
		
		return bean;
		
	}
	
	
	
	
	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		long id = DataUtility.getLong(request.getParameter("id"));
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		int pageNo = 1;
		
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));
		
		CollegeModel model = new CollegeModel();
		CollegeBean bean = new CollegeBean();
		
		List list = null;
		List next = null;
		
		
		try {
			
		    list = 	model.search(bean, pageNo, pageSize);
			
			next = model.search(bean, pageNo+1, pageSize);
			
			if(list==null || list.size()==0){
				
				ServletUtility.setErrorMessage("No Record Exist", request);
			}
			if(next==null || next.size()==0){
				request.setAttribute("nextlist", "0");
			}else{
				request.setAttribute("nextlist", next.size());
			}
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
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
		
		String ids[] = request.getParameterValues("ids");
		
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		
		
		pageNo = (pageNo==0)?1:pageNo;
		pageSize = (pageSize==0)?DataUtility.getInt(PropertyReader.getvalue("page.size")):pageSize;		
		
		if(OP_SEARCH.equalsIgnoreCase(op)){
			pageNo=1;
		}
		else if(OP_PREVIOUS.equalsIgnoreCase(op)){
			
			if(pageNo>1){
				
				pageNo--;
				
			}else{
				
				pageNo=1;
				
				ServletUtility.setErrorMessage("No Previous Page Available", request);
				
			}
			
			
			
		}else if(OP_NEXT.equalsIgnoreCase(op)){
			pageNo++;
		}
		
		else if(OP_NEW.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			return;
		}
		else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
		return;
		}
		
		else if(OP_DELETE.equalsIgnoreCase(op)){
			CollegeModel mod = new CollegeModel();
			CollegeBean bean1 = new CollegeBean();
			if(ids!=null && ids.length>0){
				for (String ids1 : ids) {
					int idnew = DataUtility.getInt(ids1);
					bean1.setId(idnew);
					
					
					
					try {
						mod.delete(bean1);
						ServletUtility.setSuccessMessage("College Deleted Successfully", request);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ApplicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}	
			
			}else{
				ServletUtility.setErrorMessage("Select Atleast One Record", request);
			}
			
			
		}
		
		CollegeModel model = new CollegeModel();
		CollegeBean bean = (CollegeBean) populateBean(request);
		List list = null;
		List next = null;
		
		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo+1, pageSize);
			
			
			if(next==null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
				
				ServletUtility.setErrorMessage("No College Found", request);
				
			}if(next==null || next.size()==0){
				request.setAttribute("nextlist", "0");
			}else{
				request.setAttribute("nextlist", next.size());
			}
			
			
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
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
		return ORSView.COLLEGE_LIST_VIEW;
	}

}
