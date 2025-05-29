package br.edu.up.pangaSongs.util;

public class AguardarUtil {

    private AguardarUtil(){};

    public static void esperarEnter() {
        System.out.println("Pressione ENTER para continuar...");
        ScannerUtil.getScanner().nextLine();
    }
}
