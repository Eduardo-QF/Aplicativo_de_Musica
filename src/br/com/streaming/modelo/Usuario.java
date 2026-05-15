package br.com.streaming.modelo;

import java.util.ArrayList;
import java.util.List;

// Classe abstrata base para todos os usuários
public abstract class Usuario {
    protected String nome;
    protected String email;
    protected List<Playlist> playlists;

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.playlists = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    // Adiciona uma playlist ao usuário
    public void adicionarPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    // Remove uma playlist
    public void removerPlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    // Método abstrato - cada tipo de usuário tem seu próprio limite
    public abstract int getLimitePlaylists();

    // Método abstrato - verifica se pode baixar conteúdo
    public abstract boolean podeBaixar();

    // Exibe informações do usuário
    public void exibirInfo() {
        System.out.println("Usuário: " + nome + " | Email: " + email);
        System.out.println("Playlists criadas: " + playlists.size());
        System.out.println("Limite de playlists: " + getLimitePlaylists());
        System.out.println("Pode baixar: " + (podeBaixar() ? "Sim" : "Não"));
    }
}