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

        Musica.carregarUltimoId("data/id_musica.txt");
        List<Musica> listaMusicas = ArquivoMusica.carregar();
        List<Playlist> listaPl = ArquivoPlaylist.carregar();
        ArquivoPlaylistMusica.carregar(listaPl, listaMusicas);

        for (Musica m : listaMusicas) mc.adicionarMusica(m);
        for (Playlist p : listaPl) pc.adicionarPlaylist(p);


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
                default -> System.out.println("Opção inválida!");

            }
        } while (opcao != 0);

        ArquivoMusica.salvar(mc.getMusicas());
        Musica.salvarUltimoId("data/id_musica.txt");
        ArquivoPlaylist.salvar(pc.getPlaylists());
        ArquivoPlaylistMusica.salvar(pc.getPlaylists());
    }
}
