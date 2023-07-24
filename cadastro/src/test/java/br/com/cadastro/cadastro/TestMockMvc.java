package br.com.cadastro.cadastro;

import antlr.ASTNULLType;
import br.com.cadastro.controller.UsuarioController;
import br.com.cadastro.dto.AlteraUsuarioDto;
import br.com.cadastro.dto.BuscaAvancadaDto;
import br.com.cadastro.dto.CadastraUsuarioDto;
import br.com.cadastro.exception.RecursoExistenteException;
import br.com.cadastro.exception.RecursoNaoEncontradoException;
import br.com.cadastro.model.Usuario;
import br.com.cadastro.service.CepService;
import br.com.cadastro.service.UsuarioService;
import classe.ObjetoJson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
 class TestMockMvc {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private CepService cepService;

    protected static final String LISTAPAGINADA_JSON = "listaPaginada.json";
    protected static final String USUARIO_JSON = "usuario.json";
    protected static final String USUARIO_PATH = "/mocks";

    protected static Usuario getMockUsuario() {
        return ObjetoJson.getMockObject(USUARIO_PATH, USUARIO_JSON, Usuario.class);
    }
    protected static Pageable getMockListaPaginada() {
        return (Pageable) ObjetoJson.getMockObject(USUARIO_PATH, LISTAPAGINADA_JSON, Page.class);
    }

    @Test
     void testConsultaUsuarioSucesso() throws Exception {
        when(usuarioService.consultaUsuarioPorId(anyString(), anyBoolean())).thenReturn(getMockUsuario());

        this.mockMvc.perform(get("/usuarios/cpf")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{'cpf':'50256833001'}"));
    }

    @Test
     void TestConsultaUsuarioNaoEncontrado() throws Exception {
        when(usuarioService.consultaUsuarioPorId(anyString(), anyBoolean())).thenThrow(RecursoNaoEncontradoException.class);

        this.mockMvc.perform(get("/usuarios/cpf")).andExpect(status().is(404));
    }

    @Test
     void TestCadastroUsuarioSucesso() throws Exception {
        when(usuarioService.cadastraUsuario(any(CadastraUsuarioDto.class))).thenReturn(getMockUsuario());

        this.mockMvc.perform(post("/usuarios/")).andDo(print()).andExpect(status().is(201));
    }

    @Test
     void TestCadastroUsuarioExistente() throws Exception {
        when(usuarioService.cadastraUsuario(any())).thenReturn(getMockUsuario()).thenThrow(RecursoExistenteException.class);

        this.mockMvc.perform(post("/usuarios/")).andDo(print()).andExpect(status().is(409));
    }

    @Test
     void alteraUsuarioSucesso() throws Exception {
        when(usuarioService.alteraUsuario(any(), anyString())).thenReturn(getMockUsuario());

        this.mockMvc.perform(put("/usuarios/cpf")).andDo(print()).andExpect(status().isOk());
    }

    @Test
     void alteraUsuarioErro() throws Exception {
        when(usuarioService.alteraUsuario(any(AlteraUsuarioDto.class), anyString())).thenThrow(RecursoNaoEncontradoException.class);

        this.mockMvc.perform(put("/usuarios/cpf")).andDo(print()).andExpect(status().is(400)); // tirar dúvida 404
    }

    @Test
    void deletaUsuarioSucesso() throws Exception {
        doNothing();
        this.mockMvc.perform(delete("/usuarios/cpf")).andDo(print()).andExpect(status().is(200));
    }

    @Test
    void deletaUsuarioErro() throws Exception {
        doNothing().doThrow(RecursoNaoEncontradoException.class);
        this.mockMvc.perform(delete("/usuarios/cpf")).andDo(print()).andExpect(status().is(404));
    }

}
