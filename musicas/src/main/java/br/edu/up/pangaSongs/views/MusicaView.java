package br.edu.up.pangaSongs.views;

import br.edu.up.pangaSongs.controller.MusicaController;
import br.edu.up.pangaSongs.controller.PlaylistMusicaController;
import br.edu.up.pangaSongs.models.Musica;
import br.edu.up.pangaSongs.util.ConsoleUtil;
import br.edu.up.pangaSongs.util.ScannerUtil;
import org.apache.logging.log4j.*;
import java.util.UUID;

public class MusicaView {
    private static final Logger logger = LogManager.getLogger(MusicaView.class);

    private MusicaView(){}

    public static void menuPrincipal() {
        String opcao;
        logger.info("Menu de músicas iniciado.");
        do{
            ConsoleUtil.limparConsole();
            System.out.println("\n***** Gerenciar Músicas *****\n\n1. Adicionar música\n2. Remover música\n3. Editar música\n4. Listar músicas\n5. Tocar música\n0. Voltar");
            System.out.print("\nEscolha uma Opção: ");

            opcao = ScannerUtil.getScanner().nextLine();

            logger.info("Opção selecionada: {}", opcao);

            switch (opcao){
                case "1" -> adicionarMusica();
                case "2" -> removerMusica();
                case "3" -> editarMusica();
                case "4" -> {
                    logger.info("Listando músicas");
                    ConsoleUtil.limparConsole();
                    System.out.println("***** Músicas *****\n");
                    MusicaController.listarMusicas();
                    ConsoleUtil.esperarEnter();
                }
                case "5" -> tocarMusica();
                case "0" -> {}
                default -> {
                    System.out.println("\nOpção inválida!");
                    ConsoleUtil.esperarEnter();
                }
            }
        } while (!opcao.equals("0"));
        logger.info("Menu de músicas encerrado.");
    }

    private static void adicionarMusica() {
        ConsoleUtil.limparConsole();
        System.out.println("***** Adicionar música *****");
        System.out.print("\nNome da nova música: ");
        String nome = ScannerUtil.getScanner().nextLine();

        if(!nome.trim().isEmpty()) {
            Musica musica = MusicaController.buscarMusicaNome(nome);
            if (musica == null) {
                System.out.print("Caminho do arquivo: ");
                String caminho = ScannerUtil.getScanner().nextLine();
                if (caminho.endsWith(".wav")) {
                    System.out.print("Gênero da música: ");
                    String genero = ScannerUtil.getScanner().nextLine();
                    System.out.print("Artista: ");
                    String artista = ScannerUtil.getScanner().nextLine();

                    musica = new Musica(UUID.randomUUID().toString(), nome, caminho, artista, genero);
                    if (musica.getDuracao() != 0.0) {
                        MusicaController.adicionarMusica(musica);
                        System.out.println("\nMúsica adicionada!");
                        logger.info("Música criada");
                    }
                } else {
                    System.out.println("\nTipo de arquivo não suportado, música não cadastrada.");
                    logger.warn("Tipo de arquivo não suportado, música não cadastrada.");
                }
            } else {
                System.out.println("\nMúsica já cadastrada: " + musica.getNome());
            }
        }else{
            System.out.println("Digite um nome válido!");
        }
        ConsoleUtil.esperarEnter();
    }

    private static void removerMusica() {
        ConsoleUtil.limparConsole();
        System.out.println("***** Remover música *****");
        System.out.print("\nNome da música a remover: ");
        String nome = ScannerUtil.getScanner().nextLine().trim();

        Musica musica = MusicaController.buscarMusicaNome(nome);
        if(musica != null){
            PlaylistMusicaController.removerMusicaDasPlaylists(musica);
            MusicaController.removerMusica(nome);
        }else{
            System.out.println("Música não cadastrada");
        }
        ConsoleUtil.esperarEnter();
    }

    private static void editarMusica(){
        ConsoleUtil.limparConsole();
        System.out.println("***** Editar música *****");
        System.out.print("\nNome da música a editar: ");
        String nome = ScannerUtil.getScanner().nextLine().trim();

        Musica musica = MusicaController.buscarMusicaNome(nome);
        if(musica != null){
            logger.info("Editando a música: {}", musica.getNome());
            String opcao;
            do {
                ConsoleUtil.limparConsole();
                System.out.println("**** Editar música - " + musica.getNome() + " ****\n\n1. Editar nome\n2. Editar artista\n3. Editar gênero\n4. Editar caminho\n0. Voltar");
                System.out.print("\nEscolha uma Opção: ");

                opcao = ScannerUtil.getScanner().nextLine();

                logger.info("Opção selecionada: {}", opcao);

                switch (opcao){
                    case "1" -> {
                        MusicaController.editarNome(musica);
                        ConsoleUtil.esperarEnter();
                    }
                    case "2" -> {
                        MusicaController.editarArtista(musica);
                        ConsoleUtil.esperarEnter();
                    }
                    case "3" -> {
                        MusicaController.editarGenero(musica);
                        ConsoleUtil.esperarEnter();
                    }
                    case "4" -> {
                        MusicaController.editarCaminho(musica);
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
            System.out.println("Música não encontrada!");
        }
    }

    private static void tocarMusica(){
        ConsoleUtil.limparConsole();
        System.out.print("***** Reproduzir música *****\n\nNome da musica a reproduzir: ");
        String nome = ScannerUtil.getScanner().nextLine().trim();

        Musica musica = MusicaController.buscarMusicaNome(nome);

        if(musica != null){
            musica.reproduzir();
        }else{
            System.out.println("Música não encontrada!");
            ConsoleUtil.esperarEnter();
        }
    }
}
