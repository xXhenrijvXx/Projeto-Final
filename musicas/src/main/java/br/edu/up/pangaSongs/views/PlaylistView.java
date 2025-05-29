package br.edu.up.pangaSongs.views;

import br.edu.up.pangaSongs.controller.MusicaController;
import br.edu.up.pangaSongs.controller.PlaylistController;
import br.edu.up.pangaSongs.models.Musica;
import br.edu.up.pangaSongs.models.Playlist;
import br.edu.up.pangaSongs.util.AguardarUtil;
import br.edu.up.pangaSongs.util.ScannerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.UUID;

public class PlaylistView {

    private static final Logger logger = LogManager.getLogger(PlaylistView.class);

    private PlaylistView(){}

    public static void menu() {
        String opcao;
        logger.info("Menu de playlists iniciado.");
        do{
            System.out.println("\n***** Gerenciar Playlists *****\n\n1. Criar playlist\n2. Remover playlist\n3. Editar playlist\n4. Listar playlists\n5. Reproduzir playlist\n6. Listar músicas de uma playlist\n0. Voltar");
            System.out.print("\nEscolha uma Opção: ");

            opcao = ScannerUtil.getScanner().nextLine();

            logger.info("Opção selecionada: {}", opcao);

            switch (opcao){
                case "1" -> criarPlaylist();
                case "2" -> removerPlaylist();
                case "3" -> editarPlaylist();
                case "4" -> {
                    logger.info("Listando playlists.");
                    PlaylistController.listarPlaylists();
                    AguardarUtil.esperarEnter();
                }
                case "5" -> reproduzirPlaylist();
                case "6" -> listarMusicasPlaylist();
                case "0" -> {}
                default -> {
                    logger.warn("Opção inválida!");
                    System.out.println("\nOpção inválida!");
                }
            }
        } while (opcao != "0");
        logger.info("Menu de playlists encerrado.");
    }

    private static void criarPlaylist() {
        System.out.println("Nome da nova playlist: ");
        String nome = ScannerUtil.getScanner().nextLine();

        Playlist p = PlaylistController.buscarPlaylist(nome);
        if(p == null){
            p = new Playlist(UUID.randomUUID().toString(), nome);
            PlaylistController.adicionarPlaylist(p);
            System.out.println("Playlist criada!");
            logger.info("Playlist criada: {}", p.getNome());
        }else{
            System.out.println("Playlist já cadastrada!");
            logger.warn("Playlist ja existente: {}", nome);
        }
        AguardarUtil.esperarEnter();
    }

    private static void removerPlaylist() {
        System.out.println("Nome da playlist a remover: ");
        String nome = ScannerUtil.getScanner().nextLine();
        PlaylistController.removerPlaylist(nome);
        AguardarUtil.esperarEnter();
    }

    private static void editarPlaylist(){
        System.out.println("Nome da playlist a editar: ");
        String nome = ScannerUtil.getScanner().nextLine().trim();
        Playlist p = PlaylistController.buscarPlaylist(nome);

        if(p != null){
            logger.info("Editando a playlist: {}", p.getNome());
            String opcao;
            do {
                System.out.println("\nEditar playlist - " + p.getNome() + "\n1. Editar nome\n2. Adicionar música\n3. Remover música\n0. Voltar\nEscolha: ");
                opcao = ScannerUtil.getScanner().nextLine();

                logger.info("Opção selecionada: {}", opcao);

                switch (opcao){
                    case "1" -> PlaylistController.editarNome(p);
                    case "2" -> {
                        System.out.println("Nome da música a adicionar: ");
                        Musica m = MusicaController.buscarMusicaNome(ScannerUtil.getScanner().nextLine());
                        PlaylistController.adicionarMusicaNaPlaylist(p, m);
                    }
                    case "3" -> {
                        System.out.println("Nome da música a remover: ");
                        Musica m = MusicaController.buscarMusicaNome(ScannerUtil.getScanner().nextLine());
                        PlaylistController.removerMusicaDaPlaylist(p, m);
                    }
                    case "0" -> {}
                    default -> {
                        logger.warn("Opção inválida!");
                        System.out.println("Opção inválida!");
                    }
                }
            }while(opcao != "0");
        }else{
            System.out.println("Playlist não encontrada!");
        }
        AguardarUtil.esperarEnter();
    }

    private static void reproduzirPlaylist() {
        System.out.println("Nome da playlist a reproduzir: ");
        String nome = ScannerUtil.getScanner().nextLine();

        Playlist playlist = PlaylistController.buscarPlaylist(nome);

        if(playlist != null){
            logger.info("Reproduzindo playlist: {}", playlist.getNome());
            System.out.println("Reproduzindo playlist: " + playlist.getNome());

            int idx = 0;

            while (idx >= 0 && idx < playlist.getMusicas().size()) {
                Musica m = playlist.getMusicas().get(idx);
                System.out.println("► Tocando: " + m.getNome() + " de " + m.getArtista());

                String cmd = menuReproducao(m, (idx == 0), (idx == (playlist.getMusicas().size())-1));

                switch (cmd) {
                    case "n" -> {
                        idx++;
                        logger.info("Próxima música");
                    }
                    case "b" -> {
                        logger.info("Música anterior");
                        idx = Math.max(0, idx - 1);
                    }
                    case "s" -> {
                        logger.info("Encerrando reprodução da playlist.");
                        return;
                    }
                    default -> {}
                }
            }
        } else{
            System.out.println("Playlist " + nome + " não encontrada!");
            AguardarUtil.esperarEnter();
        }
    }

    private static void listarMusicasPlaylist(){
        System.out.println("Nome da playlist: ");

        Playlist playlist = PlaylistController.buscarPlaylist(ScannerUtil.getScanner().nextLine());
        if(playlist != null){
            logger.info("Listando músicas da playlist: {}", playlist.getNome());
            System.out.println("Playlist - " + playlist.getNome());
            PlaylistController.listarMusicas(playlist);
            System.out.printf("\nTotal: %d músicas\nDuração total: %.2f min\n", playlist.getMusicas().size(), playlist.getDuracao()/60);
        }else{
            System.out.println("Playlist não encontrada!");
        }
        AguardarUtil.esperarEnter();
    }

    private static String menuReproducao(Musica musica, boolean isIndexZero, boolean isLastIndex) {
        String opcao;

        Thread thread = new Thread(() -> {
            try {
                logger.info("Reproduzindo música: {}", musica.getNome());
                musica.reproduzir();
            } catch (Exception e) {
                logger.error("Erro ao reproduzir música '{}':", musica.getNome(), e);
                System.out.println("Erro ao reproduzir música: " + e.getMessage());
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
                case "p" -> {
                    if (!musica.isPausada()){
                        musica.pausar();
                        logger.info("Música pausada.");
                    }
                }
                case "c" -> {
                    if (musica.isPausada()){
                        musica.continuar();
                        logger.info("Continuando música.");
                    }
                }
                case "b", "n", "s" -> musica.parar();
                default -> {
                    logger.info("Opção inválida!");
                    System.out.println("Opção inválida!");
                }
            }
        } while (!("b".equals(opcao) || "n".equals(opcao) || "s".equals(opcao)) && !musica.isFinalizada());

        try{
            thread.join();
        }catch (InterruptedException e){
            logger.error("Erro ao finalizar thread de reprodução: ", e);
            Thread.currentThread().interrupt();
        }

        return opcao;
    }
}
