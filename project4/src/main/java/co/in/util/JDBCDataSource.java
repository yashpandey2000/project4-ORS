package co.in.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import co.in.Exception.ApplicationException;



/**
 * The Class JDBCDataSource.
 * 
 * @author Yash Pandey
 *
 */
public final class JDBCDataSource {
	
	/** JDBC Database connection pool ( DCP ). */
	private static JDBCDataSource datasource;
	
	
	/**
	 * Instantiates a new JDBC data source.
	 */
	private JDBCDataSource(){
		
	}
	
	
	/** The cpds. */
	   private ComboPooledDataSource cpds=null;
	
	
	
	
	
	/**
	 * Create instance of Connection Pool.
	 *
	 * @return single instance of JDBCDataSource
	 */
	public static JDBCDataSource getInstance(){
		
		if(datasource==null){
			ResourceBundle rb = ResourceBundle.getBundle("co.in.Bundle.system");
			//System.out.println("Bundle");
			datasource = new JDBCDataSource();
			datasource.cpds = new ComboPooledDataSource();
			
			try{
			datasource.cpds.setDriverClass(PropertyReader.getvalue("driver"));
			//System.out.println("start");
			datasource.cpds.setJdbcUrl(PropertyReader.getvalue("url"));
			//System.out.println("1");
			datasource.cpds.setUser(PropertyReader.getvalue("username"));
			//System.out.println("2");
			datasource.cpds.setPassword(PropertyReader.getvalue("password"));
			//System.out.println("3");
			datasource.cpds.setInitialPoolSize(DataUtility.getInt(PropertyReader.getvalue("initialPoolSize")));
			//System.out.println("4");
			datasource.cpds.setAcquireIncrement(DataUtility.getInt(PropertyReader.getvalue("acquireIncrement")));
			//System.out.println("5");
			datasource.cpds.setMaxPoolSize(DataUtility.getInt(PropertyReader.getvalue("maxPoolSize")));
			//System.out.println("6");
			datasource.cpds.setMinPoolSize(DataUtility.getInt(PropertyReader.getvalue("minPoolSize")));
			//System.out.println("7");
			datasource.cpds.setMaxIdleTime(DataUtility.getInt(PropertyReader.getvalue("timeout")));  //uri concept has a link with this
			//System.out.println("end");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return datasource;
	}




	
	/**
	 * Gets the connection from ComboPooledDataSource.
	 *
	 * @return connection
	 * @throws Exception the exception
	 */

public static Connection getConnection() throws Exception{
	return getInstance().cpds.getConnection();
}





/**
 * Closes a connection.
 *
 * @param connection the connection
 */
public static void closeConnection(Connection connection){
	
	if(connection !=null){
		try{
			connection.close();
			}catch(Exception e){
				
			}
		}
   }



/**
 * Trn rollback.
 *
 * @param connection the connection
 * @throws ApplicationException the application exception
 */
public static void trnRollback(Connection connection) throws ApplicationException{
	
	if(connection !=null){
		try{
			connection.rollback();
		}catch(SQLException ex){
			throw new ApplicationException(ex.toString());
		}
	}
}





}
