package br.edu.up.cs.musicplayer.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist extends Media {
    private List<Musica> musicas = new ArrayList<>();

    public Playlist(String nome, double duracao, String caminhoArquivo) {
        super(nome, 0, ""); //caminho para playlist não existe e a duração vai ser calculada
    }

    public void reproduzir(){
        System.out.println("Tocando playlist: " + super.getNome());
        for(Musica m : musicas){
            m.reproduzir();
        }
    }

    public List<Musica> getMusicas() {
        return musicas;
    }
}
