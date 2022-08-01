
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
            produzir.execute(plantacao1.produzirTask(120_000L));
            produzir.execute(plantacao2.produzirTask(120_000L));
            produzir.execute(plantacao3.produzirTask(120_000L));
            produzir.execute(plantacao4.produzirTask(120_000L));
            produzir.execute(plantacao5.produzirTask(120_000L));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            produzir.shutdown();
        }



        Lagar lagar = new Lagar(2);
        RecepcaoLagar recepcao = new RecepcaoLagar();
        
        Runnable descarregarCaminhoesTask = () -> {
            recepcao.descarregarCaminhoes();
        };


        ExecutorService descarregarCaminhoes = Executors.newFixedThreadPool(lagar.getNumeroPortasRecepcao());
        while (!produzir.isTerminated()) {
            if (!FilaDeCaminhoes.getInstance().getFila().isEmpty())
                descarregarCaminhoes.execute(recepcao.descarregarCaminhoesTask());

        }
        descarregarCaminhoes.shutdown();


        ExecutorService descarregarRestantes = Executors.newFixedThreadPool(lagar.getNumeroPortasRecepcao());
        while(true) {
            if (produzir.isTerminated() && descarregarCaminhoes.isTerminated()) {
                //System.out.println("Aguardando caminhoes remanescentes");
                Thread.sleep(15_000);
                while (!fila.isEmpty()) {
                    descarregarRestantes.execute(descarregarCaminhoesTask);
                }
                descarregarRestantes.shutdown();
                break;
            }
        }
        
        


        
        

        
    }
}
