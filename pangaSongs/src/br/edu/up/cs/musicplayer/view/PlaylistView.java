package br.edu.up.cs.musicplayer.view;

import br.edu.up.cs.musicplayer.controller.MusicaController;
import br.edu.up.cs.musicplayer.controller.PlaylistController;
import br.edu.up.cs.musicplayer.controller.PlaylistMusicaController;
import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.model.Playlist;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class PlaylistView {
    private PlaylistController controller;
    private MusicaController musicaController;
    private PlaylistMusicaController playlistMusicaController;
    private Scanner sc = new Scanner(System.in);

    public PlaylistView(PlaylistController controller, MusicaController musicaController, PlaylistMusicaController playlistMusicaController) {
        this.controller = controller;
        this.musicaController = musicaController;
        this.playlistMusicaController = playlistMusicaController;
    }

    public void menu() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        int opcao;
        do{
            System.out.println("\nGerenciar Playlists\n1. Criar playlist\n2. Adicionar música em playlist\n3. Remover playlist\n4. Listar playlists\n5. Reproduzir playlist\n0. Voltar\nEscolha: ");
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
        Playlist p = new Playlist(UUID.randomUUID().toString(), nome);
        controller.adicionarPlaylist(p);
        System.out.println("Playlist criada!");
        esperarEnter();
    }

    public static void esperarEnter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    private void adicionarMusica(){
        System.out.println("Nome da playlist: ");
        String nomePlaylist = sc.nextLine();
        System.out.println("Nome da música a adicionar: ");
        String nomeMusica = sc.nextLine();

        Musica musica = musicaController.buscarMusicaNome(nomeMusica);
        if(musica != null){
            String playlistId = controller.adicionarMusicaNaPlaylist(nomePlaylist, musica);
            if(playlistId != null){
                playlistMusicaController.adicionarMusicaNaPlaylist(musica.getId(), playlistId);
            }
            System.out.println("Música adicionada!");
            esperarEnter();
        }
    }

    private void removerPlaylist() throws IOException {
        System.out.println("Nome da playlist a remover: ");
        String nome = sc.nextLine();
        controller.removerPlaylist(nome);
        esperarEnter();
    }

    public PlaylistController getController() {
        return controller;
    }

    private void reproduzirPlaylist() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        System.out.println("Nome da playlist a reproduzir: ");
        String nome = sc.nextLine();
        Playlist playlist = controller.buscarPlaylist(nome);
        if(playlist != null){
            playlist.reproduzir();
        } else{
            System.out.println("Playlist " + nome + " não encontrada!");
        }
        esperarEnter();
    }
}
