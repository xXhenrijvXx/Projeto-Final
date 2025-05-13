package br.edu.up.cs.musicplayer.controller;

import br.edu.up.cs.musicplayer.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistController {
    private List<Playlist> playlists = new ArrayList<>();

    public void adicionar(Playlist playlist){
        playlists.add(playlist);
    }
    public void editar(int index, Playlist novaPlaylist){
        if(index >= 0 && index < playlists.size()){
            playlists.set(index, novaPlaylist);
        }
    }
    public void remover(int index){
        if(index >= 0 && index < playlists.size()){
            playlists.remove(index);
        }
    }

    public List<Playlist> listar() {
        return playlists;
    }
    public Playlist get(int index){
        return (index >= 0 && index < playlists.size()) ? playlists.get(index) : null;
    }

    
}
