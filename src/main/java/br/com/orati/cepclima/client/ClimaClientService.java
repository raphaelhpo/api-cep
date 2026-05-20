package br.com.orati.cepclima.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.orati.cepclima.dto.create.CreateClimaDTO;

@FeignClient(name = "Consulta-de-clima", url = "${service.clima.url}")
public interface ClimaClientService {

    @GetMapping("/api/clima")
    public CreateClimaDTO buscarDadosClima(
            @RequestParam Double latitude,
            @RequestParam Double longitude);
}
