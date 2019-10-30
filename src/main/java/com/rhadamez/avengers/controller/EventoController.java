package com.rhadamez.avengers.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rhadamez.avengers.model.Evento;
import com.rhadamez.avengers.security.UsuarioSistema;
import com.rhadamez.avengers.service.EventoService;

@Controller
@RequestMapping("/eventos")
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@GetMapping("/novo")
	public ModelAndView novo(Evento evento) {
		ModelAndView mv = new ModelAndView("eventos/CadastroEvento");
		mv.addObject(evento);
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Evento evento, BindingResult result, RedirectAttributes attributes,
			Principal principal) {

		
		if(result.hasErrors()) {
			
			for (ObjectError item : result.getAllErrors()) {
				System.out.println("Bah: "+item.getDefaultMessage());
			}
			
			return novo(evento);
		}

		UsuarioSistema us = (UsuarioSistema) ((Authentication) principal).getPrincipal();
		eventoService.salvar(evento, us.getUsuario());
		
		
		attributes.addFlashAttribute("mensagem", "Vento soprado! digo, evento salvo!");
		
		return new ModelAndView("redirect:/eventos");
	}

}
