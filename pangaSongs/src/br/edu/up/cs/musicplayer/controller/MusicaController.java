package br.edu.up.cs.musicplayer.controller;

import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.util.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicaController {
    private List<Musica> musicas = new ArrayList<>();

    public void adicionarMusica(Musica musica) throws IOException {
        musicas.add(musica);
        Logger.registrar("Música adicionada: " + musica.getNome());
    }

    public void editar(int index, Musica novaMusica){
        musicas.set(index, novaMusica);
    }

    public void removerMusica(String nome) throws IOException {
        musicas.removeIf(m -> m.getNome().equalsIgnoreCase(nome));
        Logger.registrar("Música removida: " + nome);
    }

    public void listarMusicas() {
        for (Musica m : musicas) {
            System.out.println("🎵 " + m.getNome());
        }
    }

    public Musica buscarMusica(String nome) {
        for (Musica m : musicas) {
            if (m.getNome().equalsIgnoreCase(nome)) return m;
        }
        return null;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }
}
