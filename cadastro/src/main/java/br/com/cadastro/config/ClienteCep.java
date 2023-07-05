package br.com.cadastro.config;

import br.com.cadastro.dto.EnderecoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cepClient", url = "https://viacep.com.br/ws")
public interface ClienteCep {

    @GetMapping("/{cep}/json")
    EnderecoDto getCepInfo(@PathVariable("cep") String cep);
}
