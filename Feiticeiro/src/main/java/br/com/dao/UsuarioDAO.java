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

	public Usuario pesquisarPorLogin(String login) {

		return em.find(Usuario.class, login);

	}

	public Usuario pesquisarPorSenha(String senha) {
		return em.find(Usuario.class, senha);
	}

	public Usuario pesquisarPorEmail(String email) {
		return em.find(Usuario.class, email);
	}

	public Usuario pesquisarPorId(Long id) {
		return em.find(Usuario.class, id);
	}

	public void atualizar(Usuario usuario) {
		em.getTransaction().begin();
		em.merge(usuario);
		em.getTransaction().commit();
	}

	public boolean easyRandom() {

		Random rd = new Random();
		boolean passou = false;

		int min = 1;
		int max = 10;

		int resultadoSorte = rd.nextInt(max - min) + min;

		if (resultadoSorte >= 3) {
			passou = true;
		}

		return passou;

	}

	public boolean normalRandom() {

		Random rd = new Random();
		boolean passou = false;

		int min = 1;
		int max = 10;

		int resultadoSorte = rd.nextInt(max - min) + min;

		if (resultadoSorte > 5) {
			passou = true;
		}

		return passou;

	}

	public boolean dificilRandom() {

		Random rd = new Random();
		boolean passou = false;

		int min = 1;
		int max = 10;

		int resultadoSorte = rd.nextInt(max - min) + min;

		if (resultadoSorte > 8) {
			passou = true;
		}

		return passou;

	}
	
	
}
