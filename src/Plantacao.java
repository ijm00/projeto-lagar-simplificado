
import caminhao.Caminhao;


public class Plantacao {
    private Azeitona azeitona;
    private boolean produzindo;
    private boolean abastecendoCaminhao;
    private Integer distanciaAteLagar;
    

    public Plantacao(Azeitona azeitona, Integer distanciaAteLagar) {
        this.azeitona = azeitona;
        this.distanciaAteLagar = distanciaAteLagar;
        this.produzindo = true;
        this.abastecendoCaminhao = false;
    }

    public Azeitona getAzeitona() {
        return azeitona;
    }

    public Integer getDistanciaAteLagar() {
        return distanciaAteLagar;
    }

    public void suspenderProducao() {
        this.produzindo = false;
    }

    public void retomarProducao() {
        this.produzindo = true;
    }
    
    public boolean isProduzindo() {
        return produzindo;
    }

    public void abastecerCaminhao() {
        if (this.produzindo && !this.abastecendoCaminhao) {
            Caminhao caminhao = this.requisitarCaminhao();
            System.out.println("Abastecendo caminhão");
            abastecendoCaminhao = true;
            try {
                Thread.sleep(caminhao.getTempoProcessamentoMillis());
                caminhao.avancaEstado();
                this.abastecendoCaminhao = false;
                System.out.println(caminhao.getEstado());
                MovimentadorCaminhoes.encaminharParaLagar(this, caminhao);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        } else {
            if (!this.produzindo) {
                System.out.println("A plantação parou de produzir!");
            }
        }
    }

    private Caminhao requisitarCaminhao() {
        return FornecedorCaminhoes.enviarCaminhao();
    }


    
}
