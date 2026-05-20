package br.com.orati.cepclima.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.orati.cepclima.dto.create.CreateCepDTO;
import feign.Headers;

@FeignClient(name = "cep", url = "https://www.cepaberto.com/api/v3")
public interface CepClientService {

    @GetMapping("cep")
    @Headers("Authorization: Token token=${service.cep.token}")
    public CreateCepDTO buscarDadosCep(
            @RequestParam("cep") String cep);
}
