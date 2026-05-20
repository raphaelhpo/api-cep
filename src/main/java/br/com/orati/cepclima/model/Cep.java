package br.com.orati.cepclima.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "DataCep")
@Setter
@Getter
@NoArgsConstructor
public class Cep {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;
  String cep;
  String logradouroCompleto;
  String uf;
  String bairro;
  String latitude;
  String longitude;
  String cidade;
  String ddd;
}
