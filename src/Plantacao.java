

import java.util.concurrent.ConcurrentLinkedQueue;

import caminhao.Caminhao;
import caminhao.FilaDeCaminhoes;


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

    public Runnable produzirTask(Long limiteTempoProducao) {
        return () -> {
            ConcurrentLinkedQueue<Caminhao> fila = FilaDeCaminhoes.getInstance().getFila();
            long tempoTotaldecorrido = 0L;
            long tempoDecorrido;
            while (tempoTotaldecorrido < limiteTempoProducao) {
                long inicioOperacao = System.currentTimeMillis(); 
                    this.abastecerCaminhao().despacharCaminhao();
                long fimOperacao = System.currentTimeMillis();
                tempoDecorrido = fimOperacao - inicioOperacao;
                System.out.println("Abastecimento ocorreu no seguinte tempo em millis: " + tempoDecorrido);
                tempoTotaldecorrido += tempoDecorrido;
                
                if (fila.size() >= 12) {
                    this.suspenderProducao();
                    System.out.println("Produção suspensa!");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(!this.isProduzindo() && fila.size() == 4){
                    this.retomarProducao();
                    System.out.println("Retomando produção!");
                }
            }
        };
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
