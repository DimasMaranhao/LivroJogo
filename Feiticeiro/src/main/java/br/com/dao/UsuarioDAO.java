package br.com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.entidade.Usuario;

public class UsuarioDAO {

	EntityManager em;

	public UsuarioDAO(EntityManager em) {
		this.em = em;
	}

	public void salvarInformacoesUsuario(Usuario usuario) {
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
	}

	
	public Usuario pesquisarPorLogin(String login) {

		return em.find(Usuario.class, login);

	}
	
	
	
	public Usuario pesquisarPorSenha (String senha) {
		return em.find(Usuario.class, senha);
	}
}
