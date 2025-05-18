package br.edu.up.cs.musicplayer.model;

public class PlaylistMusica {
    private String idMusica;
    private String idPlaylist;

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

    public void setIdMusica(String idMusica) {
        this.idMusica = idMusica;
    }

    public void setIdPlaylist(String idPlaylist) {
        this.idPlaylist = idPlaylist;
    }
}
