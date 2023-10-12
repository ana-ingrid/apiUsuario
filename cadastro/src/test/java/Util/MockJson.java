package Util;

import br.com.cadastro.dtos.AlteraUsuarioDTO;
import br.com.cadastro.dtos.BuscaAvancadaDTO;
import br.com.cadastro.dtos.CadastraUsuarioDTO;
import br.com.cadastro.models.Usuario;

public class MockJson {

    protected static final String USUARIO_JSON = "usuario.json";

    protected static final String CADASTRA_USUARIO_DTO_JSON = "cadastraUsuarioDto.json";

    protected static final String ALTERA_USUARIO_DTO_JSON = "alteraUsuarioDto.json";

    protected static final String OUTRO_USUARIO_JSON = "outroUsuario.json";

    protected static final String BUSCA_AVANCADA_DTO_JSON = "buscaAvancadaDto.json";

    protected static final String USUARIO_PATH = "/mocks";


    public static Usuario getMockOutroUsuario() {
        return ObjetoJson.getMockObject(USUARIO_PATH, OUTRO_USUARIO_JSON, Usuario.class);
    }

    public static CadastraUsuarioDTO getMockCadastraUsuarioDto() {
        return ObjetoJson.getMockObject(USUARIO_PATH, CADASTRA_USUARIO_DTO_JSON, CadastraUsuarioDTO.class);
    }

    public static AlteraUsuarioDTO getAlteraUsuarioDto() {
        return ObjetoJson.getMockObject(USUARIO_PATH, ALTERA_USUARIO_DTO_JSON, AlteraUsuarioDTO.class);
    }

    public static BuscaAvancadaDTO getMockBuscaAvancadaDto() {
        return ObjetoJson.getMockObject(USUARIO_PATH, BUSCA_AVANCADA_DTO_JSON, BuscaAvancadaDTO.class);
    }

    public static Usuario getMockUsuario() {
        return ObjetoJson.getMockObject(USUARIO_PATH, USUARIO_JSON, Usuario.class);
    }

}
