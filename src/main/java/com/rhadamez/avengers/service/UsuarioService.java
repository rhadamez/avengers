package com.rhadamez.avengers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rhadamez.avengers.exception.AlreadyExistsException;
import com.rhadamez.avengers.model.Usuario;
import com.rhadamez.avengers.repository.PermissaoRepository;
import com.rhadamez.avengers.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Usuario> listar(){
		return usuarioRepository.findAll();
	}
	
	public Usuario salvar(Usuario usuario) {
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmailIgnoreCase(usuario.getEmail());
		
		if(usuarioExistente.isPresent()) {
			throw new AlreadyExistsException("E-mail já existe maluco, o fiapo...o fiapo ahh ahh ah...");
		}
		
		usuario.setPermissoes(permissaoRepository.findAll());
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
}
