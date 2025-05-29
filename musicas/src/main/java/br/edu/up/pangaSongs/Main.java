package br.edu.up.pangaSongs;

import br.edu.up.pangaSongs.controller.*;
import br.edu.up.pangaSongs.models.*;
import br.edu.up.pangaSongs.archives.*;
import br.edu.up.pangaSongs.util.ScannerUtil;
import br.edu.up.pangaSongs.views.*;
import org.apache.logging.log4j.*;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Logger logger = LogManager.getLogger(Main.class);

        logger.info("Iniciando aplicação...");

        carregarArquivos(logger);
        logger.info("Arquivos carregados com sucesso!");

        PrincipalView.menu();

        salvarArquivos();

        ScannerUtil.fechaScanner();

        logger.info("Encerrando aplicação...");
    }

    private static void salvarArquivos() {
        ArquivoMusica.salvar(MusicaController.getMusicas());
        ArquivoPlaylist.salvar(PlaylistController.getPlaylists());
        ArquivoPlaylistMusica.salvar(PlaylistMusicaController.getIds());
    }

    private static void carregarArquivos(Logger logger) {
        List<Musica> musicasCarregadas = ArquivoMusica.carregar();
        List<Playlist> playlistsCarregadas = ArquivoPlaylist.carregar();
        List<PlaylistMusica> idsCarregados = ArquivoPlaylistMusica.carregar();

        if(musicasCarregadas != null && playlistsCarregadas != null && idsCarregados != null) {
            for (Musica m : musicasCarregadas) {
                MusicaController.adicionarMusica(m);
            }


            for (Playlist p : playlistsCarregadas) {
                PlaylistController.adicionarPlaylist(p);
            }


            for (PlaylistMusica id : idsCarregados) {
                Playlist p = PlaylistController.buscarPlaylistId(id.getIdPlaylist());
                Musica m = MusicaController.buscarMusicaId(id.getIdMusica());

                if (p != null && m != null) {
                    PlaylistMusicaController.adicionarMusicaNaPlaylist(m.getId(), p.getId());
                    PlaylistController.adicionarMusicaNaPlaylist(p, m);
                } else {
                    System.out.println("Erro, Id cadastrado mas música ou playlist não.");
                    logger.error("Erro, Id cadastrado mas música ou playlist não.");
                }
            }
        }
    }
}