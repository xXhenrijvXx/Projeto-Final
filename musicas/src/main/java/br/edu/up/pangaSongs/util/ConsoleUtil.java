package br.edu.up.pangaSongs.util;

public class ConsoleUtil {

    private ConsoleUtil(){}

    public static void esperarEnter() {
        System.out.println("\nPressione ENTER para continuar...");
        ScannerUtil.getScanner().nextLine();
    }

    public static void limparConsole(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
    }
}
