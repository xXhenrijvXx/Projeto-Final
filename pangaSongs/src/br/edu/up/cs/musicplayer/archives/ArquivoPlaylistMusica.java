package br.edu.up.cs.musicplayer.archives;

import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoPlaylistMusica {
    private String idMusica;
    private String idPlaylist;
    private static final String caminhoArquivo = "data/musica_playlist.txt";

    public ArquivoPlaylistMusica(String idMusica, String idPlaylist) {
        this.idMusica = idMusica;
        this.idPlaylist = idPlaylist;
    }

    public void setIdMusica(String idMusica) {
        this.idMusica = idMusica;
    }

    public void setIdPlaylist(String idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getIdMusica() {
        return idMusica;
    }

    public String getIdPlaylist() {
        return idPlaylist;
    }

    public static void salvar(List<ArquivoPlaylistMusica> ids) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));
        for (ArquivoPlaylistMusica pm : ids) {
            bw.write(pm.getIdMusica() + ";" + pm.getIdPlaylist());
            bw.newLine();
        }
        bw.close();
    }

    public static List<ArquivoPlaylistMusica> carregar() throws IOException {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            arquivo.getParentFile().mkdirs();
            arquivo.createNewFile();
            return new ArrayList<>();
        }

        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        List<ArquivoPlaylistMusica> ids = new ArrayList<>();
        String linha;

        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split(";");
            if (partes.length == 2) {
                ArquivoPlaylistMusica pm = new ArquivoPlaylistMusica(partes[0], partes[1]);
                ids.add(pm);
            }
        }

        br.close();
        return ids;
    }
}
