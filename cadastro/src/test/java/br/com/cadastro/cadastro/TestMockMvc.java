package br.com.cadastro.cadastro;

import br.com.cadastro.controller.UsuarioController;
import br.com.cadastro.dto.AlteraUsuarioDto;
import br.com.cadastro.dto.CadastraUsuarioDto;
import br.com.cadastro.exception.RecursoExistenteException;
import br.com.cadastro.exception.RecursoNaoEncontradoException;
import br.com.cadastro.service.CepService;
import br.com.cadastro.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static Util.ConverterUtil.conversaoJson;
import static Util.MockJson.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
class TestMockMvc {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected UsuarioService usuarioService;

    @MockBean
    protected CepService cepSer1vice;

    @Test
    void testConsultaUsuarioSucesso() throws Exception {
        when(usuarioService.consultaUsuarioPorId(anyString(), anyBoolean())).thenReturn(getMockUsuario());

        this.mockMvc.perform(get("/usuarios/cpf")).andDo(print()).andExpect(status().isOk()).andExpect(content().json("{'cpf':'50256833001'}"));
    }

    @Test
    void testConsultaUsuarioNaoEncontrado() throws Exception {
        when(usuarioService.consultaUsuarioPorId(anyString(), anyBoolean())).thenThrow(RecursoNaoEncontradoException.class);

        this.mockMvc.perform(get("/usuarios/cpf")).andExpect(status().is(404));
    }

    @Test
    void testCadastroUsuarioSucesso() throws Exception {
        when(usuarioService.cadastraUsuario(any(CadastraUsuarioDto.class))).thenReturn(getMockUsuario());

        String mockCadastraUsuarioDto = conversaoJson(getMockCadastraUsuarioDto());

        this.mockMvc.perform(post("/usuarios/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockCadastraUsuarioDto))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void testCadastroUsuarioExistente() throws Exception {
        when(usuarioService.cadastraUsuario(any(CadastraUsuarioDto.class)))
                .thenReturn(getMockUsuario())
                .thenThrow(RecursoExistenteException.class);

        String mockCadastraUsuarioDto = conversaoJson(getMockCadastraUsuarioDto());

        this.mockMvc.perform(post("/usuarios/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockCadastraUsuarioDto))
                .andDo(print())
                .andExpect(status().isCreated());

        this.mockMvc.perform(post("/usuarios/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockCadastraUsuarioDto))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void testAlteraUsuarioSucesso() throws Exception {

        AlteraUsuarioDto alteraUsuarioDto = getAlteraUsuarioDto();
        String mockAlteraUsuarioDTO = conversaoJson(alteraUsuarioDto);

        when(usuarioService.alteraUsuario(any(), anyString())).thenReturn(getMockUsuario());

        this.mockMvc.perform(put("/usuarios/cpf").
                        contentType(MediaType.APPLICATION_JSON).
                        content(mockAlteraUsuarioDTO)).
                andDo(print()).
                andExpect(status().is(200));
    }

    @Test
    void testAlteraUsuarioErro() throws Exception {
        String mockAlteraUsuarioDTO = conversaoJson(getAlteraUsuarioDto());

        when(usuarioService.alteraUsuario(any(), anyString())).thenThrow(RecursoNaoEncontradoException.class);

        this.mockMvc.perform(put("/usuarios/cpf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockAlteraUsuarioDTO))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    void testDeletaUsuarioSucesso() throws Exception {
        doNothing().when(usuarioService).deletaUsuario("cpf");

        this.mockMvc.perform(delete("/usuarios/cpf"))
                .andDo(print())
                .andExpect(status().is(204));
    }

    @Test
    void testDeletaUsuarioErro() throws Exception {
        doThrow(RecursoNaoEncontradoException.class).when(usuarioService).deletaUsuario("cpf");
        this.mockMvc.perform(delete("/usuarios/cpf"))
                .andDo(print())
                .andExpect(status().is(404));
    }

}
