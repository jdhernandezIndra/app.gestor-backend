package com.app.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;

import java.util.List;

import com.app.modelos.Usuario;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;

import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class UsuariosPdf {

	public ByteArrayOutputStream getPDF(List<Usuario> usuarios) {

		// Creamos la instancia de memoria en la que se escribirá el archivo
		// temporalmente
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

			Document document = new Document();
			// Asignamos la variable ByteArrayOutputStream bos donde se escribirá el
			// documento
			PdfWriter.getInstance(document, bos).setInitialLeading(20);
			document.open();
			Font font = new Font();
			font.setColor(Color.DARK_GRAY);
			font.setFamily("arial");
			font.setSize(22);
			Paragraph titulo= new Paragraph("Usuarios",font);
			titulo.setAlignment("center");
			document.add(titulo);
			
			
			document.add(new Paragraph(" "));

			
			// We create the table (Creamos la tabla).
						PdfPTable table = new PdfPTable(5);
						// Now we fill the PDF table
						// Ahora llenamos la tabla del PDF
						PdfPCell columnHeader;
						// Fill table rows (rellenamos las filas de la tabla).
						columnHeader = new PdfPCell(new Phrase("Nombres"));
						columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
						columnHeader.setBackgroundColor(Color.GRAY);
						table.addCell(columnHeader);

						columnHeader = new PdfPCell(new Phrase("Apellidos"));
						columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
						columnHeader.setBackgroundColor(Color.GRAY);
						table.addCell(columnHeader);

						columnHeader = new PdfPCell(new Phrase("Usuarios"));
						columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
						columnHeader.setBackgroundColor(Color.GRAY);
						table.addCell(columnHeader);

						columnHeader = new PdfPCell(new Phrase("Rol"));
						columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
						columnHeader.setBackgroundColor(Color.GRAY);
						table.addCell(columnHeader);

						columnHeader = new PdfPCell(new Phrase("Estado"));
						columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
						columnHeader.setBackgroundColor(Color.GRAY);
						table.addCell(columnHeader);

//			            for (int column = 0; column < numColumns; column++) {
//			                columnHeader = new PdfPCell(new Phrase("COL " + column));
//			                columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
//			                table.addCell(columnHeader);
//			            }
						table.setHeaderRows(1);
						// Fill table rows (rellenamos las filas de la tabla).

						for (Usuario datos : usuarios) {
							table.addCell(datos.getNombres());
							table.addCell(datos.getApellidos());
							table.addCell(datos.getUsuario());
							table.addCell(datos.getRol());
							table.addCell(datos.isEstado() + "");

						}
			document.add(table);
			document.close();
			// Retornamos la variable al finalizar
			return bos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
