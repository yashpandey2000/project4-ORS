package co.in.testmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import co.in.bean.StudentBean;
import co.in.model.Studentmodel;

/**
 * @author Yash Pandey
 *
 */
public class TestStudentModel {
	
	public static Studentmodel model = new Studentmodel();

	public static void main(String[] args) {
		
		//testadd();
		//testfindbyemail();
		//testdelete();
		//testupdate();
		//testfindbypk();
	    //testlist();
		//testsearch();
	}

	
	
	private static void testsearch() {
		StudentBean bean = new StudentBean();
		
		List<StudentBean> ll = new ArrayList<StudentBean>();
		try{
			
			ll = model.search(bean, 1, 1);
			Iterator<StudentBean> it = ll.iterator();
			while(it.hasNext()){
				bean = it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstname());
				System.out.println(bean.getLastname());
				System.out.println(bean.getEmail());
				System.out.println(bean.getCreatedby());
				System.out.println(bean.getDob());
				System.out.println(bean.getAddress());
				
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}



	private static void testlist() {
		// TODO Auto-generated method stub
		
		StudentBean bean = new StudentBean();
		List<StudentBean> ll = new ArrayList();
		try{
			
			ll = model.list(1,10);
			Iterator<StudentBean> it = ll.iterator();
			
			while(it.hasNext()){
				bean = it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstname());
				System.out.println(bean.getLastname());
				System.out.println(bean.getEmail());
				System.out.println(bean.getCollegeid());
				System.out.println(bean.getCollegename());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}



	private static void testfindbypk() {
		// TODO Auto-generated method stub
		
		try{
			StudentBean bean = new StudentBean();
			long pk=3;
			bean = model.findBypk(pk);
			
			
			if(bean == null){
				System.out.println("test find by pk fail");
			}	
			
		System.out.println(bean.getId());	
		System.out.println(bean.getCollegeid());
		System.out.println(bean.getCollegename());	
		System.out.println(bean.getAddress());	
		System.out.println(bean.getEmail());
		System.out.println(bean.getFirstname());
		System.out.println(bean.getDob());
		System.out.println(bean.getCreatedby());
		System.out.println(bean.getCreateddatetime());
			
		System.out.println("getting");	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}



	private static void testupdate() {
	
		try{
			StudentBean bean = new StudentBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
			bean.setId(4l);
			bean.setCollegeid(3);
			bean.setCollegename("LNCT");
			bean.setFirstname("lokesh");
			bean.setLastname("dixit");
			bean.setDob(sdf.parse("23/07/2000"));
			bean.setMobileno("8329504064");
			bean.setCreatedby("admin");
			bean.setEmail("hello@2000gmail.com");
			bean.setCreateddatetime(new Timestamp(new Date().getTime()));
			
			model.update(bean);
			
		//	System.out.println("updated");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}



	private static void testdelete() {
		
		StudentBean bean = new StudentBean();
		try{
			bean.setId(1);
			
			model.delete(bean);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}



	private static void testfindbyemail() {
		
	
		try{
			
			StudentBean bean = new StudentBean();
			bean = model.findByEmail("yash@gmail.com");
			
			System.out.println(bean.getCollegeid());
			System.out.println(bean.getCollegename());
	        System.out.println(bean.getDob());
			System.out.println(bean.getFirstname());
			System.out.println(bean.getLastname());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}



	private static void testadd() {
		StudentBean bean = new StudentBean();
	
		try{
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
			
			
			bean.setFirstname("ritik");
			bean.setLastname("gehlot");
			bean.setDob(sdf.parse("23/10/2012"));
			bean.setMobileno("7858695954");
			bean.setEmail("ritik2323@gmail.com");
			bean.setCollegeid(3l);
			bean.setCreatedby("admin");
			bean.setModifiedby("admin");
			bean.setCreateddatetime(new Timestamp(new Date().getTime()));
			bean.setModifieddatetime(new Timestamp(new Date().getTime()));
			
			model.add(bean);
			System.out.println("inserted");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
