import caminhao.Caminhao;
import caminhao.FilaDeCaminhoes;
import caminhao.EstadosCaminhao.StatusCaminhao;

public class RecepcaoLagar {
    public void descarregarCaminhao(Caminhao caminhao) {
        if (caminhao.getEstado() == StatusCaminhao.AGUARDANDO_NA_RECEPCAO) {
                System.out.println("Descarregando caminhão");
                FilaDeCaminhoes.getInstance().processar();
                try {
                    Thread.sleep(caminhao.getTempoProcessamentoMillis());
                    caminhao.avancaEstado();
                    System.out.println(caminhao.getEstado());
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
        }
    }

    
}
