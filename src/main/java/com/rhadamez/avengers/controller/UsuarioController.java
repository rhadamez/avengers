package com.rhadamez.avengers.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rhadamez.avengers.exception.AlreadyExistsException;
import com.rhadamez.avengers.model.Usuario;
import com.rhadamez.avengers.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ModelAndView listagem() {
		ModelAndView mv = new ModelAndView("usuarios/Usuarios");
		mv.addObject("usuarios", usuarioService.listar());

		return mv;
	}

	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuarios/CadastroUsuario");
		mv.addObject(usuario);

		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes,
			Principal principal) {

		if (result.hasErrors()) {
			return novo(usuario);
		}

		try {
			usuarioService.salvar(usuario);
		} catch (AlreadyExistsException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		}

		if (principal != null) {
			attributes.addFlashAttribute("mensagem", "Avenger salvo! (nem todos, ROB.)");
			return new ModelAndView("redirect:/usuarios");
		}

		attributes.addFlashAttribute("mensagem", "Avenger salvo! (nem todos, ROB.)...Tá, tenta logar agora.");
		return new ModelAndView("redirect:/login");
	}

}
