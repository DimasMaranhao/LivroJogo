package br.com.feiticeiro;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.menu.Menus;

public class Aplicacao {

	public static void main(String[] args) throws IOException {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("FEITICEIRO-PU");
		EntityManager em = emf.createEntityManager();

		Menus menu = new Menus(emf, null, em);

		menu.exibirMenuInicial();

	}

}
