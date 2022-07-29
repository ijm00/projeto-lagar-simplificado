import java.time.LocalDateTime;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import caminhao.Caminhao;
import caminhao.FilaDeCaminhoes;

public class App {
    public static void main(String[] args) throws InterruptedException {
        
        Azeitona azeitonaGalega = new Azeitona("galega");
        Plantacao plantacao = new Plantacao(azeitonaGalega, 4);
        RecepcaoLagar recepcao = new RecepcaoLagar();
        
        Thread abastecimentoCaminhoes = new Thread(() -> {
            long tempoTotaldecorrido = 0L;
            long tempoDecorrido;
            while (tempoTotaldecorrido < 30_000L) {
                long inicioOperacao = System.currentTimeMillis(); 
                    plantacao.abastecerCaminhao();
                    long fimOperacao = System.currentTimeMillis();
                tempoDecorrido = fimOperacao - inicioOperacao; 
                System.out.println("Abastecimento ocorreu no seguinte tempo em millis: " + tempoDecorrido);
                tempoTotaldecorrido += tempoDecorrido;
            }
             
            

        });

        abastecimentoCaminhoes.setPriority(1);
        abastecimentoCaminhoes.start();




        
            

        

            


        

        

    }
}
