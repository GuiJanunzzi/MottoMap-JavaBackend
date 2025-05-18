package br.com.fiap.mottomap.model;

public record MotoFilter(
    String placa,
    String chassi,
    ModeloMoto modeloMoto,
    StatusMoto statusMoto
) {}