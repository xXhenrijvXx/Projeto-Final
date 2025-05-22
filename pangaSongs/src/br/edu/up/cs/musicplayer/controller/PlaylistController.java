package br.edu.up.cs.musicplayer.controller;

import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;
import br.edu.up.cs.musicplayer.util.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistController {
    private List<Playlist> playlists = new ArrayList<>();

    public void adicionarPlaylist(Playlist playlist) throws IOException {
        playlists.add(playlist);
        Logger.registrar("Playlist adicionada: " + playlist.getNome());
    }

    public void removerPlaylist(String nome) throws IOException {
        if (playlists.removeIf(p -> p.getNome().equalsIgnoreCase(nome))) {
            Logger.registrar("Playlist removida: " + nome);
            System.out.println("Playlist removida!");
        } else {
            System.out.println("Playlist não encontrada.");
        }
    }

    public void listarPlaylists() {
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

    public Playlist buscarPlaylistId(String id) {
        for (Playlist p : playlists) {
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public String adicionarMusicaNaPlaylist(Playlist playlist, Musica musica) {
        playlist.adicionarMusica(musica);
        return playlist.getId();
    }
}
