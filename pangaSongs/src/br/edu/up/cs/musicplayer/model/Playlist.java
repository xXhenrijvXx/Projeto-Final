package br.edu.up.cs.musicplayer.model;

import br.edu.up.cs.musicplayer.util.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Playlist extends Media {
    private List<Musica> musicas = new ArrayList<>();

    public Playlist(String nome) {
        super(nome, 0, ""); //caminho para playlist não existe e a duração vai ser calculada
    }

    public void adicionarMusica(Musica musica){
        musicas.add(musica);
        atualizarDuracao();
    }

    public void adicionarMusica(String nome, double duracao, String caminhoArquivo, String artista, String genero){
        Musica musica = new Musica(nome, duracao, caminhoArquivo, artista, genero);
        adicionarMusica(musica);
    }

    public void removerMusica(Musica musica){
        if(musicas.remove(musica)) {
            atualizarDuracao();
        }
    }

    private void atualizarDuracao(){
        double total = 0;
        for (Musica m : musicas){
            total += m.getDuracao();
        }
    }
    @Override
    public void reproduzir() throws IOException {
        System.out.println("Tocando playlist: " + super.getNome());
        Logger.registrar("Reproduzindo playlist: " + super.getNome());
        for(Musica m : musicas){
            m.reproduzir();
        }
    }

    public List<Musica> getMusicas() {
        return new ArrayList<>(musicas);
    }
}
