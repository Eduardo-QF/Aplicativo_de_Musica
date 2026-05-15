package br.com.streaming.modelo;

import br.com.streaming.servico.Reproduzivel;

public class Musica extends ItemReproducao implements Reproduzivel {
    private String artista;
    private String genero;
    private boolean tocando;
    private int tempoAtual;

    public Musica(String nome, int duracao, String artista, String genero) {
        super(nome, duracao);
        this.artista = artista;
        this.genero = genero;
        this.tocando = false;
        this.tempoAtual = 0;
    }

    public String getArtista() {
        return artista;
    }

    public String getGenero() {
        return genero;
    }

    // Metodos da interface Reproduzivel
    @Override
    public void reproduzir() {
        if (tempoAtual > 0 && tempoAtual < duracao) {
            System.out.println("Retomando musica: " + nome + " - " + artista + " (tempo: " + tempoAtual + "s)");
        } else {
            System.out.println("Tocando musica: " + nome + " - " + artista);
            tempoAtual = 0;
        }
        tocando = true;
    }

    @Override
    public void pausar() {
        if (tocando) {
            System.out.println("Musica pausada: " + nome + " - tempo: " + tempoAtual + "s");
            tocando = false;
        } else {
            System.out.println("A musica nao esta tocando no momento");
        }
    }

    @Override
    public void parar() {
        if (tocando || tempoAtual > 0) {
            System.out.println("Musica parada: " + nome + " - voltando ao inicio");
            tocando = false;
            tempoAtual = 0;
        } else {
            System.out.println("A musica ja esta parada");
        }
    }

    @Override
    public int getDuracaocompleta() {
        return duracao;
    }

    // Metodo auxiliar para avancar o tempo
    public void avancarTempo(int segundos) {
        if (tocando) {
            tempoAtual += segundos;
            if (tempoAtual >= duracao) {
                tempoAtual = duracao;
                System.out.println("Musica chegou ao fim");
                tocando = false;
            } else {
                System.out.println("Avançando " + segundos + "s - Tempo atual: " + tempoAtual + "s / " + duracao + "s");
            }
        } else {
            System.out.println("A musica nao esta tocando. Use reproduzir() primeiro.");
        }
    }

    @Override
    public void exibirInfo() {
        super.exibirInfo();
        System.out.println("Artista: " + artista + " | Genero: " + genero);
        System.out.println("Status: " + (tocando ? "Tocando" : "Parado"));
        System.out.println("Tempo atual: " + tempoAtual + "s / " + duracao + "s");
    }
}