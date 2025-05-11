package br.edu.up.cs.musicplayer.model;

public abstract class Media {
    private String nome;
    private double duracao;
    private String caminhoArquivo;

    public Media(String nome, double duracao, String caminhoArquivo) {
        this.nome = nome;
        this.duracao = duracao;
        this.caminhoArquivo = caminhoArquivo;
    }

    public abstract void reproduzir();

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
