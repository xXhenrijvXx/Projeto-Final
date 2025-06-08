package br.edu.up.pangaSongs.archives;

import br.edu.up.pangaSongs.controller.MusicaController;
import br.edu.up.pangaSongs.models.Musica;
import org.apache.logging.log4j.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoMusica {
    private static final String caminhoArquivo = "data/musicas.txt";
    private static final Logger logger = LogManager.getLogger(ArquivoMusica.class);

    private ArquivoMusica(){}

    public static void salvar() {
        try {
            FileWriter fw = new FileWriter(caminhoArquivo);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Musica m : MusicaController.getMusicas()) {
                bw.write(m.getId() + ";" + m.getNome() + ";" + m.getDuracao() + ";" + m.getCaminhoArquivo() + ";" + m.getArtista() + ";" + m.getGenero());
                bw.newLine();
            }

            bw.close();
            fw.close();
        }catch (IOException e){
            System.out.println("Erro ao salvar o arquivo de músicas.");
            logger.error("Erro ao salvar o arquivo de músicas: ", e);
        }
    }

    public static void carregar() {
        File arquivo = new File(caminhoArquivo);
        List<Musica> musicas = new ArrayList<>();
        if (!arquivo.exists()) {
            arquivo.getParentFile().mkdirs();
            try{
                arquivo.createNewFile();
            }catch(IOException e){
                System.out.println("Erro ao criar o arquivo de músicas.");
                logger.error("Erro ao criar o arquivo de músicas: ", e);
            }
            return;
        }
        try{
            BufferedReader br = new BufferedReader(new FileReader(arquivo));
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 6) {
                    String id = partes[0];
                    String nome = partes[1];
                    double duracao = Double.parseDouble(partes[2]);
                    String caminho = partes[3];
                    String artista = partes[4];
                    String genero = partes[5];
                    musicas.add(new Musica(id, nome, duracao, caminho, artista, genero));
                }
            }

            br.close();

            MusicaController.setMusicas(musicas);
        }catch (IOException e){
            System.out.println("Erro ao ler o arquivo de músicas");
            logger.error("Erro ao ler o arquivo de músicas: ", e);
        }
    }
}
