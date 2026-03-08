package com.alura.foro_hub_challenge.domain.respuesta;

import com.alura.foro_hub_challenge.domain.topico.Topico;
import com.alura.foro_hub_challenge.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    private Boolean solucion = false;

    // Constructor para registrar una nueva respuesta
    // (No necesitamos pasar ID ni fecha, se generan solos)
    public Respuesta(String mensaje, Topico topico, Usuario autor) {
        this.mensaje = mensaje;
        this.topico = topico;
        this.autor = autor;
        this.fechaCreacion = LocalDateTime.now();
        this.solucion = false; // Por defecto no es la solución
    }

    // Metodo para marcar esta respuesta como la solución del tópico
    public void marcarComoSolucion() {
        this.solucion = true;
    }

    // Metodo para actualizar el mensaje
    public void actualizarMensaje(String nuevoMensaje) {
        this.mensaje = nuevoMensaje;
    }
}