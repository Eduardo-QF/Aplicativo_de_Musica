package br.com.streaming.modelo;

import br.com.streaming.servico.Baixavel;
import java.util.ArrayList;
import java.util.List;

public class UsuarioPremium extends Usuario implements Baixavel {
    private List<Musica> musicasBaixadas;
    private int tamanhoTotalBaixado; // Tamanho em MB

    public UsuarioPremium(String nome, String email) {
        super(nome, email);
        this.musicasBaixadas = new ArrayList<>();
        this.tamanhoTotalBaixado = 0;
    }

    @Override
    public int getLimitePlaylists() {
        return Integer.MAX_VALUE; // Sem limite
    }

    @Override
    public boolean podeBaixar() {
        return true; // Premium pode baixar
    }

    // Implementação dos métodos da interface Baixavel
    @Override
    public void baixar(Musica musica) {
        if (musica == null) {
            System.out.println("Erro: Música inválida!");
            return;
        }

        if (estaBaixada(musica)) {
            System.out.println("Música '" + musica.getNome() + "' já está baixada!");
            return;
        }

        // Cada música ocupa 5MB
        int tamanhoMusica = 5;

        musicasBaixadas.add(musica);
        tamanhoTotalBaixado += tamanhoMusica;

        System.out.println("Download concluído: " + musica.getNome() + " - " + musica.getArtista());
        System.out.println("Tamanho total baixado: " + tamanhoTotalBaixado + "MB");
    }

    @Override
    public void removerDownload(Musica musica) {
        if (musica == null) {
            System.out.println("Erro: Música inválida!");
            return;
        }

        if (musicasBaixadas.remove(musica)) {
            tamanhoTotalBaixado -= 5; // Remove 5MB
            System.out.println("Download removido: " + musica.getNome());
            System.out.println("Tamanho total baixado: " + tamanhoTotalBaixado + "MB");
        } else {
            System.out.println("Música não encontrada nos downloads!");
        }
    }

    @Override
    public boolean estaBaixada(Musica musica) {
        return musicasBaixadas.contains(musica);
    }

    @Override
    public int getTamanhoBaixados() {
        return tamanhoTotalBaixado;
    }

    // Método para listar todas as músicas baixadas (opcional)
    public void listarMusicasBaixadas() {
        if (musicasBaixadas.isEmpty()) {
            System.out.println("Nenhuma música baixada.");
            return;
        }

        System.out.println("MÚSICAS BAIXADAS (" + musicasBaixadas.size() + " músicas):");
        for (int i = 0; i < musicasBaixadas.size(); i++) {
            Musica m = musicasBaixadas.get(i);
            System.out.println((i + 1) + ". " + m.getNome() + " - " + m.getArtista());
        }
        System.out.println("Tamanho total: " + tamanhoTotalBaixado + "MB");
    }

    @Override
    public void exibirInfo() {
        super.exibirInfo();
        System.out.println("Músicas baixadas: " + musicasBaixadas.size());
        System.out.println("Tamanho baixado: " + tamanhoTotalBaixado + "MB");
    }
}