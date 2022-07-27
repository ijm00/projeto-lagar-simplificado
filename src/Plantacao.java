
import caminhao.Caminhao;

import caminhao.EstadosCaminhao.StatusCaminhao;

public class Plantacao {
    private Azeitona azeitona;
    private Integer distanciaAteLagar;

    public Plantacao(Azeitona azeitona, Integer distanciaAteLagar) {
        this.azeitona = azeitona;
        this.distanciaAteLagar = distanciaAteLagar;
    }

    public Azeitona getAzeitona() {
        return azeitona;
    }
    public Integer getDistanciaAteLagar() {
        return distanciaAteLagar;
    }

    public void abastecerCaminhao(Caminhao caminhao, long tempoProcessamentoMillis) {
            System.out.println("Abastecendo caminhão");
            if (caminhao.getEstado() == StatusCaminhao.VAZIO) {
                try {
                    Thread.sleep(tempoProcessamentoMillis);
                    caminhao.avancaEstado();
                    System.out.println(caminhao.getEstado());
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        
    }
    
}
