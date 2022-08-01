

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

import caminhao.Caminhao;
import caminhao.FilaDeCaminhoes;


public class Plantacao {
    private Azeitona azeitona;
    private boolean produzindo;
    private boolean abastecendoCaminhao;
    private Integer distanciaAteLagar;
    private Caminhao caminhao;
    private Integer codigo;

    private AtomicReference<ConcurrentLinkedQueue<Caminhao>> fila = 
        new AtomicReference<ConcurrentLinkedQueue<Caminhao>>(FilaDeCaminhoes.getInstance().getFila());


    private static Integer geradorCodigos = 0;

    public Plantacao(Azeitona azeitona, Integer distanciaAteLagar) {
        geradorCodigos += 1;
        this.codigo = geradorCodigos;
        this.azeitona = azeitona;
        this.distanciaAteLagar = distanciaAteLagar;
        this.produzindo = true;
        this.abastecendoCaminhao = false;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Azeitona getAzeitona() {
        return azeitona;
    }

    public Integer getDistanciaAteLagar() {
        return distanciaAteLagar;
    }

    public Runnable produzirTask(Long limiteTempoProducao) {
        return () -> {
            long tempoTotaldecorrido = 0L;
            long tempoDecorrido;
            long inicioOperacao = System.currentTimeMillis(); 
            while (tempoTotaldecorrido < limiteTempoProducao) {
                this.abastecerCaminhao().despacharCaminhao();
                
                if (fila.get().size() >= 12) {
                    try {
                    this.suspenderProducao();
                    System.out.println("Produção suspensa!" + " O tamanho da fila é " + fila.get().size() + ".");
                    Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } 
                
                if (!this.isProduzindo() && fila.get().size() <= 4) {
                    this.retomarProducao();
                    System.out.println("Plantação " + this.codigo + "retomando produção!");
                }
                
                long fimOperacao = System.currentTimeMillis();
                tempoDecorrido = fimOperacao - inicioOperacao;
                //System.out.println("Abastecimento ocorreu no seguinte tempo em millis: " + tempoDecorrido);
                tempoTotaldecorrido += tempoDecorrido;
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
        return this.produzindo;
    }

    public Plantacao abastecerCaminhao() {
        if (this.produzindo && !this.abastecendoCaminhao) {
            this.caminhao = this.requisitarCaminhao();
            this.caminhao.getRelatorio().setCodigoPlantacao(this.codigo);
            this.caminhao.getRelatorio().setTipoAzeitona(this.getAzeitona().getVariedade());
            //System.out.println("Abastecendo caminhão " + this.caminhao);
            abastecendoCaminhao = true;
            try {
                Thread.sleep(this.caminhao.getTempoProcessamentoMillis());
                this.caminhao.avancaEstado();
                this.abastecendoCaminhao = false;
                //System.out.println(this.caminhao + " " + this.caminhao.getEstado());
                
            } catch (InterruptedException ie) {
                ie.printStackTrace();
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
