package co.in.controller;

import java.io.IOException;

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
import co.in.util.DataValidator;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;

/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "GetMarksheetCtl", urlPatterns = { "/ctl/GetMarksheetCtl" })
public class GetMarksheetCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(GetMarksheetCtl.class);
	protected boolean validate(HttpServletRequest request){
		log.debug("validate start");
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("rollno"))){
			request.setAttribute("rollno", PropertyReader.getvalue("error.require", "Roll No"));
			pass = false;
		}
		/*else if(!DataValidator.isRollNo(request.getParameter("rollno"))){
			request.setAttribute("rollno", "Enter Valid RollNo");
			pass = false;
		}*/
		
		log.debug("validate end");
		return pass;
		
	}
	
	
	protected BaseBean populateBean(HttpServletRequest request){
		
		MarksheetBean bean = new MarksheetBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollno(DataUtility.getString(request.getParameter("rollno")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		bean.setPhysics(DataUtility.getInt(request.getParameter("maths")));
		
		
		return bean;
		}
	
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletUtility.forward(getView(), request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
		MarksheetModel model = new MarksheetModel();
		
		MarksheetBean bean = (MarksheetBean) populateBean(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		long id  = DataUtility.getLong(request.getParameter("id"));
		
		if(OP_GO.equalsIgnoreCase(op)){
			
			try {
				bean = model.findByRollNo(bean.getRollno());
				
				if(bean.getRollno()!=null){
					ServletUtility.setBean(bean, request);
					ServletUtility.forward(getView(), request, response);
					
				}else{
					
					ServletUtility.setErrorMessage("RollNo Does Not Exist", request);
				    ServletUtility.forward(getView(), request, response);
				
				}
				
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			return;
			}
			
			
		}else if(OP_RESET.equalsIgnoreCase(op)){
			
			ServletUtility.redirect(ORSView.GET_MARKSHEET_CTL, request, response);
		return;
		}
		
		log.debug("dopost end");
		
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.GET_MARKSHEET_VIEW;
	}

}
