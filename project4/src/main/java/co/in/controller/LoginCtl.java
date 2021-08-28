package co.in.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import co.in.bean.BaseBean;
import co.in.bean.RoleBean;
import co.in.bean.UserBean;
import co.in.model.RoleModel;
import co.in.model.UserModel;
import co.in.util.DataUtility;
import co.in.util.DataValidator;
import co.in.util.PropertyReader;
import co.in.util.ServletUtility;

/**
 * @author Yash Pandey
 *
 */
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(LoginCtl.class);
	
	private static final long serialVersionUID = 1L;

	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "LogOut";

	protected boolean validate(HttpServletRequest request) {
		log.debug("validate start");

		boolean pass = true;

		String op = request.getParameter("operation");
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("loginid"))) {
			request.setAttribute("loginid", PropertyReader.getvalue("error.require", "Loginid"));
			pass = false;

		} else if (!DataValidator.isEmail(request.getParameter("loginid"))) {
			request.setAttribute("loginid", PropertyReader.getvalue("error.email", "Invalid"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {

			request.setAttribute("password", PropertyReader.getvalue("error.require", "Password"));
			pass = false;
		}

		else if (!DataValidator.isPassword(request.getParameter("password"))) {

			request.setAttribute("password", PropertyReader.getvalue("error.password", "Invalid"));
			pass = false;
		}
		 log.debug("validate end");
		return pass;

	}

	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLoginid(DataUtility.getString(request.getParameter("loginid")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		return bean;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("doget start");
		HttpSession session = request.getSession();

		String op = DataUtility.getString(request.getParameter("operation"));
		

		if (OP_LOG_OUT.equals(op)) {

			session = request.getSession();
			session.invalidate();
			ServletUtility.setSuccessMessage("Logged Out Successfully", request);

		}
		log.debug("doget end");
		ServletUtility.forward(getView(), request, response);
	}

	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.LOGIN_VIEW;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("dopost start");
		HttpSession session = request.getSession(true);

		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();
		RoleModel rm = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			UserBean ub = (UserBean) populateBean(request);

			try {

				ub = model.authenticate(ub.getLoginid(), ub.getPassword());

				if (ub != null) {

					session.setAttribute("user", ub);
					
					long roleid = ub.getRoleid();

					RoleBean rolebean = rm.findByPk(roleid);

					if (rolebean != null) {
						session.setAttribute("role", rolebean.getName());
					}

//code of URI					
					
					String str = (String)session.getAttribute("URI");
					System.out.println(str + "=====================");

					if (str == null) {

						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					}

					else {

						ServletUtility.redirect(str, request, response);

						return;
					}

				} else {

					ub = (UserBean) populateBean(request);
					ServletUtility.setBean(ub, request);
					ServletUtility.setErrorMessage("Invalid Loginid & Password", request);
					ServletUtility.forward(getView(), request, response);
				}

			} catch (Exception e) {
				e.printStackTrace();
				request.getAttribute("exception");
				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}
		log.debug("dopost end");

	}

	
	
}
