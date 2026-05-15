package br.com.streaming.modelo;

import br.com.streaming.servico.Reproduzivel;
import java.util.ArrayList;
import java.util.List;

public class Playlist extends ItemReproducao implements Reproduzivel {
    private List<ItemReproducao> itens;
    private int indiceAtual;
    private boolean tocando;

    public Playlist(String nome) {
        super(nome, 0);
        this.itens = new ArrayList<>();
        this.indiceAtual = 0;
        this.tocando = false;
    }

    public void adicionarItem(ItemReproducao item) {
        itens.add(item);
        calcularDuracaoTotal();
    }

    public void removerItem(int indice) {
        if (indice >= 0 && indice < itens.size()) {
            itens.remove(indice);
            calcularDuracaoTotal();
        }
    }

    private void calcularDuracaoTotal() {
        int total = 0;
        for (ItemReproducao item : itens) {
            total += item.getDuracao();
        }
        this.duracao = total;
    }

    public List<ItemReproducao> getItens() {
        return itens;
    }

    // Metodos da interface Reproduzivel
    @Override
    public void reproduzir() {
        if (itens.isEmpty()) {
            System.out.println("Playlist vazia! Adicione musicas primeiro.");
            return;
        }

        System.out.println("Reproduzindo playlist: " + nome);
        tocando = true;

        for (int i = indiceAtual; i < itens.size(); i++) {
            ItemReproducao item = itens.get(i);
            System.out.println("Tocando item " + (i + 1) + "/" + itens.size());

            if (item instanceof Reproduzivel) {
                ((Reproduzivel) item).reproduzir();
            } else {
                item.reproduzir();
            }
        }

        indiceAtual = 0;
        tocando = false;
    }

    @Override
    public void pausar() {
        if (tocando && indiceAtual < itens.size()) {
            System.out.println("Playlist pausada no item " + (indiceAtual + 1));
            tocando = false;

            ItemReproducao itemAtual = itens.get(indiceAtual);
            if (itemAtual instanceof Reproduzivel) {
                ((Reproduzivel) itemAtual).pausar();
            }
        } else {
            System.out.println("Nenhuma playlist esta sendo reproduzida");
        }
    }

    @Override
    public void parar() {
        if (tocando || indiceAtual > 0) {
            System.out.println("Playlist parada: " + nome + " - voltando ao inicio");
            tocando = false;
            indiceAtual = 0;

            if (!itens.isEmpty() && itens.get(0) instanceof Reproduzivel) {
                ((Reproduzivel) itens.get(0)).parar();
            }
        } else {
            System.out.println("A playlist ja esta parada");
        }
    }

    @Override
    public int getDuracaocompleta() {
        return duracao;
    }

    // Metodo para avancar para o proximo item
    public void avancar() {
        if (indiceAtual + 1 < itens.size()) {
            ItemReproducao itemAtual = itens.get(indiceAtual);
            if (itemAtual instanceof Reproduzivel) {
                ((Reproduzivel) itemAtual).parar();
            }

            indiceAtual++;
            System.out.println("Avançando para o proximo item: " + itens.get(indiceAtual).getNome());

            ItemReproducao proximo = itens.get(indiceAtual);
            if (proximo instanceof Reproduzivel) {
                ((Reproduzivel) proximo).reproduzir();
            } else {
                proximo.reproduzir();
            }
        } else {
            System.out.println("Voce ja esta no ultimo item da playlist");
        }
    }

    // Metodo para voltar ao item anterior
    public void voltar() {
        if (indiceAtual - 1 >= 0) {
            ItemReproducao itemAtual = itens.get(indiceAtual);
            if (itemAtual instanceof Reproduzivel) {
                ((Reproduzivel) itemAtual).parar();
            }

            indiceAtual--;
            System.out.println("Voltando ao item anterior: " + itens.get(indiceAtual).getNome());

            ItemReproducao anterior = itens.get(indiceAtual);
            if (anterior instanceof Reproduzivel) {
                ((Reproduzivel) anterior).reproduzir();
            } else {
                anterior.reproduzir();
            }
        } else {
            System.out.println("Voce ja esta no primeiro item da playlist");
        }
    }

    // Metodo para reproduzir apenas o proximo item
    public void reproduzirProximo() {
        if (indiceAtual < itens.size()) {
            ItemReproducao item = itens.get(indiceAtual);
            if (item instanceof Reproduzivel) {
                ((Reproduzivel) item).reproduzir();
            } else {
                item.reproduzir();
            }
            indiceAtual++;
        } else {
            System.out.println("Fim da playlist!");
            indiceAtual = 0;
        }
    }

    @Override
    public void exibirInfo() {
        super.exibirInfo();
        System.out.println("Total de itens: " + itens.size());
        System.out.println("Status: " + (tocando ? "Reproduzindo" : "Parada"));
        if (indiceAtual < itens.size()) {
            System.out.println("Proximo item: " + itens.get(indiceAtual).getNome());
        }
    }
}