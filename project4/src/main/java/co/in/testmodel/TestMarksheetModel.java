package co.in.testmodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.ls.LSInput;

import co.in.Exception.ApplicationException;
import co.in.bean.MarksheetBean;
import co.in.model.MarksheetModel;

/**
 * @author Yash Pandey
 *
 */
public class TestMarksheetModel {
	
	public static MarksheetModel model = new MarksheetModel();
	
	public static void main(String[] args) throws ApplicationException {
		
	//testfindbyrollno();
	//testadd();
	//testdelete();
	// testupdate(); 
	//testfindbypk();
		//testsearch();
		//testlist();
		//testmeritlist();
		
	}

	
	private static void testmeritlist() {
		MarksheetBean bean = new MarksheetBean();
		
		List<MarksheetBean> list = new ArrayList();
		
		try {
		list = 	model.getMeritList(1, 3);
		Iterator<MarksheetBean> it = list.iterator();
		
		while(it.hasNext()){
			bean = it.next();
			
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
			
			System.out.println("getting");
			
		}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private static void testsearch() {
		MarksheetBean bean = new MarksheetBean();
		List<MarksheetBean> list = new ArrayList();
		try{
			list = model.search(bean, 1, 1);
		   // bean.setName("ritik gehlot");
			Iterator< MarksheetBean> it = list.iterator();
			while(it.hasNext()){
				bean = it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollno());
				System.out.println(bean.getStudentid());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
				
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}

	private static void testlist() {
		MarksheetBean bean = new MarksheetBean();
		List<MarksheetBean> list = new ArrayList<MarksheetBean>();
		
		try{
			
			list = model.list(1,10);
			Iterator< MarksheetBean> it = list.iterator();
			while(it.hasNext()){
				
				bean = it.next();
				System.out.println(bean.getRollno());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
					}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	private static void testfindbypk() {
		MarksheetBean bean = new MarksheetBean();
		try{
			
			bean = model.findBypk(1);
			System.out.println(bean.getId());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getMaths());
			System.out.println(bean.getName());
			System.out.println(bean.getRollno());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
	private static void testupdate() {
		
		try{	
			
		MarksheetBean bean = model.findBypk(3l);
			
			bean.setRollno("102");
	        bean.setPhysics(70);
	        bean.setChemistry(77);
	        bean.setMaths(70);
	        bean.setStudentid(4l);
	        bean.setName("ritik sharma");
	        bean.setCreatedby("Admin");
	        bean.setModifiedby("Admin");
			
			
			model.update(bean);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testdelete() {
	
		MarksheetBean bean = new MarksheetBean();
		
		try{
			
			bean.setId(2l);
			
			model.delete(bean);
			
			System.out.println("deleted");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testadd() {
		
		MarksheetBean bean = new MarksheetBean();
		try{
			
			bean.setRollno("121");
			bean.setChemistry(70);
			bean.setPhysics(82);
			bean.setMaths(67);
			bean.setStudentid(5l);
			bean.setCreatedby("admin");
			bean.setCreateddatetime(new Timestamp(new Date().getTime()));
			model.add(bean);
			
			System.out.println("inserted");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void testfindbyrollno() {
	
		MarksheetBean bean = new MarksheetBean();
		
		try{
			
			bean = model.findByRollNo("102");
			
		System.out.println(bean.getChemistry());
		System.out.println(bean.getPhysics());
		System.out.println(bean.getMaths());
		System.out.println(bean.getName());
		System.out.println(bean.getStudentid());
		System.out.println(bean.getCreateddatetime());
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
