package com.app.controladores;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.servicios.FilesServicesImpl;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("media/")
public class MediaController {

	@Autowired
	private FilesServicesImpl storages;
	@Autowired
	private HttpServletRequest request;


	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("cargar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Map<String, String> cargaFile(@RequestParam("file") MultipartFile multipartfile) {
		String path = storages.store(multipartfile);
		String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");

		String url = ServletUriComponentsBuilder.fromHttpUrl(host).path("/media/visualizar/").path(path).toUriString();

		return Map.of("url", url);
	}

	@GetMapping("visualizar/{filename:.+}")
	public ResponseEntity<Resource> getArchivo(@PathVariable String filename) throws IOException {
		Resource file = storages.loadResource(filename);
		String contenType = Files.probeContentType(file.getFile().toPath());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contenType).body(file);
	}

}
