package br.edu.up.pangaSongs.models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Playlist extends Media {
    private final List<Musica> musicas;

    public Playlist(String id, String nome) {
        super(id, nome, 0);
        this.musicas = new ArrayList<>();
    }

    public void adicionarMusica(Musica musica){
        if (musica != null) {
            musicas.add(musica);
            atualizarDuracao();
        }
    }

    public void removerMusica(Musica musica){
        if (musicas.remove(musica)) {
            atualizarDuracao();
        }
    }

    private void atualizarDuracao(){
        double total = 0;
        for (Musica m : musicas){
            total += m.getDuracao();
        }
        super.setDuracao(total);
    }

    @Override
    public void reproduzir() throws IOException, InterruptedException {

    }

    public List<Musica> getMusicas() {
        return new ArrayList<>(musicas);
    }
}
