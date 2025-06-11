package br.edu.up.pangaSongs.models;

import br.edu.up.pangaSongs.util.ConsoleUtil;
import br.edu.up.pangaSongs.util.ScannerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Playlist extends Media{
    private final List<Musica> musicas;
    private static final Logger logger = LogManager.getLogger(Playlist.class);

    public Playlist(String id, String nome){
        super(id, nome, 0);
        this.musicas = new ArrayList<>();
    }

    public void adicionarMusica(Musica musica){
        if (musica != null) {
            musicas.add(musica);
            atualizarDuracao();
        }
    }

    public void removerMusica(Musica musica){
        if (musicas.remove(musica)) {
            atualizarDuracao();
        }
    }

    private void atualizarDuracao(){
        double total = 0;
        for (Musica m : musicas){
            total += m.getDuracao();
        }
        super.setDuracao(total);
    }

    @Override
    public void reproduzir(){
        int idx = 0;

        while (idx >= 0 && idx < getMusicas().size()) {
            Musica musica = getMusicas().get(idx);
            boolean isIndexZero = (idx == 0);
            boolean isLastIndex = (idx == (getMusicas().size())-1);

            String opcao;

            Thread thread = new Thread(() -> {
                try {
                    logger.info("Reproduzindo música: {}", musica.getNome());
                    musica.tocar();
                } catch (Exception e) {
                    logger.error("Erro ao reproduzir música '{}':", musica.getNome(), e);
                    System.out.println("Erro ao reproduzir música: " + e.getMessage());
                    musica.parar();
                }
            });
            thread.start();

            do {
                ConsoleUtil.limparConsole();
                System.out.println("Reproduzindo playlist: " + getNome());
                System.out.println("► Tocando: " + musica.getNome() + " de " + musica.getArtista());
                if (!isIndexZero) System.out.print("[b] Back | ");

                if (!musica.isPausada()) System.out.print("[p] Pausar | ");
                else System.out.print("[c] Continuar | ");

                if (!isLastIndex) System.out.print("[n] Next | ");
                System.out.print("[s] Stop\nDigite: ");


                opcao = ScannerUtil.getScanner().nextLine().trim().toLowerCase();

                switch (opcao) {
                    case "p" -> {
                        if (!musica.isPausada()){
                            musica.pausar();
                            logger.info("Música pausada.");
                        }
                    }
                    case "c" -> {
                        if (musica.isPausada()){
                            musica.continuar();
                            logger.info("Continuando música.");
                        }
                    }
                    case "b", "n", "s" -> musica.parar();
                    default -> {
                        logger.info("Opção inválida!");
                        System.out.println("Opção inválida!");
                    }
                }
            } while (!("b".equals(opcao) || "n".equals(opcao) || "s".equals(opcao)) && !musica.isFinalizada());

            try{
                thread.join();
            }catch (InterruptedException e){
                logger.error("Erro ao finalizar thread de reprodução: ", e);
                Thread.currentThread().interrupt();
            }

            switch (opcao){
                case "n" -> {
                    idx++;
                    logger.info("Próxima música");
                }
                case "b" -> {
                    logger.info("Música anterior");
                    idx = Math.max(0, idx - 1);
                }
                case "s" -> {
                    logger.info("Encerrando reprodução da playlist.");
                    return;
                }
                default -> {}
            }
        }
    }

    public boolean isMusicaNaPlaylist(Musica musica){
        for(Musica m : getMusicas()){
            if(Objects.equals(m.getNome(), musica.getNome())){
                return true;
            }
        }
        return false;
    }

    public List<Musica> getMusicas(){
        return new ArrayList<>(musicas);
    }
}
