package com.alura.foro_hub_challenge.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Comprueba si existe un tópico con el mismo título Y mensaje ignora mayúsculas/minúsculas
    boolean existsByTituloAndMensajeAllIgnoreCase(String titulo, String mensaje);

    Page<Topico> findByStatus(StatusTopico status, Pageable paginacion);
}