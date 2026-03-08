package com.alura.foro_hub_challenge.domain.topico;


import com.alura.foro_hub_challenge.domain.curso.Curso;
import com.alura.foro_hub_challenge.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now(); // Se genera automático al instanciar

    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.NO_RESPONDIDO; // Estado por defecto

    // Relación Muchos Tópicos -> Un Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    // Relación Muchos Tópicos -> Un Curso
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    // Constructor para registrar un nuevo tópico
    public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.curso = curso;
        // status y fechaCreacion se asignan por defecto
    }

    // Metodo para actualizar datos (PUT)
    public void actualizarDatos(String titulo, String mensaje, StatusTopico status) {
        if (titulo != null) {
            this.titulo = titulo;
        }
        if (mensaje != null) {
            this.mensaje = mensaje;
        }
        if (status != null) {
            this.status = status;
        }
    }
}