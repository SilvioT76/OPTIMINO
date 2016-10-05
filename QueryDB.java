package optimino;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryDB {

	String nomeDB;
	String nomeTabella;
	static String connectionUrl, connectionUrl2 ;
	static Statement stmt = null;
	static Connection dbcon, destcon = null;

	public void CreateTable(String sql)
	{
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + this.nomeDB);
			System.out.println("Opened database successfully");
			System.out.println("");

			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Table created successfully");    
	}

	public void UpdateTable(String sql)
	{
		Connection connDB = null;
		Statement stmtDB = null;

		try{
			Class.forName("org.sqlite.JDBC");
			connDB = DriverManager.getConnection("jdbc:sqlite:" + this.nomeDB);
			connDB.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmtDB = connDB.createStatement();
			System.out.println(sql);
			stmtDB.executeUpdate(sql);
			stmtDB.close();
			connDB.commit();
			connDB.close();
		}
		catch (Exception e) {
			System.err.println (e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);                                 
		} 		
		System.out.println("Table updated successfully");
	}

	public void DeleteTable(String nomeTabella)
	{
		Connection connDB = null;
		Statement stmtDB = null;

		try{
			Class.forName("org.sqlite.JDBC");
			connDB = DriverManager.getConnection("jdbc:sqlite:" + this.nomeDB);
			connDB.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmtDB = connDB.createStatement();
			String sql = "DROP TABLE IF EXISTS " + nomeTabella + "";

			System.out.println(sql);
			stmtDB.executeUpdate(sql);
			stmtDB.close();
			connDB.commit();
			connDB.close();
		}
		catch (Exception e) {
			System.err.println (e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);                                 
		} 		
		System.out.println("Table deleted successfully");
	}
	
	public static ResultSet Dati_source(Connection dbcon, String cella) throws SQLException {

		Statement s0 = dbcon.createStatement();
		ResultSet rs0 = s0.executeQuery("SELECT Latitude, Longitude, Azimuth, BCCHFREQ FROM [Consulenti_LCC].[dbo].[Celle_NO_NE] where SYMBOLIC = '" + cella + "'");
		return rs0;
	}

	public QueryDB(String nomeDB, String nomeTabella) {

		this.nomeDB = nomeDB;
		this.nomeTabella = nomeTabella;
	}

	public QueryDB(String nomeDB) {

		this.nomeDB = nomeDB;

	}

	public static Connection DB_conn_Consulenti() {

		connectionUrl = "jdbc:sqlserver://10.60.220.38:1433;databaseName=Consulenti_LCC;user=rf_manager;password=Rfm2015!;";

		try {  
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			if (dbcon == null )  {
				dbcon = DriverManager.getConnection(connectionUrl);
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception: "+ e.toString());

		} catch (ClassNotFoundException cE) {
			System.out.println("Class Not Found Exception: "+ cE.toString());

		}
		return dbcon;
	}
}
