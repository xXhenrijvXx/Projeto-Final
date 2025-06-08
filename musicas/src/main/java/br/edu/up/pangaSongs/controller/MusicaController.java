package br.edu.up.pangaSongs.controller;

import br.edu.up.pangaSongs.models.Musica;
import br.edu.up.pangaSongs.util.ConsoleUtil;
import br.edu.up.pangaSongs.util.ScannerUtil;
import org.apache.logging.log4j.*;
import java.util.ArrayList;
import java.util.List;

public class MusicaController {
    private static List<Musica> musicas = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(MusicaController.class);

    private MusicaController(){}

    public static void adicionarMusica(Musica musica) {
        musicas.add(musica);
    }

    public static void editarNome(Musica musica){
        ConsoleUtil.limparConsole();
        System.out.print("**** Editar nome ****\n\nNome da música atual -> " + musica.getNome() + "\nDigite o novo nome: ");
        musica.setNome(ScannerUtil.getScanner().nextLine());
    }

    public static void editarArtista(Musica musica){
        ConsoleUtil.limparConsole();
        System.out.print("**** Editar artista ****\n\nNome do artista atual -> " + musica.getArtista() + "\nDigite o novo artista: ");
        musica.setArtista(ScannerUtil.getScanner().nextLine());
    }

    public static void editarGenero(Musica musica){
        ConsoleUtil.limparConsole();
        System.out.print("**** Editar gênero ****\n\nNome do gênero atual -> " + musica.getGenero() + "\nDigite o novo gênero: ");
        musica.setGenero(ScannerUtil.getScanner().nextLine());
    }

    public static void editarCaminho(Musica musica){
        ConsoleUtil.limparConsole();
        System.out.print("**** Editar caminho ****\n\nCaminho da música atual -> " + musica.getCaminhoArquivo() + "\nDigite o novo caminho: ");
        musica.setCaminhoArquivo(ScannerUtil.getScanner().nextLine());
    }

    public static void removerMusica(String nome) {
        if(musicas.removeIf(m -> m.getNome().equalsIgnoreCase(nome))){
            System.out.println("Música removida!");
            logger.info("Música removida.");
        }
    }

    public static void listarMusicas() {
        for (Musica m : musicas) {
            System.out.println("🎵 " + m.getNome());
        }
    }

    public static Musica buscarMusicaId(String id) {
        for (Musica m : musicas) {
            if (m.getId().equalsIgnoreCase(id)) return m;
        }
        logger.warn("Música não encontrada!");
        return null;
    }

    public static Musica buscarMusicaNome(String nome) {
        for (Musica m : musicas) {
            if (m.getNome().equalsIgnoreCase(nome)) return m;
        }
        logger.warn("Música não encontrada!");
        return null;
    }

    public static List<Musica> getMusicas() {
        return musicas;
    }

    public static void setMusicas(List<Musica> musicas) {
        MusicaController.musicas = musicas;
    }
}
