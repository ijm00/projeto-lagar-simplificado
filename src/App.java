
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import caminhao.Caminhao;
import caminhao.FilaDeCaminhoes;

public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ConcurrentLinkedQueue<Caminhao> fila = FilaDeCaminhoes.getInstance().getFila();

        Azeitona azeitonaGalega = new Azeitona("Galega");
        Azeitona azeitonaCordovil = new Azeitona("Cordovil");
        Azeitona azeitonaPicual = new Azeitona("Picual");

        Lagar lagar = new Lagar(3);
        RecepcaoLagar recepcao = new RecepcaoLagar();

        List<Plantacao> plantacoes = new ArrayList<>() {
            {
                add(new Plantacao(azeitonaGalega, 4));
                add(new Plantacao(azeitonaGalega, 4));
                add(new Plantacao(azeitonaCordovil, 3));
                add(new Plantacao(azeitonaCordovil, 3));
                add(new Plantacao(azeitonaPicual, 2));
            }
        };

        final var inicioOperacao = LocalDateTime.now();
        
        System.out.println("Iniciando execução...");

        ExecutorService descarregarCaminhoes = Executors.newFixedThreadPool(lagar.getNumeroPortasRecepcao());
        while (LocalDateTime.now().isBefore(inicioOperacao.plusMinutes(2))) {
            for (Plantacao plantacao : plantacoes) {
                plantacao.produzir();
            }
            descarregarCaminhoes.execute(recepcao.descarregarCaminhoesTask());
        }
        descarregarCaminhoes.shutdown();

        ExecutorService descarregarRestantes = Executors.newFixedThreadPool(lagar.getNumeroPortasRecepcao());
        try {
            while (true) {
                if (descarregarCaminhoes.awaitTermination(15, TimeUnit.SECONDS)) {
                    while (!fila.isEmpty()) {
                        descarregarRestantes.execute(recepcao.descarregarCaminhoesTask());
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            descarregarRestantes.shutdown();
        }
    }
}
