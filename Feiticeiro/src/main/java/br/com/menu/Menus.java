package br.com.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import br.com.dao.UsuarioDAO;
import br.com.entidade.Usuario;

public class Menus {

	private EntityManagerFactory factory;
	private EntityManager manager;
	private UsuarioDAO dao;

	public Menus(EntityManagerFactory factory, UsuarioDAO dao, EntityManager manager) {
		this.factory = factory;
		this.manager = manager;
		this.dao = new UsuarioDAO(this.factory.createEntityManager());
	}

	public void exibirMenuInicial() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		boolean continuar = true;
		int opcao;

		while (continuar) {

			limparTela();

			System.out.println("LOGIN:");
			System.out.println("[1] Já possuo cadastro");
			System.out.println("[2] Ainda não possuo cadastro. Desejo me cadastrar");
			System.out.println("[3] Sair");

			String opcaoDigitada = reader.readLine();
			opcao = Integer.parseInt(opcaoDigitada);

			switch (opcao) {
			case 1:

				exibirMenuLogin();
				break;

			case 2:

				cadastrarUsuario();
				break;

			case 3:
				continuar = false;
				break;

			default:
				System.out.println("Opção inválida");
				break;

			}
		}

	}

	public void cadastrarUsuario() throws IOException {

		Usuario user = new Usuario();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		limparTela();

		System.out.println("Digite o nome do usuário:");
		String nome = reader.readLine();
		user.setNome(nome);
		System.out.println("Digite o login:");
		String login = reader.readLine();
		user.setLogin(login);
		System.out.println("Digite o e-mail:");
		String email = reader.readLine();
		user.setEmail(email);
		System.out.println("Digite uma senha:");
		String senha = reader.readLine();
		user.setSenha(senha);

		dao.salvarInformacoesUsuario(user);

	}

	public void limparTela() {
		for (int i = 0; i < 30; i++) {
			System.out.println("\n");
		}
	}

	public void exibirMenuLogin() throws IOException {

		limparTela();

		boolean continuar = true;

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		TypedQuery<Usuario> resultadoLoginAndSenha = manager.createNamedQuery("Usuario.findByLoginAndSenha",
				Usuario.class);

		System.out.println("LOGIN:");
		String login = reader.readLine();
		System.out.println("SENHA:");
		String senha = reader.readLine();

		resultadoLoginAndSenha.setParameter("login", login);
		resultadoLoginAndSenha.setParameter("senha", senha);

		List<Usuario> resultPesqLoginAndSenha = resultadoLoginAndSenha.getResultList();

		String loginSys = null;
		String senhaSys = null;

		for (Usuario usuario : resultPesqLoginAndSenha) {
			loginSys = usuario.getLogin();
			senhaSys = usuario.getSenha();
		}


		if ((!loginSys.equals(login)) && (senhaSys.equals(senha))) {
			System.out.println("\nAcesso permitido!");
			continuar = false;
		} else {
			System.out.println("\nLogin ou senha não encontrados");
		}

	}

}