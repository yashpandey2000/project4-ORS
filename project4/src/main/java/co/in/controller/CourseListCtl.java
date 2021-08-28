package co.in.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.bean.BaseBean;
import co.in.bean.CourseBean;
import co.in.model.CourseModel;
import co.in.util.DataUtility;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;



/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(CourseListCtl.class);
   
	protected BaseBean populateBean(HttpServletRequest request){
		
		CourseBean bean =  new CourseBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCname(DataUtility.getString(request.getParameter("cname")));
		bean.setDuration(DataUtility.getString(request.getParameter("duration")));
		bean.setDescription(DataUtility.getString(request.getParameter("desc")));
		
		populateDTO(bean, request);
		
		return bean;
		
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		int pageNo =1;
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));
		
		CourseModel model = new CourseModel();
		CourseBean bean =  new CourseBean();
		
		List list = new ArrayList();
		List next = null;
		
	        try {
			list = 	model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo+1, pageSize);
			if(list == null || list.size()==0){
				
				ServletUtility.setErrorMessage("No Record Found", request);
			}if(next==null || next.size()==0){
				request.setAttribute("nextlist", "0");
			}else{
				request.setAttribute("nextlist", next.size());
			}
			
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));
		
		String ids[] = request.getParameterValues("ids");
		
		int pageNo = DataUtility.getInt(request.getParameter("pageno"));
		int pageSize = DataUtility.getInt(request.getParameter("pagesize"));
		
		
		
		 pageNo = (pageNo == 0) ? 1:pageNo;
		pageSize = (pageSize==0) ? DataUtility.getInt(PropertyReader.getvalue("page.size")) : pageSize;
		
		if(OP_SEARCH.equalsIgnoreCase(op)){
			pageNo = 1;
			
		}else if(OP_PREVIOUS.equalsIgnoreCase(op)){
			
			pageNo--;
		}
		
		else if(OP_NEXT.equalsIgnoreCase(op)){
			pageNo++;
			
		}else if(OP_NEW.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
			
		}else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
		return;
		}else if(OP_DELETE.equalsIgnoreCase(op)){
			CourseModel mod = new CourseModel();
			CourseBean bean = new CourseBean();
			
			if(ids!=null && ids.length>0){
				
				for (String id2 : ids) {
					
					int idnew  = DataUtility.getInt(id2);
					bean.setId(idnew);
					
					ServletUtility.setSuccessMessage("Course Deleted Successfully", request);
					try {
						mod.delete(bean);
						
						//ServletUtility.forward(getView(), request, response);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
			}else {
				ServletUtility.setErrorMessage("Select Atleast One Record", request);
			}
			
			
		}
		
		
		CourseModel model = new CourseModel();
		
		CourseBean bean = (CourseBean) populateBean(request);
		
		List list = new ArrayList();
		List next = null;
		
		try {
		list = 	model.search(bean, pageNo, pageSize);
		next = model.search(bean, pageNo+1, pageSize);
		
		if(list==null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)){
			ServletUtility.setErrorMessage("No Course Found", request);
		}if(next==null || next.size()==0){
			request.setAttribute("nextlist", "0");
		}else{
			request.setAttribute("nextlist", next.size());
		}
		
		
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setpageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		
		
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("dopost end");
			
		
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COURSE_LIST_VIEW;
	}

}
