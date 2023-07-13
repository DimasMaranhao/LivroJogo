package br.com.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import br.com.dao.InfoJogoDAO;
import br.com.dao.UsuarioDAO;
import br.com.entidade.InfoJogo;
import br.com.entidade.Usuario;

public class Menus {

	private EntityManagerFactory factory;
	private EntityManager manager;
	private UsuarioDAO daoUser;
	private InfoJogoDAO daoInfo;
	private String userGeral;

	public Menus(String userGeral) {
		super();
		this.userGeral = userGeral;
	}

	public Menus(EntityManagerFactory factory, UsuarioDAO dao, EntityManager manager) {
		this.factory = factory;
		this.manager = manager;
		this.daoUser = new UsuarioDAO(this.factory.createEntityManager());
		this.daoInfo = new InfoJogoDAO(this.factory.createEntityManager());

	}

	public void exibirMenuInicial() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		boolean continuar = true;
		int opcao;

		while (continuar) {

			limparTela();

			System.out.println(
					"  ____     _____ _    _          __  __          _____   ____    _____   ____    _    _ ______ _____   __ _____");
			System.out.println(
					" / __ \\   / ____| |  | |   /\\   |  \\/  |   /\\   |  __ \\ / __ \\  |  __ \\ / __ \\  | |  | |  ____|  __ \\ /_/|_   _|");
			System.out.println(
					"| |  | | | |    | |__| |  /  \\  | \\  / |  /  \\  | |  | | |  | | | |  | | |  | | | |__| | |__  | |__) / _ \\ | |");
			System.out.println(
					"| |  | | | |    |  __  | / /\\ \\ | |\\/| | / /\\ \\ | |  | | |  | | | |  | | |  | | |  __  |  __| |  _  / | | || |");
			System.out.println(
					"| |__| | | |____| |  | |/ ____ \\| |  | |/ ____ \\| |__| | |__| | | |__| | |__| | | |  | | |____| | \\ \\ |_| || |");
			System.out.println(
					" \\____/   \\_____|_|  |_/_/    \\_\\_|  |_/_/    \\_\\_____/ \\____/  |_____/ \\____/  |_|  |_|______|_|  \\_\\___/_____|");
			System.out.println(
					"  _   _ __  __   _     ___ _   _  _ _____ __ ___ _____ ___ ___   _       ___   _____ _  _ _____ _   _ ___   _");
			System.out.println(
					" | | | |  \\/  | /_\\   | __/_\\ | \\| |_   _/_// __|_   _|_ _/ __| /_\\     /_\\ \\ / | __| \\| |_   _| | | | _ \\ /_\\");
			System.out.println(
					" | |_| | |\\/| |/ _ \\  | _/ _ \\| .` | | |/--\\\\__ \\ | |  | | (__ / _ \\   / _ \\ V /| _|| .` | | | | |_| |   // _ \\");
			System.out.println(
					"  \\___/|_|  |_/_/ \\_\\ |_/_/ \\_|_|\\_| |_/_/\\_|___/ |_| |___\\___/_/ \\_\\ /_/ \\_\\_/ |___|_|\\_| |_|  \\___/|_|_/_/ \\_\\");

			System.out.println("\n\n[1] Entrar");
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
		InfoJogo info = new InfoJogo();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		limparTela();

		System.out.println("Digite o nome do usuário:");
		String nome = reader.readLine();
		user.setNome(nome);
		System.out.println("Digite o login:");
		String login = reader.readLine();
		user.setLogin(login);
		info.setLogin(login);
		System.out.println("Digite o e-mail:");
		String email = reader.readLine();
		user.setEmail(email);
		System.out.println("Digite uma senha:");
		String senha = reader.readLine();
		user.setSenha(senha);

		daoUser.salvarInformacoesUsuario(user);
		daoInfo.salvarInformacoesInfo(info);

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
		System.out.println("\nSENHA:");
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

			this.userGeral = login;
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
				recuperarSenha();

			} else if (opcaoAlterarSenha == 3) {
				exibirMenuLogin();

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
		System.out.println("[3] Sair");

		String opcaoInicioJogo = reader.readLine();
		int opcaoInicJogo = Integer.parseInt(opcaoInicioJogo);

		do {

			if (opcaoInicJogo == 1) {

				exibirMenuNovoJogo();

			} else if (opcaoInicJogo == 2) {

				alterarDados();

			} else if (opcaoInicJogo == 3) {

				System.exit(0);

			} else {
				System.out.println("Opção inválida");
			}

		} while (opcaoInicJogo != 3);
	}

