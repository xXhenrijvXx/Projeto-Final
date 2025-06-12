package br.edu.up.pangaSongs.controller;

import br.edu.up.pangaSongs.models.Musica;
import br.edu.up.pangaSongs.models.Playlist;
import br.edu.up.pangaSongs.models.Ids;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IdsController {
    private static final List<Ids> ids = new ArrayList<>();

    private IdsController(){}

    public static void removerMusicaDasPlaylists(Musica musica){
        for(Playlist p : PlaylistController.getPlaylists()){
            for(Musica m : p.getMusicas()){
                if(Objects.equals(m.getId(), musica.getId())){
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

    public static void adicionarMusicaNaPlaylist(String musicaId, String playlistId){
        ids.add(new Ids(musicaId, playlistId));
    }

    public static List<Ids> getIds() {
        return ids;
    }
}
