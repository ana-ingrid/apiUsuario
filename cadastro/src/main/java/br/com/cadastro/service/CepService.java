package br.com.cadastro.service;

import br.com.cadastro.util.ClienteCep;
import br.com.cadastro.dto.EnderecoDTO;
import org.springframework.stereotype.Service;

@Service
public class CepService {

    private final ClienteCep feignClient;

    public CepService(ClienteCep feignClient) {
        this.feignClient = feignClient;
    }

    public EnderecoDTO dadosCep(String cep) {
        return feignClient.getCepInfo(cep);
    }

}
