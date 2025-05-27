package br.edu.up.pangaSongs.controller;

import br.edu.up.pangaSongs.models.Musica;
import br.edu.up.pangaSongs.util.ScannerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicaController {
    private static final List<Musica> musicas = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(MusicaController.class);

    private MusicaController(){}

    public static void adicionarMusica(Musica musica) throws IOException {
        musicas.add(musica);
    }


    public static void editarNome(Musica musica){
        System.out.println("Nome da música atual -> " + musica.getNome() + "\nDigite o novo nome: ");
        musica.setNome(ScannerUtil.getScanner().nextLine());
    }

    public static void editarArtista(Musica musica){
        System.out.println("Nome do artista atual -> " + musica.getArtista() + "\nDigite o novo artista: ");
        musica.setArtista(ScannerUtil.getScanner().nextLine());
    }

    public static void editarGenero(Musica musica){
        System.out.println("Nome do gênero atual -> " + musica.getGenero() + "\nDigite o novo gênero: ");
        musica.setGenero(ScannerUtil.getScanner().nextLine());
    }

    public static void editarCaminho(Musica musica){
        System.out.println("Caminho da música atual -> " + musica.getCaminhoArquivo() + "\nDigite o novo caminho: ");
        musica.setCaminhoArquivo(ScannerUtil.getScanner().nextLine());
    }

    public static void removerMusica(String nome) throws IOException {
        if(musicas.removeIf(m -> m.getNome().equalsIgnoreCase(nome))){
            System.out.println("Música removida!");
        } else{
            System.out.println("Música não encontrada.");
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
            //log erro
        }
        return null;
    }

    public static Musica buscarMusicaNome(String nome) {
        for (Musica m : musicas) {
            if (m.getNome().equalsIgnoreCase(nome)) return m;
        }
        return null;
    }

    public static List<Musica> getMusicas() {
        return musicas;
    }
}
