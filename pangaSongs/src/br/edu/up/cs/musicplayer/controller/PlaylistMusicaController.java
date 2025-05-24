package br.edu.up.cs.musicplayer.controller;

import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;
import br.edu.up.cs.musicplayer.model.PlaylistMusica;
import java.util.ArrayList;
import java.util.List;

public class PlaylistMusicaController {
    private static final List<PlaylistMusica> ids = new ArrayList<>();

    private PlaylistMusicaController(){}

    public static void removerMusicaDasPlaylists(Musica musica){
        for(Playlist p : PlaylistController.getPlaylists()){
            for(Musica m : p.getMusicas()){
                if(m.getId() == musica.getId()){
                    p.removerMusica(musica);
                    removerMusicaDaPlaylist(m, p);
                }
            }
        }
    }

    public static void removerMusicaDaPlaylist(Musica musica, Playlist playlist){
        ids.removeIf(pm ->
                pm.getIdMusica().equals(musica.getId()) && pm.getIdPlaylist().equals(playlist.getId())
        );

    }

    public static void adicionarMusicaNaPlaylist(String musicaId, String playlistId) {
        ids.add(new PlaylistMusica(musicaId, playlistId));
    }

    public static List<PlaylistMusica> getIds() {
        return ids;
    }
}
