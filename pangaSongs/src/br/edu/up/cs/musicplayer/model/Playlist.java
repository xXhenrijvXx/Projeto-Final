package br.edu.up.cs.musicplayer.model;

import br.edu.up.cs.musicplayer.util.Logger;
import br.edu.up.cs.musicplayer.view.PlaylistPlayerView;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Playlist extends Media {
    private List<Musica> musicas;

    public Playlist(String id, String nome) {
        super(id, nome, 0, ""); // caminho não se aplica a playlists
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
    public void reproduzir() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        System.out.println("Tocando playlist: " + super.getNome());
        Logger.registrar("Reproduzindo playlist: " + super.getNome());

        int idx = 0;
        PlaylistPlayerView ppv = new PlaylistPlayerView();

        while (idx >= 0 && idx < this.musicas.size()) {
            Musica m = this.musicas.get(idx);
            System.out.println("► Tocando: " + m.getNome());

            String cmd = ppv.menu(m.getCaminhoArquivo(), (idx == 0), (idx == (this.musicas.size())-1));

            switch (cmd) {
                case "n" -> idx++;
                case "b" -> idx = Math.max(0, idx - 1);
                case "s" -> {
                    return;
                }
                default -> {}
            }
        }
    }

    public List<Musica> getMusicas() {
        return new ArrayList<>(musicas);
    }
}
