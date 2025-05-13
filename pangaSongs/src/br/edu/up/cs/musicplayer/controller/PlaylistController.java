package br.edu.up.cs.musicplayer.controller;

import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistController {
    private List<Playlist> playlists = new ArrayList<>();

    public void adicionarPlaylist(Playlist playlist){
        playlists.add(playlist);
    }
    public void editar(int index, Playlist novaPlaylist){
        if(index >= 0 && index < playlists.size()){
            playlists.set(index, novaPlaylist);
        }
    }
    public void removerPlaylist(String nome){
        playlists.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
    }

    public void listarPlaylist() {
        for (Playlist p : playlists) {
            System.out.println("📁 Playlist: " + p.getNome());
        }
    }

    public Playlist buscarPlaylist(String nome) {
        for (Playlist p : playlists) {
            if (p.getNome().equalsIgnoreCase(nome)) return p;
        }
        return null;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void adicionarMusicaNaPlaylist(String nomePlaylist, Musica musica) {
        Playlist p = buscarPlaylist(nomePlaylist);
        if (p != null) {
            p.adicionarMusica(musica);
        } else {
            System.out.println("Playlist não encontrada.");
        }
    }
}
