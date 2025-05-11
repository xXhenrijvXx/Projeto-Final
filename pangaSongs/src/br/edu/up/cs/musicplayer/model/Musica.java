package br.edu.up.cs.musicplayer.model;

public class Musica extends Media {
    private String artista;
    private String genero;

    public Musica(String nome, String artista, String genero, double duracao, String caminhoArquivo) {
        super(nome, duracao, caminhoArquivo);
        this.artista = artista;
        this.genero = genero;
    }

    public void reproduzir(){
        System.out.println("Tocando " + super.getNome() + " de " + artista);
        //Implementar código de reprodução real
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
