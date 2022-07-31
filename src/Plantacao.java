

import caminhao.Caminhao;


public class Plantacao {
    private Azeitona azeitona;
    private boolean produzindo;
    private boolean abastecendoCaminhao;
    private Integer distanciaAteLagar;
    private Caminhao caminhao;
   

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

    public Plantacao abastecerCaminhao() {
        if (this.produzindo && !this.abastecendoCaminhao) {
            this.caminhao = this.requisitarCaminhao();
            this.caminhao.getRelatorio().setCodigoPlantacao(1); //TODO hardcode aqui!
            this.caminhao.getRelatorio().setTipoAzeitona(this.getAzeitona().getVariedade());
            System.out.println("Abastecendo caminhão " + this.caminhao);
            abastecendoCaminhao = true;
            try {
                Thread.sleep(this.caminhao.getTempoProcessamentoMillis());
                this.caminhao.avancaEstado();
                this.abastecendoCaminhao = false;
                System.out.println(this.caminhao + " " + this.caminhao.getEstado());
                
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        } else {
            if (!this.produzindo) {
                System.out.println("A plantação parou de produzir!");
            }
        }
        return this;
    }

    private Caminhao requisitarCaminhao() {
        return FornecedorCaminhoes.enviarCaminhao();
    }

    public void despacharCaminhao() {
        OrquestradorCaminhoes.encaminharParaLagar(this, this.caminhao);
    }


    
}
