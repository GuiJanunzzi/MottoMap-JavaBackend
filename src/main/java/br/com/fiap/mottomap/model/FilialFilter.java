package br.com.fiap.mottomap.model;

public record FilialFilter(
    String nome,
    String cidade,
    String siglaEstado,
    Integer capacidadeMin

) {} 