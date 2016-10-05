package optimino;

import java.util.*;
import java.sql.SQLException;


public class Main_calcola2G {
	
	  public static void test() throws SQLException {
	        Scanner input = new Scanner(System.in);
	        System.out.println("Inserisci CELLA");
	        String cella = input.nextLine();
	        System.out.println("Inserisci BCCHFREQ (x per estrarre quella in DB)");
	        String bcchfreq = input.nextLine();
	        input.close();
	        String bcch = "";
	        if (bcchfreq.equalsIgnoreCase("x")) {
	            bcch = Optimino2G.Estrai_bcch(cella);
	        }
	        else {
	             bcch = bcchfreq;      
	        }
	            
	        System.out.println("Inserisci DISTANZA");
	        String dist = input.nextLine();
	        Optimino2G.Calcola(cella,bcch,dist);
	        System.out.println("Frequenza cercata: " + bcch);
	        }

}
