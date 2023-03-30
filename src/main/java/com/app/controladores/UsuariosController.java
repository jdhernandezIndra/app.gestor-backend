package com.app.controladores;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.excel.UsuariosExcel;
import com.app.modelos.Usuario;
import com.app.pdf.UsuariosPdf;
import com.app.servicios.UsuarioServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("usuarios/")
public class UsuariosController {

	@Autowired
	private UsuarioServiceImpl UsuarioServices;

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("lista")
	public List<Usuario> listar() {
		return UsuarioServices.listar();
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("us/{user}")
	public Usuario usuarioId(@PathVariable String user) {
		return UsuarioServices.usuarioPorUser(user);
	}

	@PostMapping("guardar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario guardar(@RequestBody Usuario u) {
		return UsuarioServices.guardar(u);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("actualiza")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario actualiza(@RequestBody Usuario u) {

		return UsuarioServices.actualiza(u);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("inhabilitar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario inhabilitar(@RequestBody Usuario u) {
		return UsuarioServices.inhabilitar(u);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("rol")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Usuario cambiarRol(@RequestBody Usuario u) {
		return UsuarioServices.cambioRol(u);
	}
	
//	@GetMapping("/lista/pdf")
//	public Map<String, String>  response(HttpServletResponse response) {
//		response.setContentType("application/pdf");
//		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", "reporte.pdf"));
//		
//		
//		return Map.of("url", "pdf");
//	}
	
    @GetMapping("/pdf/lista")
    public void downloadFile(HttpServletResponse response) throws IOException {
        UsuariosPdf generator = new UsuariosPdf();
        byte[] pdfReport = generator.getPDF(UsuarioServices.listar()).toByteArray();

        String mimeType =  "application/pdf";
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", "reporte_usuarios.pdf"));

        response.setContentLength(pdfReport.length);

        ByteArrayInputStream inStream = new ByteArrayInputStream( pdfReport);

        FileCopyUtils.copy(inStream, response.getOutputStream());
    }
    
    @GetMapping("/excel/lista")
    public void downloadFileExcel(HttpServletResponse response) throws IOException {
        UsuariosExcel generator = new UsuariosExcel();
        byte[] ExcelReport = generator.getExcel(UsuarioServices.listar()).readAllBytes();

        String mimeType =  "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", "reporte_usuarios.xlsx"));

        response.setContentLength(ExcelReport.length);

        ByteArrayInputStream inStream = new ByteArrayInputStream( ExcelReport);

        FileCopyUtils.copy(inStream, response.getOutputStream());
    }

}
