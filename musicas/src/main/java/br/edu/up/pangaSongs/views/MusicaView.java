package br.edu.up.pangaSongs.views;

import br.edu.up.pangaSongs.controller.MusicaController;
import br.edu.up.pangaSongs.controller.PlaylistMusicaController;
import br.edu.up.pangaSongs.models.Musica;
import br.edu.up.pangaSongs.util.AguardarUtil;
import br.edu.up.pangaSongs.util.ScannerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.UUID;

public class MusicaView {
    private static final Logger logger = LogManager.getLogger(MusicaView.class);

    private MusicaView(){}

    public static void menuPrincipal() {
        String opcao;
        logger.info("Menu de músicas iniciado.");
        do{
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
                    System.out.println("\n***** Músicas *****\n");
                    MusicaController.listarMusicas();
                    AguardarUtil.esperarEnter();
                }
                case "5" -> tocarMusica();
                case "0" -> {}
                default -> System.out.println("\nOpção inválida!");
            }
        } while (!opcao.equals("0"));
        logger.info("Menu de músicas encerrado.");
    }

    private static void adicionarMusica() {
        System.out.println("***** Adicionar música *****");
        System.out.print("\nNome da nova música: ");
        String nome = ScannerUtil.getScanner().nextLine();

        Musica musica = MusicaController.buscarMusicaNome(nome);
        if(musica == null){
            System.out.print("Caminho do arquivo: ");
            String caminho = ScannerUtil.getScanner().nextLine();
            if(caminho.endsWith(".wav")){
                System.out.print("Gênero da música: ");
                String genero = ScannerUtil.getScanner().nextLine();
                System.out.print("Artista: ");
                String artista = ScannerUtil.getScanner().nextLine();

                musica = new Musica(UUID.randomUUID().toString(), nome, caminho, artista, genero);
                if(musica.getDuracao() != 0.0) {
                    MusicaController.adicionarMusica(musica);
                    System.out.println("Música adicionada!");
                    logger.info("Música criada");
                }
            }else{
                System.out.println("Tipo de arquivo não suportado, música não cadastrada.");
                logger.warn("Tipo de arquivo não suportado, música não cadastrada.");
            }
        }else{
            System.out.println("Música já cadastrada");
        }
        AguardarUtil.esperarEnter();
    }

    private static void removerMusica() {
        System.out.println("Nome da música a remover: ");
        String nome = ScannerUtil.getScanner().nextLine().trim().toLowerCase();

        Musica musica = MusicaController.buscarMusicaNome(nome);
        if(musica != null){
            PlaylistMusicaController.removerMusicaDasPlaylists(musica);
            MusicaController.removerMusica(nome);
            System.out.println("Música removida!");
            logger.info("Música removida.");
        }else{
            System.out.println("Música não encontrada!");
        }
        AguardarUtil.esperarEnter();
    }

    private static void editarMusica(){

        System.out.print("\nNome da música a editar: ");
        String nome = ScannerUtil.getScanner().nextLine().trim();

        Musica musica = MusicaController.buscarMusicaNome(nome);
        if(musica != null){
            logger.info("Editando a música: {}", musica.getNome());
            String opcao;
            do {
                System.out.println("\n**** Editar Música - " + musica.getNome() + " ****\n\n1. Editar nome\n2. Editar artista\n3. Editar gênero\n4. Editar caminho\n0. Voltar");
                System.out.print("\nEscolha uma Opção: ");

                opcao = ScannerUtil.getScanner().nextLine();

                logger.info("Opção selecionada: {}", opcao);

                switch (opcao){
                    case "1" -> {
                        MusicaController.editarNome(musica);
                        AguardarUtil.esperarEnter();
                    }
                    case "2" -> {
                        MusicaController.editarArtista(musica);
                        AguardarUtil.esperarEnter();
                    }
                    case "3" -> {
                        MusicaController.editarGenero(musica);
                        AguardarUtil.esperarEnter();
                    }
                    case "4" -> {
                        MusicaController.editarCaminho(musica);
                        AguardarUtil.esperarEnter();
                    }
                    case "0" -> {}
                    default -> {
                        logger.warn("Opção inválida!");
                        System.out.println("Opção inválida!");
                        AguardarUtil.esperarEnter();
                    }
                }

            }while(!opcao.equals("0"));
        }else{
            System.out.println("Música não encontrada!");
        }
    }

    private static void menuReproducao(Musica musica) {
        String opcao;

        Thread thread = new Thread(() -> {
            try {
                musica.reproduzir();
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                musica.parar();
            }
        });
        thread.start();

        System.out.println("\n► Tocando: " + musica.getNome() + " de " + musica.getArtista());

        do{
            if (!musica.isPausada()) System.out.print("[p] Pausar | ");
            else System.out.print("[c] Continuar | ");
            System.out.print("[s] Stop\n");
            System.out.print("Digite: ");
            opcao = ScannerUtil.getScanner().nextLine().trim().toLowerCase();

            switch (opcao){
                case "p" -> {
                    if (!musica.isPausada()){
                        logger.info("Música pausada.");
                        musica.pausar();
                    }
                }
                case "c" -> {
                    if (musica.isPausada()){
                        logger.info("Continuando música.");
                        musica.continuar();
                    }
                }
                case "s" -> musica.parar();
                default -> {
                    logger.info("Opção inválida!");
                    System.out.println("Opção inválida!");
                }
            }
        }while(!opcao.equalsIgnoreCase("s") && !musica.isFinalizada());

        try{
            thread.join();
            System.out.println("Música finalizada");
            logger.info("Música encerrada.");
        } catch (InterruptedException e){
            logger.error("Erro ao finalizar thread de reprodução: ", e);
            Thread.currentThread().interrupt();
        }
    }

    private static void tocarMusica(){
        System.out.print("\n***** Reproduzir música *****\n\nNome da musica a tocar: ");
        String nome = ScannerUtil.getScanner().nextLine().trim().toLowerCase();

        Musica musica = MusicaController.buscarMusicaNome(nome);

        if(musica != null){
            menuReproducao(musica);
        }else{
            System.out.println("Música não encontrada!");
            AguardarUtil.esperarEnter();
        }
    }
}
