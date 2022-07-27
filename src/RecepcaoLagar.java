import caminhao.Caminhao;
import caminhao.EstadosCaminhao.StatusCaminhao;

public class RecepcaoLagar {
    public void descarregarCaminhao(Caminhao caminhao, long tempoProcessamentoMillis) {
        if (caminhao.getEstado() == StatusCaminhao.AGUARDANDO_NA_RECEPCAO) {
                System.out.println("Descarregando caminhão");
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
