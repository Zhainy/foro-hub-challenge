package com.alura.foro_hub_challenge.domain.respuesta;

import java.time.LocalDateTime;

public record DatosRetornoRespuesta(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        String autor,   // Devolvemos solo el nombre
        String topico   // Devolvemos solo el título
) {
    public DatosRetornoRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getAutor().getNombre(),
                respuesta.getTopico().getTitulo()
        );
    }
}