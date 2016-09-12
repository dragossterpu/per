package es.mira.progesin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/prueba")
public class PruebaController {

	@RequestMapping(method = RequestMethod.GET)
	public String metodoPrueba() {
		System.out.println("Entro en el método");
		return "prueba";
	}
}
