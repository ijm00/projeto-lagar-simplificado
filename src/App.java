
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import caminhao.Caminhao;
import caminhao.FilaDeCaminhoes;

public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Azeitona azeitonaGalega = new Azeitona("galega");
        Plantacao plantacao = new Plantacao(azeitonaGalega, 4);
        Lagar lagar = new Lagar(3);
        RecepcaoLagar recepcao = new RecepcaoLagar();
        ExecutorService produzir = Executors.newFixedThreadPool(1);
        ConcurrentLinkedQueue<Caminhao> fila = FilaDeCaminhoes.getInstance().getFila();

        Runnable produzirTask = () -> {
            long tempoTotaldecorrido = 0L;
            long tempoDecorrido;
            while (tempoTotaldecorrido < 30_000) {
                long inicioOperacao = System.currentTimeMillis(); 
                    plantacao.abastecerCaminhao().despacharCaminhao();
                long fimOperacao = System.currentTimeMillis();
                tempoDecorrido = fimOperacao - inicioOperacao;
                System.out.println("Abastecimento ocorreu no seguinte tempo em millis: " + tempoDecorrido);
                tempoTotaldecorrido += tempoDecorrido;
                
                if (fila.size() >= 12) {
                    plantacao.suspenderProducao();
                    System.out.println("Produção suspensa!");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(!plantacao.isProduzindo() && fila.size() == 4){
                    plantacao.retomarProducao();
                    System.out.println("Retomando produção!");
                }
            }
        };
        
        Runnable descarregarCaminhoesTask = () -> {
            recepcao.descarregarCaminhoes();
        };


        produzir.execute(produzirTask);

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
