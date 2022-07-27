import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


import caminhao.Caminhao;

public class App {
    public static void main(String[] args) throws InterruptedException {

        Caminhao caminhao = new Caminhao(4, 16);
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
                        caminhao.getCapacidadeCaminhaoToneladas());

        long inicio = System.currentTimeMillis();
        plantacao.abastecerCaminhao(caminhao, tempoProcessamentoMillis);
        caminhao.transportarAzeitonas(plantacao.getDistanciaAteLagar());
        caminhao.entrarNaFilaRecepcao();
        recepcao.descarregarCaminhao(caminhao, tempoProcessamentoMillis);
        long fim = System.currentTimeMillis();
        tempoDecorrido = fim - inicio;

        System.out.println(String.format("%s - %04d >> %d toneladas de Galega na recepção 1 de origem da plantação 1 com tempo total de %d segundos.",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
            caminhao.getCapacidadeCaminhaoToneladas(),
            caminhao.getCapacidadeCaminhaoToneladas(),
            TimeUnit.MILLISECONDS.toSeconds(tempoDecorrido)
            ));
    }

}
