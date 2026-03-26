import java.util.ArrayList;
import java.util.Scanner;

public class StreamingMusica {

    static ArrayList<Musica> musicas = new ArrayList<>();
    static Usuario usuario = new Usuario();

    static final String[] GENEROS_VALIDOS = {"Pop", "Rock", "Jazz", "Eletrônica", "Hip-Hop", "Clássica"};

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        usuario.nome = "Niccolas";
        adicionarMusicasTeste();

        int opcao;

        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);

        System.out.println("\nAté logo!");
        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE STREAMING DE MÚSICA ===");
        System.out.println("1. Cadastrar música");
        System.out.println("2. Listar todas as músicas");
        System.out.println("3. Buscar música");
        System.out.println("4. Criar playlist");
        System.out.println("5. Gerenciar playlists");
        System.out.println("6. Exibir estatísticas");
        System.out.println("0. Sair");
        System.out.print("Opção: ");
    }

    public static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1: cadastrarMusica(); break;
            case 2: listarMusicas(); break;
            case 3: buscarMusica(); break;
            case 4: criarPlaylist(); break;
            case 5: gerenciarPlaylists(); break;
            case 6: exibirEstatisticas(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

    // ================= MÚSICAS =================

    public static void cadastrarMusica() {
        Musica m = new Musica();

        System.out.print("Título: ");
        m.titulo = scanner.nextLine();

        System.out.print("Artista: ");
        m.artista = scanner.nextLine();

        System.out.print("Duração (segundos): ");
        m.duracaoSegundos = Integer.parseInt(scanner.nextLine());

        System.out.println("Escolha gênero:");
        for (int i = 0; i < GENEROS_VALIDOS.length; i++) {
            System.out.println((i + 1) + ". " + GENEROS_VALIDOS[i]);
        }

        int g = Integer.parseInt(scanner.nextLine());
        m.genero = GENEROS_VALIDOS[g - 1];

        musicas.add(m);

        System.out.println("Música cadastrada.");
    }

    public static void listarMusicas() {
        if (musicas.isEmpty()) {
            System.out.println("Nenhuma música cadastrada.");
            return;
        }

        for (int i = 0; i < musicas.size(); i++) {
            System.out.print((i + 1) + ". ");
            musicas.get(i).exibir();
        }
    }

    public static void buscarMusica() {
        System.out.print("Buscar: ");
        String busca = scanner.nextLine();

        boolean encontrou = false;

        for (Musica m : musicas) {
            if (m.contemTitulo(busca) || m.contemArtista(busca)) {
                m.exibir();
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma música encontrada.");
        }
    }

    // ================= PLAYLIST =================

    public static void criarPlaylist() {
        System.out.print("Nome da playlist: ");
        String nome = scanner.nextLine();

        usuario.criarPlaylist(nome);

        System.out.println("Playlist criada.");
    }

    public static void gerenciarPlaylists() {
        int op;

        do {
            System.out.println("\n=== GERENCIAR PLAYLISTS ===");
            System.out.println("1. Listar minhas playlists");
            System.out.println("2. Adicionar música");
            System.out.println("3. Remover música");
            System.out.println("4. Exibir detalhes");
            System.out.println("0. Voltar");

            op = lerOpcao();

            switch (op) {
                case 1:
                    usuario.listarPlaylists();
                    break;

                case 2:
                    adicionarMusicaPlaylist();
                    break;

                case 3:
                    removerMusicaPlaylist();
                    break;

                case 4:
                    detalhesPlaylist();
                    break;
            }

        } while (op != 0);
    }

    public static void adicionarMusicaPlaylist() {
        usuario.listarPlaylists();
        System.out.print("Escolha playlist: ");
        int p = Integer.parseInt(scanner.nextLine()) - 1;

        Playlist pl = usuario.getPlaylist(p);
        if (pl == null) return;

        listarMusicas();
        System.out.print("Escolha música: ");
        int m = Integer.parseInt(scanner.nextLine()) - 1;

        if (m >= 0 && m < musicas.size()) {
            pl.adicionarMusica(musicas.get(m));
            System.out.println("Música adicionada.");
        }
    }

    public static void removerMusicaPlaylist() {
        usuario.listarPlaylists();
        System.out.print("Escolha playlist: ");
        int p = Integer.parseInt(scanner.nextLine()) - 1;

        Playlist pl = usuario.getPlaylist(p);
        if (pl == null) return;

        pl.listarMusicas();
        System.out.print("Índice da música: ");
        int m = Integer.parseInt(scanner.nextLine()) - 1;

        pl.removerMusica(m);

        System.out.println("Música removida.");
    }

    public static void detalhesPlaylist() {
        usuario.listarPlaylists();
        System.out.print("Escolha playlist: ");
        int p = Integer.parseInt(scanner.nextLine()) - 1;

        Playlist pl = usuario.getPlaylist(p);
        if (pl == null) return;

        System.out.println("\nMúsicas:");
        pl.listarMusicas();

        System.out.println("Total músicas: " + pl.getQuantidadeMusicas());
        System.out.println("Duração total: " + pl.getDuracaoTotal() + " segundos");
    }

    // ================= ESTATÍSTICAS =================

    public static void exibirEstatisticas() {
        System.out.println("\n--- ESTATÍSTICAS ---");

        System.out.println("Total de músicas: " + musicas.size());
        System.out.println("Total de playlists: " + usuario.getTotalPlaylists());
    }

    // ================= TESTE =================

    public static void adicionarMusicasTeste() {
        Musica m1 = new Musica();
        m1.titulo = "Bohemian Rhapsody";
        m1.artista = "Queen";
        m1.duracaoSegundos = 354;
        m1.genero = "Rock";

        Musica m2 = new Musica();
        m2.titulo = "Billie Jean";
        m2.artista = "Michael Jackson";
        m2.duracaoSegundos = 293;
        m2.genero = "Pop";

        musicas.add(m1);
        musicas.add(m2);
    }
}