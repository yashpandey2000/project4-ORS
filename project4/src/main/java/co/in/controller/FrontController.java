package co.in.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.in.util.ServletUtility;


/**
 * @author Yash Pandey
 *
 */
@WebFilter(urlPatterns = {  "/ctl/*","/doc/*", })
public class FrontController implements Filter {

   
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request1 =  (HttpServletRequest) request;
		HttpServletResponse response1 = (HttpServletResponse) response;
		
		HttpSession session  = request1.getSession();
		
		if(session.getAttribute("user")==null){
		
			request1.setAttribute("error11", "Your session has been expired ! please login");
		
		String str = request1.getRequestURI();
		session.setAttribute("URI", str);
		
		ServletUtility.forward(ORSView.LOGIN_VIEW, request1, response1);
		return;
		
		}else{
		
		
		chain.doFilter(request1, response1);
		
		}
	}


	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