	public void exibirMenuDificuldade() throws IOException {

		limparTela();

		String login = this.userGeral;
		InfoJogo info = new InfoJogo();

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

				limparTela();

				System.out.println(
						"\n\nNa dificuldade FÁCIL, você terá 80% de chance de passar sempre que um TESTE DE SORTE for exigido.");
				System.out.println("Você tem certeza de deseja jogar na dificuldade FÁCIL?");
				System.out.println("\n[1] Sim");
				System.out.println("[2] Não");

				dificuldade = "facil";
				info.setDificuldade(dificuldade);
				info.setLogin(login);

			} else if (opcaoDificuldadeInic == 2) {

				limparTela();

				System.out.println(
						"\n\nNa dificuldade NORMAL, você terá 50% de chance de passar sempre que um TESTE DE SORTE for exigido.");
				System.out.println("Você tem certeza de deseja jogar na dificuldade NORMAL?");
				System.out.println("\n[1] Sim");
				System.out.println("[2] Não");

				dificuldade = "normal";
				info.setDificuldade(dificuldade);
				info.setLogin(login);

			} else if (opcaoDificuldadeInic == 3) {

				limparTela();

				System.out.println(
						"\n\nNa dificuldade DIFÍCIL, você terá apenas 20% de chance de passar sempre que um TESTE DE SORTE for exigido.");
				System.out.println("Você tem certeza de deseja jogar na dificuldade DIFÍCIL?");
				System.out.println("\n[1] Sim");
				System.out.println("[2] Não");

				dificuldade = "dificil";
				info.setDificuldade(dificuldade);
				info.setLogin(login);

			} else if (opcaoDificuldadeInic == 4) {
				exibirMenuInicioJogo();

			} else {
				System.out.println("Opção inválida");
			}

