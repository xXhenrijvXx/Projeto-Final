package br.edu.up.cs.musicplayer.view;

import br.edu.up.cs.musicplayer.controller.MusicPlayerController;
import java.util.Scanner;

public class PlaylistPlayerView {
    private final MusicPlayerController mp = new MusicPlayerController();

    public String menu(String caminhoArquivo, boolean isIndexZero, boolean isLastIndex)
            throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        String opcao;

        Thread thread = new Thread(() -> {
            try {
                mp.tocar(caminhoArquivo);
            } catch (Exception e) {
                System.out.println("Erro ao tocar: " + e.getMessage());
                mp.parar();
            }
        });
        thread.start();

        do {

            if (!isIndexZero) System.out.print("[b] Back | ");

            if (!mp.isPausada()) System.out.print("[p] Pausar | ");
            else System.out.print("[c] Continuar | ");

            if (!isLastIndex) System.out.print("[n] Next | ");
            System.out.print("[s] Stop\n");

            opcao = sc.nextLine().trim().toLowerCase();

            switch (opcao) {
                case "p" -> { if (!mp.isPausada()) mp.pausar(); } //tava bugando
                case "c" -> { if (mp.isPausada()) mp.continuar(); }
                case "b", "n", "s" -> { mp.parar(); }
                default -> System.out.println("Opção inválida!");
            }
        } while (!("b".equals(opcao) || "n".equals(opcao) || "s".equals(opcao)) && !mp.isFinalizada());

        thread.join();

        return opcao;
    }
}
