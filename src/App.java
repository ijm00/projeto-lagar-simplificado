
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import caminhao.Caminhao;
import caminhao.FilaDeCaminhoes;

public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Azeitona azeitonaGalega = new Azeitona("Galega");
        Azeitona azeitonapicual = new Azeitona("Picual");

        Plantacao plantacao = new Plantacao(azeitonaGalega, 4);
        Plantacao plantacao2 = new Plantacao(azeitonapicual, 3);

        Lagar lagar = new Lagar(3);
        RecepcaoLagar recepcao = new RecepcaoLagar();

        ExecutorService produzir = Executors.newFixedThreadPool(2);
        ConcurrentLinkedQueue<Caminhao> fila = FilaDeCaminhoes.getInstance().getFila();
        
        Runnable descarregarCaminhoesTask = () -> {
            recepcao.descarregarCaminhoes();
        };


        produzir.execute(plantacao.produzirTask(30_000L));
        produzir.execute(plantacao2.produzirTask(30_000L));

        produzir.shutdown();


        ExecutorService descarregarCaminhoes = Executors.newFixedThreadPool(lagar.getNumeroPortasRecepcao());
        while (!produzir.isTerminated()) {
            if (!FilaDeCaminhoes.getInstance().getFila().isEmpty())
                descarregarCaminhoes.execute(descarregarCaminhoesTask);

        }
        descarregarCaminhoes.shutdown();

        ExecutorService descarregarRestantes = Executors.newFixedThreadPool(lagar.getNumeroPortasRecepcao());

        
        while(true) {
            if (produzir.isTerminated() && descarregarCaminhoes.isTerminated()) {
                System.out.println("Aguardando caminhoes remanescentes");
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
