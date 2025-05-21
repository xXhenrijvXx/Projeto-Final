package br.edu.up.cs.musicplayer.model;

import br.edu.up.cs.musicplayer.util.Logger;
import br.edu.up.cs.musicplayer.view.MusicPlayerView;

import javax.sound.sampled.*;
import java.io.*;

public class Musica extends Media {
    private String artista;
    private String genero;

    public Musica(String id, String nome, String caminhoArquivo, String artista, String genero) throws IOException {
        super(id, nome, 0.0, caminhoArquivo);
        this.artista = artista;
        this.genero = genero;

        super.setDuracao(calculaDuracao());
    }

    public Musica(String id, String nome, Double duracao ,String caminhoArquivo, String artista, String genero) {
        super(id, nome, duracao, caminhoArquivo);
        this.artista = artista;
        this.genero = genero;
    }

    @Override
    public void reproduzir() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        System.out.println("Tocando " + super.getNome() + " de " + getArtista());
        Logger.registrar("Reproduzindo música: " + super.getNome());

        MusicPlayerView mv = new MusicPlayerView();
        mv.menu(super.getCaminhoArquivo());
    }

    private Double calculaDuracao() throws IOException {
        try {
            File arquivo = new File(super.getCaminhoArquivo());
            if(super.getCaminhoArquivo().endsWith(".mp3")){
                return 0.0;
            } else{
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(arquivo);
                AudioFormat format = audioInputStream.getFormat();
                long frames = audioInputStream.getFrameLength();
                return (frames+0.0) / format.getFrameRate();
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            System.out.println("Erro ao ler WAV: " + e.getMessage());
            return 0.0;
        }
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
