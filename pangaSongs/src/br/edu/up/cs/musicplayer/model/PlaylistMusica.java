package br.edu.up.cs.musicplayer.model;

public class PlaylistMusica {
    private final String idMusica;
    private final String idPlaylist;

    public PlaylistMusica(String idMusica, String idPlaylist) {
        this.idMusica = idMusica;
        this.idPlaylist = idPlaylist;
    }

    public String getIdMusica() {
        return idMusica;
    }

    public String getIdPlaylist() {
        return idPlaylist;
    }
}
