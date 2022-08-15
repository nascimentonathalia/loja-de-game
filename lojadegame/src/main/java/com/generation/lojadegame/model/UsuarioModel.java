package com.generation.lojadegame.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_usuarios")
public class UsuarioModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "O Atributo Nome é Obrigatório!")
	private String nome;

	@NotNull(message = "O Atributo Usuário é Obrigatório!")
	@Email(message = "O Atributo Usuário deve ser um email válido!")
	private String usuario;

	@NotBlank(message = "O Atributo Senha é Obrigatório!")
	@Size(min = 8, message = "A Senha deve ter no mínimo 8 caracteres")
	private String senha;

	@Size(max = 5000, message = "O link da foto não pode ser maior do que 5000 caracteres")
	private String foto;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<ProdutoModel> produto;
	
	/*Mantenha a Atenção aos Detalhes, o Atributo senha definido acima, não pode conter o 
	 * Atributo size max, que limita seu tamanho máximo. 
	 * Dependendo do limite inserido, pode provocar um erro 500, devido ao limite de 
	 * caracteres que serão utilizados na criptografia. Deixe apenas com size min 
	 * configurado.
	 */

	/*
	 *Mantenha a Atenção aos Detalhes, o Atributo de relacionamento definido como postagem, 
	 *possui a anotação @OneToMany com a opção cascade type = REMOVE. 
	 *Se o seu projeto estiver com ALL, ao deletar uma postagem você apagará também o 
	 *usuário, pois este relacionamento tem uma dependência de pai e filho. 
	 *Mantenha como REMOVE para que isso não aconteça
	 */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<ProdutoModel> getPostagem() {
		return produto;
	}

	public void setPostagem(List<ProdutoModel> produto) {
		this.produto = produto;
	}
	

}
