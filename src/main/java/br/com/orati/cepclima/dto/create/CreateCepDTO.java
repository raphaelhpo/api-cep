package br.com.orati.cepclima.dto.create;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.orati.cepclima.model.Cep;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCepDTO {
    String cep;
    @JsonProperty("logradouro")
    String logradouroCompleto;
    @JsonProperty("bairro")
    String bairro;
    @JsonProperty("latitude")
    String latitude;
    @JsonProperty("longitude")
    String longitude;
    @JsonProperty("cidade")
    Cidade cidade;
    @JsonProperty("estado")
    Estado estado;

    public Cep toEntity() {
        Cep cepObj = new Cep();
        cepObj.setCep(cep);
        cepObj.setLogradouroCompleto(logradouroCompleto);
        cepObj.setUf(estado.getSigla());
        cepObj.setBairro(bairro);
        cepObj.setLatitude(latitude);
        cepObj.setLongitude(longitude);
        cepObj.setCidade(cidade.getNome());
        cepObj.setDdd(cidade.getDdd());
        return cepObj;
    }

}
