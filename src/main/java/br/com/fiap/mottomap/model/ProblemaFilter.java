package br.com.fiap.mottomap.model;

import java.time.LocalDate;

public record ProblemaFilter(
    TipoProblema tipoProblema,
    LocalDate dataInicio,
    LocalDate dataFim,
    Boolean resolvido,
    Long motoId
) {}
