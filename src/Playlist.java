import java.util.ArrayList;

class Playlist {

    protected String nome;
    protected ArrayList<Musica> musicas;
    protected String descricao;

    public Playlist() {
        this("Sem nome");
    }

    public Playlist(String nome) {
        setNome(nome);
        this.musicas = new ArrayList<>();
        this.descricao = "";
    }

    public String getNome() {
        return nome;
    }

    public ArrayList<Musica> getMusicas() {
        return new ArrayList<>(musicas);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da playlist não pode ser nulo ou vazio.");
        }
        this.nome = nome.trim();
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void adicionarMusica(Musica musica) {
        if (musica == null) {
            throw new IllegalArgumentException("Não é possível adicionar uma música nula.");
        }
        musicas.add(musica);
    }

    public void removerMusica(int indice) {
        if (indice < 0 || indice >= musicas.size()) {
            System.out.println("Índice inválido. Deve ser entre 0 e " + (musicas.size() - 1) + ".");
            return;
        }
        musicas.remove(indice);
    }

    public void listarMusicas() {
        if (musicas.isEmpty()) {
            System.out.println("Playlist vazia.");
            return;
        }

        for (int i = 0; i < musicas.size(); i++) {
            System.out.print((i + 1) + ". ");
            musicas.get(i).exibir();
        }
    }

    public int getDuracaoTotal() {
        int total = 0;
        for (Musica m : musicas) {
            total += m.getDuracaoSegundos();
        }
        return total;
    }

    public int getQuantidadeMusicas() {
        return musicas.size();
    }

    public void reproduzir() {
        System.out.println("🎵 Reproduzindo playlist: " + nome);
        for (Musica m : musicas) {
            System.out.println("  ▶ " + m.getTitulo());
        }
    }
}