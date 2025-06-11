package br.edu.up.pangaSongs.models;

import br.edu.up.pangaSongs.interfaces.Reproduzivel;

public abstract class Media implements Reproduzivel {
    private final String id;
    private String nome;
    private double duracao;

    public Media(String id, String nome, double duracao) {
        this.id = id;
        this.nome = nome;
        this.duracao = duracao;
    }

    public abstract void reproduzir();

    public String getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
        System.out.println("\nNome editado com sucesso!");
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
    }

    public double getDuracao() {
        return duracao;
    }

}
