package br.com.orati.cepclima.dto.response;

import br.com.orati.cepclima.dto.create.CreateCepDTO;
import br.com.orati.cepclima.dto.create.CreateClimaDTO;

public record ResponseDTO(
        CreateCepDTO cep,
        CreateClimaDTO clima) {

}
