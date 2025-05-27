package br.edu.up.pangaSongs.views;

import br.edu.up.pangaSongs.controller.MusicaController;
import br.edu.up.pangaSongs.controller.PlaylistController;
import br.edu.up.pangaSongs.models.Musica;
import br.edu.up.pangaSongs.models.Playlist;
import br.edu.up.pangaSongs.util.ScannerUtil;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.UUID;

public class PlaylistView {

    private PlaylistView(){}

    public static void menu() throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
        int opcao;
        do{
            System.out.println("\n***** Gerenciar Playlists *****\n\n1. Criar playlist\n2. Remover playlist\n3. Editar playlist\n4. Listar playlists\n5. Reproduzir playlist\n6. Listar músicas de uma playlist\n0. Voltar");
            System.out.print("\nEscolha uma Opção: ");
            opcao = ScannerUtil.getScanner().nextInt();
            ScannerUtil.getScanner().nextLine();

            switch (opcao){
                case 1 -> criarPlaylist();
                case 2 -> removerPlaylist();
                case 3 -> editarPlaylist();
                case 4 -> {
                    PlaylistController.listarPlaylists();
                    esperarEnter();
                }
                case 5 -> reproduzirPlaylist();
                case 6 -> listarMusicasPlaylist();
                case 0 -> {}
                default -> System.out.println("\nOpção inválida!");
            }
        } while (opcao != 0);
    }

    private static void editarPlaylist(){
        System.out.println("Nome da playlist a editar: ");
        String nome = ScannerUtil.getScanner().nextLine().trim().toLowerCase();
        Playlist p = PlaylistController.buscarPlaylist(nome);
        if(p != null){
            int opcao;
            do {
                System.out.println("\nEditar playlist - " + p.getNome() + "\n1. Editar nome\n2. Adicionar música\n3. Remover música\n0. Voltar\nEscolha: ");
                opcao = ScannerUtil.getScanner().nextInt();
                ScannerUtil.getScanner().nextLine();

                switch (opcao){
                    case 1 -> PlaylistController.editarNome(p);
                    case 2 -> {
                        System.out.println("Nome da música a adicionar: ");
                        Musica m = MusicaController.buscarMusicaNome(ScannerUtil.getScanner().nextLine());
                        PlaylistController.adicionarMusicaNaPlaylist(p, m);
                    }
                    case 3 -> {
                        System.out.println("Nome da música a remover: ");
                        Musica m = MusicaController.buscarMusicaNome(ScannerUtil.getScanner().nextLine());
                        PlaylistController.removerMusicaDaPlaylist(p, m);
                    }
                    case 0 -> {}
                    default -> System.out.println("Opção inválida!");
                }
            }while(opcao != 0);
        }else{
            System.out.println("Playlist não encontrada!");
        }
        esperarEnter();
    }

    private static void listarMusicasPlaylist(){
        System.out.println("Nome da playlist: ");

        Playlist playlist = PlaylistController.buscarPlaylist(ScannerUtil.getScanner().nextLine());
        if(playlist != null){
            System.out.println("Playlist - " + playlist.getNome());
            PlaylistController.listarMusicas(playlist);
            System.out.printf("\nTotal: %d músicas\nDuração total: %.2f min\n", playlist.getMusicas().size(), playlist.getDuracao()/60);
        }else{
            System.out.println("Playlist não encontrada!");
        }
        esperarEnter();
    }

    private static void criarPlaylist() throws IOException {
        System.out.println("Nome da nova playlist: ");
        String nome = ScannerUtil.getScanner().nextLine();

        Playlist playlist = PlaylistController.buscarPlaylist(nome);
        if(playlist == null){
            Playlist p = new Playlist(UUID.randomUUID().toString(), nome);
            PlaylistController.adicionarPlaylist(p);
            System.out.println("Playlist criada!");
        }else{
            System.out.println("Playlist já cadastrada!");
        }
        esperarEnter();
    }

    public static void esperarEnter() {
        System.out.println("Pressione ENTER para continuar...");
        ScannerUtil.getScanner().nextLine();
    }

    private static void removerPlaylist() throws IOException {
        System.out.println("Nome da playlist a remover: ");
        String nome = ScannerUtil.getScanner().nextLine();
        PlaylistController.removerPlaylist(nome);
        esperarEnter();
    }

    private static String menuReproducao(Musica musica, boolean isIndexZero, boolean isLastIndex) throws InterruptedException {
        String opcao;

        Thread thread = new Thread(() -> {
            try {
                musica.reproduzir();
            } catch (Exception e) {
                System.out.println("Erro ao tocar: " + e.getMessage());
                musica.parar();
            }
        });
        thread.start();

        do {

            if (!isIndexZero) System.out.print("[b] Back | ");

            if (!musica.isPausada()) System.out.print("[p] Pausar | ");
            else System.out.print("[c] Continuar | ");

            if (!isLastIndex) System.out.print("[n] Next | ");
            System.out.print("[s] Stop\nDigite: ");

            opcao = ScannerUtil.getScanner().nextLine().trim().toLowerCase();

            switch (opcao) {
                case "p" -> { if (!musica.isPausada()) musica.pausar(); } //tava bugando
                case "c" -> { if (musica.isPausada()) musica.continuar(); }
                case "b", "n", "s" -> musica.parar();
                default -> System.out.println("Opção inválida!");
            }
        } while (!("b".equals(opcao) || "n".equals(opcao) || "s".equals(opcao)) && !musica.isFinalizada());

        thread.join();

        return opcao;
    }

    private static void reproduzirPlaylist() throws IOException, InterruptedException {
        System.out.println("Nome da playlist a reproduzir: ");
        String nome = ScannerUtil.getScanner().nextLine();

        Playlist playlist = PlaylistController.buscarPlaylist(nome);

        if(playlist != null){
            System.out.println("Tocando playlist: " + playlist.getNome());

            int idx = 0;

            while (idx >= 0 && idx < playlist.getMusicas().size()) {
                Musica m = playlist.getMusicas().get(idx);
                System.out.println("► Tocando: " + m.getNome() + " de " + m.getArtista());

                String cmd = menuReproducao(m, (idx == 0), (idx == (playlist.getMusicas().size())-1));

                switch (cmd) {
                    case "n" -> idx++;
                    case "b" -> idx = Math.max(0, idx - 1);
                    case "s" -> {
                        return;
                    }
                    default -> {}
                }
            }
        } else{
            System.out.println("Playlist " + nome + " não encontrada!");
            esperarEnter();
        }
    }
}
