import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


import caminhao.Caminhao;
import caminhao.FilaDeCaminhoes;

public class App {
    public static void main(String[] args) throws InterruptedException {
        
        Caminhao caminhao1 = new Caminhao(4, 16);
        Caminhao caminhao2 = new Caminhao(4, 16);
        Azeitona azeitonaGalega = new Azeitona("galega");
        Plantacao plantacao = new Plantacao(azeitonaGalega, 4);
        RecepcaoLagar recepcao = new RecepcaoLagar();
        long tempoDecorrido;
        TempoCargaDescarga tempoProcessamentoCaminhao = (pesoConsiderado, TempoCargaDescargaDoPeso,
                capacidadeDoCaminhao) -> {
            return TimeUnit.SECONDS.toMillis( TempoCargaDescargaDoPeso * capacidadeDoCaminhao / pesoConsiderado );
        };
        Long tempoProcessamentoMillis = tempoProcessamentoCaminhao
                .getTempoProcessamento(4,
                        2,
                        caminhao1.getCapacidadeToneladas());

        long inicio = System.currentTimeMillis();
        plantacao.abastecerCaminhao(caminhao1, tempoProcessamentoMillis);
        caminhao1.transportarAzeitonas(plantacao.getDistanciaAteLagar());
        caminhao1.entrarNaFilaRecepcao();
        plantacao.abastecerCaminhao(caminhao2, tempoProcessamentoMillis);
        caminhao2.transportarAzeitonas(plantacao.getDistanciaAteLagar());
        caminhao2.entrarNaFilaRecepcao();
        System.out.println(FilaDeCaminhoes.getInstance().getFila().stream().count());
        recepcao.descarregarCaminhao(caminhao1, tempoProcessamentoMillis);
        FilaDeCaminhoes.getInstance().getFila().forEach(System.out::println);
        recepcao.descarregarCaminhao(caminhao2, tempoProcessamentoMillis);
        FilaDeCaminhoes.getInstance().getFila().forEach(System.out::println);
        long fim = System.currentTimeMillis();
        tempoDecorrido = fim - inicio;

        

        System.out.println(String.format("%s - %04d >> %d toneladas de Galega na recepção 1 de origem da plantação 1 com tempo total de %d segundos.",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
            caminhao1.getCapacidadeToneladas(),
            caminhao1.getCapacidadeToneladas(),
            TimeUnit.MILLISECONDS.toSeconds(tempoDecorrido)
            ));
        System.out.println(String.format("%s - %04d >> %d toneladas de Galega na recepção 1 de origem da plantação 1 com tempo total de %d segundos.",
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
        caminhao2.getCapacidadeToneladas(),
        caminhao2.getCapacidadeToneladas(),
        TimeUnit.MILLISECONDS.toSeconds(tempoDecorrido)
        ));
    }

}
