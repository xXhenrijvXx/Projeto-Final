package br.edu.up.pangaSongs.archives;

import br.edu.up.pangaSongs.controller.MusicaController;
import br.edu.up.pangaSongs.controller.PlaylistController;
import br.edu.up.pangaSongs.controller.IdsController;
import br.edu.up.pangaSongs.models.Musica;
import br.edu.up.pangaSongs.models.Playlist;
import br.edu.up.pangaSongs.models.Ids;
import org.apache.logging.log4j.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoIds {
    private static final String caminhoArquivo = "data/musica_playlist.txt";
    private static final Logger logger = LogManager.getLogger(ArquivoIds.class);

    private ArquivoIds(){}

    public static void salvar() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));
            for (Ids pm : IdsController.getIds()) {
                bw.write(pm.getIdMusica() + ";" + pm.getIdPlaylist());
                bw.newLine();
            }
            bw.close();
        }catch (IOException e){
            System.out.println("Erro ao salvar o arquivo de músicas.");
            logger.error("Erro ao salvar o arquivo de músicas: ", e);
        }
    }

    public static void carregar() {
        File arquivo = new File(caminhoArquivo);
        List<Ids> ids = new ArrayList<>();

        if (!arquivo.exists()) {
            arquivo.getParentFile().mkdirs();
            try {
                arquivo.createNewFile();
            }catch (IOException e){
                System.out.println("Erro ao salvar o arquivo de ids.");
                logger.error("Erro ao salvar o arquivo de ids: ", e);
            }
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    Ids pm = new Ids(partes[0], partes[1]);
                    ids.add(pm);
                }
            }

            br.close();

            for (Ids id : ids) {
                Playlist p = PlaylistController.buscarPlaylistId(id.getIdPlaylist());
                Musica m = MusicaController.buscarMusicaId(id.getIdMusica());

                if (p != null && m != null) {
                    IdsController.adicionarMusicaNaPlaylist(m.getId(), p.getId());
                    PlaylistController.adicionarMusicaNaPlaylist(p, m);
                } else {
                    System.out.println("Erro, Id cadastrado mas música ou playlist não.");
                    logger.error("Erro, Id cadastrado mas música ou playlist não.");
                }
            }
        }catch (IOException e){
            System.out.println("Erro ao ler o arquivo de ids");
            logger.error("Erro ao ler o arquivo de ids: ", e);
        }
    }
}
