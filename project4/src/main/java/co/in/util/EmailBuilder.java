package co.in.util;

import java.util.HashMap;



/**
 * The Class EmailBuilder.
 * 
 * @author Yash Pandey
 *
 */
public class EmailBuilder {
	
	/**
	 * 
	 * 
	 * @param map
	 * @return
	 */
	public static String getUserRegistrationMessage(HashMap<String,String>Map){
		
		//System.out.println("33333333333333333333333333333333 in emailbuilder");
		StringBuilder msg=new StringBuilder();
		
		msg.append("<HTML><BODY>");
		msg.append("Registration is successfull for ORS Project");
		msg.append("<h1>Hi! Greeting from SunilOS!</h1>");
		msg.append("<p>Congratulations for registering on ORS!You can now "
				+ "access your ORS account online-anywhere,anytime and enjoy "
				+ "the flexibility to check the Marksheet Detail.</p>");
		msg.append("<p>As a security measure, we recomended that you change "
				+ "your password after you first log in  </p>");
		msg.append("<p>For any assistance,please feel free to call us at 9876543234 </p> ");
		msg.append("<p>you may also write to us at dsaf@fsdg</p>");
		msg.append("<p>we assure you the best service at "
				+ "all times and lock forward to a warm and long-standing association with you</p>");
		msg.append("<p> <a href='http://www.sunrays.co.in'>Sunrays technology</a></p>");
		msg.append("</body></html>");
		
		//System.out.println("2222222222222222222222222222222222222 is in emailbuilder");
		return msg.toString();
	}

	 public static String getForgetPasswordMessage(HashMap<String, String> map) {
	       
		 System.out.println("11111111111 ForgetPasswordMessage");
		 	StringBuilder msg = new StringBuilder();

	        msg.append("<html><body>");
	        msg.append("<h1>Your password is recovered !! " + map.get("firstName")
	                + " " + map.get("lastName") + "</h1>");
     
	 /*         msg.append("<p>To access account user login ID : " + map.get("login")
	          + " and password " + map.get("password") + "</p>");
	 */  
	        msg.append("<p><B>To access account user Login Id : "
	                + map.get("login") + "<BR>" + " Password : "
	                + map.get("password") + "</b></p>");
	        msg.append("</body></html>");

	        return msg.toString();
	    }
	 
	  public static String getChangePasswordMessage(HashMap<String, String> map) {
	       
		  
		  
		  StringBuilder msg = new StringBuilder();

	        msg.append("<html><body>");
	        msg.append("<h1>Your Password has been changed successfully !! "
	                + map.get("firstName") + " " + map.get("lastName") + " </h1>");
	        
/*	          msg.append("<P>To access account user login ID : " + map.get("login")
	          + " and password " + map.get("password") + "</P>");
*/	         
	        msg.append("<p><b>To access account user Login Id : "
	                + map.get("login") + "<br>" + " Password : "
	                + map.get("password") + "</b></p>");
	        msg.append("</body></html>");

	        return msg.toString();
	    }

}