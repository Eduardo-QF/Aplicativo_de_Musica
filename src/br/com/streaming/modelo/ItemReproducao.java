package br.com.streaming.modelo;

// Classe abstrata que representa um item reproduzível genérico
public abstract class ItemReproducao {
    protected String nome;
    protected int duracao; // em segundos

    public ItemReproducao(String nome, int duracao) {
        this.nome = nome;
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracao() {
        return duracao;
    }

    // Método abstrato que será implementado pelas subclasses
    public abstract void reproduzir();

    // Exibe informações básicas do item
    public void exibirInfo() {
        System.out.println("Nome: " + nome + " | Duração: " + duracao + " segundos");
    }
}