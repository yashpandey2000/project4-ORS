package co.in.util;

import java.util.ResourceBundle;

/**
 * Read the property values from application properties file using Resource Bundle
 *
 * @author Yash Pandey
 *
 */
public class PropertyReader {

	private static ResourceBundle rb = ResourceBundle.getBundle("co.in.Bundle.system");
	
	
	
	/**
     * Return value of key
     *
     * @param key
     * @return
     */
	public static String getvalue(String key){
		
		String val = null;
		try{
			
			val = rb.getString(key);	
		}catch(Exception e){
			val = key;
		}
		return val;	
		
	}
	
	
	/**
     * Gets String after placing param values
     *
     * @param key
     * @param param
     * @return String
     */
	
	
	public static String getvalue(String key , String param){
		
		String msg = getvalue(key);
		msg = msg.replace("{0}", param);
		return msg;
		}
	
	/**
     * Gets String after placing params values
     *
     * @param key
     * @param params
     * @return
     */
	
	public static String getvalue(String key , String[] params){
	
		String msg = getvalue(key);
		for(int i=0 ; i<params.length ; i++){
			msg = msg.replace("{"+i+"}", params[i]);
		}

		return msg;
}
	
	
	/**
     * Test method
     *
     * @param args
     */
	
	public static void main(String[] args) {
		
		String param = "name";
		System.out.println(PropertyReader.getvalue(param));
		
	}
	
}
