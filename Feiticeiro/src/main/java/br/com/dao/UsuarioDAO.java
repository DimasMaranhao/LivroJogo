package br.com.dao;

import java.util.Random;

import javax.persistence.EntityManager;

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

	public Usuario pesquisarPorLogin(Usuario usuario, String login) {
		Class classe = usuario.getClass();
		return (Usuario) em.find(classe, login);

	}

	public Usuario pesquisarPorSenha(Usuario usuario, String senha) {
		Class classe = usuario.getClass();
		return (Usuario) em.find(classe, senha);
	}

	public Usuario pesquisarPorEmail(Usuario usuario, String email) {
		Class classe = usuario.getClass();
		return (Usuario) em.find(classe, email);
	}

	public Usuario pesquisarPorId(Usuario usuario, Long id) {
		Class classe = usuario.getClass();
		return (Usuario) em.find(classe, id);
	}

	public void atualizar(Usuario usuario) {
		em.getTransaction().begin();
		em.merge(usuario);
		em.getTransaction().commit();
	}

	public void excluirUser(Usuario usuario) {
		em.remove(usuario);
		em.getTransaction().begin();
		em.getTransaction().commit();
	}

}