package br.edu.up.cs.musicplayer.controller;

import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;
import br.edu.up.cs.musicplayer.model.PlaylistMusica;

import java.util.ArrayList;
import java.util.List;

public class PlaylistMusicaController {
    private List<PlaylistMusica> ids = new ArrayList<>();

    public void removerMusicaDasPlaylists(Musica musica, List<Playlist> playlists){
        for(Playlist p : playlists){
            for(Musica m : p.getMusicas()){
                if(m.getId() == musica.getId()){
                    p.removerMusica(musica);
                    ids.removeIf(pm -> pm.getIdMusica().equals(m.getId()));
                }
            }
        }
    }

    public void adicionarMusicaNaPlaylist(String musicaId, String playlistId) {
        ids.add(new PlaylistMusica(musicaId, playlistId));
    }

    public List<PlaylistMusica> getIds() {
        return ids;
    }
}
