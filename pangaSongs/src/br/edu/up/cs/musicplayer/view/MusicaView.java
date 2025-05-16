package br.edu.up.cs.musicplayer.view;

import br.edu.up.cs.musicplayer.controller.MusicaController;
import br.edu.up.cs.musicplayer.model.Musica;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class MusicaView {
    private MusicaController controller;
    private Scanner sc = new Scanner(System.in);

    public MusicaView(MusicaController controller){
        this.controller = controller;
    }

    public void menu() throws IOException {
        int opcao;
        do{
            System.out.println("\nGerenciar Músicas\n1. Adicionar música\n2. Remover música\n3. Editar música\n4. Listar músicas\n0. Voltar\nEscolha: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1 -> adicionarMusica();
                case 2 -> removerMusica();
                case 3 -> editarMusica();
                case 4 -> controller.listarMusicas();
                case 0 -> {}
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    public MusicaController getController() {
        return controller;
    }

    private void adicionarMusica() throws IOException {
        System.out.println("Nome da música: ");
        String nome = sc.nextLine();
        System.out.println("Duração (em segundos): ");
        double duracao = sc.nextDouble();
        sc.nextLine();
        System.out.println("Caminho do arquivo: ");
        String caminho = sc.nextLine();
        System.out.println("Gênero da música: ");
        String genero = sc.nextLine();
        System.out.println("Artista: ");
        String artista = sc.nextLine();

        Musica musica = new Musica(UUID.randomUUID().toString(), nome, duracao, caminho, artista, genero);
        controller.adicionarMusica(musica);
        System.out.println("Música adicionada!");
        MusicaController.esperarEnter();
    }


    private void removerMusica() throws IOException {
        System.out.println("Nome da música a remover: ");
        String nome = sc.nextLine();
        controller.removerMusica(nome);
    }

    private void editarMusica(){
        System.out.println("Nome da música a editar: ");
        String nome = sc.nextLine();
        controller.editarMusica(nome);
    }
}
