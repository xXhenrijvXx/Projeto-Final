package br.edu.up.pangaSongs.models;

import br.edu.up.pangaSongs.controller.MusicPlayerController;
import br.edu.up.pangaSongs.util.ConsoleUtil;
import br.edu.up.pangaSongs.util.ScannerUtil;
import org.apache.logging.log4j.*;
import javax.sound.sampled.*;
import java.io.*;

public class Musica extends Media {
    private String artista;
    private String genero;
    private String caminhoArquivo;
    private static final Logger logger = LogManager.getLogger(Musica.class);

    public Musica(String id, String nome, String caminhoArquivo, String artista, String genero){
        super(id, nome, 0.0);
        this.artista = artista;
        this.genero = genero;
        this.caminhoArquivo = caminhoArquivo;

        super.setDuracao(calculaDuracao());
    }

    public Musica(String id, String nome, Double duracao ,String caminhoArquivo, String artista, String genero) {
        super(id, nome, duracao);
        this.artista = artista;
        this.genero = genero;
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public void reproduzir(){

        String opcao;

        Thread thread = new Thread(() -> {
            try {
                MusicPlayerController.tocar(getCaminhoArquivo());
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                parar();
            }
        });
        thread.start();

        logger.info("Reproduzindo música: {}", getNome());

        do{
            ConsoleUtil.limparConsole();
            System.out.println("\n► Tocando: " + getNome() + " de " + getArtista());
            if (!isPausada()) System.out.print("[p] Pausar | ");
            else System.out.print("[c] Continuar | ");
            System.out.print("[s] Stop\n");
            System.out.print("Digite: ");
            opcao = ScannerUtil.getScanner().nextLine().trim().toLowerCase();

            switch (opcao){
                case "p" -> {
                    if (!isPausada()){
                        logger.info("Música pausada.");
                        pausar();
                    }
                }
                case "c" -> {
                    if (isPausada()){
                        logger.info("Continuando música.");
                        continuar();
                    }
                }
                case "s" -> {
                    logger.info("Música encerrada.");
                    parar();
                }
                default -> {
                    logger.info("Opção inválida!");
                    System.out.println("Opção inválida!");
                }
            }
        }while(!opcao.equalsIgnoreCase("s") && !isFinalizada());

        try{
            thread.join();
            System.out.println("Música finalizada");
        } catch (InterruptedException e){
            logger.error("Erro ao finalizar thread de reprodução: ", e);
            Thread.currentThread().interrupt();
        }
    }

    public boolean isFinalizada(){
        return MusicPlayerController.isFinalizada();
    }

    public boolean isPausada(){
        return MusicPlayerController.isPausada();
    }

    public void pausar(){
        MusicPlayerController.pausar();
    }

    public void continuar(){
        MusicPlayerController.continuar();
    }

    public void tocar(){
        MusicPlayerController.tocar(getCaminhoArquivo());
    }

    public void parar(){
        MusicPlayerController.parar();
    }

    private Double calculaDuracao() {//estudar
        try {
            File arquivo = new File(getCaminhoArquivo());

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(arquivo);
            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            return (frames+0.0) / format.getFrameRate();

        } catch (UnsupportedAudioFileException | IOException e) {
            System.out.println("Erro ao ler WAV: " + e.getMessage());
            logger.error("Erro ao ler WAV: ", e);
            return 0.0;
        }
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        System.out.println("\nCaminho editado com sucesso!");
    }

    public void setArtista(String artista) {
        this.artista = artista;
        System.out.println("\nArtista editado com sucesso!");
    }

    public void setGenero(String genero) {
        this.genero = genero;
        System.out.println("\nGênero editado com sucesso!");
    }

    public String getArtista() {
        return artista;
    }

    public String getGenero() {
        return genero;
    }
}
