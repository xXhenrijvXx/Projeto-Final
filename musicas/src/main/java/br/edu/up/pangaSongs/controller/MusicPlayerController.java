package br.edu.up.pangaSongs.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayerController {
    private static Clip clip;
    private static boolean pausada = false;
    private static boolean finalizada = false;
    private static final Logger logger = LogManager.getLogger(MusicPlayerController.class);

    private MusicPlayerController(){}

    public static void tocar(String caminhoArquivo) {
        pausada = false;
        finalizada = false;

        try {
            File arquivo = new File(caminhoArquivo);

            if (!arquivo.exists()) {
                System.out.println("Arquivo de áudio não encontrado.");
                logger.warn("Arquivo de áudio não encontrado.");
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivo);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && !pausada) {
                    finalizada = true;
                }
            });

            clip.start();
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Formato de arquivo não suportado.");
            logger.error("Formato de arquivo não suportado: ", e);
        }catch (LineUnavailableException e) {
            System.out.println("Dispositivo de áudio não disponível no momento.");
            logger.error("Dispositivo de áudio não disponível no momento: ", e);
        }catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo de áudio.");
            logger.error("Erro ao carregar o arquivo de áudio: ", e);
        }
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
