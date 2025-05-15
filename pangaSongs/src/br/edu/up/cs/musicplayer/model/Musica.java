package br.edu.up.cs.musicplayer.model;

import br.edu.up.cs.musicplayer.util.Logger;

import java.io.*;
import java.util.ArrayList;

public class Musica extends Media {
    private static int contador = 0;
    private String artista;
    private String genero;

    public Musica(String nome, double duracao, String caminhoArquivo, String artista, String genero) {
        super(contador++, nome, duracao, caminhoArquivo);
        this.artista = artista;
        this.genero = genero;
    }

    @Override
    public void reproduzir() throws IOException {
        System.out.println("Tocando " + super.getNome() + " de " + artista);
        Logger.registrar("Reproduzindo música: " + super.getNome());
        //Implementar código de reprodução real
    }

    public static void salvarUltimoId(String caminho) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            writer.write(Integer.toString(contador));
        }
    }

    public static void carregarUltimoId(String caminho) throws IOException {
        File file = new File(caminho);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linha = reader.readLine();
                if (linha != null) {
                    contador = Integer.parseInt(linha);
                }
            }
        }else{
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }


    public static void setContador(int contador) {
        Musica.contador = contador;
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
