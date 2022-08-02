
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import caminhao.Caminhao;
import caminhao.FilaDeCaminhoes;

public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Azeitona azeitonaGalega = new Azeitona("Galega");
        Azeitona azeitonaCordovil = new Azeitona("Cordovil");
        Azeitona azeitonaPicual = new Azeitona("Picual");

        Plantacao plantacao1 = new Plantacao(azeitonaGalega, 4);
        Plantacao plantacao2 = new Plantacao(azeitonaGalega, 4);
        Plantacao plantacao3 = new Plantacao(azeitonaCordovil, 3);
        Plantacao plantacao4 = new Plantacao(azeitonaCordovil, 3);
        Plantacao plantacao5 = new Plantacao(azeitonaPicual, 2);

        ExecutorService produzir = Executors.newFixedThreadPool(5);
        ConcurrentLinkedQueue<Caminhao> fila = FilaDeCaminhoes.getInstance().getFila();

        try {
            produzir.execute(plantacao1.produzirTask(2));
            produzir.execute(plantacao2.produzirTask(2));
            produzir.execute(plantacao3.produzirTask(2));
            produzir.execute(plantacao4.produzirTask(2));
            produzir.execute(plantacao5.produzirTask(2));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            produzir.shutdown();
        }

        Lagar lagar = new Lagar(3);
        RecepcaoLagar recepcao = new RecepcaoLagar();

        ExecutorService descarregarCaminhoes = Executors.newFixedThreadPool(lagar.getNumeroPortasRecepcao());
        try {
            while (!produzir.isTerminated()) {
                if (!FilaDeCaminhoes.getInstance().getFila().isEmpty())
                    descarregarCaminhoes.execute(recepcao.descarregarCaminhoesTask());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            descarregarCaminhoes.shutdown();
        }

        ExecutorService descarregarRestantes = Executors.newFixedThreadPool(lagar.getNumeroPortasRecepcao());
        try {
            while (true) {
                if (produzir.isTerminated() && descarregarCaminhoes.isTerminated()) {
                    // System.out.println("Aguardando caminhoes remanescentes");
                    Thread.sleep(15_000);
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
