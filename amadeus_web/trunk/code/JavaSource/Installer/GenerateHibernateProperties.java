package Installer;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.hibernate.SessionFactory;

public class GenerateHibernateProperties {
	
	public final static String POSTGRE_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
	public final static String POSTGRE_DRIVER  = "org.postgresql.Driver";
	public final static String MYSQL_DIALECT  ="org.hibernate.dialect.MySQLDialect";
	public final static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	
	
	/**
	 * 
	 * @param databaseType postgresql = 0, mysql = 1
	 * @param login
	 * @param password
	 * @param databaseHost
	 * @param port
	 * @param databaseName
	 */
	public void generate(int databaseType, String login, String password, String databaseHost, String port, String databaseName ){
		switch(databaseType){
			case 0:
				String dialect = POSTGRE_DIALECT;
				String driver = POSTGRE_DRIVER;
				String jdbc = "postgresql";
				this.generateHibernatePropertiesFile(login, jdbc, dialect, driver, password,databaseHost, port, databaseName);
			break;
			
			case 1:
				String dialect2 = MYSQL_DIALECT;
				String driver2 = MYSQL_DRIVER;
				String jdbc2 = "mysql";
				this.generateHibernatePropertiesFile(login, jdbc2, dialect2, driver2,password,databaseHost,port, databaseName);
		}
	}
	
	/**
	 * Metodo que gera um hibernate.properties
	 * @param login
	 * @param password
	 * @param databaseHost
	 * @param port
	 * @param databaseName
	 */
	public void generateHibernatePropertiesFile(String login, String jdbc, String dialect, String driver, String password, String databaseHost, String port, String databaseName){
		try {
			FileWriter file = new FileWriter(new File("./WebContent/WEB-INF/classes/hibernate.properties"),true);
			PrintWriter printf = new PrintWriter(file);
			printf.println("hibernate.dialect = "+dialect);
			printf.println("hibernate.connection.driver_class = "+driver);
			printf.println("hibernate.connection.url = jdbc:"+jdbc+"://"+databaseHost+""+port+"/"+databaseName);
			printf.println("hibernate.connection.username = "+login);
			printf.println("hibernate.connection.password = "+password);
			
			printf.println("hibernate.transaction.factory_class = org.hibernate.transaction.JDBCTransactionFactory");
			printf.println("hibernate.current_session_context_class = thread");
			printf.println("hibernate.c3p0.max_size = 10");
			printf.println("hibernate.c3p0.min_size = 2");
			printf.println( "hibernate.c3p0.timeout = 5000");
			printf.println( "hibernate.c3p0.max_statements = 10");
	        printf.println( "hibernate.c3p0.idle_test_period = 3000");
			printf.print("hibernate.c3p0.acquire_increment = 2");
			printf.close();
			file.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		GenerateHibernateProperties gen = new GenerateHibernateProperties();
		gen.generate(0,"amadeus","amadeus","jupi.cin.ufpe.br",":5432","amadeus");
		System.out.println("done.");
	}

}
