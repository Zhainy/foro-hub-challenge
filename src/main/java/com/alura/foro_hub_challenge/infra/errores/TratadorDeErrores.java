package com.alura.foro_hub_challenge.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    // Error 404: Recurso no encontrado (ID inexistente)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    // Error 400: Fallan las validaciones de atributos (@NotBlank, @NotNull)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream()
                .map(DatosErrorValidacion::new)
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }

    // Error 400: Validaciones de Negocio (Lanzadas manualmente)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity tratarErrorDeValidacionDeNegocio(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // Error 400: JSON mal formado (Comas faltantes, tipos de datos erróneos, etc.)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity tratarErrorMensajeNoLeible(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // Error 401: Fallo de autenticación (Login incorrecto o token inválido)
    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity tratarErrorBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    // Error 403: Acceso denegado (Falta de permisos o sin token)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity tratarErrorAccesoDenegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado");
    }

    // Error 500: Error interno del servidor (Para cualquier otra cosa no capturada)
    @ExceptionHandler(Exception.class)
    public ResponseEntity tratarError500(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getLocalizedMessage());
    }

    // DTO interno para devolver solo la info necesaria del error
    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}