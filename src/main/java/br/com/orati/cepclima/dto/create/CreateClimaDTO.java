package br.com.orati.cepclima.dto.create;

import java.time.LocalDateTime;

public record CreateClimaDTO(
        LocalDateTime data,
        Float temperatura,
        Float sensacaoTermica,
        Integer condicaoClimatica,
        Float velocidadeVento) {

}
