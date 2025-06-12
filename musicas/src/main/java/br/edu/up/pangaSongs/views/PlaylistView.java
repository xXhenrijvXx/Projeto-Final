package br.edu.up.pangaSongs.views;

import br.edu.up.pangaSongs.controller.MusicaController;
import br.edu.up.pangaSongs.controller.PlaylistController;
import br.edu.up.pangaSongs.models.Musica;
import br.edu.up.pangaSongs.models.Playlist;
import br.edu.up.pangaSongs.util.ConsoleUtil;
import br.edu.up.pangaSongs.util.ScannerUtil;
import org.apache.logging.log4j.*;
import java.util.UUID;

public class PlaylistView {

    private static final Logger logger = LogManager.getLogger(PlaylistView.class);

    private PlaylistView(){}

    public static void menu() {
        String opcao;
        logger.info("Menu de playlists iniciado.");
        do{
            ConsoleUtil.limparConsole();
            System.out.println("\n***** Gerenciar Playlists *****\n\n1. Criar playlist\n2. Remover playlist\n3. Editar playlist\n4. Listar playlists\n5. Reproduzir playlist\n6. Listar músicas de uma playlist\n0. Voltar");
            System.out.print("\nEscolha uma Opção: ");

            opcao = ScannerUtil.getScanner().nextLine();

            logger.info("Opção selecionada: {}", opcao);

            switch (opcao){
                case "1" -> criarPlaylist();
                case "2" -> removerPlaylist();
                case "3" -> editarPlaylist();
                case "4" -> {
                    ConsoleUtil.limparConsole();
                    System.out.println("***** Playlists *****\n");
                    PlaylistController.listarPlaylists();
                    ConsoleUtil.esperarEnter();
                }
                case "5" -> reproduzirPlaylist();
                case "6" -> listarMusicasPlaylist();
                case "0" -> {}
                default -> {
                    logger.warn("Opção inválida!");
                    System.out.println("\nOpção inválida!");
                    ConsoleUtil.esperarEnter();
                }
            }
        } while (!opcao.equals("0"));
        logger.info("Menu de playlists encerrado.");
    }

    private static void criarPlaylist() {
        ConsoleUtil.limparConsole();
        System.out.println("**** Adicionar playlist ****");
        System.out.print("\nNome da nova playlist: ");
        String nome = ScannerUtil.getScanner().nextLine();

        Playlist p = PlaylistController.buscarPlaylist(nome);
        if(p == null){
            p = new Playlist(UUID.randomUUID().toString(), nome);
            PlaylistController.adicionarPlaylist(p);
            System.out.println("Playlist criada!");
            logger.info("Playlist criada: {}", p.getNome());
        }else{
            System.out.println("Playlist já cadastrada!");
        }
        ConsoleUtil.esperarEnter();
    }

    private static void removerPlaylist() {
        ConsoleUtil.limparConsole();
        System.out.println("***** Remover playlist *****");
        System.out.print("\nNome da playlist a remover: ");
        String nome = ScannerUtil.getScanner().nextLine();

        Playlist playlist = PlaylistController.buscarPlaylist(nome);

        if(playlist != null){
            PlaylistController.removerPlaylist(nome);//verificar duplicata
            ConsoleUtil.esperarEnter();
        }
    }

    private static void editarPlaylist(){
        ConsoleUtil.limparConsole();
        System.out.println("***** Editar playlist *****");
        System.out.print("\nNome da playlist a editar: ");
        String nome = ScannerUtil.getScanner().nextLine().trim();

        Playlist p = PlaylistController.buscarPlaylist(nome);

        if(p != null){
            String opcao;
            do {
                ConsoleUtil.limparConsole();
                System.out.println("**** Editar playlist - " + p.getNome() + " ****\n\n1. Editar nome\n2. Adicionar música\n3. Remover música\n0. Voltar");
                System.out.print("\nEscolha uma opção: ");

                opcao = ScannerUtil.getScanner().nextLine();

                logger.info("Opção selecionada: {}", opcao);

                switch (opcao){
                    case "1" -> {
                        PlaylistController.editarNome(p);
                        ConsoleUtil.esperarEnter();
                    }
                    case "2" -> {
                        ConsoleUtil.limparConsole();
                        System.out.println("**** Adicionar música na playlist - " + p.getNome() + " ****");
                        System.out.print("\nNome da música a adicionar: ");
                        Musica m = MusicaController.buscarMusicaNome(ScannerUtil.getScanner().nextLine());

                        if(m != null) {
                            if(!p.isMusicaNaPlaylist(m)) {
                                PlaylistController.adicionarMusicaNaPlaylist(p, m);
                                logger.info("Música '{}' adicionada na playlist '{}'", m.getNome(), p.getNome());
                                System.out.println("Música adicionada com sucesso!");
                            }else{
                                System.out.println("Música já cadastrada na playlist!");
                                logger.warn("Música já cadastrada na playlist!");
                            }
                        }else{
                            System.out.println("Música não cadastrada");
                            logger.warn("Música não cadastrada");
                        }
                        ConsoleUtil.esperarEnter();
                    }
                    case "3" -> {
                        ConsoleUtil.limparConsole();
                        System.out.println("**** Remover música na playlist - " + p.getNome() + " ****");
                        System.out.print("\nNome da música a remover: ");
                        Musica m = MusicaController.buscarMusicaNome(ScannerUtil.getScanner().nextLine());
                        if(m != null){
                            if(p.isMusicaNaPlaylist(m)){
                                PlaylistController.removerMusicaDaPlaylist(p, m);
                                logger.info("Música '{}' removida da playlist '{}'", m.getNome(), p.getNome());
                                System.out.println("Música removida");
                            }else{
                                System.out.println("Música não cadastrada na playlist.");
                                logger.warn("Música não cadastrada na playlist.");
                            }
                        }
                        ConsoleUtil.esperarEnter();
                    }
                    case "0" -> {}
                    default -> {
                        logger.warn("Opção inválida!");
                        System.out.println("Opção inválida!");
                        ConsoleUtil.esperarEnter();
                    }
                }
            }while(!opcao.equals("0"));
        }else{
            System.out.println("Playlist não encontrada!");
            ConsoleUtil.esperarEnter();
        }

    }

    private static void reproduzirPlaylist(){
        ConsoleUtil.limparConsole();

        System.out.print("***** Reproduzir playlist *****\n\nNome da playlist a reproduzir: ");
        String nome = ScannerUtil.getScanner().nextLine();

        Playlist playlist = PlaylistController.buscarPlaylist(nome);

        if(playlist != null){
            logger.info("Reproduzindo playlist: {}", playlist.getNome());

            playlist.reproduzir();

        } else{
            System.out.println("Playlist " + nome + " não encontrada!");
            ConsoleUtil.esperarEnter();
        }
    }

    private static void listarMusicasPlaylist(){
        ConsoleUtil.limparConsole();
        System.out.println("**** Listar músicas ****");
        System.out.print("\nNome da playlist: ");

        Playlist playlist = PlaylistController.buscarPlaylist(ScannerUtil.getScanner().nextLine());
        if(playlist != null){
            ConsoleUtil.limparConsole();
            System.out.println("**** Playlist - " + playlist.getNome() + " ****\n");
            PlaylistController.listarMusicas(playlist);
            System.out.printf("\nTotal: %d músicas\nDuração total: %.2f min\n", playlist.getMusicas().size(), playlist.getDuracao()/60.0);
        }else{
            System.out.println("Playlist não encontrada!");
        }
        ConsoleUtil.esperarEnter();
    }
}
