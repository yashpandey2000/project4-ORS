package co.in.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import co.in.Exception.ApplicationException;
import co.in.model.MarksheetModel;
import co.in.util.DataUtility;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;

/**
 * @author Yash Pandey
 *
 */
@WebServlet(name="MarksheetMeritListCtl",urlPatterns={"/ctl/MarksheetMeritListCtl"})
public class MarksheetMeritListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MarksheetMeritListCtl.class);   
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("doget start");
		int pageNo=1;
		int pageSize= DataUtility.getInt(PropertyReader.getvalue("page.size"));
		
		MarksheetModel model = new MarksheetModel();
		
		try {
			List list = null;
		list  = model.getMeritList(1,10);
		
		if(list==null && list.size()>0){
			ServletUtility.setErrorMessage("No Record Found", request);
		}
			
		request.setAttribute("list", list);
		
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setpageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("doget end");
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("dopost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		if(OP_BACK.equalsIgnoreCase(op)){
			
			ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
		}
		log.debug("dopost end");
	}


	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}

}
