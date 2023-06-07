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

			System.out.println("[1] Entrar");
			System.out.println("[2] Registar-se");
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

		String loginSys = "";
		String senhaSys = "";

		for (Usuario usuario : resultPesqLoginAndSenha) {
			loginSys = usuario.getLogin();
			senhaSys = usuario.getSenha();

		}

		if ((loginSys.equals(login)) && (senhaSys.equals(senha))) {

			exibirMenuInicioJogo();

		} else {

			limparTela();

			System.out.println("\nLogin ou senha não encontrados");

			System.out.println("\n[1] Tentar novamente");
			System.out.println("[2] Recuperar senha");
			System.out.println("[3] Voltar");

			String opcaoAltSenha = reader.readLine();
			int opcaoAlterarSenha = Integer.parseInt(opcaoAltSenha);

			if (opcaoAlterarSenha == 1) {
				exibirMenuLogin();
			} else if (opcaoAlterarSenha == 2) {
				alterarSenha();

			} else if (opcaoAlterarSenha == 3) {
				return;

			} else {
				System.out.println("Opção inválida");
			}

		}

	}

	public void exibirMenuInicioJogo() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		limparTela();

		System.out.println("SELECIONE SUA OPÇÃO");
		System.out.println("[1] Jogar");
		System.out.println("[2] Gerenciar conta");
		System.out.println("[3] Voltar");

		String opcaoInicioJogo = reader.readLine();
		int opcaoInicJogo = Integer.parseInt(opcaoInicioJogo);

		switch (opcaoInicJogo) {

		case 1:
			limparTela();

			System.out.println("[1] Novo jogo");
			System.out.println("[2] Continuar");
			System.out.println("[3] Voltar");

			String opcaoNovoJogo = reader.readLine();
			int opcaoNewGame = Integer.parseInt(opcaoNovoJogo);

			if (opcaoNewGame == 1) {

				exibirMenuDificuldade();

			} else if (opcaoNewGame == 2) {

				// continuar();

			} else if (opcaoNewGame == 3) {

				return;

			} else {
				System.out.println("Opção inválida");
			}

			break;

		case 2:

			alterarDados();
			break;

		case 3:
			return;

		default:
			System.out.println("Opção inválida");
		}

	}

	public String exibirMenuDificuldade() throws IOException {

		limparTela();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int opcaoDificuldadeFinal;
		String dificuldade = "";

		do {

			limparTela();

			System.out.println("SELECIONE A DIFICULDADE:");
			System.out.println("[1] Fácil");
			System.out.println("[2] Normal");
			System.out.println("[3] Difícil");
			System.out.println("[4] Voltar");

			String opcaoStrDificuldadeInic = reader.readLine();
			int opcaoDificuldadeInic = Integer.parseInt(opcaoStrDificuldadeInic);

			if (opcaoDificuldadeInic == 1) {

				int selecaoDificuldade;

				System.out.println(
						"\n\nNa dificuldade FÁCIL, você terá 80% de chance de passar sempre que um TESTE DE SORTE for exigido.");
				System.out.println("Você tem certeza de deseja jogar na dificuldade FÁCIL?");
				System.out.println("[1] Sim");
				System.out.println("[2] Não");

				dificuldade = "facil";
			} else if (opcaoDificuldadeInic == 2) {
				System.out.println(
						"\n\nNa dificuldade NORMAL, você terá 50% de chance de passar sempre que um TESTE DE SORTE for exigido.");
				System.out.println("Você tem certeza de deseja jogar na dificuldade NORMAL?");
				System.out.println("[1] Sim");
				System.out.println("[2] Não");

				dificuldade = "normal";
			} else if (opcaoDificuldadeInic == 3) {
				System.out.println(
						"\n\nNa dificuldade DIFÍCIL, você terá apenas 20% de chance de passar sempre que um TESTE DE SORTE for exigido.");
				System.out.println("Você tem certeza de deseja jogar na dificuldade DIFÍCIL?");
				System.out.println("[1] Sim");
				System.out.println("[2] Não");

				dificuldade = "dificil";

			} else {
				System.out.println("Opção inválida");
			}

			String opcaoStrDificuldadeFinal = reader.readLine();
			opcaoDificuldadeFinal = Integer.parseInt(opcaoStrDificuldadeFinal);

		} while (opcaoDificuldadeFinal != 1);

		return dificuldade;

	}

	public void alterarSenha() throws IOException {

		limparTela();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Digite seu e-mail:");
		String email = reader.readLine();

		Usuario user = dao.pesquisarPorEmail(email);

		if (user != null) {

			limparTela();

			System.out.println("Digite uma nova senha:");
			String novaSenha = reader.readLine();

			user.setSenha(novaSenha);

			dao.atualizar(user);

		}

		System.out.println("\nSenha alterada com sucesso!");

	}

	public void alterarNome() throws IOException {
		limparTela();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Digite seu e-mail:");
		String email = reader.readLine();

		Usuario user = dao.pesquisarPorEmail(email);

		if (user != null) {

			limparTela();

			System.out.println("Digite um novo nome:");
			String novoNome = reader.readLine();

			user.setNome(novoNome);

			dao.atualizar(user);

		}

		System.out.println("\nNome alterado com sucesso!");
	}

	public void alterarLogin() throws IOException {
		limparTela();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Digite seu e-mail:");
		String email = reader.readLine();

		Usuario user = dao.pesquisarPorEmail(email);

		if (user != null) {

			limparTela();

			System.out.println("Digite um novo login:");
			String novoLogin = reader.readLine();

			user.setLogin(novoLogin);

			dao.atualizar(user);

		}

		System.out.println("\nLogin alterado com sucesso!");
	}

	public void alterarEmail() throws IOException {
		limparTela();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Digite seu e-mail:");
		String email = reader.readLine();

		Usuario user = dao.pesquisarPorEmail(email);

		if (user != null) {

			limparTela();

			System.out.println("Digite um novo e-mail:");
			String novoEmail = reader.readLine();

			user.setLogin(novoEmail);

			dao.atualizar(user);

		}

		System.out.println("\nE-mail alterado com sucesso!");
	}

	public void alterarDados() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		limparTela();

		System.out.println("[1] Alterar nome");
		System.out.println("[2] Alterar login");
		System.out.println("[3] ALterar senha");
		System.out.println("[4] ALterar e-mail");
		System.out.println("[5] Voltar");

		String opcaoAltDados = reader.readLine();
		int opcaoAlterarDados = Integer.parseInt(opcaoAltDados);

		switch (opcaoAlterarDados) {

		case 1:

			alterarNome();
			break;

		case 2:

			alterarLogin();
			break;

		case 3:

			alterarSenha();
			break;

		case 4:
			alterarEmail();
			break;

		case 5:
			return;

		}

	}

}