package com.alura.foro_hub_challenge.domain.topico;

public enum StatusTopico {

    // El tema fue creado, pero aún no tiene ninguna respuesta
    NO_RESPONDIDO,

    // El tema tiene respuestas, pero el autor aún no ha marcado ninguna como la solución definitiva
    NO_SOLUCIONADO,

    // El tema tiene una respuesta marcada como solución
    SOLUCIONADO,

    // El tema fue cerrado por un moderador o por inactividad (ya no recibe respuestas)
    CERRADO;
}