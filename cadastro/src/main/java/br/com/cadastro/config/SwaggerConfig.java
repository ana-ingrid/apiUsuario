package br.com.cadastro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final ResponseMessage m201 = simpleMessage(201, "Recurso Criado");
    private final ResponseMessage m200 = simpleMessage(200, "Recurso Encontrado");
    private final ResponseMessage m409 = simpleMessage(409, "Recurso Existente");
    private final ResponseMessage m200put = simpleMessage(200, "Recurso Alterado");
    private final ResponseMessage m204 = simpleMessage(204, "Recurso Deletado");
    private final ResponseMessage m404 = simpleMessage(404, "Recurso Não Encontrado");
    private final ResponseMessage m400 = simpleMessage(400, "Erro de Validação");
    private final ResponseMessage m500 = simpleMessage(500, "Erro Inesperado");

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, Arrays.asList(m200, m404, m500))
                .globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m400, m409, m500))
                .globalResponseMessage(RequestMethod.PUT, Arrays.asList(m200put, m404, m400, m500))
                .globalResponseMessage(RequestMethod.DELETE, Arrays.asList( m204, m404, m500))
          .select()
          .apis(RequestHandlerSelectors.basePackage("br.com.cadastro.controller"))
          .paths(PathSelectors.any())
          .build()
          .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "API Usuários",
                "API de Cadastro e Consulta de Usuários",
                "Versão 1.0",
                "https://github.com/AnaISantos/apiUsuario.git",
                new Contact("Ana Ingrid dos Santos", "https://github.com/AnaISantos/apiUsuario.git",
                        "ingridsantoscosta2003@gmail.com"), "Desafio proposto pela empresa Nova Tendência",
                "https://www.ntendencia.com.br/",
                Collections.emptyList());
    }

    private ResponseMessage simpleMessage(int code, String msg) {
        return new ResponseMessageBuilder().code(code).message(msg).build();
    }

}
