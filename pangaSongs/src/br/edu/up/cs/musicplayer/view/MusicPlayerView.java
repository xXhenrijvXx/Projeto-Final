package br.edu.up.cs.musicplayer.view;

import br.edu.up.cs.musicplayer.controller.MusicPlayerController;
import br.edu.up.cs.musicplayer.util.Logger;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Scanner;

public class MusicPlayerView {
    private MusicPlayerController mp = new MusicPlayerController();

    public void menu(String caminhoArquivo) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String opcao = "";

        Thread thread = new Thread(() -> {
            try {
                mp.tocar(caminhoArquivo);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                mp.parar();
            }
        });
        thread.start();

        do{
            opcao = sc.nextLine().trim().toLowerCase();

            switch (opcao){
                case "p" -> mp.pausar();
                case "c" -> mp.continuar();
                case "s" -> mp.parar();
                default -> System.out.println("Comando inválido!");
            }
        }while(!opcao.equalsIgnoreCase("s") && !mp.isFinalizada());

        thread.join();
        Logger.registrar("Musica finalizada");
        System.out.println("Música finalizada");;
    }
}
