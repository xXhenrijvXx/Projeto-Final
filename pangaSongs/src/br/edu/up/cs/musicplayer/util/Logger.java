package br.edu.up.cs.musicplayer.util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String logPath = "log.txt";

    public static void registrar(String mensagem) throws IOException {
        FileWriter fw = new FileWriter(logPath, true);
        String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        fw.write("[" + dataHora + "] " + mensagem + "\n");
    }
}
