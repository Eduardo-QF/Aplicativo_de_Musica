package br.com.streaming.principal;

import br.com.streaming.modelo.Musica;
import br.com.streaming.modelo.Playlist;
import br.com.streaming.modelo.Usuario;
import br.com.streaming.modelo.UsuarioFree;
import br.com.streaming.modelo.UsuarioPremium;
import br.com.streaming.servico.GeradorRecomendacoes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StreamingMusica {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Musica> biblioteca = new ArrayList<>();
    private static Usuario usuarioLogado = null;
    private static GeradorRecomendacoes gerador = new GeradorRecomendacoes();

    public static void main(String[] args) {
        inicializarDados();

        System.out.println("    SISTEMA DE STREAMING DE MUSICA      ");

        boolean executando = true;
        while (executando) {
            if (usuarioLogado == null) {
                executando = menuPrincipal();
            } else {
                executando = menuUsuario();
            }
        }

        System.out.println("      OBRIGADO POR USAR O SISTEMA!      ");
        scanner.close();
    }

    // Menu principal (antes do login)
    private static boolean menuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1 - Fazer login");
        System.out.println("2 - Cadastrar novo usuario");
        System.out.println("3 - Listar usuarios cadastrados");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                fazerLogin();
                break;
            case 2:
                cadastrarUsuario();
                break;
            case 3:
                listarUsuarios();
                break;
            case 0:
                return false;
            default:
                System.out.println("Opcao invalida! Tente novamente.");
        }
        return true;
    }

    // Menu do usuario logado
    private static boolean menuUsuario() {
        System.out.println("  BEM-VINDO, " + usuarioLogado.getNome().toUpperCase());
        System.out.println("1 - Reproduzir musica");
        System.out.println("2 - Gerenciar playlists");
        System.out.println("3 - Ver recomendacoes");
        System.out.println("4 - Buscar musicas");
        System.out.println("5 - Exibir minhas informacoes");

        if (usuarioLogado instanceof UsuarioPremium) {
            System.out.println("6 - Gerenciar downloads (Premium)");
        }

        System.out.println("0 - Fazer logout");
        System.out.print("Escolha uma opcao: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                menuReproducao();
                break;
            case 2:
                menuPlaylists();
                break;
            case 3:
                menuRecomendacoes();
                break;
            case 4:
                buscarMusicas();
                break;
            case 5:
                exibirInfoUsuario();
                break;
            case 6:
                if (usuarioLogado instanceof UsuarioPremium) {
                    menuDownloads();
                } else {
                    System.out.println("Opcao invalida!");
                }
                break;
            case 0:
                usuarioLogado = null;
                System.out.println("Logout realizado com sucesso!");
                break;
            default:
                System.out.println("Opcao invalida!");
        }
        return true;
    }

    // Menu de reproducao
    private static void menuReproducao() {
        System.out.println("\n--- REPRODUCAO ---");
        System.out.println("1 - Reproduzir uma musica");
        System.out.println("2 - Reproduzir uma playlist");
        System.out.println("3 - Voltar");
        System.out.print("Escolha: ");

        int opcao = lerInteiro();

        if (opcao == 1) {
            listarMusicasDisponiveis();
            System.out.print("Digite o numero da musica: ");
            int indice = lerInteiro();

            if (indice > 0 && indice <= biblioteca.size()) {
                Musica m = biblioteca.get(indice - 1);
                System.out.println("\n--- CONTROLES DA MUSICA ---");
                System.out.println("1 - Reproduzir");
                System.out.println("2 - Pausar");
                System.out.println("3 - Parar");
                System.out.println("4 - Avancar 10s");
                System.out.println("5 - Ver informacoes");
                System.out.println("0 - Voltar");
                System.out.print("Escolha: ");

                int acao = lerInteiro();
                switch (acao) {
                    case 1:
                        m.reproduzir();
                        break;
                    case 2:
                        m.pausar();
                        break;
                    case 3:
                        m.parar();
                        break;
                    case 4:
                        System.out.print("Quantos segundos? ");
                        int seg = lerInteiro();
                        m.avancarTempo(seg);
                        break;
                    case 5:
                        m.exibirInfo();
                        break;
                }
            } else {
                System.out.println("Indice invalido!");
            }
        } else if (opcao == 2) {
            if (usuarioLogado.getPlaylists().isEmpty()) {
                System.out.println("Voce nao tem playlists ainda!");
                return;
            }

            System.out.println("\nSuas playlists:");
            for (int i = 0; i < usuarioLogado.getPlaylists().size(); i++) {
                System.out.println((i + 1) + " - " + usuarioLogado.getPlaylists().get(i).getNome());
            }

            System.out.print("Escolha uma playlist: ");
            int idx = lerInteiro();

            if (idx > 0 && idx <= usuarioLogado.getPlaylists().size()) {
                Playlist p = usuarioLogado.getPlaylists().get(idx - 1);
                System.out.println("\n--- CONTROLES DA PLAYLIST ---");
                System.out.println("1 - Reproduzir playlist completa");
                System.out.println("2 - Reproduzir item a item");
                System.out.println("3 - Ver informacoes");
                System.out.print("Escolha: ");

                int acao = lerInteiro();
                if (acao == 1) {
                    p.reproduzir();
                } else if (acao == 2) {
                    boolean navegando = true;
                    while (navegando) {
                        System.out.println("\n--- NAVEGACAO ---");
                        System.out.println("1 - Proximo");
                        System.out.println("2 - Anterior");
                        System.out.println("3 - Pausar");
                        System.out.println("4 - Parar");
                        System.out.println("0 - Sair");
                        System.out.print("Escolha: ");

                        int nav = lerInteiro();
                        switch (nav) {
                            case 1:
                                p.reproduzirProximo();
                                break;
                            case 2:
                                p.voltar();
                                break;
                            case 3:
                                p.pausar();
                                break;
                            case 4:
                                p.parar();
                                break;
                            case 0:
                                navegando = false;
                                break;
                        }
                    }
                } else if (acao == 3) {
                    p.exibirInfo();
                }
            }
        }
    }

    // Menu de playlists
    private static void menuPlaylists() {
        System.out.println("\n--- GERENCIAR PLAYLISTS ---");
        System.out.println("1 - Criar nova playlist");
        System.out.println("2 - Ver minhas playlists");
        System.out.println("3 - Adicionar musica a uma playlist");
        System.out.println("4 - Remover musica de uma playlist");
        System.out.println("5 - Excluir playlist");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                System.out.print("Nome da nova playlist: ");
                String nome = scanner.nextLine();
                Playlist nova = new Playlist(nome);
                usuarioLogado.adicionarPlaylist(nova);
                System.out.println("Playlist '" + nome + "' criada!");
                break;

            case 2:
                if (usuarioLogado.getPlaylists().isEmpty()) {
                    System.out.println("Voce nao tem playlists!");
                } else {
                    System.out.println("\nSUAS PLAYLISTS:");
                    for (int i = 0; i < usuarioLogado.getPlaylists().size(); i++) {
                        Playlist p = usuarioLogado.getPlaylists().get(i);
                        System.out.println((i + 1) + " - " + p.getNome() + " (" + p.getItens().size() + " musicas)");
                    }
                }
                break;

            case 3:
                if (usuarioLogado.getPlaylists().isEmpty()) {
                    System.out.println("Voce nao tem playlists!");
                    return;
                }

                System.out.println("\nEscolha a playlist:");
                for (int i = 0; i < usuarioLogado.getPlaylists().size(); i++) {
                    System.out.println((i + 1) + " - " + usuarioLogado.getPlaylists().get(i).getNome());
                }
                System.out.print("Playlist: ");
                int idxPlaylist = lerInteiro() - 1;

                if (idxPlaylist >= 0 && idxPlaylist < usuarioLogado.getPlaylists().size()) {
                    listarMusicasDisponiveis();
                    System.out.print("Musica para adicionar: ");
                    int idxMusica = lerInteiro() - 1;

                    if (idxMusica >= 0 && idxMusica < biblioteca.size()) {
                        usuarioLogado.getPlaylists().get(idxPlaylist).adicionarItem(biblioteca.get(idxMusica));
                        System.out.println("Musica adicionada!");
                    }
                }
                break;

            case 4:
                if (usuarioLogado.getPlaylists().isEmpty()) {
                    System.out.println("Voce nao tem playlists!");
                    return;
                }

                System.out.println("\nEscolha a playlist:");
                for (int i = 0; i < usuarioLogado.getPlaylists().size(); i++) {
                    System.out.println((i + 1) + " - " + usuarioLogado.getPlaylists().get(i).getNome());
                }
                System.out.print("Playlist: ");
                int idxP = lerInteiro() - 1;

                if (idxP >= 0 && idxP < usuarioLogado.getPlaylists().size()) {
                    Playlist p = usuarioLogado.getPlaylists().get(idxP);
                    if (p.getItens().isEmpty()) {
                        System.out.println("Playlist vazia!");
                        return;
                    }

                    System.out.println("\nMusicas na playlist:");
                    for (int i = 0; i < p.getItens().size(); i++) {
                        System.out.println((i + 1) + " - " + p.getItens().get(i).getNome());
                    }
                    System.out.print("Musica para remover: ");
                    int idxM = lerInteiro() - 1;

                    if (idxM >= 0 && idxM < p.getItens().size()) {
                        p.removerItem(idxM);
                        System.out.println("Musica removida!");
                    }
                }
                break;

            case 5:
                if (usuarioLogado.getPlaylists().isEmpty()) {
                    System.out.println("Voce nao tem playlists para excluir!");
                    return;
                }

                System.out.println("\nEscolha a playlist para excluir:");
                for (int i = 0; i < usuarioLogado.getPlaylists().size(); i++) {
                    System.out.println((i + 1) + " - " + usuarioLogado.getPlaylists().get(i).getNome());
                }
                System.out.print("Playlist: ");
                int idxExcluir = lerInteiro() - 1;

                if (idxExcluir >= 0 && idxExcluir < usuarioLogado.getPlaylists().size()) {
                    Playlist removida = usuarioLogado.getPlaylists().get(idxExcluir);
                    usuarioLogado.removerPlaylist(removida);
                    System.out.println("Playlist '" + removida.getNome() + "' excluida!");
                }
                break;
        }
    }

    // Menu de recomendacoes
    private static void menuRecomendacoes() {
        System.out.println("\n--- RECOMENDACOES ---");
        System.out.println("1 - Recomendacoes por genero");
        System.out.println("2 - Musica aleatoria");
        System.out.print("Escolha: ");

        int opcao = lerInteiro();

        if (opcao == 1) {
            System.out.print("Digite o genero (Rock, Pop, etc): ");
            String genero = scanner.nextLine();
            var recomendacoes = gerador.gerarRecomendacoes(usuarioLogado, genero);

            if (recomendacoes.isEmpty()) {
                System.out.println("Nenhuma musica encontrada para o genero '" + genero + "'");
            } else {
                System.out.println("\nRECOMENDACOES DE " + genero.toUpperCase() + ":");
                for (Musica m : recomendacoes) {
                    System.out.println("- " + m.getNome() + " | " + m.getArtista());
                }
            }
        } else if (opcao == 2) {
            Musica aleatoria = gerador.getMusicaAleatoria();
            if (aleatoria != null) {
                System.out.println("\nMusica aleatoria: " + aleatoria.getNome() + " - " + aleatoria.getArtista());
            }
        }
    }

    // Buscar musicas
    private static void buscarMusicas() {
        System.out.print("\nDigite o nome da musica ou artista: ");
        String busca = scanner.nextLine().toLowerCase();

        List<Musica> resultados = new ArrayList<>();
        for (Musica m : biblioteca) {
            if (m.getNome().toLowerCase().contains(busca) ||
                    m.getArtista().toLowerCase().contains(busca)) {
                resultados.add(m);
            }
        }

        if (resultados.isEmpty()) {
            System.out.println("Nenhuma musica encontrada!");
        } else {
            System.out.println("\nRESULTADOS DA BUSCA:");
            for (int i = 0; i < resultados.size(); i++) {
                Musica m = resultados.get(i);
                System.out.println((i + 1) + " - " + m.getNome() + " - " + m.getArtista() + " (" + m.getGenero() + ")");
            }
        }
    }

    // Menu de downloads (apenas premium)
    private static void menuDownloads() {
        UsuarioPremium premium = (UsuarioPremium) usuarioLogado;

        System.out.println("\n--- GERENCIAR DOWNLOADS ---");
        System.out.println("1 - Baixar musica");
        System.out.println("2 - Remover download");
        System.out.println("3 - Listar musicas baixadas");
        System.out.println("4 - Ver espaco utilizado");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");

        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                listarMusicasDisponiveis();
                System.out.print("Musica para baixar: ");
                int idx = lerInteiro() - 1;
                if (idx >= 0 && idx < biblioteca.size()) {
                    premium.baixar(biblioteca.get(idx));
                }
                break;

            case 2:
                premium.listarMusicasBaixadas();
                System.out.print("Digite o nome da musica para remover: ");
                String nome = scanner.nextLine();

                Musica paraRemover = null;
                for (Musica m : biblioteca) {
                    if (m.getNome().equalsIgnoreCase(nome)) {
                        paraRemover = m;
                        break;
                    }
                }

                if (paraRemover != null) {
                    premium.removerDownload(paraRemover);
                } else {
                    System.out.println("Musica nao encontrada!");
                }
                break;

            case 3:
                premium.listarMusicasBaixadas();
                break;

            case 4:
                System.out.println("Tamanho total baixado: " + premium.getTamanhoBaixados() + "MB");
                break;
        }
    }

    // Exibir informacoes do usuario
    private static void exibirInfoUsuario() {
        System.out.println("\n--- MINHAS INFORMACOES ---");
        usuarioLogado.exibirInfo();
    }

    // Metodos de autenticacao
    private static void fazerLogin() {
        System.out.print("\nDigite seu email: ");
        String email = scanner.nextLine();

        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                usuarioLogado = u;
                System.out.println("Login realizado! Bem-vindo, " + u.getNome());
                return;
            }
        }
        System.out.println("Usuario nao encontrado!");
    }

    private static void cadastrarUsuario() {
        System.out.println("\n--- CADASTRO DE USUARIO ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Tipo (1 - Free, 2 - Premium): ");
        int tipo = lerInteiro();

        Usuario novo;
        if (tipo == 2) {
            novo = new UsuarioPremium(nome, email);
            System.out.println("Usuario Premium cadastrado com sucesso!");
        } else {
            novo = new UsuarioFree(nome, email);
            System.out.println("Usuario Free cadastrado com sucesso!");
        }

        usuarios.add(novo);
    }

    private static void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("\nNenhum usuario cadastrado!");
            return;
        }

        System.out.println("\n--- USUARIOS CADASTRADOS ---");
        for (Usuario u : usuarios) {
            String tipo = (u instanceof UsuarioPremium) ? "Premium" : "Free";
            System.out.println("- " + u.getNome() + " | " + u.getEmail() + " | " + tipo);
        }
    }

    private static void listarMusicasDisponiveis() {
        System.out.println("\n--- MUSICAS DISPONIVEIS ---");
        for (int i = 0; i < biblioteca.size(); i++) {
            Musica m = biblioteca.get(i);
            System.out.println((i + 1) + " - " + m.getNome() + " - " + m.getArtista() + " (" + m.getGenero() + ")");
        }
    }

    // Inicializar dados para teste
    private static void inicializarDados() {
        // Criando musicas
        Musica m1 = new Musica("Bohemian Rhapsody", 354, "Queen", "Rock");
        Musica m2 = new Musica("Shape of You", 233, "Ed Sheeran", "Pop");
        Musica m3 = new Musica("Imagine", 183, "John Lennon", "Rock");
        Musica m4 = new Musica("Blinding Lights", 200, "The Weeknd", "Pop");
        Musica m5 = new Musica("Hotel California", 390, "Eagles", "Rock");
        Musica m6 = new Musica("Billie Jean", 294, "Michael Jackson", "Pop");

        biblioteca.add(m1);
        biblioteca.add(m2);
        biblioteca.add(m3);
        biblioteca.add(m4);
        biblioteca.add(m5);
        biblioteca.add(m6);

        // Adicionando ao gerador de recomendacoes
        for (Musica m : biblioteca) {
            gerador.adicionarMusicaBiblioteca(m);
        }

        // Criando usuario padrao para teste
        UsuarioFree user1 = new UsuarioFree("Joao Silva", "joao@email.com");
        UsuarioPremium user2 = new UsuarioPremium("Maria Santos", "maria@email.com");

        usuarios.add(user1);
        usuarios.add(user2);

        // Criando playlist padrao para o usuario premium
        Playlist playlistRock = new Playlist("Rock Classico");
        playlistRock.adicionarItem(m1);
        playlistRock.adicionarItem(m3);
        playlistRock.adicionarItem(m5);
        user2.adicionarPlaylist(playlistRock);

        System.out.println("Sistema inicializado com " + biblioteca.size() + " musicas e " + usuarios.size() + " usuarios cadastrados.");
    }

    // Ler inteiro do teclado tratando erro
    private static int lerInteiro() {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.print("Digite um numero valido: ");
            }
        }
    }
}