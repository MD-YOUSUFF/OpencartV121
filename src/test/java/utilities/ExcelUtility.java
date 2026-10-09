package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	String path;
	FileInputStream fi;
	FileOutputStream fo;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	Row row;
	Cell cell;

	public ExcelUtility(String path) {
		this.path = path;
	}

	public int getRowCount(String sheetName) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int rowcount = sheet.getLastRowNum();
		workbook.close();
		fi.close();
		return rowcount;
	}

	public int getCellCount(String sheetName, int rownum) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		int cellcount = row.getLastCellNum();
		workbook.close();
		fi.close();
		return cellcount;
	}

	public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);

		if (row == null)
			return "";

		cell = row.getCell(colnum);

		DataFormatter formatter = new DataFormatter();
		String data;
		try {
//			cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
//			data = cell.getStringCellValue();
			data = formatter.formatCellValue(cell); //returns formatted cell as string regardless of type
			
		} catch (Exception e) {
			data = "";
		}

		workbook.close();
		fi.close();
		return data;
	}

	public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {

		File xlfile = new File(path);
		if (!xlfile.exists()) {    	//If the file not exists then create new file
			workbook = new XSSFWorkbook();
			fo = new FileOutputStream(path);
			workbook.write(fo);
			fo.close();
		}

		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		fi.close();

		if (workbook.getSheetIndex(sheetName) == -1) //If sheet not exists then create new sheet
			workbook.createSheet(sheetName);

		sheet = workbook.getSheet(sheetName);

		if (sheet.getRow(rownum) == null)  // If row not exists then create new row
			sheet.createRow(rownum);
		row = sheet.getRow(rownum);

		cell = row.getCell(colnum);
		if (cell == null)
			cell = row.createCell(colnum);

		cell.setCellValue(data);

		fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fo.close();
	}
}