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
import co.in.bean.MarksheetBean;
import co.in.model.MarksheetModel;
import co.in.util.DataUtility;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;

@WebServlet(name="MarksheetListCtl",urlPatterns= {"/ctl/MarksheetListCtl"})
public class MarksheetListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MarksheetListCtl.class);
       
    protected BaseBean populateBean(HttpServletRequest request){
		
    	MarksheetBean bean = new MarksheetBean();
    	
    	bean.setName(DataUtility.getString(request.getParameter("name")));
    	bean.setRollno(DataUtility.getString(request.getParameter("rollno")));
    	
    	return bean;
    	
    	
    	
    }
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		int pageNo = 1;
		int pageSize= DataUtility.getInt(PropertyReader.getvalue("page.size"));
		
		MarksheetModel model = new MarksheetModel();
		MarksheetBean bean = (MarksheetBean) populateBean(request);
		
		List list = new ArrayList();
		List next =null;
		
				
			try {
				
				
				list = model.search(bean, pageNo, pageSize);
				next = model.search(bean, pageNo+1, pageSize);
				
				if(list==null || list.size()==0){
					ServletUtility.setErrorMessage("No Record Found", request);
				}if(next ==null || next.size()==0){
					request.setAttribute("nextlist", "0");
				}else{
					request.setAttribute("nextlist", next.size());
				}
				
					
				
			} catch (ApplicationException e) {
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
		String op = DataUtility.getString(request.getParameter("operation"));
		
		int pageNo = DataUtility.getInt(request.getParameter("pageno"));
		int pageSize = DataUtility.getInt(request.getParameter("pagesize"));
		
		String ids[] =request.getParameterValues("ids");
		
		pageNo = (pageNo==0)?1:pageNo;
		pageSize = (pageSize==0)?DataUtility.getInt(PropertyReader.getvalue("page.size")):pageSize;
		
		if(OP_SEARCH.equalsIgnoreCase(op)){
			pageNo = 1;
		}else if(OP_NEXT.equalsIgnoreCase(op)){
			pageNo++;
		}else if(OP_PREVIOUS.equalsIgnoreCase(op)){
			if(pageNo>1){
				pageNo--;
			}else{
				pageNo=1;
				ServletUtility.setErrorMessage("No Previous Page Available", request);
				
			}
			
		}else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		
		
		else if(OP_NEW.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		  
		}else if(OP_DELETE.equalsIgnoreCase(op)){
			MarksheetModel mod = new MarksheetModel();
			if(ids!=null && ids.length>0){
				pageNo=1;
				
				MarksheetBean bean1 = new MarksheetBean();
				
				for(String id2 : ids){
					
					int idnew = DataUtility.getInt(id2);
					bean1.setId(idnew);
					
					try {
						mod.delete(bean1);
						ServletUtility.setSuccessMessage("Marksheet Deleted Successfully", request);
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
		
		MarksheetBean bean = (MarksheetBean) populateBean(request);
		MarksheetModel model = new MarksheetModel();
		
		List next =null;

		
		List list = new ArrayList();
		
		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo+1, pageSize);
			
			if(list==null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
				ServletUtility.setErrorMessage("No Marksheet Found", request);
			}
			if(next ==null || next.size()==0){
				request.setAttribute("nextlist", "0");
			}else{
				request.setAttribute("nextlist", "next.size()");
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
		return ORSView.MARKSHEET_LIST_VIEW;
	}

}
