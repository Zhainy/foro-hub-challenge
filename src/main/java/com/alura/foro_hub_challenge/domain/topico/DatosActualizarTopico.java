package com.alura.foro_hub_challenge.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DatosActualizarTopico(

        @NotBlank(message = "El título no puede estar vacío")
        String titulo,

        @NotBlank(message = "El mensaje no puede estar vacío")
        String mensaje

        // Opcional: Agregar 'StatusTopico status'
        // marcar un tópico como SOLUCIONADO desde este mismo endpoint.
) {
}