import caminhao.Caminhao;
import caminhao.FilaDeCaminhoes;

public class RecepcaoLagar {
    public void descarregarCaminhoes() {
        Caminhao caminhao = FilaDeCaminhoes.getInstance().processar();
        if (caminhao != null) {
            try {
                System.out.println("Descarregando caminhão " + caminhao);
                Thread.sleep(caminhao.getTempoProcessamentoMillis());
                caminhao.avancaEstado();
                System.out.println(caminhao.toString() + caminhao.getEstado());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    
}
