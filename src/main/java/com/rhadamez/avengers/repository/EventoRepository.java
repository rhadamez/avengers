package com.rhadamez.avengers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhadamez.avengers.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

}
