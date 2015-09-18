package io.redspark.dbunitspring.sample.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author vinicius.moreira
 */
@Entity
@Table
public class Publicacao {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, length = 140)
	private String conteudo;

	@ManyToOne(fetch=FetchType.EAGER, optional = false)
	@JoinColumn(name = "USUARIO_ID", nullable = false)
	private Usuario usuario;
	
	@Column(nullable = false)
	private Date data;
	
	public Publicacao(Usuario usuario, String conteudo){
		this.usuario = usuario;
		this.conteudo = conteudo;
		this.data = new Date();
	}
	
	/** Criado somente porque a implementação do JPA precisa de um construtor padrão */
	@SuppressWarnings("unused")
	private Publicacao() { }

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Long getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Date getData() {
		return data;
	}
}