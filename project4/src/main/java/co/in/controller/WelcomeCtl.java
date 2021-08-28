package co.in.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import co.in.util.ServletUtility;

/**
 * @author Yash Pandey
 *
 */
@WebServlet(name="WelcomeCtl",urlPatterns={"/WelcomeCtl"})
public class WelcomeCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(WelcomeCtl.class);
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
		
	}

	
	protected String getView(){
		return ORSView.WELCOME_VIEW;
		}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

}
