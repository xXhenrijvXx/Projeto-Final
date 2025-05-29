package br.edu.up.pangaSongs.archives;


import br.edu.up.pangaSongs.models.Playlist;
import org.apache.logging.log4j.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoPlaylist {
    private static final String caminhoArquivo = "data/playlists.txt";
    private static final Logger logger = LogManager.getLogger(ArquivoPlaylist.class);

    private ArquivoPlaylist(){}

    public static void salvar(List<Playlist> playlists) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));
            for (Playlist p : playlists) {
                bw.write(p.getId() + ";" + p.getNome());
                bw.newLine();
            }
            bw.close();
        }catch (IOException e){
            System.out.println("Erro ao salvar o arquivo de playlist.");
            logger.error("Erro ao salvar o arquivo de playlists: ", e);
        }
    }

    public static List<Playlist> carregar() {
        File arquivo = new File(caminhoArquivo);
        List<Playlist> playlists = new ArrayList<>();
        if (!arquivo.exists()) {
            arquivo.getParentFile().mkdirs();
            try {
                arquivo.createNewFile();
            }catch (IOException e){
                System.out.println("Erro ao criar o arquivo de playlist.");
                logger.error("Erro ao criar o arquivo de playlists: ", e);
                return null;
            }
            return playlists;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    Playlist p = new Playlist(partes[0], partes[1]);
                    playlists.add(p);
                }
            }

            br.close();
        }catch (IOException e){
            System.out.println("Erro ao ler o arquivo de playlists");
            logger.error("Erro ao ler o arquivo de playlists: ", e);
            return null;
        }
        return playlists;
    }
}
