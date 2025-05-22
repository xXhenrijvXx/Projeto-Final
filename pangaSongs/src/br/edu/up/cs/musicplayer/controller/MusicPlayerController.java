package br.edu.up.cs.musicplayer.controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayerController {
    private Clip clip;
    private boolean pausada = false;
    private boolean finalizada = false;

    public void tocar(String caminhoArquivo) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
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

    public void pausar() {
        if (clip != null && clip.isRunning()) {
            pausada = true;
            clip.stop();
            System.out.println("Música pausada.");
        }
    }

    public void continuar() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
            pausada = false;
            System.out.println("Música continuada.");
        }
    }

    public void parar() {
        if (clip != null) {
            clip.stop();
            clip.close();
            finalizada = true;
            System.out.println("Música parada.");
        }
    }

    public boolean isPausada() {
        return pausada;
    }

    public boolean isFinalizada() {
        return finalizada;
    }
}
