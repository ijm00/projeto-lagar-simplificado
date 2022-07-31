
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import caminhao.FilaDeCaminhoes;

public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Azeitona azeitonaGalega = new Azeitona("galega");
        Plantacao plantacao = new Plantacao(azeitonaGalega, 4);
        Lagar lagar = new Lagar(1);
        RecepcaoLagar recepcao = new RecepcaoLagar();
        ExecutorService produzir = Executors.newFixedThreadPool(1);


        produzir.execute(() -> {
            long tempoTotaldecorrido = 0L;
            long tempoDecorrido;
            while (tempoTotaldecorrido < 12_000) {
                long inicioOperacao = System.currentTimeMillis(); 
                    plantacao.abastecerCaminhao().despacharCaminhao();
                    long fimOperacao = System.currentTimeMillis();
                tempoDecorrido = fimOperacao - inicioOperacao;
                System.out.println("Abastecimento ocorreu no seguinte tempo em millis: " + tempoDecorrido);
                tempoTotaldecorrido += tempoDecorrido;
            }
        });

        produzir.shutdown();


        ExecutorService descarregarCaminhoes = Executors.newFixedThreadPool(lagar.getNumeroPortasRecepcao());
        while (!produzir.isTerminated()) {
            if (!FilaDeCaminhoes.getInstance().getFila().isEmpty())
                descarregarCaminhoes.execute(() -> {
                    recepcao.descarregarCaminhoes();
            });

        }
        descarregarCaminhoes.shutdown();


        


        



        

        
        
    }
}
