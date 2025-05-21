package br.edu.up.cs.musicplayer.model;

import br.edu.up.cs.musicplayer.interfaces.Reproduzivel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Media implements Reproduzivel {
    String id;
    private String nome;
    private double duracao;
    private String caminhoArquivo;

    public Media(String id, String nome, double duracao, String caminhoArquivo) {
        this.id = id;
        this.nome = nome;
        this.duracao = duracao;
        this.caminhoArquivo = caminhoArquivo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract void reproduzir() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public String getNome() {
        return nome;
    }

    public double getDuracao() {
        return duracao;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }
}
