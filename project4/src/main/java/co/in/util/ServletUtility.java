package co.in.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.in.bean.BaseBean;
import co.in.controller.BaseCtl;
import co.in.controller.ORSView;
import co.in.model.BaseModel;
/**
 * The Class ServletUtility.
 * @author Yash Pandey
 *
 */
public class ServletUtility {
	
	/**
	 * Forward.
	 *
	 * @param page the page
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	

	public static void forward(String page,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		
		RequestDispatcher rd = request.getRequestDispatcher(page);
	    rd.forward(request, response);
	
	}

	/**
	 * Forward to Layout View.
	 *
	 * @param page the page
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void forwardView(String page , HttpServletRequest request ,HttpServletResponse response)throws ServletException , IOException{
		
		request.setAttribute("bodypage", page);
		RequestDispatcher rd = request.getRequestDispatcher(ORSView.LAYOUT_VIEW);
	  rd.forward(request, response);
	}
	
	/**
	 * Redirect to given JSP/Servlet.
	 *
	 * @param page the page
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	public static void redirect(String page , HttpServletRequest request , HttpServletResponse response) throws IOException{
		response.sendRedirect(page);
	}
	
	/**
	 * Sets the bean.
	 *
	 * @param bean the bean
	 * @param request the request
	 */
	
	public static void setBean(BaseBean bean , HttpServletRequest request){
		request.setAttribute("bean",bean);
	}
	
	/**
	 * Redirect to Application Error Handler Page.
	 *
	 * @param e the e
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	public static void handleException(Exception e , HttpServletRequest request , HttpServletResponse response) throws IOException{
		request.setAttribute("exception", e);
		
		response.sendRedirect(ORSView.ERROR_CTL);
	}
	
	/**
	 * Sets the error message.
	 *
	 * @param msg the msg
	 * @param request the request
	 */
	
	public static void setErrorMessage(String msg , HttpServletRequest request){
		request.setAttribute(BaseCtl.MSG_ERROR, msg);
	}
	
	/**
	 * Gets the error message.
	 *
	 * @param property the property
	 * @param request the request
	 * @return the error message
	 */
	
	public static String getErrorMessage(String property , HttpServletRequest request){
		String val = (String) request.getAttribute(property);
		if(val == null){
			return "";
		}else{
			return val;
		}
		
	}
	
	/**
	 * Gets the error message.
	 *
	 * @param request the request
	 * @return the error message
	 */
	
	public static String getErrorMessage( HttpServletRequest request){
		String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
		if(val == null){
			return "";
		}else{
			return val;
		}
		
	}
	
	/**
	 * Sets the success message.
	 *
	 * @param msg the msg
	 * @param request the request
	 */
	
	public static void setSuccessMessage(String msg , HttpServletRequest request){
		request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
	}
	
	
	/**
	 * Gets the success message.
	 *
	 * @param request the request
	 * @return the success message
	 */
	
	
	public static String getSuccessMessage(HttpServletRequest request){
		String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
		
		if(val == null){
			return "";
		}else{
			
			return val;
		}
		
			
	}
	
	/**
	 * Sets the model.
	 *
	 * @param model the model
	 * @param request the request
	 */
	
	
	
	public static void setModel(BaseModel model , HttpServletRequest request){
		request.setAttribute("model", model);
	}
	
	/**
	 * Gets the model.
	 *
	 * @param request the request
	 * @return the model
	 */
	
	public static BaseModel getModel(HttpServletRequest request){
		return (BaseModel) request.getAttribute("model");
		
	}
	
	/**
	 * Get request parameter to display. If value is null then return empty
	 * string
	 *
	 * @param property the property
	 * @param request the request
	 * @return the parameter
	 */
	
	
	public static String getParameter(String property,HttpServletRequest request){
		
		String val = (String) request.getParameter(property);
		if(val ==null){
			return "";
		}else{
	
		return val;
		}
	
	}
	
	/**
     * Sets the list.
     *
     * @param list the list
     * @param request the request
     */
	
	public static void setList(List list , HttpServletRequest request){
		request.setAttribute("list", list);
		
		}
	
	
	 
    /**
     * Gets the list.
     *
     * @param request the request
     * @return the list
     */
	
		public static List getList(HttpServletRequest request){
			return (List) request.getAttribute("list");
			
			}
		
		/**
	     * Sets the page no.
	     *
	     * @param pageNo the page no
	     * @param request the request
	     */
		
	
		public static void setPageNo(int pageNo , HttpServletRequest request){
			request.setAttribute("pageNo", pageNo);
		}
		
		 /**
	     * Gets the page no.
	     *
	     * @param request the request
	     * @return the page no
	     */
	
		
		public static int getpageNo(HttpServletRequest request){
			return (Integer) request.getAttribute("pageNo");
			
		}
		
		
		  /**
	     * Sets the page size.
	     *
	     * @param pageSize the page size
	     * @param request the request
	     */
		public static void setpageSize(int pageSize , HttpServletRequest request){
			request.setAttribute("pageSize", pageSize);
		}
		
		
		/**
	     * Gets the page size.
	     *
	     * @param request the request
	     * @return the page size
	     */
		
		
		
		public static int getpageSize(HttpServletRequest request){
			return (Integer) request.getAttribute("pageSize");
		}
		
		
		
		
		

}
