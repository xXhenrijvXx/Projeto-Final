package br.edu.up.pangaSongs.models;

import br.edu.up.pangaSongs.controller.MusicPlayerController;
import javax.sound.sampled.*;
import java.io.*;

public class Musica extends Media {
    private String artista;
    private String genero;
    private String caminhoArquivo;

    public Musica(String id, String nome, String caminhoArquivo, String artista, String genero) throws IOException {
        super(id, nome, 0.0);
        this.artista = artista;
        this.genero = genero;
        this.caminhoArquivo = caminhoArquivo;

        super.setDuracao(calculaDuracao());
    }

    public Musica(String id, String nome, Double duracao ,String caminhoArquivo, String artista, String genero) {
        super(id, nome, duracao);
        this.artista = artista;
        this.genero = genero;
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public void reproduzir() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {

        MusicPlayerController.tocar(getCaminhoArquivo());
    }

    public boolean isFinalizada(){
        return MusicPlayerController.isFinalizada();
    }

    public boolean isPausada(){
        return MusicPlayerController.isPausada();
    }

    public void pausar(){
        MusicPlayerController.pausar();
    }

    public void continuar(){
        MusicPlayerController.continuar();
    }

    public void parar(){
        MusicPlayerController.parar();
    }



    private Double calculaDuracao() {
        try {
            File arquivo = new File(getCaminhoArquivo());

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(arquivo);
            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            return (frames+0.0) / format.getFrameRate();

        } catch (UnsupportedAudioFileException | IOException e) {
            System.out.println("Erro ao ler WAV: " + e.getMessage());
            return 0.0;
        }
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
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
