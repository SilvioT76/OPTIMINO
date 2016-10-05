package optimino;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import jxl.read.biff.BiffException;

public class Main {

	public static void main(String[] args)  throws SQLException, BiffException, IOException {
		
		Vicinanza vicinanza;
		Scanner input = new Scanner(System.in);
		System.out.println("trova siti o celle (S/C)?");
		String scelta = input.nextLine();
		if (scelta.equalsIgnoreCase("S")) {

			Import_Dati.LeggiSiti();
		}
		else if (scelta.equalsIgnoreCase("C")) {

			Import_Dati.LeggiCelle();   
		}
		else {

			System.out.println("Errore: indicare S oppure C");   
			System.exit(0);	
			}

		System.out.println("Inserisci Latitudine (decimale)");
		String latitude = input.nextLine();
		System.out.println("Inserisci Longitudine (decimale)");
		String longitude = input.nextLine();
		input.close();
		vicinanza = Vicinanza.TrovaSito(latitude, longitude);
		System.out.println("Il sito più vicino è " + vicinanza.site + " che si trova a " + vicinanza.distanza +" metri");   
	}

}