			String opcaoStrDificuldadeFinal = reader.readLine();
			opcaoDificuldadeFinal = Integer.parseInt(opcaoStrDificuldadeFinal);

		} while (opcaoDificuldadeFinal != 1);

		daoInfo.atualizar(info);

	}

	public void alterarSenha() throws IOException {

		limparTela();

		Usuario user = new Usuario();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		TypedQuery<Usuario> resultadoSenha = manager.createNamedQuery("Usuario.findBySenha", Usuario.class);

		System.out.println("Digite a senha atual:");
		String senhaAtual = reader.readLine();

		resultadoSenha.setParameter("senha", senhaAtual);

		List<Usuario> resultPesqSenha = resultadoSenha.getResultList();

		String senhaSys = "";

		for (Usuario usuario : resultPesqSenha) {

			senhaSys = usuario.getSenha();

			if (senhaAtual.equals(senhaSys)) {

				System.out.println("\nDigite a nova senha:");
				String novaSenha = reader.readLine();

				usuario.setSenha(novaSenha);

				daoUser.atualizar(usuario);

				System.out.println("\nSENHA ALTERADA COM SUCESSO!");

			}
		}

		if (!senhaAtual.equals(senhaSys)) {

			System.out.println("\n\n\nSENHA INCORRETA!");
		}

	}

	public void alterarNome() throws IOException {

		limparTela();

		Usuario user = new Usuario();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		TypedQuery<Usuario> resultadoSenha = manager.createNamedQuery("Usuario.findBySenha", Usuario.class);

		System.out.println("Digite a senha atual:");
		String senhaAtual = reader.readLine();

		resultadoSenha.setParameter("senha", senhaAtual);

		List<Usuario> resultPesqSenha = resultadoSenha.getResultList();

		String senhaSys = "";

		for (Usuario usuario : resultPesqSenha) {

			senhaSys = usuario.getSenha();

			if (senhaAtual.equals(senhaSys)) {

				System.out.println("\nDigite um novo nome::");
				String novoNome = reader.readLine();

				usuario.setNome(novoNome);

				daoUser.atualizar(usuario);

				System.out.println("\nNOME ALTERADO COM SUCESSO!");

			}
		}

		if (!senhaAtual.equals(senhaSys)) {

			System.out.println("\n\n\nSENHA INCORRETA!");
		}
	}

	public void alterarLogin() throws IOException {

		limparTela();

		Usuario user = new Usuario();
		InfoJogo info = new InfoJogo();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		TypedQuery<Usuario> resultadoSenha = manager.createNamedQuery("Usuario.findBySenha", Usuario.class);

		System.out.println("Digite a senha atual:");
		String senhaAtual = reader.readLine();

		resultadoSenha.setParameter("senha", senhaAtual);

		List<Usuario> resultPesqSenha = resultadoSenha.getResultList();

		String senhaSys = "";

		for (Usuario usuario : resultPesqSenha) {

			senhaSys = usuario.getSenha();

			if (senhaAtual.equals(senhaSys)) {

				System.out.println("\nDigite um novo login::");
				String novoLogin = reader.readLine();

				usuario.setLogin(novoLogin);
				info.setLogin(novoLogin);

				daoUser.atualizar(usuario);

				System.out.println("\nLOGIN ALTERADO COM SUCESSO!");

			}
		}

		if (!senhaAtual.equals(senhaSys)) {

			System.out.println("\n\n\nSENHA INCORRETA!");
		}
	}

	public void alterarEmail() throws IOException {

		limparTela();

		Usuario user = new Usuario();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		TypedQuery<Usuario> resultadoSenha = manager.createNamedQuery("Usuario.findBySenha", Usuario.class);

		System.out.println("Digite a senha atual:");
		String senhaAtual = reader.readLine();

		resultadoSenha.setParameter("senha", senhaAtual);

		List<Usuario> resultPesqSenha = resultadoSenha.getResultList();

		String senhaSys = "";

		for (Usuario usuario : resultPesqSenha) {

			senhaSys = usuario.getSenha();

			if (senhaAtual.equals(senhaSys)) {

				System.out.println("\nDigite um novo e-mail:");
				String novoEmail = reader.readLine();

				usuario.setEmail(novoEmail);

				daoUser.atualizar(usuario);

				System.out.println("\nE-MAIL ALTERADO COM SUCESSO!");

			}
		}

		if (!senhaAtual.equals(senhaSys)) {

			System.out.println("\n\n\nSENHA INCORRETA!");
		}
	}

	public void alterarDados() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		limparTela();

		System.out.println("[1] Alterar nome");
		System.out.println("[2] Alterar login");
		System.out.println("[3] Alterar senha");
		System.out.println("[4] Alterar e-mail");
		System.out.println("[5] Apagar conta");
		System.out.println("[6] Voltar");

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
			deletarUsuario();
			break;

		case 6:
			exibirMenuInicioJogo();

		}

	}

	public void recuperarSenha() throws IOException {

		limparTela();

		Usuario user = new Usuario();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		TypedQuery<Usuario> resultadoEmail = manager.createNamedQuery("Usuario.findByEmail", Usuario.class);

		System.out.println("Digite seu e-mail:");
		String email = reader.readLine();

		resultadoEmail.setParameter("email", email);

		List<Usuario> resultPesqEmail = resultadoEmail.getResultList();

		String emailSys = "";

		for (Usuario usuario : resultPesqEmail) {

			emailSys = usuario.getEmail();

			if (email.equals(emailSys)) {

				System.out.println("\nDigite a nova senha:");
				String novaSenha = reader.readLine();

				usuario.setSenha(novaSenha);

				daoUser.atualizar(usuario);

				System.out.println("\nSENHA ALTERADA COM SUCESSO!");

			}
		}

		if (!email.equals(emailSys)) {

			System.out.println("\n\n\nE-MAIL NÃO ENCONTRADO!");
		}

	}

	public void exibirMenuNovoJogo() throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		limparTela();

		System.out.println("[1] Novo jogo");
		System.out.println("[2] Continuar");
		System.out.println("[3] Voltar");

		String opcaoNovoJogo = reader.readLine();
		int opcaoNewGame = Integer.parseInt(opcaoNovoJogo);

		if (opcaoNewGame == 1) {

			String entConfirmaInicio;
			int confirmaInicio;

			limparTela();
			System.out.println(
					"Ao iniciar um novo jogo, caso você já tenha começado a jogar, seu progresso anterior será apagado.\nDeseja iniciar um novo jogo assim mesmo?");
			System.out.println("\n[1] Sim\n[2] Não");
			entConfirmaInicio = reader.readLine();
			confirmaInicio = Integer.parseInt(entConfirmaInicio);

			if (confirmaInicio == 1) {

				exibirMenuDificuldade();

			} else {
				return;
			}

		} else if (opcaoNewGame == 2) {

			// continuar();

		} else if (opcaoNewGame == 3) {

			exibirMenuInicioJogo();

		} else {
			System.out.println("Opção inválida");
		}
	}

	public void deletarUsuario() throws IOException {

		limparTela();

		Usuario user = new Usuario();
		InfoJogo info = new InfoJogo();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("\nTem certeza de  que deseja apagar sua conta?");
		System.out.println("[1] Sim");
		System.out.println("[2] Não");

		String decisaoUmEnt = reader.readLine();
		int decisaoUm = Integer.parseInt(decisaoUmEnt);

		if (decisaoUm == 1) {

			limparTela();

			System.out.println(
					"\nSe você continuar, todos os seus dados, assim  como seu progresso serão apagados e você não terá mais acesso ao jogo.");
			System.out.println("Tem certeza de que deseja continuar?");
			System.out.println("[1] Sim");
			System.out.println("[2] Não");

			String decisaoFinalEnt = reader.readLine();
			int decisaoFinal = Integer.parseInt(decisaoFinalEnt);

			if (decisaoFinal == 1) {

				String login = this.userGeral;
				String SenhaSys = "";
				String loginSysUser = "";
				String loginSysInfo = "";

				TypedQuery<Usuario> resultadoLoginAndSenhaUser = manager.createNamedQuery("Usuario.findByLoginAndSenha",
						Usuario.class);
				TypedQuery<InfoJogo> resultadoLoginInfo = manager.createNamedQuery("InfoJogo.findByLogin",
						InfoJogo.class);

				System.out.println("\nDigite sua senha:");
				String senha = reader.readLine();

				resultadoLoginAndSenhaUser.setParameter("login", login);
				resultadoLoginInfo.setParameter("login", login);
				resultadoLoginAndSenhaUser.setParameter("senha", senha);

				List<Usuario> resultPesqLoginUser = resultadoLoginAndSenhaUser.getResultList();
				List<InfoJogo> resultPesqLoginInfo = resultadoLoginInfo.getResultList();

				// FAZER QUERY PRA PEGAR O ID, E UTILIZÁ-LO PRA FAZER UM FIND ANTES DO DELETE.

				String senhaSys = "";

				for (Usuario usuario : resultPesqLoginUser) {

					loginSysUser = usuario.getLogin();
					senhaSys = usuario.getSenha();

					for (InfoJogo infoJogo : resultPesqLoginInfo) {

						loginSysInfo = infoJogo.getLogin();

					}

					if ((login.equals(loginSysUser) && (login.equals(loginSysInfo) && (senha.equals(senhaSys))))) {

						usuario = manager.find(Usuario.class, usuario.getId());
						manager.remove(usuario);						
						info = manager.find(InfoJogo.class, info.getId());
						manager.remove(info);

						System.out.println("SUA CONTA FOI APAGADA COM  SUCESSO...");

						System.exit(0);

					}

				}

				if (!senha.equals(senhaSys)) {

					System.out.println("\nSENHA INCORRETA!");
					alterarDados();
				}

			} else {

				alterarDados();
			}
		}

	}

}