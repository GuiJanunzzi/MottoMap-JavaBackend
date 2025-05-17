package br.com.fiap.mottomap.model;

public record PosicaoPatioFilter(
    String identificacao,
    Area area,
    Boolean ocupado,
    Long filialId
    
) {}