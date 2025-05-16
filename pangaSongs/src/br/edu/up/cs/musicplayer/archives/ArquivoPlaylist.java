package br.edu.up.cs.musicplayer.archives;

import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoPlaylist {
    private static final String CAMINHO_ARQUIVO = "data/playlists.txt";

    public static void salvar(List<Playlist> playlists) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO));
        for (Playlist p : playlists) {
            bw.write(p.getId() + ";" + p.getNome());
            bw.newLine();
        }
        bw.close();
    }

    public static List<Playlist> carregar() throws IOException {
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) {
            arquivo.getParentFile().mkdirs();
            arquivo.createNewFile();
            return new ArrayList<>();
        }

        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        List<Playlist> playlists = new ArrayList<>();
        String linha;

        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split(";");
            if (partes.length == 2) {
                Playlist p = new Playlist(partes[0], partes[1]);
                playlists.add(p);
            }
        }

        br.close();
        return playlists;
    }
}
