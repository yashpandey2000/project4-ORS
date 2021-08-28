package co.in.util;

import java.text.ParseException;
import java.util.Date;

/**
 * This class validates input data
 * 
 * @author Yash Pandey
 *
 */
public class DataValidator {
	
	
	/**
	 * Check if value is Null
	 * @param val :val
	 * @return boolean
	 */
	public static boolean isNull(String val) {
	//	System.out.println("------ isNull in dv "+val);
        if (val == null || val.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }
	
	/**
	 * check if value is Not Null
	 * @param val :value
	 * @return boolean
	 */
	public static boolean isNotNull(String val) {
		System.out.println("-----isNotNull in dv "+val);
	        return !isNull(val);
	    }
	 
	/**
	 * check if an value is an Integer
	 * @param val :value
	 * @return boolean
	 */
	public static boolean isInteger(String val) {
		//System.out.println("----- isInteger in dv "+val);

	        if (isNotNull(val)) {
	            try {
	                int i = Integer.parseInt(val);
	                return true;
	            } catch (NumberFormatException e) {
	                return false;
	            }

	        } else {
	            return false;
	        }
	    }
	
	/**
	 * check if an value is an Long
	 * @param val :value
	 * @return boolean
	 */
	public static boolean isLong(String val) {
		//System.out.println("------ isLong in dv "+val);
	        if (isNotNull(val)) {
	            try {
	                long i = Long.parseLong(val);
	                return true;
	            } catch (NumberFormatException e) {
	                return false;
	            }

	        } else {
	            return false;
	        }
	    }
	   
	/**
	  * Check if value is valid EmailId
	  * @param val :val
	  * @return boolean
	  */   
	public static boolean isEmail(String val) {
		 //  System.out.println("------ isemail in dv "+val);

	        String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	        if (isNotNull(val)) {
	            try {
	                return val.matches(emailreg);
	            } catch (NumberFormatException e) {
	                return false;
	            }

	        } else {
	            return false;
	        }
	    }
	  
	/**
	  * check if value is date
	  * @param val :val
	  * @return boolean
	  */
	public static boolean isDate(String val) {
		 //  System.out.println("------- isdate in dv "+val);

	        Date d = null;
	        if (isNotNull(val)) {
	            d = DataUtility.getDate(val);
	        }
	        return d != null;
	    }
	/**
	  * Test Above Methods
	  * @param args :args
	  */   
	public static void main(String[] args) {

//	        System.out.println("Not Null 2 DV(main) " + isNotNull("ABC"));
//	        System.out.println("Not Null 3 DV(main)" + isNotNull(null));
//	        System.out.println("Not Null 4 DV(main)" + isNull("123"));
//
//	        System.out.println("Is Int DV(main)" + isInteger(null));
//	        System.out.println("Is Int DV(main)" + isInteger("ABC1"));
//	        System.out.println("Is Int DV(main)" + isInteger("123"));
//	        System.out.println("Is Int DV(main)" + isNotNull("123"));
	    }
	public static boolean isName(String name) {
		//System.out.println("-------  isname in DV "+name);


		String namereg = "^[^-\\s][\\p{L} .']+$";
		

		//String sname = name.trim();

		if (isNotNull(name) && name.matches(namereg)) {

			return true;
		} else {
			return false;
		}
		
	}
	public static boolean isValidAge(String val)
	{
		System.out.println("------- isvalidage in dv "+val);
		
		boolean pass = false;
		if (isDate(val)) {
			Date cdate = new Date();
			try {
				Date userdate = DataUtility.formatter.parse(val);
				int age = cdate.getYear()-userdate.getYear();
				System.out.println("final age  "+age);
				if(age>=18){
					pass=true;
				}
			} catch (ParseException e) {
				
			}
		}
		
		return pass;
	}
	
	/**
	 * Checks if value of Password is in between 8 and 12 characters
	 * 
	 * @param val
	 * @return true or false
	 */
	public static boolean isPasswordLength(String val) {

		if (isNotNull(val) && val.length() >= 8 && val.length() <= 12) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean isPassword(String pass) {
		
		System.out.println("validate isPassword");
		String passreg = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
		//String passreg="^[0-9a-zA-Z]{5}$";
		//String spass = pass.trim();
		//int checkIndex = spass.indexOf(" ");
                                //checkIndex==-1
		if (isNotNull(pass) && pass.matches(passreg) ) {
			System.out.println("true");
			return true;
		}

		else {
			return false;
		}

	
	}
	public static boolean isRollNo(String roll) { // my method created
		
		String rollreg = "[a-zA-Z]{2}[0-9]{4}";
		//String sroll = roll.trim();

		System.out.println("------ isRollNo "+roll);	
		if (DataValidator.isNotNull(roll)) {

			boolean check = roll.matches(rollreg);
			//System.out.println("isRollNo"+check);
			return check;
		}

		else {

			return false;
		}
	}

	/**
	 * Checks if value of Mobile No is 10
	 * 
	 * @param val :value
	 * @return boolean
	 */
	public static boolean isPhoneLength(String val) {
	
		if (isNotNull(val) && val.length() == 10) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Checks if value is valid Phone No.
	 * 
	 * @param val :val
	 * @return boolean
	 */
	public static boolean isMobileNo(String val) {
	//	System.out.println("------ isphoneno "+val);

		String phonereg = "^[6-9][0-9]{9}$";


		if (isNotNull(val)) {
			try {
				return val.matches(phonereg);
			} catch (NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}

	}


	
	
	public static boolean isAddress(String pass) { 

		//System.out.println("validate pass");
		
		String passreg = "^[0-9a-zA-Z\\s,-]+$";
		
		
		if (isNotNull(pass) && pass.matches(passreg)) {
			System.out.println("true");
			return true;
		}

		else {
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}