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
            bw.write(m.getNome() + ";" + m.getDuracao() + ";" + m.getCaminhoArquivo());
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
            if(partes.length == 3){
                String nome = partes[0];
                double duracao = Double.parseDouble(partes[1]);
                String caminho = partes[2];
                musicas.add(new Musica(nome, duracao, caminho));
            }
        }

        br.close();
        return musicas;
    }
}
