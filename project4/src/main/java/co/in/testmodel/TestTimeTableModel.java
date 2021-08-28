package co.in.testmodel;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import co.in.bean.TimeTableBean;
import co.in.model.TimeTableModel;

/**
 * @author Yash Pandey
 *
 */
public class TestTimeTableModel {
	
	public static TimeTableModel model = new TimeTableModel();
	
	public static void main(String[] args) throws SQLException {
		
	//testfindbypk();	
	//testdelete();
	//testlist();
	//testadd();
	//testupdate();
	//testsearch();
	//testfindbycoursename();

		
		
	}

	private static void testfindbycoursename() {
		TimeTableBean bean = new TimeTableBean();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		try{
			
			bean = model.findByCourseName(4, sdf.parse("26/01/2021"));
			
			System.out.println(bean.getId());
			System.out.println(bean.getCoursename());
			System.out.println(bean.getSubjectname());
			System.out.println(bean.getExamdate());
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testsearch() {
		
		TimeTableBean bean = new TimeTableBean();
		
		try{
			
			List<TimeTableBean> list = new ArrayList<TimeTableBean>();
		list =	model.search(bean, 1, 1);
		
		Iterator<TimeTableBean> it = list.iterator();
		while(it.hasNext()){
			bean = it.next();
			
			System.out.println(bean.getId());
			System.out.println(bean.getCoursename());
			System.out.println(bean.getSubjectname());
			System.out.println(bean.getSemester());
			System.out.println(bean.getExamtime());
			System.out.println(bean.getExamdate());
			System.out.println(bean.getCreatedby());	
			System.out.println(bean.getModifieddatetime());
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testupdate() {
		TimeTableBean bean = new TimeTableBean();
SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		
		try{
			bean.setId(4);
			bean.setCourseid(4);
			bean.setSubjectid(1);
			bean.setSemester("4");
			bean.setExamtime("11:00");
			bean.setExamdate(sdf.parse("26/05/2021"));
			bean.setCreatedby("admin");
			bean.setModifiedby("admin");
			bean.setCreateddatetime(new Timestamp(new Date().getTime()));
			bean.setModifieddatetime(new Timestamp(new Date().getTime()));
		
		model.update(bean);
		System.out.println("record updated");
		
	}catch(Exception e){
		e.printStackTrace();
		}
	}

	

	private static void testadd() {
		TimeTableBean bean = new TimeTableBean();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		
		try{
			bean.setCourseid(5);
			bean.setSubjectid(2);
			bean.setSemester("4 Sem");
			bean.setExamtime("11:00 Am");
			bean.setExamdate(sdf.parse("24/05/2021"));
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

	private static void testlist() throws SQLException {
	
		TimeTableBean bean = new TimeTableBean();
		try{
		List list = new ArrayList();
		
		list = model.list(0,0);
		
		Iterator it = list.iterator();
		while(it.hasNext()){
			bean = (TimeTableBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getCoursename());
			System.out.println(bean.getSubjectname());
			System.out.println(bean.getExamdate());
			System.out.println(bean.getExamtime());
			System.out.println(bean.getCreatedby());
			System.out.println(bean.getModifieddatetime());
			
		}
	}catch(Exception e){
		e.printStackTrace();
	}

	}
	
	
	
	private static void testdelete() throws SQLException {
		
		TimeTableBean bean = new TimeTableBean();
		try{
		bean.setId(2l);
		
		model.delete(bean);
		
		System.out.println("record deleted");
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	
	
	private static void testfindbypk() {
		
		try{
			
			
			TimeTableBean bean = new TimeTableBean();
			
			bean = model.findbypk(4l);
			
			System.out.println(bean.getId());
			System.out.println(bean.getCourseid());
			System.out.println(bean.getCoursename());
			System.out.println(bean.getSubjectid());
			System.out.println(bean.getSubjectname());
			System.out.println(bean.getExamtime());
			System.out.println(bean.getExamdate());
			System.out.println(bean.getCreatedby());
			System.out.println(bean.getModifieddatetime());
	
				
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
