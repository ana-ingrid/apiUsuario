package br.com.cadastro.util;

import br.com.cadastro.dtos.EnderecoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cepClient", url = "https://viacep.com.br/ws")
public interface ClienteCep {

    @GetMapping("/{cep}/json")
    EnderecoDTO getCepInfo(@PathVariable("cep") String cep);
}
