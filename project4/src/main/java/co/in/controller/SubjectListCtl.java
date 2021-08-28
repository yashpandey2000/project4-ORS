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
import co.in.bean.SubjectBean;
import co.in.model.CourseModel;
import co.in.model.SubjectModel;
import co.in.util.DataUtility;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;


/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "SubjectListCtl", urlPatterns = { "/ctl/SubjectListCtl" })
public class SubjectListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SubjectListCtl.class);
	protected void preload(HttpServletRequest request){
		
		CourseModel model = new CourseModel();
		
		try {
		List list = model.list();
		request.setAttribute("courselist", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SubjectModel mod = new SubjectModel();
		
		try {
			List l =  mod.list();
			request.setAttribute("subjectlist", l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
   
	
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		SubjectBean bean = new SubjectBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("subjectid")));
		bean.setCourseid(DataUtility.getInt(request.getParameter("courseid")));
		
		populateDTO(bean, request);
		
		return bean;
		
		}
	

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
	int pageNo = 1;
	int pageSize= DataUtility.getInt(PropertyReader.getvalue("page.size"));
	
	List list = new ArrayList();
	
	SubjectModel model = new SubjectModel();
	SubjectBean bean = (SubjectBean) populateBean(request);
	
	
	try {
		list = model.search(bean, pageNo, pageSize);
		
		if(list==null || list.size()==0){
			
			ServletUtility.setErrorMessage("No Record Exist", request);
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
	    
	    pageNo = (pageNo == 0)? 1:pageNo;
	    pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getvalue("page.size")) : pageSize;
	
	    
	    if(OP_SEARCH.equalsIgnoreCase(op)){
	    	pageNo =1;
	    }else if(OP_PREVIOUS.equalsIgnoreCase(op)){
	    	
	    	if(pageNo > 1){
	    		pageNo--;
	    	}
	    	
	    	else{
	    		ServletUtility.setErrorMessage("No Previous Page", request);
	    		pageNo =1;
	    		
	    	}
	    	
	    	
	    }else if(OP_NEXT.equalsIgnoreCase(op)){
	    	pageNo++;
	    }else if(OP_NEW.equalsIgnoreCase(op)){
	    	ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
	    	return;
	    }else if(OP_RESET.equalsIgnoreCase(op)){
	    	ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
	    	return;
	    }else if(OP_DELETE.equalsIgnoreCase(op)){
	    	
	    	SubjectModel mod = new SubjectModel();
	    	SubjectBean bean1 = new SubjectBean();
	    	
	    	if(ids !=null && ids.length>0){
	    		
	    		for(String id2 : ids){
	    			int idnew = DataUtility.getInt(id2);
	    			bean1.setId(idnew);
	    			
	    			try {
						mod.delete(bean1);
						ServletUtility.setSuccessMessage("Subject Deleted Successfully", request);
					} catch (ApplicationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			
	    		}
	    		
	    		
	    	}else{
	    		
	    		ServletUtility.setErrorMessage("Select Atleast One Record", request);
	    	} 	
	    }
	
	
	List list  = new ArrayList();
	SubjectModel model = new SubjectModel();
	SubjectBean bean = (SubjectBean) populateBean(request);
	
	
	try {
	list = 	model.search(bean, pageNo, pageSize);
	
	if(list==null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
		
		ServletUtility.setErrorMessage("No Subject Found", request);
		
	}
	ServletUtility.setBean(bean, request);
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
		return ORSView.SUBJECT_LIST_VIEW;
	}

}
