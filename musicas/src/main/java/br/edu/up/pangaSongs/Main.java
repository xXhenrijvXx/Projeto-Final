package br.edu.up.pangaSongs;

import br.edu.up.pangaSongs.controller.*;
import br.edu.up.pangaSongs.models.*;
import br.edu.up.pangaSongs.archives.*;
import br.edu.up.pangaSongs.util.ScannerUtil;
import br.edu.up.pangaSongs.views.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {

        carregarArquivos();

        int opcao;

        do {
            System.out.println("\n***** Menu Principal *****\n\n1. Gerenciar Músicas\n2. Gerenciar Playlists\n0. Sair");
            System.out.print("\nEscolha uma Opção: ");
            opcao = ScannerUtil.getScanner().nextInt();
            ScannerUtil.getScanner().nextLine();

            switch (opcao){
                case 1 -> MusicaView.menuPrincipal();
                case 2 -> PlaylistView.menu();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida!");

            }
        } while (opcao != 0);

        salvarArquivos();
        ScannerUtil.fechaScanner();
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