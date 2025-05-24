package br.edu.up.cs.musicplayer.controller;

import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;
import br.edu.up.cs.musicplayer.util.Logger;
import br.edu.up.cs.musicplayer.util.ScannerUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistController {
    private static final List<Playlist> playlists = new ArrayList<>();

    private PlaylistController(){}

    public static void adicionarPlaylist(Playlist playlist) throws IOException {
        playlists.add(playlist);
        Logger.registrar("Playlist adicionada: " + playlist.getNome());
    }

    public static void removerPlaylist(String nome) throws IOException {
        if (playlists.removeIf(p -> p.getNome().equalsIgnoreCase(nome))) {
            Logger.registrar("Playlist removida: " + nome);
            System.out.println("Playlist removida!");
        } else {
            System.out.println("Playlist não encontrada.");
        }
    }

    public static void editarNome(Playlist p){
        System.out.println("Digite o novo nome: ");
        p.setNome(ScannerUtil.getScanner().nextLine());
    }

    public static void listarMusicas(Playlist p){
        for(Musica m : p.getMusicas()){
            System.out.println("Música: " + m.getNome());
        }
    }

    public static void listarPlaylists() {
        for (Playlist p : playlists) {
            System.out.println("📁 Playlist: " + p.getNome());
        }
    }

    public static Playlist buscarPlaylist(String nome) {
        for (Playlist p : playlists) {
            if (p.getNome().equalsIgnoreCase(nome)) return p;
        }
        return null;
    }

    public static Playlist buscarPlaylistId(String id) {
        for (Playlist p : playlists) {
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public static List<Playlist> getPlaylists() {
        return playlists;
    }

    public static void removerMusicaDaPlaylist(Playlist playlist, Musica musica) {
        if(musica != null){
            playlist.removerMusica(musica);
            PlaylistMusicaController.removerMusicaDaPlaylist(musica, playlist);
        }else{
            System.out.println("Música não encontrada");
        }
    }

    public static void adicionarMusicaNaPlaylist(Playlist playlist, Musica musica) {
        if(musica != null){
            playlist.adicionarMusica(musica);
        }else{
            System.out.println("Música não encontrada");
        }
    }
}
