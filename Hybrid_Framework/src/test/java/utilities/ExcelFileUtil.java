package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {

	Workbook wb;

	public ExcelFileUtil(String ExcelPath) throws Throwable{
		FileInputStream fi = new FileInputStream(ExcelPath);
		wb = WorkbookFactory.create(fi);
	}

	public int rowCount(String SheetName) {	
		return wb.getSheet(SheetName).getLastRowNum();
	}

	public String getCellData(String SheetName, int row, int Column) {
		String celldata ="";
		if(wb.getSheet(SheetName).getRow(row).getCell(Column).getCellType()==CellType.NUMERIC) {
			int  data = (int)wb.getSheet(SheetName).getRow(row).getCell(Column).getNumericCellValue();
			celldata = String.valueOf(data);
		}
		else {
			celldata = wb.getSheet(SheetName).getRow(row).getCell(Column).getStringCellValue();
		}
		return celldata;
	}
 
	public void setCellData(String SheetName, int row, int column, String status, String WriteExcel) throws Throwable{
		wb.getSheet(SheetName).getRow(row).createCell(column).setCellValue(status);
		if(status.equalsIgnoreCase("Pass")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			font.setFontName("Cambria");
			font.setItalic(true);
			font.setFontHeightInPoints((short) 12);
			style.setFont(font);
			wb.getSheet(SheetName).getRow(row).getCell(column).setCellStyle(style);
		}
		else if (status.equalsIgnoreCase("Fail")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			font.setFontName("Cambria");
			font.setItalic(true);
			font.setFontHeightInPoints((short) 12);
			style.setFont(font);
			wb.getSheet(SheetName).getRow(row).getCell(column).setCellStyle(style);
		}
		else if (status.equalsIgnoreCase("Blocked")) {
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			font.setFontName("Cambria");
			font.setItalic(true);
			font.setFontHeightInPoints((short) 12);
			style.setFont(font);
			wb.getSheet(SheetName).getRow(row).getCell(column).setCellStyle(style);
		}

		FileOutputStream fo = new FileOutputStream(WriteExcel);
		wb.write(fo);

	}


}
