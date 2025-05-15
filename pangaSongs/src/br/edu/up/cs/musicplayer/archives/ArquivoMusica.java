package br.edu.up.cs.musicplayer.archives;

import br.edu.up.cs.musicplayer.model.Musica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoMusica {
    private static final String arquivoPath = "data/musicas.txt";

    public static void salvar(List<Musica> musicas) throws IOException {
        FileWriter fw = new FileWriter(arquivoPath);
        BufferedWriter bw = new BufferedWriter(fw);

        for(Musica m : musicas){
            bw.write(m.getId() + ";" + m.getNome() + ";" + m.getArtista() + ";" + m.getGenero() + ";" + m.getDuracao() + ";" + m.getCaminhoArquivo());
            bw.newLine();
        }

        bw.close();
    }

    public static List<Musica> carregar() throws IOException {
        File arquivo = new File(arquivoPath);
        if(!arquivo.exists()){
            arquivo.getParentFile().mkdirs();
            arquivo.createNewFile();
            return new ArrayList<>();
        }

        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        List<Musica> musicas = new ArrayList<>();
        String linha;

        while ((linha = br.readLine()) != null){
            String[] partes = linha.split(";");
            if(partes.length == 6){
                int id = Integer.parseInt(partes[0]);
                String nome = partes[1];
                String genero = partes[2];
                String artista = partes[3];
                double duracao = Double.parseDouble(partes[4]);
                String caminho = partes[5];
                musicas.add(new Musica(nome, duracao, caminho, artista, genero));
            }
        }

        br.close();
        return musicas;
    }
}
