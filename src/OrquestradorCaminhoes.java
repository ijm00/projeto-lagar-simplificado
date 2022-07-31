
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import caminhao.Caminhao;

public abstract class OrquestradorCaminhoes {
    
    public static void encaminharParaLagar(Plantacao origem, Caminhao caminhao) {
        ExecutorService taskTransportar = Executors.newFixedThreadPool(1);
        taskTransportar.submit(() -> caminhao.transportarAzeitonas(origem.getDistanciaAteLagar()));
        taskTransportar.shutdown();
    }


}
