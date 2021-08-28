package co.in.testmodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import co.in.bean.SubjectBean;
import co.in.model.SubjectModel;

/**
 * @author Yash Pandey
 *
 */
public class TestSubjectModel {

	public static SubjectModel model = new SubjectModel();
	
	public static void main(String[] args) {
	
	//	testadd();
	//testupdate();
	//	testdelete();
	//testfindbypk();
   //testfindbyname();
		//testlist();
		//testsearch();

	}

	private static void testsearch() {
		SubjectBean bean = new SubjectBean();
	  
		try{
			
			List<SubjectBean> list = new ArrayList<SubjectBean>();
			list = model.search(bean, 1, 1);
			Iterator<SubjectBean> it = list.iterator();			
			while(it.hasNext()){
				
				bean = it.next();
				
				System.out.println(bean.getCourseid());
				System.out.println(bean.getId());
				System.out.println(bean.getCoursename());
				System.out.println(bean.getDescription());
				System.out.println(bean.getSubjectname());
				System.out.println(bean.getCreatedby());
				System.out.println(bean.getModifieddatetime());
				
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void testlist() {
		SubjectBean bean = new SubjectBean();
		try{
			
			List<SubjectBean> list = new ArrayList<SubjectBean>();
			list = model.list(0, 0);
			Iterator<SubjectBean> it = list.iterator();			
			while(it.hasNext()){
				
				bean = it.next();
				
				System.out.println(bean.getCourseid());
				System.out.println(bean.getId());
				System.out.println(bean.getCoursename());
				System.out.println(bean.getDescription());
				System.out.println(bean.getSubjectname());
				System.out.println(bean.getCreatedby());
				System.out.println(bean.getModifieddatetime());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void testfindbyname() {
	
		SubjectBean bean = new SubjectBean();
		try{
			bean = model.findByName("Account");
			
			System.out.println(bean.getCourseid());
			System.out.println(bean.getCoursename());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedby());
			System.out.println(bean.getModifieddatetime());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testfindbypk() {
		SubjectBean bean = new SubjectBean();
		try{
		bean = model.findBypk(2);
		
		System.out.println(bean.getCourseid());
		System.out.println(bean.getCoursename());
		System.out.println(bean.getSubjectname());
		System.out.println(bean.getDescription());
		System.out.println(bean.getCreatedby());
		System.out.println(bean.getModifieddatetime());
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void testupdate() {
		SubjectBean bean = new SubjectBean();
		try{
			
			bean.setId(1);
			bean.setCourseid(2);
			//bean.setCoursename("Bcom");
			bean.setSubjectname("Account");
			bean.setDescription("regarding to accounting");
			bean.setCreatedby("admin");
			bean.setModifiedby("admin");
			bean.setCreateddatetime(new Timestamp(new Date().getTime()));
			bean.setModifieddatetime(new Timestamp(new Date().getTime()));
			
			model.update(bean);
			
			System.out.println("record update");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testdelete() {
		
	SubjectBean bean = new SubjectBean();
		try{
			
			bean.setId(3);
			model.delete(bean);
			System.out.println("record deleted");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testadd() {
		SubjectBean bean = new SubjectBean();
		try{
			
			bean.setCourseid(4);
			//bean.setCoursename("");
			bean.setSubjectname("DBMS");
		//	bean.setDescription("");
			bean.setCreatedby("admin");
			bean.setModifiedby("admin");
			bean.setCreateddatetime(new Timestamp(new Date().getTime()));
			bean.setModifieddatetime(new Timestamp(new Date().getTime()));
			
			model.add(bean);
			
			System.out.println("record inserted");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
