package br.com.pr.sida.shared;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Criptografia {

    private final TextEncryptor textEncryptor;
    private final PasswordEncoder passwordEncoder;

    public String criptografarInformacoes(String entrada){
        return textEncryptor.encrypt(entrada);
    }

    public String criptografarSenhas(String senha){
        return passwordEncoder.encode(senha);
    }

    public boolean validarSenha(String senha, String senhaCriptografada){
        return passwordEncoder.matches(senha, senhaCriptografada);
    }

    public String descriptografarInformacoes(String entradaCriptografada){
        return textEncryptor.decrypt(entradaCriptografada);
    }
}
