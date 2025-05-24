package br.edu.up.cs.musicplayer.util;

public class ConsoleUtil {
    public static void clearScreen() {
        // ANSI escape code: ESC[2J = limpa tela; ESC[H = vai para o canto
        System.out.print("\u001b[2J\u001b[H");
        System.out.flush();
    }
}

