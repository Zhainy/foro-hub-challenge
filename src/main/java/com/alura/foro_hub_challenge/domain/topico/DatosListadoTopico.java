package com.alura.foro_hub_challenge.domain.topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        String status,
        String autor,
        String curso
) {

    // Constructor personalizado para convertir Entidad -> DTO
    public DatosListadoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus().toString(),
                topico.getAutor().getNombre(), // Obtenemos solo el nombre del objeto Usuario
                topico.getCurso().getNombre()  // Obtenemos solo el nombre del objeto Curso
        );
    }
}