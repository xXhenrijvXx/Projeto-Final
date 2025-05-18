package br.edu.up.cs.musicplayer.archives;

import br.edu.up.cs.musicplayer.model.PlaylistMusica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoPlaylistMusica {
    private static final String caminhoArquivo = "data/musica_playlist.txt";

    public static void salvar(List<PlaylistMusica> ids) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));
        for (PlaylistMusica pm : ids) {
            bw.write(pm.getIdMusica() + ";" + pm.getIdPlaylist());
            bw.newLine();
        }
        bw.close();
    }

    public static List<PlaylistMusica> carregar() throws IOException {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            arquivo.getParentFile().mkdirs();
            arquivo.createNewFile();
            return new ArrayList<>();
        }

        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        List<PlaylistMusica> ids = new ArrayList<>();
        String linha;

        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split(";");
            if (partes.length == 2) {
                PlaylistMusica pm = new PlaylistMusica(partes[0], partes[1]);
                ids.add(pm);
            }
        }

        br.close();
        return ids;
    }
}
