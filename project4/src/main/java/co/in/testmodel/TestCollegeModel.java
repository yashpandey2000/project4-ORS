package co.in.testmodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import co.in.Exception.ApplicationException;
import co.in.Exception.DuplicateRecordException;
import co.in.bean.CollegeBean;
import co.in.model.CollegeModel;

/**
 * @author Yash Pandey
 *
 */
public class TestCollegeModel {
	

	public static CollegeModel model = new CollegeModel();
	
	public static void main(String[] args)throws ApplicationException ,DuplicateRecordException {
		
		// testadd();
		//testfindbyname();
		// testfindbypk();
		//testupdate();
	   // testsearch();
	     // testdelete();
		//testlist();
		
	
		
	}

private static void testlist() {
	try{
	CollegeBean bean = new CollegeBean();
	List<CollegeBean> ll = new ArrayList<CollegeBean>();
	
	ll=model.list(1,1);
	Iterator<CollegeBean> it = ll.iterator();
	while(it.hasNext()){
		bean =it.next();
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getAddress());
		System.out.println(bean.getState());
		System.out.println(bean.getCity());
	}
	
	}catch(Exception e){
		e.printStackTrace();
		
	}
	
		
	}





private static void testdelete() {
		long pk=5;
	try{
		
		CollegeBean bean = new CollegeBean();
		bean.setId(pk);
		model.delete(bean);
		
	}catch(Exception e){
		e.printStackTrace();
	}
		
	}




private static void testsearch() {
		try{
			CollegeBean bean = new CollegeBean();
			List<CollegeBean> ll = new ArrayList<CollegeBean>();
			bean.setCity("indore");
			ll=model.search(bean, 0, 0);
		  Iterator<CollegeBean> it = ll.iterator();
		  while(it.hasNext()){
			  bean = it.next();
			  System.out.println(bean.getName());
			  System.out.println(bean.getAddress());
			  System.out.println(bean.getId());
			  System.out.println(bean.getCity());
		  }
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}




public static void testupdate() {
		
	
		try{
			CollegeBean bean = new CollegeBean();
			bean = model.findByPK(11);
			bean.setName("prestige");
			bean.setState("m.p.");
			bean.setCity("indore");
			model.update(bean);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	


	public static void testfindbypk() {
	try{
		CollegeBean bean = new CollegeBean();
	    bean =  model.findByPK(3);
	   
	    System.out.println(bean.getId());
	    System.out.println(bean.getName());
	    System.out.println(bean.getAddress());
	    System.out.println(bean.getState());
	    System.out.println(bean.getCity());
	    System.out.println(bean.getPhoneno());
	    System.out.println(bean.getCreatedby());
	    System.out.println(bean.getModifiedby());
	    System.out.println(bean.getCreateddatetime());
	    System.out.println(bean.getModifieddatetime());
	
	}	
	

	    catch(Exception e){
		e.printStackTrace();
	}
		
	}

	
	
	
	
	
	    public static void testfindbyname() {
		try{
			CollegeBean bean = new CollegeBean();
			bean = model.findByName("Gujrati");
			if(bean != null){
				System.out.println(bean.getId());
				System.out.println(bean.getName());
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	 }

	
	    

	    
	    
	public static void testadd() throws DuplicateRecordException,ApplicationException {
	try{
		CollegeBean bean = new CollegeBean();
		
		bean.setName("Gujrati");
		bean.setAddress("Madhumilan");
		bean.setState("indore");
		bean.setCity("india");
		bean.setPhoneno("982424243");
		bean.setCreatedby("admin");
		bean.setModifiedby("admin");
		bean.setCreateddatetime(new Timestamp(new Date().getTime()));
		bean.setModifieddatetime(new Timestamp(new Date().getTime()));
		
		
		long pk = model.add(bean);
		
		CollegeBean addedbean = model.findByPK(pk);
		if(addedbean == null){
			System.out.println("test add fail");
		}
	}catch(ApplicationException e){
		e.printStackTrace();
	}
			
		
	}

}
