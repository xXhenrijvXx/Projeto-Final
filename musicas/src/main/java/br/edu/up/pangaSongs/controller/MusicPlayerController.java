package br.edu.up.pangaSongs.controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayerController {
    private static Clip clip;
    private static boolean pausada = false;
    private static boolean finalizada = false;

    private MusicPlayerController(){}

    public static void tocar(String caminhoArquivo) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        pausada = false;
        finalizada = false;

        File arquivo = new File(caminhoArquivo);

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivo);
        clip = AudioSystem.getClip();
        clip.open(audioStream);

        clip.addLineListener(event -> {
            if (event.getType() == LineEvent.Type.STOP && !pausada) {
                finalizada = true;
            }
        });

        clip.start();
    }

    public static void pausar() {
        if (clip != null && clip.isRunning()) {
            pausada = true;
            clip.stop();
            System.out.println("Música pausada.");
        }
    }

    public static void continuar() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
            pausada = false;
            System.out.println("Música continuada.");
        }
    }

    public static void parar() {
        if (clip != null) {
            clip.stop();
            clip.close();
            finalizada = true;
            System.out.println("Música parada.");
        }
    }

    public static boolean isPausada() {
        return pausada;
    }

    public static boolean isFinalizada() {
        return finalizada;
    }
}
