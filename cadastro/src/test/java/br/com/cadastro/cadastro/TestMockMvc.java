package br.com.cadastro.cadastro;

import br.com.cadastro.controller.UsuarioController;
import br.com.cadastro.exception.RecursoNaoEncontradoException;
import br.com.cadastro.model.Usuario;
import br.com.cadastro.service.UsuarioService;
import classe.ObjetoJson;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class TestMockMvc {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    protected static final String USUARIO_JSON = "usuario.json";

    protected static final String USUARIO_PATH = "/mocks";

    protected static Usuario getMockUsuario() {
        return ObjetoJson.getMockObject(USUARIO_PATH, USUARIO_JSON, Usuario.class);
    }

    @Test
    public void TestConsultaUsuarioSucesso() throws Exception {
        when(usuarioService.consultaUsuarioPorId(anyString(), anyBoolean())).thenReturn(getMockUsuario());

        this.mockMvc.perform(get("/usuarios/50256833001")).andDo(print()).andExpect(status().isOk()).andExpect(content().json("{'cpf':'50256833001'}"));
    }

    @Test
    public void TestConsultaUsuarioNaoEncontrado() throws Exception {
        when(usuarioService.consultaUsuarioPorId("50256833002", true)).thenThrow(RecursoNaoEncontradoException.class);

        this.mockMvc.perform(get("/usuarios/50256833002")).andDo(print()).andExpect(status().is(404))
                .andExpect(jsonPath("$").doesNotExist());
    }



}
