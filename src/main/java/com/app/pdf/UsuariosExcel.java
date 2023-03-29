package com.app.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.app.modelos.Usuario;



public class UsuariosExcel {
	
	
	public ByteArrayInputStream getExcel(List<Usuario> usuarios) throws IOException {

		int columns = usuarios.size();//para generar total de columnas por ID encontrados
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet("Datos");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            // Header Row
            Row headerRow = sheet.createRow(0);

            int col = 0;
            for (Usuario dato : usuarios) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(dato.getNombres());
                cell.setCellStyle(headerCellStyle);
                col+=2;
            }

            Row headerRow2 = sheet.createRow(1);

            for (int colx = 0; colx < columns*2; colx++) {
                Cell cell = headerRow2.createCell(colx);
                if(colx%2==0)
                    cell.setCellValue("VALOR 1");
                else
                    cell.setCellValue("VALOR 2");

                sheet.autoSizeColumn(colx);
            }

            int firstCol = 0;
            int lastCol = 1;

            for (int colx = 0; colx < columns; colx++) {
                sheet.addMergedRegion(new CellRangeAddress(0, 0, firstCol, lastCol));
                firstCol+=2;
                lastCol+=2;
                //asi voy fucionando las columnas de 2 en 2 según numero de registro
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
	}

}
