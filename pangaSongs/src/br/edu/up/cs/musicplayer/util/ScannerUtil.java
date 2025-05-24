package br.edu.up.cs.musicplayer.util;

import java.util.Scanner;

public class ScannerUtil {
    private static final Scanner scanner = new Scanner(System.in);

    private ScannerUtil(){}

    public static Scanner getScanner() {
        return scanner;
    }

    public static void fechaScanner(){
        scanner.close();
    }
}
