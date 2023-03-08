package com.app.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControlServices {

	@GetMapping("ping")
	public String ping() {
		return "Pong!";
	}
}
