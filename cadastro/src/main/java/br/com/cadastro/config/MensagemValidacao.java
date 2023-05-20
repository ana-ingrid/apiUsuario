package br.com.cadastro.config;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public final class MensagemValidacao extends Exception {

    public static String getMensagemValidacao(final String chaveMensagem, final Object... params) {
        ResourceBundle bundle = ResourceBundle.getBundle("MensagemValidacao", Locale.getDefault());
        String pattern = bundle.getString(chaveMensagem);
        return MessageFormat.format(pattern, params);
    }

}
