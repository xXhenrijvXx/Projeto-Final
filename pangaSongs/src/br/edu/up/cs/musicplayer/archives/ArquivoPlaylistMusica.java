package br.edu.up.cs.musicplayer.archives;

import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArquivoPlaylistMusica {
    private static final String caminhoArquivo = "data/playlist_musica.txt";

    public static void salvar(List<Playlist> playlists) throws IOException {
        File arquivo = new File(caminhoArquivo);
        BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));
        for(Playlist p : playlists){
            for(Musica m : p.getMusicas()){
                bw.write(p.getId() + m.getId());
                bw.newLine();
            }
        }
    }

    public static void carregar(List<Playlist> playlists, List<Musica> musicas) throws IOException {
        File arquivo = new File(caminhoArquivo);
        if(!arquivo.exists()) {
            arquivo.getParentFile().mkdirs();
            arquivo.createNewFile();
            return;
        }

        Map<Integer, Playlist> mapP = new HashMap<>();
        for(Playlist p : playlists){
            mapP.put(p.getId(), p);
        }

        Map<Integer, Musica> mapM = new HashMap<>();
        for(Musica m : musicas){
            mapM.put(m.getId(), m);
        }

        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        String linha;

        while((linha = br.readLine()) != null){
            String[] p = linha.split(";");
            int pid = Integer.parseInt(p[0]);
            int mid = Integer.parseInt(p[1]);
            Playlist pl = mapP.get(pid);
            Musica mu = mapM.get(mid);
            if (pl != null && mu != null) {
                pl.adicionarMusica(mu);
            }
        }
    }
}
