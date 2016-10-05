package optimino;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Vicinanza {

	String site;
	String distanza;

	public static List<Vicinanza> TrovaSito (String latitude, String longitude) {

		Connection connDB = null;
		Statement stmtDB = null;
		String site = null;
		String lat = null;
		String lon = null;
		String site_result = null;
		double dist = 10000;
		double distanza;
		List<Vicinanza> ristoranti = new ArrayList<Vicinanza>();
		

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
				Vicinanza vicinanza = new Vicinanza();
				distanza = Calcola_distanza.distanza(latitude.replaceAll(",", "."), longitude.replaceAll(",", "."), lat, lon);
				if (distanza < dist)  {

					vicinanza.site = site;
					vicinanza.distanza = String.format("%.2f",distanza/1000);
					ristoranti.add(vicinanza);			
				}

			}

			stmtDB.close();
			connDB.close();
		} catch (Exception e) {
			System.err.println (e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);                                 
		}  
		return ristoranti;

	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDistanza() {
		return distanza;
	}

	public void setDistanza(String distanza) {
		this.distanza = distanza;
	}

	public Vicinanza (String site, String distanza) {

		this.site = site;
		this.distanza = distanza;

	}

	public Vicinanza() {
		// TODO Auto-generated constructor stub
	}

}
