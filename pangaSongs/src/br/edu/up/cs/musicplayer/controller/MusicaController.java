package br.edu.up.cs.musicplayer.controller;

import br.edu.up.cs.musicplayer.model.Musica;
import br.edu.up.cs.musicplayer.util.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MusicaController {
    private List<Musica> musicas = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void adicionarMusica(Musica musica) throws IOException {
        musicas.add(musica);
        Logger.registrar("Música adicionada: " + musica.getNome());
    }

    public void editarMusica(String nome){
        Musica m = buscarMusica(nome);
        if(m != null){
            int opcao;
            do {
                System.out.println("\nEditar Música - " + m.getNome() + "\n1. Editar nome\n2. Editar artista\n3. Editar gênero\nEditar caminho\n0. Voltar\nEscolha: ");
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao){
                    case 1 -> editarNome(m);
                    case 2 -> editarArtista(m);
                    case 3 -> editarGenero(m);
                    case 4 -> editarCaminho(m);
                    case 0 -> {}
                    default -> System.out.println("Opção inválida!");
                }
            }while(opcao != 0);
        }
    }

    public void editarNome(Musica musica){
        System.out.println("Nome da música atual -> " + musica.getNome() + "\nDigite o novo nome: ");
        musica.setNome(sc.nextLine());
    }

    public void editarArtista(Musica musica){
        System.out.println("Nome do artista atual -> " + musica.getArtista() + "\nDigite o novo artista: ");
        musica.setArtista(sc.nextLine());
    }

    public void editarGenero(Musica musica){
        System.out.println("Nome do gênero atual -> " + musica.getGenero() + "\nDigite o novo gênero: ");
        musica.setGenero(sc.nextLine());
    }

    public void editarCaminho(Musica musica){
        System.out.println("Caminho da música atual -> " + musica.getCaminhoArquivo() + "\nDigite o novo caminho: ");
        musica.setCaminhoArquivo(sc.nextLine());
    }

    public void removerMusica(String nome) throws IOException {
        if(musicas.removeIf(m -> m.getNome().equalsIgnoreCase(nome))){
            Logger.registrar("Música removida: " + nome);
            System.out.println("Música removida!");
        } else{
            System.out.println("Música não encontrada.");
        }
    }

    public void listarMusicas() {
        for (Musica m : musicas) {
            System.out.println("🎵 " + m.getNome());
        }
    }

    public Musica buscarMusica(String nome) {
        for (Musica m : musicas) {
            if (m.getNome().equalsIgnoreCase(nome)) return m;
        }
        System.out.println("Música não encontrada!");
        return null;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }
}
