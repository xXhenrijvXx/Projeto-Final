package br.edu.up.pangaSongs;

import br.edu.up.pangaSongs.archives.*;
import br.edu.up.pangaSongs.util.ScannerUtil;
import br.edu.up.pangaSongs.views.*;
import org.apache.logging.log4j.*;

public class Main {
    public static void main(String[] args){
        Logger logger = LogManager.getLogger(Main.class);

        logger.info("Iniciando aplicação...");

        carregarArquivos();
        logger.info("Arquivos carregados com sucesso!");

        PrincipalView.menu();

        salvarArquivos();

        ScannerUtil.fechaScanner();

        logger.info("Encerrando aplicação...");
    }

    private static void salvarArquivos() {
        ArquivoMusica.salvar();
        ArquivoPlaylist.salvar();
        ArquivoIds.salvar();
    }

    private static void carregarArquivos() {
        ArquivoMusica.carregar();
        ArquivoPlaylist.carregar();
        ArquivoIds.carregar();
    }
}