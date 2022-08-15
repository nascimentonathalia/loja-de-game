package com.generation.lojadegame.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.lojadegame.model.UsuarioModel;

/*
 * Por se tratar de uma Implementação de uma Interface (UserDetails), todos os Métodos 
 * da Interface devem ser implementados e o nome da Classe deve obrigatoriamente conter 
 * o sufixo Impl (Implements), indicando que a Classe está Implementando a Interface.
 */

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;

	private List<GrantedAuthority> authorities;

	public UserDetailsImpl(UsuarioModel user) {
		this.userName = user.getUsuario();
		this.password = user.getSenha();

	}
	
public UserDetailsImpl (){ }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return authorities;
	}

	@Override
	public String getPassword() {
	
		return password;
	}

	@Override
	public String getUsername() {
		
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

    @Override
	public boolean isEnabled() {
		
		return true;
	}
}

/*Mantenha a Atenção aos Detalhes, é necessário ter um construtor adaptado para receber 
 * Atributos que serão utilizados para logar no sistema. 
 * Os Atributos em questão são usuário e senha, mas poderia ser também e-mail e senha. 
 * Tudo irá depender de como a model de Usuario esta elaborada.
 */
 
/* Observe que o Método getAuthorities(), que retorna a lista com os direitos de acesso 
 * do usuário, sempre retornará uma List vazia, porquê este Atributo não pode ser Nulo. 
 * Com o objetivo de simplificar a nossa implementação, todo o Usuário autenticado 
 * terá todos os direitos de acesso sobre a aplicação.
 */
