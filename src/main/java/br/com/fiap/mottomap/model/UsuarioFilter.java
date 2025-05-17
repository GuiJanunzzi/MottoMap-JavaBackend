package br.com.fiap.mottomap.model;

public record UsuarioFilter(
    String nome,
    String email,
    CargoUsuario cargoUsuario
) {}