package br.edu.up.cs.musicplayer.app;

import br.edu.up.cs.musicplayer.archives.ArquivoPlaylistMusica;
import br.edu.up.cs.musicplayer.controller.MusicaController;
import br.edu.up.cs.musicplayer.controller.PlaylistController;
import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;
import br.edu.up.cs.musicplayer.archives.ArquivoMusica;
import br.edu.up.cs.musicplayer.archives.ArquivoPlaylist;
import br.edu.up.cs.musicplayer.view.MusicaView;
import br.edu.up.cs.musicplayer.view.PlaylistView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        MusicaController mc = new MusicaController();
        PlaylistController pc = new PlaylistController();
        MusicaView mv = new MusicaView(mc);
        PlaylistView pv = new PlaylistView(pc, mc);

        List<Musica> musicasCarregadas = ArquivoMusica.carregar();
        for (Musica m : musicasCarregadas) {
            mc.adicionarMusica(m);
        }

        List<Playlist> playlistsCarregadas = ArquivoPlaylist.carregar();
        for (Playlist p : playlistsCarregadas) {
            pc.adicionarPlaylist(p);
        }
        
        List<ArquivoPlaylistMusica> idsCarregados = ArquivoPlaylistMusica.carregar();
        for(ArquivoPlaylistMusica pm : idsCarregados){
            Playlist p = pc.buscarPlaylistId(pm.getIdPlaylist());
            Musica m = mc.buscarMusicaId(pm.getIdMusica());
            
            pc.adicionarMusicaNaPlaylist(p.getNome(), m);
        }

        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nMenu Principal\n1. Gerenciar Músicas\n2. Gerenciar Playlists\n0. Sair\nEscolha uma Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> mv.menu();
                case 2 -> pv.menu();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opcção inválida!");

            }
        } while (opcao != 0);

        ArquivoMusica.salvar(mc.getMusicas());
        ArquivoPlaylist.salvar(pc.getPlaylists());
        ArquivoPlaylistMusica.salvar(pc.getIds());
    }
}