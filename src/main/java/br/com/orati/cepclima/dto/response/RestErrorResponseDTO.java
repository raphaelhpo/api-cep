package br.com.orati.cepclima.dto.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestErrorResponseDTO {
    String message;
    HttpStatus status;
}
