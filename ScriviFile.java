package optimino;

import java.io.*;

public class ScriviFile {

	public static void ScriviKMZ (String s, FileOutputStream file){    

		PrintStream scrivi = new PrintStream(file);
		scrivi.println(s);

	}

	public static void ScriviCella (String cell, String bcch_psc, String lat, String lon, String azi, FileOutputStream file) {

		ScriviKMZ("<Placemark><name>" + cell + "</name><visibility>1</visibility><description>" + bcch_psc + "</description>", file);
		ScriviKMZ("<Style><IconStyle><color>ffffffff</color><scale>7</scale><heading>" + azi + "</heading><Icon><href>\\\\10.60.220.38\\Optimino\\Icons\\Sector_UU" + cell.charAt(5) + ".ico</href></Icon>",file);
		ScriviKMZ("<gx:headingMode>worldNorth</gx:headingMode></IconStyle><LabelStyle><scale>0.7</scale></LabelStyle></Style><Point><coordinates>" + lon + "," + lat + ",0</coordinates></Point></Placemark>",file);


	}
	
	public static void ScriviCellaSource (String cell, String bcch, String lat, String lon, String azi, FileOutputStream file) {

		ScriviKMZ("<Placemark><name>" + cell + "</name><visibility>1</visibility><description>BCCH " + bcch + "</description>", file);
		ScriviKMZ("<Style><IconStyle><color>ff000000</color><scale>9</scale><heading>" + azi + "</heading><Icon><href>\\\\10.60.220.38\\Optimino\\Icons\\Sector_UU" + cell.charAt(5) + ".ico</href></Icon>",file);
		ScriviKMZ("<gx:headingMode>worldNorth</gx:headingMode></IconStyle><LabelStyle><scale>0.7</scale></LabelStyle></Style><Point><coordinates>" + lon + "," + lat + ",0</coordinates></Point></Placemark>",file);


	}
	
	public static void Chiudi_kmz(FileOutputStream file) {
		
		ScriviFile.ScriviKMZ("</Folder></Document></kml>",file);
		
	}
	
public static void Apri_kmz(String s, FileOutputStream file) {
		
	String init1 = "<?xml version=" + (char)34 + "1.0" + (char)34 + " encoding=" + (char)34 + "UTF-8" + (char)34 + " standalone=" + (char)34 + "no" + (char)34 + "?><kml xmlns=" + (char)34 + "http://earth.google.com/kml/2.0" + (char)34 + ">";
	String init2 = "<Document><Style id=" + (char)34 + "Stile" + (char)34 + "><LineStyle><color>ff0000ff</color><width>5</width></LineStyle></Style>";
	String init3 = "<Folder><name>" + s +"</name><description></description>";
	ScriviFile.ScriviKMZ(init1, file);
	ScriviFile.ScriviKMZ(init2, file);
	ScriviFile.ScriviKMZ(init3, file);
		
	}
	
}
