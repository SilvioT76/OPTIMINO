package optimino;

import java.lang.Math;

public class Calcola_distanza {
	
	public static double distanza(String lat_A, String lon_A, String lat_B, String lon_B) {
		   
        Double Lat_A = Double.valueOf(lat_A);
        Double Lon_A = Double.valueOf(lon_A);
        Double Lat_B = Double.valueOf(lat_B);
        Double Lon_B = Double.valueOf(lon_B);
        Double temp, temp1, temp2, temp3, Distanza;
        Double distanza_radiale;

        Double Svar = 3.14159 / 180;

        Lat_A = Lat_A * Svar;
        Lat_B = Lat_B * Svar;
        Lon_A = Lon_A * Svar;
        Lon_B = Lon_B * Svar;

        temp1 = Math.sin(Lat_A) * Math.sin(Lat_B);
        temp2 = Math.cos(Lat_A) * Math.cos(Lat_B);
        temp3 = Math.cos(Lon_A - Lon_B);
        temp = temp1 + (temp2 * temp3);
        distanza_radiale = Math.atan(-temp / Math.sqrt(-temp * temp + 1)) + 2 * Math.atan(1);
        temp = distanza_radiale * 3437.74677 * 1.1508;

        /* RISULTATO ESPRESSO IN METRI */
        Distanza = (temp * 1.60934708788644) * 1000;
        return Distanza;
        
     }   

}
