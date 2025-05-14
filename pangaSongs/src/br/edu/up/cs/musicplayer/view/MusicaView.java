package br.edu.up.cs.musicplayer.view;

import br.edu.up.cs.musicplayer.controller.MusicaController;
import br.edu.up.cs.musicplayer.model.Musica;

import java.io.IOException;
import java.util.Scanner;

public class MusicaView {
    private MusicaController controller;
    private Scanner sc = new Scanner(System.in);

    public MusicaView(MusicaController controller){
        this.controller = controller;
    }

    public void menu() throws IOException {
        int opcao;
        do{
            System.out.println("\n🎵 Gerenciar Músicas\n1. Adicionar música\n2. Remover música\n3. Listar músicas\n0. Voltar\nEscolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> adicionarMusica();
                case 2 -> removerMusica();
                case 3 -> controller.listarMusicas();
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void adicionarMusica() throws IOException {
        System.out.println("Nome da música: ");
        String nome = sc.nextLine();
        System.out.println("Duração (em segundos): ");
        double duracao = sc.nextDouble();
        sc.nextLine();
        System.out.println("Caminho do arquivo: ");
        String caminho = sc.nextLine();

        Musica musica = new Musica(nome, duracao, caminho);
        controller.adicionarMusica(musica);
        System.out.println("Música adicionada!");
    }

    private void removerMusica() throws IOException {
        System.out.println("Nome da música a remover: ");
        String nome = sc.nextLine();
        controller.removerMusica(nome);
        System.out.println("Música removida!");
    }
}
