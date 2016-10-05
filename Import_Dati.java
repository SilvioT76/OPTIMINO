package optimino;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.*;

public class Import_Dati {

	public static void LeggiCelle() throws IOException  {

		FileInputStream file = new FileInputStream(new File("C:\\Users\\ads\\Desktop\\Schede_primarie.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheetCell = workbook.getSheet("Celle");

		String Tabella = "CELL";
		QueryDB querydb_cell  = new QueryDB("test.db");

		querydb_cell.DeleteTable(Tabella);
		String query = "CREATE TABLE IF NOT EXISTS " + Tabella + "" +
				"(cell     TEXT    NOT NULL, " + 
				" latitude		 TEXT	 NOT NULL, " +	
				" longitude        TEXT    NOT NULL, " + 
				" azimuth        	 TEXT	 NOT NULL)";  
		querydb_cell.CreateTable(query);

		//XSSFSheet foglio2 = workbook.createSheet("Results Worksheet");
		// Dichiaro l'oggetto cella
		String cella = null;
		String lat = null;
		String lon = null;
		String azi = null;

		//Cell cellupd = null;
		//recupero ultima riga del foglio
		int rows = sheetCell.getLastRowNum();
		int i;
		//double sum = 0;
		//aggiorno tutte le righe del foglio 1
		for (i = 1; i <= rows; i++) {
			cella = sheetCell.getRow(i).getCell(1).toString();
			lat = sheetCell.getRow(i).getCell(130).toString().replaceAll(",", ".");
			lon = sheetCell.getRow(i).getCell(131).toString().replaceAll(",", ".");
			azi = sheetCell.getRow(i).getCell(40).toString();

			String queryUpdate = "INSERT INTO " + Tabella + " (cell,latitude,longitude,azimuth) "+
					"VALUES ('" + cella + "','" + lat + "','" + lon + "','" + azi + "')";
			querydb_cell.UpdateTable(queryUpdate);
			//cellupd = foglio1.getRow(i).createCell(1);
			//cellupd.setCellValue(cell.getNumericCellValue() * 2);
			//sum = sum + cell.getNumericCellValue();
			System.out.println(cella);
		}
		//scrivo la somma delle righe del foglio 1 nel nuovo foglio 2 creato
		//cell = foglio2.createRow(0).createCell(0);
		//cell.setCellValue("La somma è:");
		//cell = foglio2.getRow(0).createCell(1);
		//cell.setCellValue(sum);
		//Definisco input stream
		//FileOutputStream output_file = new FileOutputStream(new File("C:\\testExcel.xls"));
		//Scrivo i cambiamenti nel file
		//workbook.write(output_file);
		//chiudo stream e workbook
		//output_file.close();
		workbook.close();

	}

	public static void LeggiSiti() throws IOException  {

		FileInputStream file = new FileInputStream(new File("C:\\Users\\ads\\Desktop\\RISTORANTI.xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheetCell = workbook.getSheet("Siti");

		String Tabella = "SITES";
		QueryDB querydb_sites  = new QueryDB("test.db");

		querydb_sites.DeleteTable(Tabella);
		String query = "CREATE TABLE IF NOT EXISTS " + Tabella +"" +
				"(site     TEXT    NOT NULL, " + 
				" latitude		 TEXT	 NOT NULL, " +	
				" longitude        TEXT    NOT NULL)";  
		querydb_sites.CreateTable(query);

		String site = null;
		String lat = null;
		String lon = null;

		int rows = sheetCell.getLastRowNum();
		int i;

		for (i = 1; i <= rows; i++) {
			site = sheetCell.getRow(i).getCell(0).toString();
			lat = sheetCell.getRow(i).getCell(1).toString().replaceAll(",", ".");
			lon = sheetCell.getRow(i).getCell(2).toString().replaceAll(",", ".");

			String queryUpdate = "INSERT INTO " + Tabella + " (site,latitude,longitude) "+
					"VALUES ('" + site + "','" + lat + "','" + lon + "')";
			querydb_sites.UpdateTable(queryUpdate);

		}

		workbook.close();

	}
}