package com.rhadamez.avengers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhadamez.avengers.model.Evento;
import com.rhadamez.avengers.model.Usuario;
import com.rhadamez.avengers.repository.EventoRepository;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	public List<Evento> listar() {
		return eventoRepository.findAll();
	}
	
	public Evento salvar(Evento evento, Usuario usuario) {
		evento.setCriador(usuario);
		return eventoRepository.save(evento);
	}
	
	public void delete(Long idEvento) {
		Optional<Evento> evento = eventoRepository.findById(idEvento);
		
		if(evento.isEmpty()) {
			throw new RuntimeException("Vento não soprado. Digo, evento não encontrado");
		}
		
		eventoRepository.delete(evento.get());
	}

}
