package co.in.testmodel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import co.in.bean.FacultyBean;
import co.in.model.FacultyModel;

/**
 * @author Yash Pandey
 *
 */
public class TestFacultyModel {
	
	public static FacultyModel model = new FacultyModel();
	
	public static void main(String[] args) {
		
		
	testadd();
	//testupdate();
	//testdelete();
	//testfindbypk();
	//testfindbyemail();
	//testsearch();
	//testlist();
		
	}

	private static void testlist() {
		FacultyBean bean = new FacultyBean();
		
		try{
			List<FacultyBean> list = new ArrayList<FacultyBean>();
			list = model.list(0,0);
			Iterator<FacultyBean> it = list.iterator();
			while(it.hasNext()){
				
				bean = it.next();
				
				System.out.println(bean.getId());
				System.out.println(bean.getFirstname());
				System.out.println(bean.getLastname());
				System.out.println(bean.getCollegename());
				System.out.println(bean.getCoursename());
				System.out.println(bean.getSubjectname());
				System.out.println(bean.getCreatedby());
				System.out.println(bean.getModifieddatetime());
				
				
				
			}
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testsearch() {
		FacultyBean bean = new FacultyBean();
		
		try{
			List<FacultyBean> list = new ArrayList<FacultyBean>();
			list = model.search(bean, 1, 1);
			Iterator<FacultyBean> it = list.iterator();
			while(it.hasNext()){
				bean = it.next();
				
				System.out.println(bean.getId());
				System.out.println(bean.getFirstname());
				System.out.println(bean.getLastname());
				System.out.println(bean.getCollegename());
				System.out.println(bean.getCoursename());
				System.out.println(bean.getSubjectname());
				System.out.println(bean.getCreatedby());
				System.out.println(bean.getModifieddatetime());
				
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testfindbyemail() {
		FacultyBean bean = new FacultyBean();
		
		try{
			
			bean = model.findByEmail("kelashjoshi123@gmail.com");
			
			System.out.println(bean.getId());
			System.out.println(bean.getFirstname());
			System.out.println(bean.getLastname());
			System.out.println(bean.getCollegename());
			System.out.println(bean.getCoursename());
			System.out.println(bean.getSubjectname());
			System.out.println(bean.getCreatedby());
			System.out.println(bean.getModifieddatetime());
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testfindbypk() {
		FacultyBean bean = new FacultyBean();
		
		try{
		bean = 	model.findBypk(1);
			
			System.out.println(bean.getId());
			System.out.println(bean.getCollegename());
			System.out.println(bean.getCoursename());
			System.out.println(bean.getSubjectname());
			System.out.println(bean.getCreatedby());
			System.out.println(bean.getModifieddatetime());
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	private static void testupdate() {
		FacultyBean bean = new FacultyBean();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		try{
			bean.setId(1);
			bean.setFirstname("jatin");
			bean.setLastname("neema");
			bean.setLoginid("jatin2323@gmail.com");
			bean.setCourseid(5);
			bean.setCollegeid(2);
			bean.setSubjectid(2);
			bean.setCreatedby("student");
			bean.setDateofjoining(sdf.parse("14/06/2018"));
			bean.setCreateddatetime(new Timestamp(new Date().getTime()));
			bean.setModifieddatetime(new Timestamp(new Date().getTime()));
			
		model.update(bean);
		System.out.println("record updated");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testdelete() {
		
		FacultyBean bean = new FacultyBean();
		
		try{
			
			bean.setId(3);
			model.delete(bean);
			System.out.println("record deleted");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void testadd() {
		
		FacultyBean bean = new FacultyBean();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		try{
			
			bean.setFirstname("yuvraj");
			bean.setLastname("sarma");
			bean.setLoginid("shrama2323@gmail.com");
			bean.setCollegeid(3);
			bean.setCourseid(4);
			bean.setSubjectid(2);
			bean.setDateofjoining(sdf.parse("12/06/2015"));
			bean.setQualification("svdbdbhshbhb");
			bean.setMobileno("55666445656");
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
