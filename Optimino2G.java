package optimino;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Optimino2G {

	static String connectionUrl, connectionUrl2 ;
	static Statement stmt = null;
	static Connection dbcon, destcon = null;
	static String latS = "";
	static String lonS = "";
	static String aziS = "";
	static String bcchS = "";


	public static void Calcola (String cella, String bcchfreq, String dist) throws SQLException{

		String cellaS = cella.toUpperCase();
		dbcon = QueryDB.DB_conn_Consulenti();
		ResultSet rs0 = QueryDB.Dati_source(dbcon, cellaS);

		Statement s1 = dbcon.createStatement();
		ResultSet rs1 = s1.executeQuery("SELECT DISTINCT SYMBOLIC, Latitude, Longitude, Azimuth, BCCHFREQ, BSIC, Donatrice FROM [Consulenti_LCC].[dbo].[Celle_NO_NE] where [Consulenti_LCC].[dbo].[Celle_NO_NE].[BCCHFREQ] = '" + bcchfreq + "'");

		if(rs0!=null){

			rs0.next();

			latS = rs0.getString("Latitude");
			lonS = rs0.getString("Longitude");
			aziS = rs0.getString("Azimuth");
			bcchS = rs0.getString("BCCHFREQ");

		}

		if(rs1!=null){
			
			try {
				FileOutputStream file = new FileOutputStream("prova.kml");

				ScriviFile.Apri_kmz("Celle con stesso BCCH", file);

				String Cella, lat, lon, azi, bcch;

				while (rs1.next()){

					Cella = rs1.getString("SYMBOLIC");
					lat = rs1.getString("Latitude");
					lon = rs1.getString("Longitude");
					azi = rs1.getString("Azimuth");
					bcch = rs1.getString("BCCHFREQ");
					
					Double Distanza_cellaB = Calcola_distanza.distanza(latS, lonS, lat, lon); 

					if ((Math.round(Distanza_cellaB/1000) < Double.valueOf(dist)) && (Math.round(Distanza_cellaB/1000) > 0)) {


						ScriviFile.ScriviCella(Cella, bcch, lat, lon, azi, file);

					}

				}

				ScriviFile.ScriviCellaSource(cellaS, bcchS, latS, lonS, aziS, file);
				ScriviFile.ScriviKMZ("</Folder>", file);
				Cell_to_adj(cellaS, file);			

			}

			catch (IOException e)
			{
				System.out.println("Errore: " + e);
				System.exit(1);
			}
		}

	}

	public static void Cell_to_adj (String cella, FileOutputStream file) throws SQLException{

		Statement s1 = dbcon.createStatement();
		ResultSet rs1 = s1.executeQuery("SELECT distinct Left([Consulenti_LCC].[dbo].[Adj_NO_NE].SYMBOLIC_TGT,7) as SYMBOLIC, [Consulenti_LCC].[dbo].[Celle_NO_NE].BCCHFREQ, [Consulenti_LCC].[dbo].[Celle_NO_NE].Latitude, [Consulenti_LCC].[dbo].[Celle_NO_NE].Longitude, [Consulenti_LCC].[dbo].[Celle_NO_NE].[Azimuth] from [Consulenti_LCC].[dbo].[Adj_NO_NE] inner join [Consulenti_LCC].[dbo].[Celle_NO_NE] on left([Consulenti_LCC].[dbo].[Adj_NO_NE].SYMBOLIC_TGT,7) = [Consulenti_LCC].[dbo].[Celle_NO_NE].Donatrice where [Consulenti_LCC].[dbo].[Adj_NO_NE].SYMBOLIC = '" + cella + "' and (SYMBOLIC_TGT like '%G%' or SYMBOLIC_TGT like '%D%')");

		if(rs1!=null){

			String Folder_C_to_A = "<Folder><name>CELL TO ADJ</name><description></description>";
			String Folder2G = "<Folder><name>2G</name><description></description>";
			ScriviFile.ScriviKMZ(Folder_C_to_A, file);
			ScriviFile.ScriviKMZ(Folder2G, file);

			String Adj, lat, lon, azi, bcch, psc;

			while (rs1.next()){

				Adj = rs1.getString("SYMBOLIC");
				lat = rs1.getString("Latitude");
				lon = rs1.getString("Longitude");
				azi = rs1.getString("Azimuth");
				bcch = rs1.getString("BCCHFREQ");

				ScriviFile.ScriviCella(Adj, bcch, lat, lon, azi, file);			

			}

			ScriviFile.ScriviCellaSource(cella, bcchS, latS, lonS, aziS, file);

			Statement s2 = dbcon.createStatement();
			ResultSet rs2 = s2.executeQuery("SELECT Left([Consulenti_LCC].[dbo].[Adj_NO_NE].SYMBOLIC_TGT,7) as SYMBOLIC, [Consulenti_LCC].[dbo].[Celle3G_NO_NE].primaryScramblingCode as PSC, [Consulenti_LCC].[dbo].[Celle3G_NO_NE].Latitude, [Consulenti_LCC].[dbo].[Celle3G_NO_NE].Longitude, [Consulenti_LCC].[dbo].[Celle3G_NO_NE].[Azimuth] from [Consulenti_LCC].[dbo].[Adj_NO_NE] inner join [Consulenti_LCC].[dbo].[Celle3G_NO_NE] on left([Consulenti_LCC].[dbo].[Adj_NO_NE].SYMBOLIC_TGT,7) = [Consulenti_LCC].[dbo].[Celle3G_NO_NE].Donatrice where [Consulenti_LCC].[dbo].[Adj_NO_NE].SYMBOLIC = '" + cella + "' order by Left([Consulenti_LCC].[dbo].[Adj_NO_NE].SYMBOLIC_TGT,7)");

			String Folder3G = "</Folder><Folder><name>3G</name><description></description>";
			ScriviFile.ScriviKMZ(Folder3G, file);

			while (rs2.next()){

				Adj = rs2.getString("SYMBOLIC");
				lat = rs2.getString("Latitude");
				lon = rs2.getString("Longitude");
				azi = rs2.getString("Azimuth");
				psc = rs2.getString("PSC");

				ScriviFile.ScriviCella(Adj, psc, lat, lon, azi, file);			

			}

			ScriviFile.ScriviCellaSource(cella, bcchS, latS, lonS, aziS, file);
			ScriviFile.ScriviKMZ("</Folder>", file);
			ScriviFile.Chiudi_kmz(file);

		}

	}

	public static String Estrai_bcch (String cella) throws SQLException{

		cella = cella.toUpperCase();
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

		Statement s1 = dbcon.createStatement();
		ResultSet rs = s1.executeQuery("SELECT DISTINCT BCCHFREQ FROM Celle_NO_NE where SYMBOLIC = '" + cella + "'");
		String bcch = "";
		if(rs!=null){
			rs.next();
			bcch = rs.getString("BCCHFREQ");

		}

		return bcch;

	}

}
