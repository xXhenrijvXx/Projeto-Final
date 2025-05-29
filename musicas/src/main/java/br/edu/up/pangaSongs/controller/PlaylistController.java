package br.edu.up.pangaSongs.controller;

import br.edu.up.pangaSongs.models.Musica;
import br.edu.up.pangaSongs.models.Playlist;
import br.edu.up.pangaSongs.util.ScannerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

public class PlaylistController {
    private static final List<Playlist> playlists = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(PlaylistController.class);

    private PlaylistController(){}

    public static void adicionarPlaylist(Playlist playlist){
        playlists.add(playlist);
    }

    public static void removerPlaylist(String nome){
        if (playlists.removeIf(p -> p.getNome().equalsIgnoreCase(nome))) {
            System.out.println("Playlist removida!");
            logger.info("Playlist removida: {}", nome);
        } else {
            System.out.println("Playlist não encontrada.");
            logger.warn("Playlist não encontrada.");
        }
    }

    public static void editarNome(Playlist p){
        logger.info("Alterando nome da playlist: {}", p.getNome());
        System.out.println("Digite o novo nome: ");
        p.setNome(ScannerUtil.getScanner().nextLine());
    }

    public static void listarMusicas(Playlist p){
        for(Musica m : p.getMusicas()){
            System.out.println("Música: " + m.getNome());
        }
    }

    public static void listarPlaylists(){
        for (Playlist p : playlists) {
            System.out.println("📁 Playlist: " + p.getNome());
        }
    }

    public static Playlist buscarPlaylist(String nome){
        for (Playlist p : playlists){
            if (p.getNome().equalsIgnoreCase(nome)) return p;
        }
        logger.warn("Playlist não encontrada!");
        return null;
    }

    public static Playlist buscarPlaylistId(String id){
        for (Playlist p : playlists){
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        return null;
    }

    public static List<Playlist> getPlaylists(){
        return playlists;
    }

    public static void removerMusicaDaPlaylist(Playlist playlist, Musica musica){
        if(musica != null){
            playlist.removerMusica(musica);
            PlaylistMusicaController.removerMusicaDaPlaylist(musica, playlist);
            logger.info("Música '{}' removida da playlist '{}'!", musica.getNome(), playlist.getNome());
        }else{
            System.out.println("Música não encontrada");
        }
    }

    public static void adicionarMusicaNaPlaylist(Playlist playlist, Musica musica){
        if(musica != null){
            playlist.adicionarMusica(musica);
        }else{
            System.out.println("Música não encontrada");
        }
    }
}
