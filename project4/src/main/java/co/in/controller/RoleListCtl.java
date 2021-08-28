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

import co.in.bean.BaseBean;
import co.in.bean.RoleBean;
import co.in.model.RoleModel;
import co.in.util.DataUtility;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;

/**
 * @author Yash Pandey
 *
 */
@WebServlet(name="RoleListCtl",urlPatterns={"/ctl/RoleListCtl"})
public class RoleListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(RoleListCtl.class);
	protected BaseBean populateBean(HttpServletRequest request){
	
		
		RoleBean bean = new RoleBean();
		
		
		bean.setName(DataUtility.getString(request.getParameter("name")));
		
		
		return bean;
	
		
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		int pageNo=1;
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));
		
		RoleModel model = new RoleModel();
		RoleBean bean  = (RoleBean) populateBean(request);
		
		List<RoleBean> list = new ArrayList<RoleBean>();
		
		try{
			
			list = model.search(pageNo, bean, pageSize);
			if(list.size()==0 || list==null){
				
				ServletUtility.setErrorMessage("No record exist", request);
				
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
		int pageNo = DataUtility.getInt(request.getParameter("pageno"));
		int pageSize = DataUtility.getInt(request.getParameter("pagesize"));
		
		
		pageNo = (pageNo==0)?1:pageNo;
		pageSize = (pageSize==0)?DataUtility.getInt(PropertyReader.getvalue("page.size")):pageSize;
		
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		String ids[] = request.getParameterValues("ids");
		
		if(OP_SEARCH.equalsIgnoreCase(op)){
			
			pageNo = 1;
			
			
			
		}else if(OP_PREVIOUS.equalsIgnoreCase(op)){
			
			if(pageNo>1){
				pageNo--;
			}else{
				pageNo=1;
			}
		
		}else if(OP_NEXT.equalsIgnoreCase(op)){
			
			pageNo++;
		}
		
		else if(OP_NEW.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			return;
			
		}
		
		else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;
		}else if(OP_DELETE.equalsIgnoreCase(op)){
			
			RoleModel model = new RoleModel();
			RoleBean bean = new RoleBean();
			
			if(ids!=null && ids.length>0){
				
				 for (String id2 : ids) {
					int idnew = DataUtility.getInt(id2);
					bean.setId(idnew);
					try{
					model.delete(bean);
					ServletUtility.setSuccessMessage("Role Deleted Successfully", request);
					}catch(Exception e){
						e.printStackTrace();
						}
					}
			}
			
 	else{
			ServletUtility.setErrorMessage("Select Atleast One Record", request);
		}
		
		}	
		RoleModel model = new RoleModel();
		RoleBean bean = (RoleBean) populateBean(request);
		
		
		List<RoleBean> list = new ArrayList<RoleBean>();
		try{
			
			list = model.search(pageNo, bean, pageSize);
			if(list==null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
				ServletUtility.setErrorMessage("No Role Found", request);
			}
			
		}catch(Exception e){
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
		return ORSView.ROLE_LIST_VIEW;
	}

}
