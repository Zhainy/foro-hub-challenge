package com.alura.foro_hub_challenge.controller;

import com.alura.foro_hub_challenge.domain.respuesta.*;
import com.alura.foro_hub_challenge.domain.topico.TopicoRepository;
import com.alura.foro_hub_challenge.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. REGISTRAR UNA RESPUESTA (POST)
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRetornoRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datos,
                                                                    UriComponentsBuilder uriComponentsBuilder) {

        // Validamos que el Tópico y el Usuario existan. Si no, lanzamos 404.
        var topico = topicoRepository.findById(datos.topicoId())
                .orElseThrow(() -> new EntityNotFoundException("El tópico no existe"));

        var autor = usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new EntityNotFoundException("El usuario no existe"));

        // Creamos la entidad Respuesta
        var respuesta = new Respuesta(datos.mensaje(), topico, autor);
        respuestaRepository.save(respuesta);

        // Retornamos 201 Created con la URL del nuevo recurso
        var datosRespuesta = new DatosRetornoRespuesta(respuesta);
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    // 2. LISTAR RESPUESTAS (GET)
    @GetMapping
    public ResponseEntity<Page<DatosRetornoRespuesta>> listarRespuestas(@PageableDefault(sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(respuestaRepository.findAll(paginacion).map(DatosRetornoRespuesta::new));
    }

    // 3. DETALLE DE UNA RESPUESTA (GET /{id})
    @GetMapping("/{id}")
    public ResponseEntity<DatosRetornoRespuesta> obtenerRespuesta(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRetornoRespuesta(respuesta));
    }

    // 4. ACTUALIZAR RESPUESTA (PUT /{id})
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRetornoRespuesta> actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid DatosActualizarRespuesta datosActualizar) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);

        // Actualizamos solo el mensaje (la lógica está en la entidad)
        respuesta.actualizarMensaje(datosActualizar.mensaje());

        return ResponseEntity.ok(new DatosRetornoRespuesta(respuesta));
    }

    // 5. ELIMINAR RESPUESTA (DELETE /{id})
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id) {
        if (!respuestaRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}