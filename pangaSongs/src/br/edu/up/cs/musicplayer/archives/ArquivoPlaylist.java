package br.edu.up.cs.musicplayer.archives;

import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoPlaylist {
    private static final String arquivoPath = "data/playlist.txt";

    public static void salvar(List<Playlist> playlists) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoPath));
        for(Playlist p : playlists){
            StringBuilder sb = new StringBuilder(p.getNome());
            for(Musica m : p.getMusicas()){
                sb.append(";").append(m.getNome());
            }
            bw.write(sb.toString());
            bw.newLine();
        }
        bw.close();
    }

    public static List<Playlist> carregar(List<Musica> musicasDisponiveis) throws IOException {
        File arquivo = new File(arquivoPath);
        if(!arquivo.exists()){
            arquivo.getParentFile().mkdirs();
            arquivo.createNewFile();
            return new ArrayList<>();
        }

        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        List<Playlist> playlists = new ArrayList<>();
        String linha;

        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split(";");
            if (partes.length >= 1) {
                Playlist p = new Playlist(partes[0]);

                for (int i = 1; i < partes.length; i++) {
                    for (Musica m : musicasDisponiveis) {
                        if (m.getNome().equalsIgnoreCase(partes[i])) {
                            p.adicionarMusica(m);
                            break;
                        }
                    }
                }

                playlists.add(p);
            }
        }

        br.close();
        return playlists;
    }
}
