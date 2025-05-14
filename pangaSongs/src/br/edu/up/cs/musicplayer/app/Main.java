package br.edu.up.cs.musicplayer.app;

import br.edu.up.cs.musicplayer.controller.MusicaController;
import br.edu.up.cs.musicplayer.controller.PlaylistController;
import br.edu.up.cs.musicplayer.view.MusicaView;
import br.edu.up.cs.musicplayer.view.PlaylistView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MusicaController mc = new MusicaController();
        PlaylistController pc = new PlaylistController();
        MusicaView mv = new MusicaView(mc);
        PlaylistView pv = new PlaylistView(pc, mc);

        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nMenu Principal\n1. Gerenciar Músicas\n2. Gerenciar Playlists\n0. Sair\nEscolha uma Opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> mv.menu();
                case 2 -> pv.menu();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opcção inválida!");

            }
        } while (opcao != 0);
    }
}
