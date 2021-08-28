package co.in.testmodel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import co.in.Exception.ApplicationException;
import co.in.bean.UserBean;
import co.in.model.UserModel;
import co.in.util.DataUtility;

/**
 * @author Yash Pandey
 *
 */
public class TestUserModel {
	
	public static UserModel model = new UserModel();
	
	
	public static void main(String[] args) throws ParseException, ApplicationException {
		
		
		//testadd();
		//testfindbyloginid();
		//testfindbypk();
		//testdelete();
		// testupdate();
	   // testsearch();
	    	//testlist();
		//testgetrole();
		//testauthenticate();
		
		//testchangePassword();
		//testforgotpassword(); 
	
		
		
		
	}
	




	private static void testforgotpassword() {
	
		 try{
			// UserBean bean = new UserBean();
			 model.forgetpassword("sharma2323@gmail.com");
			 System.out.println("forgot password is working");
			 
		 }catch(Exception e){
			 
			 e.printStackTrace();
		 }
		
	}




	public static void testchangePassword(){
		   try{
			   UserBean bean = model.findByLogin("ramesh366@gmail.com");
			   
			      /* String oldPass=bean.getPassword();
			       * 
			       bean.setId(5);
			       bean.setPassword("ankur");
			       bean.setConfirmPassword("ankur");
			       
			       String newPass=bean.getPassword();*/
			       
			   
			       model.changePassword(4L,"1234","55555");
			       System.out.println("password has been change successfully");
		   }
		   catch(Exception e){
			   e.printStackTrace();
		   }
	   }	




	private static void testregisteruser() {
	UserBean bean = new UserBean();
	
	try{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		
	bean.setFirstname("ram");
	bean.setLastname("mehta");
	bean.setLoginid("keldgdfgash2323@gmail.com");
	bean.setPassword("1234");
	//bean.setConfirmpassword("1234");
	bean.setDob(sdf.parse("19/5/2016"));
	bean.setGender("male");
	bean.setRoleid(18);
	
	long pk = model.registerUser(bean);
	
		System.out.println("successful register");
	}catch(Exception e){
		e.printStackTrace();
	}
		
		
	}




