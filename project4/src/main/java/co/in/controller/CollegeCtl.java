package co.in.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.BaseBean;
import co.in.bean.CollegeBean;
import co.in.model.CollegeModel;
import co.in.util.DataUtility;
import co.in.util.DataValidator;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;


/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {
	
	private static Logger log = Logger.getLogger(CollegeCtl.class);

	
	private static final long serialVersionUID = 1L;
       
	
	protected boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("cname"))){
			request.setAttribute("cname", PropertyReader.getvalue("error.require", "College Name"));
			pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("cname"))){
			request.setAttribute("cname","Invalid College Name");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("caddress"))){
			request.setAttribute("caddress", PropertyReader.getvalue("error.require", "College Addrss"));
			pass = false;
		}
		else if(!DataValidator.isAddress(request.getParameter("caddress"))){
			request.setAttribute("caddress", "Invalid Address");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("cstate"))){
			request.setAttribute("cstate", PropertyReader.getvalue("error.require", "State"));
		pass = false;	
		}
		else if(!DataValidator.isName(request.getParameter("cstate"))){
			request.setAttribute("cstate", "Invalid State Name");
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("ccity"))){
			request.setAttribute("ccity", PropertyReader.getvalue("error.require", "City"));
			pass = false;
		}
		
		else if(!DataValidator.isName(request.getParameter("ccity"))){
			request.setAttribute("ccity", "Invalid City Name");			
		pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("cphone"))){
			request.setAttribute("cphone",PropertyReader.getvalue("error.require", "Phone No"));
			pass = false;
		}
		
		else if(!DataValidator.isPasswordLength(request.getParameter("cphone"))){
			request.setAttribute("cphone", "Invalid Phone No");
			pass = false;
		}
		
		log.debug("validate end");
		return pass;
		
	}
	
	
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		CollegeBean bean = new CollegeBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("cname")));
		bean.setAddress(DataUtility.getString(request.getParameter("caddress")));
		bean.setState(DataUtility.getString(request.getParameter("cstate")));
		bean.setCity(DataUtility.getString(request.getParameter("ccity")));
		bean.setPhoneno(DataUtility.getString(request.getParameter("cphone")));
		
		populateDTO(bean, request);
		
		return bean;
		
	}
	
	

   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");

		long id = DataUtility.getLong(request.getParameter("id"));
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		CollegeModel model = new CollegeModel();
		
		CollegeBean bean = null;
		
		if(id>0){
			
			try{
				
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
				
			}catch(Exception e){
				e.printStackTrace();
			}	
			
		}
		
		ServletUtility.forward(getView(), request, response);
		log.debug("doget end");

	}

	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");

		long id = DataUtility.getLong(request.getParameter("id"));
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		CollegeModel model = new CollegeModel();
		
		
		System.out.println(" post method update method starts ========================");
		if(OP_SAVE.equalsIgnoreCase(op)){
			
		CollegeBean bean = (CollegeBean) populateBean(request);	
			
			
			try {
				id = model.add(bean);
				bean.setId(id);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("College Added Successfully", request);
				
			} catch (ApplicationException e) {
	
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				
				e.printStackTrace();
				ServletUtility.setErrorMessage("College Already Exist", request);
			    
			}
			 
		ServletUtility.forward(getView(), request, response);
			
		}
		
		else if(OP_UPDATE.equalsIgnoreCase(op)){
			 id = DataUtility.getLong(request.getParameter("id"));
			 System.out.println(id+"id of college");
			System.out.println("update method starts");
			
			if(id>0){
				CollegeBean bean1 =  (CollegeBean) populateBean(request);
				
				System.out.println("update method populate works");
					try {
						model.update(bean1);
						System.out.println("update method update works");
						ServletUtility.setSuccessMessage("College Updated Successfully", request);
					} catch (DuplicateRecordException e) {
						
						e.printStackTrace();
						
					} catch (ApplicationException e) {
						
						e.printStackTrace();
						ServletUtility.handleException(e, request, response);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				ServletUtility.setBean(bean1, request);
				ServletUtility.forward(getView(), request, response);	
				
			}
			
			
				
		}
		else if(OP_CANCEL.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			
			return;
			
		}else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			return;
		}
				
		log.debug("dopost end");

		
	}



	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COLLEGE_VIEW;
	}

}
