package br.edu.up.cs.musicplayer.view;

import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;

public class PlaylistView {
    public void mostrarPlaylist(Playlist playlist){
        System.out.println("Playlist: " + playlist.getNome());
        for(Musica musica : playlist.getMusicas()){
            System.out.println("  - " + musica.getNome() + " (" + musica.getDuracao() + " min");
        }
    }
}
