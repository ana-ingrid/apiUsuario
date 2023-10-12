package br.com.cadastro.services;

import br.com.cadastro.util.ClienteCep;
import br.com.cadastro.dtos.EnderecoDTO;
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
