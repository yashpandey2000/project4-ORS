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

import co.in.bean.BaseBean;
import co.in.bean.FacultyBean;
import co.in.model.FacultyModel;
import co.in.util.DataUtility;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;

/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "FacultyListCtl", urlPatterns = { "/ctl/FacultyListCtl" })
public class FacultyListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(FacultyListCtl.class);
	protected BaseBean populateBean(HttpServletRequest request) {

		FacultyBean bean = new FacultyBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstname(DataUtility.getString(request.getParameter("fname")));
		bean.setLastname(DataUtility.getString(request.getParameter("lname")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setLoginid(DataUtility.getString(request.getParameter("loginid")));
		bean.setQualification(DataUtility.getString(request.getParameter("qual")));
		bean.setMobileno(DataUtility.getString(request.getParameter("mobile")));
		bean.setCollegeid(DataUtility.getInt(request.getParameter("collegeid")));
		bean.setCollegename(DataUtility.getString(request.getParameter("collegename")));
		bean.setCourseid(DataUtility.getInt(request.getParameter("courseid")));
		bean.setCoursename(DataUtility.getString(request.getParameter("coursename")));
		bean.setSubjectid(DataUtility.getInt(request.getParameter("subjectid")));
		bean.setSubjectname(DataUtility.getString(request.getParameter("subjectname")));

		populateDTO(bean, request);

		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("doget start");
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getvalue("page.size"));

		List list = new ArrayList();

		FacultyModel model = new FacultyModel();
		FacultyBean bean = new FacultyBean();

		try {
			list = model.search(bean, pageNo, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setpageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("doget end");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("dopost start");
		long id = DataUtility.getInt(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));

		String ids[] = request.getParameterValues("ids");

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getvalue("page.size")) : pageSize;

		
		if(OP_SEARCH.equalsIgnoreCase(op)){
			pageNo = 1;
		}
		else if(OP_PREVIOUS.equalsIgnoreCase(op)){
			if(pageNo>1){
				pageNo--;
			}else{
				pageNo = 1;
			}
			
		}else if(OP_NEXT.equalsIgnoreCase(op)){
			pageNo++;
		}
		else if(OP_NEW.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
		
		return;
		}
		
		else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
		return;
		}
		
		else if(OP_BACK.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		}
		else if(OP_DELETE.equalsIgnoreCase(op)){
			
			FacultyModel mod = new FacultyModel();
			FacultyBean be = new FacultyBean();
			
			if(ids !=null && ids.length>0){
				
				for (String id2 : ids) {
					int idnew = DataUtility.getInt(id2);
				    be.setId(idnew);
				    
				    
				    try {
						mod.delete(be);
						ServletUtility.setSuccessMessage("Faculty Deleted Successfully", request);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				}
				
				
			}else{
				
				ServletUtility.setErrorMessage("Select Atleast One Record", request);
			}
				
			
		}
		
		List list = new ArrayList();
		
		FacultyModel model = new FacultyModel();
		FacultyBean bean = null;
		List next = null;
		bean = (FacultyBean) populateBean(request);
		try {
			
		    list = 	model.search(bean, pageNo, pageSize);
		    next = model.search(bean, pageNo+1, pageSize);
		    if(next==null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
		    	ServletUtility.setErrorMessage("No Faculty Found", request);
		    }if(next==null || next.size()==0){
				request.setAttribute("nextlist", "0");
			}else{
				request.setAttribute("nextlist", next.size());
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
		return ORSView.FACULTY_LIST_VIEW;
	}

}
