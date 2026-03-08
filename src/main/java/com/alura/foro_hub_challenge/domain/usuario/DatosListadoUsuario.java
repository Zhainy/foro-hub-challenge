package com.alura.foro_hub_challenge.domain.usuario;

public record DatosListadoUsuario(Long id, String nombre, String correoElectronico) {
    public DatosListadoUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getCorreoElectronico());
    }
}