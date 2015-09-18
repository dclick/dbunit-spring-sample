package io.redspark.dbunitspring.sample.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author vinicius.moreira
 */
@Entity
@Table
public class Usuario {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = true)
	private String apelido;
	
	@OneToMany(mappedBy="usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Publicacao> publicacoes;
	
	/** Criado somente porque a implementação do JPA precisa de um construtor padrão */
	@SuppressWarnings("unused")
	private Usuario(){}
	
	public Usuario(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public Long getId() {
		return id;
	}
	
	public Set<Publicacao> getPublicacoes() {
		return publicacoes;
	}

	/**
	 * Realiza uma nova publicação.
	 * @param conteudo
	 * 		o conteúdo (texto).
	 * @return
	 * 		a nova publicação gerada.
	 */
	public Publicacao publicar(String conteudo){
		
		if(this.publicacoes == null){
			this.publicacoes = new HashSet<>();
		}
		
		Publicacao publicacao = new Publicacao(this, conteudo);
		this.publicacoes.add(publicacao);
		return publicacao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}