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
import co.in.bean.UserBean;
import co.in.model.UserModel;
import co.in.util.DataUtility;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;


/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UserListCtl.class);
   
	protected BaseBean populateBean(HttpServletRequest request){
	
		UserBean bean = new UserBean();
		
		bean.setFirstname(DataUtility.getString(request.getParameter("firstname")));
		bean.setLastname(DataUtility.getString(request.getParameter("lastname")));
		bean.setLoginid(DataUtility.getString(request.getParameter("loginid")));
		
		
		populateDTO(bean, request);
		
		return bean;
		
	}
	
	

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		
		UserModel model = new UserModel();
		
		UserBean bean = (UserBean) populateBean(request);
		
		List list = new ArrayList();
		
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));
		List next = null;
		
		try{
			
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo+1, pageSize);
			
			if(list == null || list.size()==0){
				ServletUtility.setErrorMessage("no record exist", request);
			}
			if(next==null || next.size() == 0){
				request.setAttribute("nextlist", "0");
			}
			else{
				request.setAttribute("nextlist", next.size());
			}
			
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			System.out.println(pageNo+"-------------------------------");
			ServletUtility.setpageSize(pageSize, request);
			System.out.println(pageSize+"------------------------------");
			ServletUtility.forward(getView(), request, response);
			
			
		}catch(Exception e){
			e.printStackTrace();
			ServletUtility.handleException(e, request, response);
		}
		
		
		log.debug("doget end");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		String ids[] = request.getParameterValues("ids");
		
		int pageNo = DataUtility.getInt(request.getParameter("pageno"));
		int pageSize = DataUtility.getInt(request.getParameter("pagesize"));
		
		pageNo = (pageNo == 0)? 1 : pageNo;
		pageSize = (pageSize == 0 ) ? DataUtility.getInt(request.getParameter("page.size")):pageSize;
		
		
		if(OP_SEARCH.equalsIgnoreCase(op)){
			pageNo=1;
		}else if(OP_PREVIOUS.equalsIgnoreCase(op)){
			if(pageNo > 1){
				pageNo--;
			}else {
				pageNo = 1;
				ServletUtility.setErrorMessage("No Previous Page", request);
			}
		
		}else if(OP_NEXT.equalsIgnoreCase(op)){
			pageNo++;
			
		}
		
		else if(OP_NEW.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
		     return;	
		}
		
		else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
		    return;
		}
		
		
		
		else if(OP_DELETE.equalsIgnoreCase(op)){
			
			if(ids !=null && ids.length >0){
				
				UserModel mod = new UserModel();
				UserBean bean1 = new UserBean();
				
				 for (String idnew : ids) {
					 
					 
					 int idnew1 = DataUtility.getInt(idnew);
					 bean1.setId(idnew1);
					 
					
						 
						 try {
							try {
								mod.delete(bean1);
								ServletUtility.setSuccessMessage("User Deleted Successfully", request);
							} catch (ApplicationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 	
				}
				
			}else{
				
				ServletUtility.setErrorMessage("Select Atleast One Record", request);
			}
			
			
			
		}
		
		
		List<UserBean> list = new ArrayList<UserBean>();
		
		List next =null;

		UserModel model = new UserModel();
		
		UserBean bean = (UserBean) populateBean(request);
		
		try{
			
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo+1, pageSize);
			
			if(list == null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
				ServletUtility.setErrorMessage("No User Found", request);
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
			
				
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		log.debug("dopost end");
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_LIST_VIEW;
	}

}
