package com.rhadamez.avengers.controller;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
			return novo(evento);
		}

		UsuarioSistema us = (UsuarioSistema) ((Authentication) principal).getPrincipal();
		eventoService.salvar(evento, us.getUsuario());
		
		
		attributes.addFlashAttribute("mensagem", "Vento soprado! digo, evento salvo!");
		return new ModelAndView("redirect:/home");
	}

	@GetMapping("/manda-pro-alem/{id}")
	public ModelAndView deletar(@PathVariable("id") Long id, RedirectAttributes attributes, Principal principal) {
		
		UsuarioSistema us = (UsuarioSistema) ((Authentication) principal).getPrincipal();
		Optional<Evento> evento = eventoService.buscar(id);
		
		if(us.getUsuario().getId() != evento.get().getCriador().getId()) {
			attributes.addFlashAttribute("mensagemErro", "Tentando deletar um evento que não é seu, né mizerávi?");
			return new ModelAndView("redirect:/home");
		}
		
		try {
			eventoService.delete(id);	
		}catch(RuntimeException e) {
			attributes.addFlashAttribute("mensagemErro", "Não deu pra excluir sabagaça, já deve ter sido excluída.");
			return new ModelAndView("redirect:/home");
		}
		
		attributes.addFlashAttribute("mensagem", "Vento soprado! digo, evento deletado.");
		return new ModelAndView("redirect:/home");
	}
	
}
