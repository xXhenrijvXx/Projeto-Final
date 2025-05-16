package br.edu.up.cs.musicplayer.archives;

import br.edu.up.cs.musicplayer.model.Musica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoMusica {
    private static final String CAMINHO_ARQUIVO = "data/musicas.txt";

    public static void salvar(List<Musica> musicas) throws IOException {
        FileWriter fw = new FileWriter(CAMINHO_ARQUIVO);
        BufferedWriter bw = new BufferedWriter(fw);

        for (Musica m : musicas) {
            bw.write(m.getId() + ";" + m.getNome() + ";" + m.getDuracao() + ";" + m.getCaminhoArquivo() + ";" + m.getGenero() + ";" + m.getArtista());
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    public static List<Musica> carregar() throws IOException {
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) {
            arquivo.getParentFile().mkdirs();
            arquivo.createNewFile();
            return new ArrayList<>();
        }

        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        List<Musica> musicas = new ArrayList<>();
        String linha;

        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split(";");
            if (partes.length == 6) {
                String id = partes[0];
                String nome = partes[1];
                double duracao = Double.parseDouble(partes[2]);
                String caminho = partes[3];
                String artista = partes[4];
                String genero = partes[5];
                musicas.add(new Musica(id, nome, duracao, caminho, artista, genero));
            }
        }

        br.close();
        return musicas;
    }
}
