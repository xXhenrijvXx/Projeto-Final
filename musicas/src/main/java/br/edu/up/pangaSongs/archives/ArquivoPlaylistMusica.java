package br.edu.up.pangaSongs.archives;

import br.edu.up.pangaSongs.models.PlaylistMusica;
import org.apache.logging.log4j.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoPlaylistMusica {
    private static final String caminhoArquivo = "data/musica_playlist.txt";
    private static final Logger logger = LogManager.getLogger(ArquivoPlaylistMusica.class);

    private ArquivoPlaylistMusica(){}

    public static void salvar(List<PlaylistMusica> ids) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));
            for (PlaylistMusica pm : ids) {
                bw.write(pm.getIdMusica() + ";" + pm.getIdPlaylist());
                bw.newLine();
            }
            bw.close();
        }catch (IOException e){
            System.out.println("Erro ao salvar o arquivo de músicas.");
            logger.error("Erro ao salvar o arquivo de músicas: ", e);
        }
    }

    public static List<PlaylistMusica> carregar() {
        File arquivo = new File(caminhoArquivo);
        List<PlaylistMusica> ids = new ArrayList<>();

        if (!arquivo.exists()) {
            arquivo.getParentFile().mkdirs();
            try {
                arquivo.createNewFile();
            }catch (IOException e){
                System.out.println("Erro ao salvar o arquivo de ids.");
                logger.error("Erro ao salvar o arquivo de ids: ", e);
                return null;
            }
            return ids;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    PlaylistMusica pm = new PlaylistMusica(partes[0], partes[1]);
                    ids.add(pm);
                }
            }

            br.close();
        }catch (IOException e){
            System.out.println("Erro ao ler o arquivo de ids");
            logger.error("Erro ao ler o arquivo de ids: ", e);
            return null;
        }
        return ids;
    }
}
