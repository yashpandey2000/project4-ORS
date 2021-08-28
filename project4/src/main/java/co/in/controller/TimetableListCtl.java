package co.in.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mysql.fabric.xmlrpc.base.Data;

import co.in.bean.BaseBean;
import co.in.bean.TimeTableBean;
import co.in.model.CourseModel;
import co.in.model.SubjectModel;
import co.in.model.TimeTableModel;
import co.in.util.DataUtility;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;


/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "TimetableListCtl", urlPatterns = { "/ctl/TimetableListCtl" })
public class TimetableListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TimetableListCtl.class);
    protected void preload(HttpServletRequest request){
    	
    	CourseModel cm = new CourseModel();
    	
    	try {
			List list1 = cm.list();
			request.setAttribute("courselist", list1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	SubjectModel sm = new SubjectModel();
    	 try {
		List list2 = sm.list();
		request.setAttribute("subjectlist", list2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    protected BaseBean populateBean(HttpServletRequest request){
		
    	
    	TimeTableBean bean = new TimeTableBean();
    	
    	bean.setCourseid(DataUtility.getInt(request.getParameter("courseid")));
    	bean.setSubjectid(DataUtility.getInt(request.getParameter("subjectid")));
    	bean.setExamdate(DataUtility.getDate(request.getParameter("examdate")));
    	bean.setExamtime(DataUtility.getString(request.getParameter("examtime")));
    	populateDTO(bean, request);
    	
    	return bean;
    	
    }
    
    
    
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));
		TimeTableModel model = new TimeTableModel();
		TimeTableBean bean = (TimeTableBean) populateBean(request);
		List list = new ArrayList();
		
		try {
			list = model.search(bean, pageNo, pageSize);
			if(list==null || list.size()==0){
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			
			
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
		
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		
		
		
		pageNo = (pageNo==0)?1:pageNo;
		pageSize = (pageSize==0)?DataUtility.getInt(PropertyReader.getvalue("page.size")):pageSize;
		
		
		if(OP_SEARCH.equalsIgnoreCase(op)){
			pageNo =1;
		}
		else if(OP_PREVIOUS.equalsIgnoreCase(op)){
			if(pageNo>1){
				pageNo--;
			}else{
				ServletUtility.setErrorMessage("No Previous Page", request);
				pageNo=1;
			}
		}else if(OP_NEXT.equalsIgnoreCase(op)){
			pageNo++;
		}else if(OP_NEW.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}else if(OP_RESET.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		}else if(OP_BACK.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
		return;
		}else if(OP_DELETE.equalsIgnoreCase(op)){
			TimeTableModel mod = new TimeTableModel();
			TimeTableBean be = new TimeTableBean();
			
			if(ids!=null && ids.length>0){
				for (String id2 : ids) {
					int idnew = DataUtility.getInt(id2);
					be.setId(idnew);
					
					
					try {
						mod.delete(be);
						ServletUtility.setSuccessMessage("Timetable Deleted Successfully", request);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}else{
				ServletUtility.setErrorMessage("Select Atleast One Record", request);
			}
			
			
		}
		
		TimeTableModel model = new TimeTableModel();
		TimeTableBean bean = (TimeTableBean) populateBean(request);
		
		List next =null;
		
		List list = new ArrayList();
		try {
			
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo+1, pageSize);
			
			
			if(list == null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
				ServletUtility.setErrorMessage("No timetable Found", request);
				
			}
			
			if(next ==null || next.size()==0){
				request.setAttribute("nextlist", "0");
			}else{
				request.setAttribute("nextlist", "next.size()");
			}
			
			
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setpageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("dopost end");
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIMETABLE_LIST_VIEW;
	}

}
