package co.in.testmodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import co.in.bean.CourseBean;
import co.in.model.CourseModel;

/**
 * @author Yash Pandey
 *
 */
public class TestCourseModel {
	
	public static CourseModel model = new CourseModel();

	public static void main(String[] args) {

//testadd();
//testdelete();
testupdate();
//testfindbyname();
//testfindbypk();
//testlist();
//testsearch();

	}

	private static void testsearch() {
		
		CourseBean bean = new CourseBean();
		try{
			
			List<CourseBean> list = new ArrayList<CourseBean>();
			bean.setCname("BTEch");
			list = model.search(bean, 0, 0);
			
			Iterator<CourseBean> it = list.iterator();
			while(it.hasNext()){
				
				bean = it.next();
				
				System.out.println(bean.getId());
				System.out.println(bean.getCname());
				System.out.println(bean.getDuration());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedby());
				System.out.println(bean.getModifiedby());
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void testlist() {
		CourseBean bean = new CourseBean();
		try{
			List<CourseBean> list = new ArrayList<CourseBean>();
			list = model.list(0,0);
			Iterator<CourseBean> it = list.iterator();
			while(it.hasNext()){
				bean = it.next();
				
				System.out.println(bean.getId());
				System.out.println(bean.getCname());
				System.out.println(bean.getDuration());
				System.out.println(bean.getDescription());
				System.out.println(bean.getDuration());
				System.out.println(bean.getCreatedby());
				System.out.println(bean.getModifiedby());
				System.out.println(bean.getCreateddatetime());
				System.out.println(bean.getModifieddatetime());
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testfindbypk() {

CourseBean bean = new CourseBean();

try{
	bean = model.findBypk(3l);
	
	System.out.println(bean.getId());
	System.out.println(bean.getDescription());
	System.out.println(bean.getDuration());
	System.out.println(bean.getCname());
	System.out.println(bean.getCreatedby());
	System.out.println(bean.getModifiedby());
	
	
	
}catch(Exception e){
	e.printStackTrace();
}
		
	}

	private static void testfindbyname() {
		
		CourseBean bean = new CourseBean();
		try{
			bean = model.findByName("BTech");
			
			System.out.println(bean.getId());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
			System.out.println(bean.getCreatedby());
			System.out.println(bean.getModifiedby());
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testupdate() {
		CourseBean bean = new CourseBean();
		
		try{
			bean = model.findBypk(2l);
			bean.setCname("BTech");
			bean.setDuration("4 year");
			bean.setDescription("bachlors degree");
			bean.setCreatedby("akash");
			bean.setModifiedby("akash");
			
			model.update(bean);
			System.out.println("record updated");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testdelete() {
		CourseBean bean = new CourseBean();
		
		try{
			bean.setId(2l);
			model.delete(bean);
			System.out.println("record deleted");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testadd() {
		CourseBean bean = new CourseBean();
		
		try{
			bean.setCname("MTech");
			bean.setDescription("technical bachlors degree");
			bean.setDuration("2 yrs");
			bean.setCreatedby("ritik");
			bean.setModifiedby("ritik");
			bean.setCreateddatetime(new Timestamp(new Date().getTime()));
			bean.setModifieddatetime(new Timestamp(new Date().getTime()));
			
			model.add(bean);
			System.out.println("inserted");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
