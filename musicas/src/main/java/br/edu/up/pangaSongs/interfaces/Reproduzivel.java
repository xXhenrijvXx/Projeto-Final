package br.edu.up.pangaSongs.interfaces;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public interface Reproduzivel {
    void reproduzir() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException;
}
