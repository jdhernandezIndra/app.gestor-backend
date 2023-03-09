package com.app.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.interfaces.IFiles;

import jakarta.annotation.PostConstruct;

@Service
public class FilesServicesImpl implements IFiles {

	@Value("${media.location}")
	private String mediaLocation;

	private Path path;

	@Override
	@PostConstruct
	public void init() {
		// TODO Auto-generated method stub

		path = Paths.get(mediaLocation);
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Error al generar medialocation" + e);
		}
	}

	@Override
	public String store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new RuntimeException("Error al cargar archivo");
			}
			String filename = file.getOriginalFilename();
			Path destinoarchivo = path.resolve(Paths.get(filename)).normalize().toAbsolutePath();
			try (InputStream input = file.getInputStream()) {
				Files.copy(input, destinoarchivo, StandardCopyOption.REPLACE_EXISTING);
			}
			return filename;
		} catch (IOException e) {
			// TODO: handle exception
			throw new RuntimeException("Error al cargar archivo", e);
		}
	}

	@Override
	public Resource loadResource(String nameFile) {

		try {
			Path file = path.resolve(nameFile);
			Resource resource = new UrlResource((file.toUri()));

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Error al mostrar archivo" + nameFile);
			}
		} catch (MalformedURLException e) {
			// TODO: handle exception
			throw new RuntimeException("Error al mostrar archivo" + nameFile);
		}
	}

}
