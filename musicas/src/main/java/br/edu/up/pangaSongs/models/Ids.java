package br.edu.up.pangaSongs.models;

public class Ids {
    private final String idMusica;
    private final String idPlaylist;

    public Ids(String idMusica, String idPlaylist) {
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
