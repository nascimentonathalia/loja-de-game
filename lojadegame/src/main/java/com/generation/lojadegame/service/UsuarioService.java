package com.generation.lojadegame.service;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.lojadegame.model.UsuarioLogin;
import com.generation.lojadegame.model.UsuarioModel;
import com.generation.lojadegame.repository.UsuarioRepository;

/*
 *  O uso da Classe de Serviço não se restringe apenas a Spring Security, 
 *  ela pode ser utilizada em qualquer endpoint para criar Regras de Negócio. 
 *  Exemplo: Não permitir postagens ou temas duplicados.
 */

@Service
public class UsuarioService {
	@Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<UsuarioModel> cadastrarUsuario(UsuarioModel usuario) {

        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
            return Optional.empty();

        usuario.setSenha(criptografarSenha(usuario.getSenha()));

        return Optional.of(usuarioRepository.save(usuario));
    
    }

    public Optional<UsuarioModel> atualizarUsuario(UsuarioModel usuario) {
        
        if(usuarioRepository.findById(usuario.getId()).isPresent()) {

            Optional<UsuarioModel> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

            if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != usuario.getId()))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            usuario.setSenha(criptografarSenha(usuario.getSenha()));

            return Optional.ofNullable(usuarioRepository.save(usuario));
            
        }

        return Optional.empty();
    
    }   

    public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

        Optional<UsuarioModel> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

        if (usuario.isPresent()) {

            if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

                usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setNome(usuario.get().getNome());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(),        usuarioLogin.get().getSenha()));
                usuarioLogin.get().setSenha(usuario.get().getSenha());

                return usuarioLogin;

            }
        }   

        return Optional.empty();
        
    }

    private String criptografarSenha(String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        return encoder.encode(senha);

    }
    
    private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        return encoder.matches(senhaDigitada, senhaBanco);

    }

    private String gerarBasicToken(String usuario, String senha) {

        String token = usuario + ":" + senha;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(tokenBase64);

    }
    

}

/* Mantenha a Atenção aos Detalhes, o cadastro de um novo usuário no sistema necessita ser 
 * validado no Banco de dados. Caso o usuário já exista, a aplicação não deve permitir que 
 * ele seja criado novamente, pois um usuário duplicado no sistema ocasionará 
 * um erro HTTP Status 500.
 * 
 * Mantenha a Atenção aos Detalhes, ao atualizar um usuário é importante que seja 
 * validado novamente a criptografia da senha e o usuário (e-mail). 
 * Caso não seja validado ocasionará um problema ao tentar autenticar 
 * pelo front-end da aplicação.
 * 
 * Mantenha a Atenção aos Detalhes, ao utilizar o Método autenticarUsuario, 
 * certifique que todos os Atributos do Objeto usuarioLogin sejam 
 * preenchidos com os dados recuperados do Banco de dados, pois o mesmo 
 * será utilizado pelo front-end da aplicação.
 */
 
