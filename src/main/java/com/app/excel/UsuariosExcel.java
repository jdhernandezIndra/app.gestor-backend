package com.app.excel;

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

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.app.modelos.Usuario;

public class UsuariosExcel {

	public ByteArrayInputStream getExcel(List<Usuario> usuarios) throws IOException {

		// int columns = usuarios.size();// para generar total de columnas por ID
		// encontrados
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet("Datos");

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLACK.getIndex());

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			headerCellStyle.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());

			// Header Row
			Row headerRow = sheet.createRow(0);

			int col = 0;
			Cell cellN = headerRow.createCell(col);
			cellN.setCellValue("Nombres");
			cellN.setCellStyle(headerCellStyle);
			col += 1;
			Cell cellA = headerRow.createCell(col);
			cellA.setCellValue("Apellidos");
			cellA.setCellStyle(headerCellStyle);
			col += 1;
			Cell cellU = headerRow.createCell(col);
			cellU.setCellValue("Usuarios");
			cellU.setCellStyle(headerCellStyle);
			col += 1;
			Cell cellR = headerRow.createCell(col);
			cellR.setCellValue("Rol");
			cellR.setCellStyle(headerCellStyle);
			col += 1;
			Cell cellE = headerRow.createCell(col);
			cellE.setCellValue("Estado");
			cellE.setCellStyle(headerCellStyle);
			col += 1;
			int rows = 1;
			for (Usuario dato : usuarios) {
				Row Rows = sheet.createRow(rows);
				Rows.createCell(0).setCellValue(dato.getNombres());
				Rows.createCell(1).setCellValue(dato.getApellidos());
				Rows.createCell(2).setCellValue(dato.getUsuario());
				Rows.createCell(3).setCellValue(dato.getRol());
				Rows.createCell(4).setCellValue(dato.isEstado());
				sheet.autoSizeColumn(0);
				sheet.autoSizeColumn(1);
				sheet.autoSizeColumn(2);
				sheet.autoSizeColumn(3);
				sheet.autoSizeColumn(4);
				rows++;
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}

}
