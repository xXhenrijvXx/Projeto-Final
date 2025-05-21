package br.edu.up.cs.musicplayer.view;

import br.edu.up.cs.musicplayer.controller.MusicaController;
import br.edu.up.cs.musicplayer.controller.PlaylistController;
import br.edu.up.cs.musicplayer.controller.PlaylistMusicaController;
import br.edu.up.cs.musicplayer.model.Musica;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class MusicaView {
    private MusicaController controller;
    private PlaylistMusicaController pmc;
    private Scanner sc = new Scanner(System.in);

    public MusicaView(MusicaController controller, PlaylistMusicaController pmc){
        this.controller = controller;
        this.pmc = pmc;
    }

    public void menu(PlaylistController pc) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        int opcao;
        do{
            System.out.println("\nGerenciar Músicas\n1. Adicionar música\n2. Remover música\n3. Editar música\n4. Listar músicas\n5. Tocar música\n0. Voltar\nEscolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> adicionarMusica();
                case 2 -> removerMusica(pc);
                case 3 -> editarMusica();
                case 4 -> controller.listarMusicas();
                case 5 -> tocarMusica();
                case 0 -> {}
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    public MusicaController getController() {
        return controller;
    }

    private void tocarMusica() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        System.out.println("Nome da musica a tocar: ");
        String nome = sc.nextLine();

        MusicPlayerView mpv = new MusicPlayerView();
        mpv.menu(controller.buscarMusicaNome(nome).getCaminhoArquivo());

    }

    private void adicionarMusica() throws IOException {
        System.out.println("Nome da música: ");
        String nome = sc.nextLine();
        System.out.println("Caminho do arquivo: ");
        String caminho = sc.nextLine();
        if(caminho.endsWith(".wav")){
            System.out.println("Gênero da música: ");
            String genero = sc.nextLine();
            System.out.println("Artista: ");
            String artista = sc.nextLine();

            Musica musica = new Musica(UUID.randomUUID().toString(), nome, caminho, artista, genero);
            controller.adicionarMusica(musica);
            System.out.println("Música adicionada!");
            esperarEnter();
            return;
        }

        System.out.println("Tipo de arquivo não suportado, música não cadastrada.");
        esperarEnter();
    }

    public static void esperarEnter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    private void removerMusica(PlaylistController pc) throws IOException {
        System.out.println("Nome da música a remover: ");
        String nome = sc.nextLine().trim().toLowerCase();
        pmc.removerMusicaDasPlaylists(controller.buscarMusicaNome(nome), pc.getPlaylists());
        controller.removerMusica(nome);
        System.out.println("Música removida!");
        esperarEnter();
    }

    private void editarMusica(){
        System.out.println("Nome da música a editar: ");
        String nome = sc.nextLine().trim().toLowerCase();
        controller.editarMusica(nome);
        esperarEnter();
    }
}
