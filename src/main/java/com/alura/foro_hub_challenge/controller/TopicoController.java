package com.alura.foro_hub_challenge.controller;

import com.alura.foro_hub_challenge.domain.curso.CursoRepository;
import com.alura.foro_hub_challenge.domain.topico.*;
import com.alura.foro_hub_challenge.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key") // Habilita el candadito en Swagger
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    // 1. POST: Registrar un nuevo tópico
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistro,
                                                                UriComponentsBuilder uriComponentsBuilder) {

        // Validar duplicados (Regla de Negocio)
        if (topicoRepository.existsByTituloAndMensajeAllIgnoreCase(datosRegistro.titulo(), datosRegistro.mensaje())) {
            throw new ValidationException("Ya existe un tópico con este título y mensaje");
        }

        // Buscar las entidades relacionadas (Usuario y Curso)
        var autor = usuarioRepository.findById(datosRegistro.autorId())
                .orElseThrow(() -> new ValidationException("No existe el usuario con el ID proporcionado"));

        var curso = cursoRepository.findById(datosRegistro.cursoId())
                .orElseThrow(() -> new ValidationException("No existe el curso con el ID proporcionado"));

        // Crear y guardar la entidad
        var topico = new Topico(datosRegistro.titulo(), datosRegistro.mensaje(), autor, curso);
        topicoRepository.save(topico);

        // Retornar 201 Created + Header Location + Body
        var datosRespuesta = new DatosRespuestaTopico(topico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    // 2. GET: Listar tópicos (Paginado)
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListadoTopico::new));
    }

    // 3. PUT: Actualizar tópico
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizar) {
        Topico topico = topicoRepository.getReferenceById(id); // Lazy loading (proxy)

        // Ejecuta la lógica de negocio dentro de la entidad
        topico.actualizarDatos(datosActualizar.titulo(), datosActualizar.mensaje(), null);

        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    // 4. DELETE: Eliminar tópico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        // Validamos existencia antes de borrar para evitar excepciones silenciosas
        if (!topicoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna 204
    }

    // 5. GET: Detalle de un tópico específico
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> retornaDatosTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosRespuestaTopico(topico);
        return ResponseEntity.ok(datosTopico);
    }
}