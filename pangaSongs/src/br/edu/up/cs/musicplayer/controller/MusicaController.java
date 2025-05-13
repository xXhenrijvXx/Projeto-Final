package br.edu.up.cs.musicplayer.controller;

import br.edu.up.cs.musicplayer.model.Musica;
import java.util.ArrayList;
import java.util.List;

public class MusicaController {
    private List<Musica> musicas = new ArrayList<>();

    public void adicionar(Musica musica){
        musicas.add(musica);
    }
    public void editar(int index, Musica novaMusica){
        musicas.set(index, novaMusica);
    }
    public void remover(int index){
        if(index >= 0 && index < musicas.size()){
            musicas.remove(index);
        }
    }
    public List<Musica> listar() {
        return musicas;
    }
    public Musica get(int index){
        return (index >= 0 && index < musicas.size()) ? musicas.get(index) : null;
    }
}
