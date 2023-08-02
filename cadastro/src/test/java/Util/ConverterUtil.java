package Util;

import br.com.cadastro.config.MensagemValidacao;
import br.com.cadastro.exception.ErroConversaoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class ConverterUtil {

    public static String conversaoJson(Object obj) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new ErroConversaoException(MensagemValidacao.getMensagemValidacao("validacao.excecao.erro.conversao"));
        }
    }
}
