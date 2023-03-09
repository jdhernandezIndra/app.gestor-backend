package com.app.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFiles {
	void init();

	String store(MultipartFile file);

	Resource loadResource(String nameFile);
}
