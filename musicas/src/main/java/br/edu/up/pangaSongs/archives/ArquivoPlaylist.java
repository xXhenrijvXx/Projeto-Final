package br.edu.up.pangaSongs.archives;


import br.edu.up.pangaSongs.models.Playlist;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoPlaylist {
    private static final String caminhoArquivo = "data/playlists.txt";

    public static void salvar(List<Playlist> playlists) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));
        for (Playlist p : playlists) {
            bw.write(p.getId() + ";" + p.getNome());
            bw.newLine();
        }
        bw.close();
    }

    public static List<Playlist> carregar() throws IOException {
        File arquivo = new File(caminhoArquivo);
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
