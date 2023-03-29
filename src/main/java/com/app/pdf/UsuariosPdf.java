package com.app.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;

import java.util.List;



import com.app.modelos.Usuario;


import com.lowagie.text.Document;

import com.lowagie.text.PageSize;

import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class UsuariosPdf {
	
	
	public ByteArrayOutputStream getPDF(List<Usuario> usuarios) {

        // Creamos la instancia de memoria en la que se escribirá el archivo temporalmente
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4);

    		PdfPTable tabla = new PdfPTable(1);
    		tabla.setSpacingAfter(20);

    		PdfPCell cell = null;

    		cell = new PdfPCell(new Phrase("Usuarios"));
    		cell.setBackgroundColor(new Color(184, 218, 255));
    		cell.setPadding(8f);
    		tabla.addCell(cell);
    		
    		PdfPTable tabla3 = new PdfPTable(4);
    		tabla3.setWidths(new float [] {3.5f, 1, 1, 1});
    		tabla3.addCell("Nombres");
    		tabla3.addCell("Apellidos");
    		tabla3.addCell("Usuario");
    		tabla3.addCell("Rol");
    		tabla3.addCell("Estado");
    		
    		for(Usuario item: usuarios) {
    			tabla3.addCell(item.getNombres());
    			tabla3.addCell(item.getApellidos());
    			tabla3.addCell(item.getUsuario());
    			switch (item.getRol()) {
    			case "ROLE_ADMIN": 
    				tabla3.addCell("ADMIN");
    			break;
    			case "ROLE_USER": 
    				tabla3.addCell("USER");
    			break;
    			default:
    				tabla3.addCell("USER");
    			}
    			
    			if(item.isEstado()){
    				tabla3.addCell("Habilitado");
    			}else {
    				tabla3.addCell("inhabilitado");
    			}
    			
    		}

            // Asignamos la variable ByteArrayOutputStream bos donde se escribirá el documento
            PdfWriter.getInstance(document, bos);
            document.open();
            document.add(tabla3);
            document.close();
            // Retornamos la variable al finalizar
            return bos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
