package br.edu.up.pangaSongs;

import br.edu.up.pangaSongs.controller.*;
import br.edu.up.pangaSongs.models.*;
import br.edu.up.pangaSongs.archives.*;
import br.edu.up.pangaSongs.util.ScannerUtil;
import br.edu.up.pangaSongs.views.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Logger logger = LogManager.getLogger(PrincipalView.class);

        logger.info("Iniciando aplicação...");

        try{
            carregarArquivos();
            logger.info("Arquivos carregados com sucesso!");
        } catch (Exception e) {
            logger.error("Erro ao carregar arquivos: ", e);
        }

        try {
            PrincipalView.menu();
        } catch (Exception e) {
            logger.error("Erro durante a execução do menu principal: ", e);
        }

        try {
            salvarArquivos();
        } catch (Exception e) {
            logger.error("Erro ao salvar arquivos: ", e);
        }
        ScannerUtil.fechaScanner();

        logger.info("Encerrando aplicação...");
    }

    private static void salvarArquivos() throws IOException {
        ArquivoMusica.salvar(MusicaController.getMusicas());
        ArquivoPlaylist.salvar(PlaylistController.getPlaylists());
        ArquivoPlaylistMusica.salvar(PlaylistMusicaController.getIds());
    }

    private static void carregarArquivos() throws IOException {
        List<Musica> musicasCarregadas = ArquivoMusica.carregar();
        for (Musica m : musicasCarregadas) {
            MusicaController.adicionarMusica(m);
        }

        List<Playlist> playlistsCarregadas = ArquivoPlaylist.carregar();
        for (Playlist p : playlistsCarregadas) {
            PlaylistController.adicionarPlaylist(p);
        }

        List<PlaylistMusica> idsCarregados = ArquivoPlaylistMusica.carregar();
        for(PlaylistMusica id : idsCarregados){
            Playlist p = PlaylistController.buscarPlaylistId(id.getIdPlaylist());
            Musica m = MusicaController.buscarMusicaId(id.getIdMusica());

            PlaylistMusicaController.adicionarMusicaNaPlaylist(m.getId(), p.getId());
            PlaylistController.adicionarMusicaNaPlaylist(p, m);
        }
    }
}