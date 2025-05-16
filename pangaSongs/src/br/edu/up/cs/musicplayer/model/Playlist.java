package br.edu.up.cs.musicplayer.model;

import br.edu.up.cs.musicplayer.util.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Playlist extends Media {
    private static Integer contador;
    private List<Musica> musicas;

    public Playlist(int id, String nome) {
        super(id, nome, 0, ""); // caminho não se aplica a playlists
        this.musicas = new ArrayList<>();
    }

    public Playlist(String nome){
        super(contador++, nome, 0, "");
        this.musicas = new ArrayList<>();
    }

    public void adicionarMusica(Musica musica){
        if (musica != null) {
            musicas.add(musica);
            atualizarDuracao();
        }
    }

    public void adicionarMusica(String nome, double duracao, String caminhoArquivo, String artista, String genero){
        Musica musica = new Musica(nome, duracao, caminhoArquivo, artista, genero);
        adicionarMusica(musica);
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
    public void reproduzir() throws IOException {
        System.out.println("Tocando playlist: " + super.getNome());
        Logger.registrar("Reproduzindo playlist: " + super.getNome());

        for(Musica m : musicas){
            m.reproduzir();
        }
    }

    public List<Musica> getMusicas() {
        return new ArrayList<>(musicas);
    }

    public static void salvarUltimoId(String caminho) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(caminho));
        System.out.println(Integer.toString(contador));
        writer.write(Integer.toString(contador));
        writer.close();
    }

    public static void carregarUltimoId(String caminho) throws IOException {
        File file = new File(caminho);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("0");
            }
            contador = 0;
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha = reader.readLine();
            if (linha != null) {
                contador = Integer.parseInt(linha);
            } else {
                contador = 0;
            }
        }
    }


    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Playlist.contador = contador;
    }
}
