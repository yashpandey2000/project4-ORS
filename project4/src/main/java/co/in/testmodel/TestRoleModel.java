package co.in.testmodel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import co.in.bean.CollegeBean;
import co.in.bean.RoleBean;
import co.in.model.RoleModel;

/**
 * @author Yash Pandey
 *
 */
public class TestRoleModel {
	
	public static RoleModel model = new RoleModel();
	
	public static void main(String[] args) {
		
		//testfindbyname();
		 testadd();
		//testfindbypk();
		//testdelete();
		//testupdate();
		//testsearch();
		//testlist();
		
	}


	private static void testlist() {
		
		try{
			RoleBean bean = new RoleBean();
			List ll = new ArrayList();
			ll = model.list(1, 1);
			Iterator it = ll.iterator();
			while(it.hasNext()){
				bean = (RoleBean) it.next();
				
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
	

	private static void testsearch() {
		try{
			RoleBean bean = new RoleBean();
			List<RoleBean> l = new ArrayList<RoleBean>();
			bean.setDescription("very good");
			l = model.search(0, bean, 0);
			Iterator<RoleBean> it = l.iterator();
			while (it.hasNext()){
				bean  = it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}


	private static void testupdate() {
		
		try{
		RoleBean bean  = new RoleBean();
		bean.setId(3);
		bean.setName("student");
		bean.setDescription("esdnmnn");
		bean.setCreatedby("student");
		bean.setModifiedby("student");
		model.update(bean);
		
		}catch(Exception e){
			e.printStackTrace();
		}	
	}

	
	
	
	private static void testdelete() {
		try{
			RoleBean bean = new RoleBean();
			bean.setId(5);
			model.delete(bean);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
	
	private static void testfindbypk() {
		try{
			RoleBean bean = new RoleBean();
			bean=model.findByPk(2);
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		
	}

	private static void testadd() {
	try{
		RoleBean bean = new RoleBean();
		bean.setId(3);
		bean.setName("student");
		bean.setDescription("vdsernns");
		bean.setModifiedby("student");
		bean.setCreatedby("student");
		bean.setCreateddatetime(new Timestamp(new Date().getTime()));
		bean.setModifieddatetime(new Timestamp(new Date().getTime()));
		
		model.add(bean);
	}catch(Exception e){
		e.printStackTrace();
	}
		
	}

	
	
	private static void testfindbyname() {
		
		try{
			RoleBean bean = new RoleBean();
			
			bean =model.findByName("yash");
			if(bean != null){
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			}
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	

}
