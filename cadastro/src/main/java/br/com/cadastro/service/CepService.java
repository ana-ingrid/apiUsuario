package br.com.cadastro.service;

import br.com.cadastro.config.ClienteCep;
import br.com.cadastro.dto.EnderecoDto;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CepService {

    private final ClienteCep feignClient;

    public CepService(ClienteCep feignClient) {
        this.feignClient = feignClient;
    }

    public EnderecoDto dadosCep(String cep) {
        return feignClient.getCepInfo(cep);
    }

}
