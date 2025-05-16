package br.edu.up.cs.musicplayer.model;

import br.edu.up.cs.musicplayer.util.Logger;

import java.io.*;
import java.util.ArrayList;

public class Musica extends Media {
    private String artista;
    private String genero;

    public Musica(String id, String nome, double duracao, String caminhoArquivo, String artista, String genero) {
        super(id, nome, duracao, caminhoArquivo);
        this.artista = artista;
        this.genero = genero;
    }

    @Override
    public void reproduzir() throws IOException {
        System.out.println("Tocando " + super.getNome() + " de " + artista);
        Logger.registrar("Reproduzindo música: " + super.getNome());
        //Implementar código de reprodução real
    }


    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getArtista() {
        return artista;
    }

    public String getGenero() {
        return genero;
    }
}
