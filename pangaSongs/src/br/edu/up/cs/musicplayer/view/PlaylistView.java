package br.edu.up.cs.musicplayer.view;

import br.edu.up.cs.musicplayer.controller.MusicaController;
import br.edu.up.cs.musicplayer.controller.PlaylistController;
import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;

import java.io.IOException;
import java.util.Scanner;

public class PlaylistView {
    private PlaylistController controller;
    private MusicaController musicaController;
    private Scanner sc = new Scanner(System.in);

    public PlaylistView(PlaylistController controller, MusicaController musicaController) {
        this.controller = controller;
        this.musicaController = musicaController;
    }

    public void menu() throws IOException {
        int opcao;
        do{
            System.out.println("\n📁 Gerenciar Playlists\n1. Criar playlist\n2. Adicionar música em playlist\n3. Remover playlist\n4. Listar playlists\n5. Reproduzir playlist\n0. Voltar\nEscolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> criarPlaylist();
                case 2 -> adicionarMusica();
                case 3 -> removerPlaylist();
                case 4 -> controller.listarPlaylists();
                case 5 -> reproduzirPlaylist();
                case 0 -> {}
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void criarPlaylist() throws IOException {
        System.out.println("Nome da nova playlist: ");
        String nome = sc.nextLine();
        Playlist p = new Playlist(nome);
        controller.adicionarPlaylist(p);
    }

    private void adicionarMusica(){
        System.out.println("Nome da playlist: ");
        String nomePlaylist = sc.nextLine();
        System.out.println("Nome da música a adicionar: ");
        String nomeMusica = sc.nextLine();

        Musica musica = musicaController.buscarMusica(nomeMusica);
        if(musica != null){
            controller.adicionarMusicaNaPlaylist(nomePlaylist, musica);
        }
    }

    private void removerPlaylist() throws IOException {
        System.out.println("Nome da playlist a remover: ");
        String nome = sc.nextLine();
        controller.removerPlaylist(nome);
    }

    private void reproduzirPlaylist() throws IOException {
        System.out.println("Nome da playlist a reproduzir: ");
        String nome = sc.nextLine();
        Playlist playlist = controller.buscarPlaylist(nome);
        if(playlist != null){
            playlist.reproduzir();
        }
    }
}
