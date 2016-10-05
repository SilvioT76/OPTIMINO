package optimino;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Vicinanza {
	
	String site;
	String distanza;

	public static Vicinanza TrovaSito (String latitude, String longitude) {

		Connection connDB = null;
		Statement stmtDB = null;
		String site = null;
		String lat = null;
		String lon = null;
		String site_result = null;
		double dist = 100000;
		double distanza;
		Vicinanza vicinanza = new Vicinanza();

		try{
			Class.forName("org.sqlite.JDBC");
			connDB = DriverManager.getConnection("jdbc:sqlite:test.db");
			System.out.println("Opened database successfully");
			System.out.println("");

			stmtDB = connDB.createStatement();
			ResultSet resultset = stmtDB.executeQuery("SELECT * FROM SITES");
			while (resultset.next()) {
					site = resultset.getString("site");
					lat = resultset.getString("latitude");
					lon = resultset.getString("longitude");
					distanza = Calcola_distanza.distanza(latitude.replaceAll(",", "."), longitude.replaceAll(",", "."), lat, lon);
					if (distanza < dist)  {
						
						dist = distanza;
						site_result = site;
						
					}

			}

			stmtDB.close();
			connDB.close();
		} catch (Exception e) {
			System.err.println (e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);                                 
		}  
		vicinanza.site = site_result;
		vicinanza.distanza = String.valueOf(dist);
		return vicinanza;
		
	}
	
	public Vicinanza (String site, String distanza) {
		
		this.site = site;
		this.distanza = distanza;
		
	}

	public Vicinanza() {
		// TODO Auto-generated constructor stub
	}

}
