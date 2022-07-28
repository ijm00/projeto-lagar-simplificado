import caminhao.Caminhao;
import caminhao.FilaDeCaminhoes;
import caminhao.EstadosCaminhao.StatusCaminhao;

public class RecepcaoLagar {
    public void descarregarCaminhao(Caminhao caminhao, long tempoProcessamentoMillis) {
        if (caminhao.getEstado() == StatusCaminhao.AGUARDANDO_NA_RECEPCAO) {
                System.out.println("Descarregando caminhão");
                FilaDeCaminhoes.getInstance().processar();
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
