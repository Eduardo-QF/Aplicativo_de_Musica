package br.com.streaming.servico;

import br.com.streaming.modelo.Musica;
import br.com.streaming.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeradorRecomendacoes {
    private List<Musica> bibliotecaGlobal;
    private Random random;

    public GeradorRecomendacoes() {
        this.bibliotecaGlobal = new ArrayList<>();
        this.random = new Random();
    }

    // Adiciona uma música à biblioteca global para recomendações
    public void adicionarMusicaBiblioteca(Musica musica) {
        bibliotecaGlobal.add(musica);
    }

    // Gera recomendações baseadas no gênero favorito (simulação)
    public List<Musica> gerarRecomendacoes(Usuario usuario, String generoFavorito) {
        List<Musica> recomendacoes = new ArrayList<>();

        for (Musica musica : bibliotecaGlobal) {
            if (musica.getGenero().equalsIgnoreCase(generoFavorito)) {
                recomendacoes.add(musica);
            }
        }

        // Limita a 5 recomendações
        if (recomendacoes.size() > 5) {
            recomendacoes = recomendacoes.subList(0, 5);
        }

        return recomendacoes;
    }

    // Gera recomendações aleatórias
    public Musica getMusicaAleatoria() {
        if (bibliotecaGlobal.isEmpty()) {
            return null;
        }
        return bibliotecaGlobal.get(random.nextInt(bibliotecaGlobal.size()));
    }

    public void exibirRecomendacoes(List<Musica> recomendacoes) {
        System.out.println("RECOMENDAÇÕES PARA VOCÊ:");
        for (Musica musica : recomendacoes) {
            System.out.println("- " + musica.getNome() + " | " + musica.getArtista() + " | " + musica.getGenero());
        }
    }
}