	private static void testgetrole() throws ApplicationException {
		try{
		UserBean bean = new UserBean();
		List list = new ArrayList();
		bean.setRoleid(16);
		list=model.getRoles(bean);
		Iterator<UserBean> it = list.iterator();
		while(it.hasNext()){
			bean=it.next();
			
			 System.out.println(bean.getId());
			  System.out.println(bean.getFirstname());
			  System.out.println(bean.getLastname());
			  System.out.println(bean.getLoginid());
             System.out.println(bean.getPassword());
             System.out.println(bean.getDob());
             System.out.println(bean.getRoleid());
             System.out.println(bean.getUnsuccessfullogin());
             System.out.println(bean.getGender());
             System.out.println(bean.getLastlogin());
             System.out.println(bean.getLock());			
			
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}




	private static void testauthenticate() {
		
		
		try{
			
			UserBean bean = new UserBean();
			bean.setLoginid("sharma2323@gmail.com");
			bean.setPassword("0872");
			bean=model.authenticate(bean.getLoginid(),bean.getPassword());
			if(bean!=null){
				
				System.out.println("login successful");
			}
			else{
				
				System.out.println("invalid loginid & password");
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}




	private static void testlist() {
		
		UserBean bean = new UserBean();
		List<UserBean> l = new ArrayList();		
		try{
			
			l = model.list(0,0);
			Iterator it = l.iterator();
			while(it.hasNext()){
				bean =(UserBean) it.next();
		          System.out.println(bean.getId());
		          System.out.println(bean.getFirstname());
		          System.out.println(bean.getLastname());
		          System.out.println(bean.getLoginid());
		          System.out.println(bean.getPassword());
		          System.out.println(bean.getDob());
		          System.out.println(bean.getRoleid());
		          System.out.println(bean.getUnsuccessfullogin());
		          System.out.println(bean.getGender());
		          System.out.println(bean.getLastlogin());
		          System.out.println(bean.getLock());
				
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}



	private static void testsearch() {
		
		   try {
		UserBean bean = new UserBean();
		   List<UserBean> list = new ArrayList<UserBean>();
		   bean.setId(1);
		
			list= model.search(bean,0,0);
		   Iterator<UserBean> it = list.iterator();
		 
		   while(it.hasNext()){
			   bean=it.next();
			   System.out.println(bean.getId());
	           System.out.println(bean.getFirstname());
	           System.out.println(bean.getLastname());
	           System.out.println(bean.getLoginid());
	           System.out.println(bean.getPassword());
	        //   System.out.println(bean.getConfirmpassword());
	        //   System.out.println(bean.getAddress());
	           System.out.println(bean.getDob());
	           System.out.println(bean.getRoleid());
	           System.out.println(bean.getUnsuccessfullogin());
	           System.out.println(bean.getGender());
	           System.out.println(bean.getLastlogin());
	           System.out.println(bean.getLock());
		   }
		   }  catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	
	

	private static void testupdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		try{
		UserBean bean = new UserBean();
		bean.setId(21l);
		bean.setFirstname("mayur");
		bean.setLastname("yadav");
	//	bean.setAddress(" indore");
		bean.setDob(sdf.parse("12/08/2019"));
		bean.setLoginid("yadav233@gmail.com");
		bean.setPassword("kalpesh@123");
		bean.setGender("Female");
		bean.setRoleid(1);
		bean.setMobileno("9521478545");
		//bean.setConfirmpassword("kalpesh@123");
		model.update(bean);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}


	private static void testdelete() {
		try{
			UserBean bean = new UserBean();
			bean.setId(2);
			model.delete(bean);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	private static void testfindbypk() {
		try{
			UserBean bean = new UserBean();
			long pk=2;
			bean = model.findByPk(pk);
			if(bean == null){
				System.out.println("test find by pk fail");
			}			
			
			System.out.println(bean.getId());
			System.out.println(bean.getFirstname());
			System.out.println(bean.getLastname());
			System.out.println(bean.getLoginid());
			System.out.println(bean.getPassword());
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}


	private static void testfindbyloginid() {
		
		try{
			UserBean bean = new UserBean();
		  bean = model.findByLogin("dixit233@gmail.com");
			if(bean==null){
				System.out.println("test find by login failed");
			}
			
			System.out.println(bean.getId());
			System.out.println(bean.getFirstname());
			System.out.println(bean.getLastname());
			System.out.println(bean.getPassword());
		//	System.out.println(bean.getConfirmpassword());
			System.out.println(bean.getLoginid());
			System.out.println(bean.getPassword());
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	
	private static void testadd() throws ParseException {

		//   Date date = new Date();  
		   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  
	
	UserBean bean = new UserBean();
	bean.setFirstname("rinku");
	bean.setLastname("dixit");
	bean.setLoginid("rinku233@gmail.com");
	bean.setPassword("rinku@123");
	//bean.setConfirmpassword("kelash@123");
//	bean.setAddress("mumbai");
	bean.setDob(sdf.parse("15/04/1998"));
	bean.setUnsuccessfullogin(3);
	bean.setMobileno("89595968468");
	bean.setRoleid(20);
	//bean.setRollname("twenty");
	bean.setGender("male");
	bean.setLastlogin(DataUtility.getTimestamp(new Date().getTime()));
	bean.setLock("yes");
	bean.setCreatedby("student");
	bean.setModifiedby("student");
	bean.setCreateddatetime(new Timestamp(new Date().getTime()));
	bean.setModifieddatetime(new Timestamp(new Date().getTime()));
	try{
	model.add(bean);
	
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	
	
	

}
