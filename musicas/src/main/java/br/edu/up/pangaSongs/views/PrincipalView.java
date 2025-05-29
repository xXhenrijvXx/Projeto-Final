package br.edu.up.pangaSongs.views;

import br.edu.up.pangaSongs.util.ScannerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrincipalView {
    private static final Logger logger = LogManager.getLogger(PrincipalView.class);

    private PrincipalView(){}

    public static void menu() {
        String opcao;

        logger.info("Menu Principal iniciado.");

        do {
            System.out.println("\n***** Menu Principal *****\n\n1. Gerenciar Músicas\n2. Gerenciar Playlists\n0. Sair");
            System.out.print("\nEscolha uma Opção: ");

            opcao = ScannerUtil.getScanner().nextLine();

            logger.info("Opção selecionada: {}", opcao);

            switch (opcao) {
                case "1" -> MusicaView.menuPrincipal();
                case "2" -> PlaylistView.menu();
                case "0" -> System.out.println("Encerrando...");
                default -> {
                    logger.warn("Opção inválida!");
                    System.out.println("Opção inválida!");
                }
            }
        } while (!opcao.equals("0"));
        logger.info("Menu principal encerrado.");
    }
}
