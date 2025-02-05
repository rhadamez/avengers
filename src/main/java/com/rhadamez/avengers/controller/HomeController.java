package com.rhadamez.avengers.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rhadamez.avengers.service.EventoService;

@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private EventoService eventoService;

	@GetMapping
	public ModelAndView home(Principal principal) {
		ModelAndView mv = new ModelAndView("home/Home");
		mv.addObject("eventos", eventoService.listar());
		
		return mv;
	}
	
	
}
