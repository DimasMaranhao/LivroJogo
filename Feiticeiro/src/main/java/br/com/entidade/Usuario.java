package br.com.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;






@Entity
@NamedQueries({ 
	@NamedQuery(name = "Usuario.findByLoginAndSenha", query = "select a from Usuario a where login like :login and senha like :senha"), 
//	@NamedQuery(name = "Usuario.findBySenha", query = "select a from Usuario a where senha like :senha"), 

})
@Table (name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GERADOR-FEITICEIRO")
	@SequenceGenerator(name = "GERADOR-FEITICEIRO", sequenceName = "FEITICEIRO", initialValue = 1, allocationSize = 1)
	private Long id;

	private String nome;
	private String email;
	private String login;
	private String senha;
	
	
	
	// GET SET
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	


}
