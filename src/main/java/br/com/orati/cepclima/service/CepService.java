package br.com.orati.cepclima.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.orati.cepclima.client.CepClientService;
import br.com.orati.cepclima.client.ClimaClientService;
import br.com.orati.cepclima.dto.create.Cidade;
import br.com.orati.cepclima.dto.create.CreateCepDTO;
import br.com.orati.cepclima.dto.create.CreateClimaDTO;
import br.com.orati.cepclima.dto.create.Estado;
import br.com.orati.cepclima.dto.response.ResponseDTO;
import br.com.orati.cepclima.exceptions.CepInvalidoException;
import br.com.orati.cepclima.exceptions.CepVazioException;
import br.com.orati.cepclima.model.Cep;
import br.com.orati.cepclima.repository.CepRepository;
import feign.FeignException;

@Service

public class CepService {

    private final CepRepository repository;
    private final CepClientService cepClientService;
    private final ClimaClientService climaClientService;

    public CepService(CepRepository repository,
            CepClientService cepClientService,
            ClimaClientService climaClientService) {
        this.repository = repository;
        this.cepClientService = cepClientService;
        this.climaClientService = climaClientService;
    }

    /**
     * Realiza a chamada da API que buscar dados do CEP.
     * 
     * @param cepDTO
     * @return
     */
    private CreateCepDTO buscarDadosCepApi(CreateCepDTO cepDTO) {
        return cepClientService.buscarDadosCep(cepDTO.getCep());
    }

    /**
     * Realiza a chamada da API que consulta o Clima.
     * 
     * @param cepDTO
     * @return
     */
    private CreateClimaDTO buscarDadosClimaApi(CreateCepDTO cepDTO) {
        return climaClientService.buscarDadosClima(
                Double.valueOf(cepDTO.getLatitude()),
                Double.valueOf(cepDTO.getLongitude()));
    }

    /**
     * Realiza a busca do CEP no banco de dados utilizando repository.
     * 
     * @param cepDTO
     * @return
     */
    private Optional<Cep> buscarDadosCepDb(CreateCepDTO cepDTO) {
        return repository.findByCep(cepDTO.getCep());
    }

    /**
     * Salva os dados no banco utilizando o repository.
     * 
     * @param cep
     */
    private void salvarNoBanco(Cep cep) {
        repository.save(cep);
    }

    /**
     * Valida a situação do objetoDTO e do CEP (se ambos ou algum está nulo).
     * 
     * @param cepDTO
     * @return
     */
    private CreateCepDTO validaCep(CreateCepDTO cepDTO) {
        if (cepDTO == null || cepDTO.getCep() == null) {
            throw new CepVazioException("CEP Vazio.");
        } else {
            return cepDTO;
        }
    }

    /**
     * Mapeia a criação de um novo objeto ResponseDTO
     * 
     * @param cep
     * @return
     */
    private ResponseDTO mapperResponseDTO(CreateCepDTO cep, CreateClimaDTO clima) {
        return new ResponseDTO(cep, clima);
    }

    /**
     * Mapeaia a criação de um novo objeto CreateDTO.
     * 
     * @param cep
     * @return
     */
    private CreateCepDTO mapperCreateCepDTO(Cep cep) {
        return new CreateCepDTO(
                cep.getCep(),
                cep.getLogradouroCompleto(),
                cep.getBairro(),
                cep.getLatitude(),
                cep.getLongitude(),
                new Cidade(cep.getDdd(), cep.getCidade()),
                new Estado(cep.getUf()));
    }

    /**
     * Cria o objeto de retorno para a consulta de solicitada.
     * 
     * @param cepDTO
     * @return
     */
    public ResponseDTO create(String cep) {
        try {
            CreateCepDTO cepDTO = new CreateCepDTO();
            cepDTO.setCep(cep);
            validaCep(cepDTO);
            CreateCepDTO dadosCep = buscarDadosCepDb(cepDTO)
                    .map((cepObj) -> mapperCreateCepDTO(cepObj))
                    .orElseGet(() -> {
                        CreateCepDTO cepApi = buscarDadosCepApi(cepDTO);
                        salvarNoBanco(cepApi.toEntity());
                        return cepApi;
                    });
            return this.mapperResponseDTO(dadosCep, buscarDadosClimaApi(dadosCep));
        } catch (FeignException e) {
            System.err.println("Erro na API Externa: " + e.contentUTF8());
            System.err.println("Status da API Externa: " + e.status());
            throw new CepInvalidoException("CEP Inválido\n" + e.getMessage());
        }
    }

}
