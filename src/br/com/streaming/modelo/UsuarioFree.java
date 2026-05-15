package br.com.streaming.modelo;

public class UsuarioFree extends Usuario {

    public UsuarioFree(String nome, String email) {
        super(nome, email);
    }

    @Override
    public int getLimitePlaylists() {
        return 3; // Usuário free só pode ter 3 playlists
    }

    @Override
    public boolean podeBaixar() {
        return false; // Usuário free não pode baixar músicas
    }

    @Override
    public void adicionarPlaylist(Playlist playlist) {
        if (playlists.size() < getLimitePlaylists()) {
            super.adicionarPlaylist(playlist);
            System.out.println("Playlist adicionada com sucesso!");
        } else {
            System.out.println("Limite de playlists atingido! Usuário free só pode ter " + getLimitePlaylists() + " playlists.");
        }
    }
}