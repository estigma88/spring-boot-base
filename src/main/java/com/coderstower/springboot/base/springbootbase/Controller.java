package com.coderstower.springboot.base.springbootbase;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class Controller {

	@GetMapping(path = "/helloWorld")
	public String helloWorld() {
		var webClient = WebClient.create("http://localhost:9090/some/thing");

		return webClient.get().retrieve().bodyToMono(String.class).block();
	}

}
