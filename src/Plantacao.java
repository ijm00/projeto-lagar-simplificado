
import java.lang.Thread.State;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import caminhao.Caminhao;
import caminhao.FilaDeCaminhoes;

public class Plantacao {
    private Azeitona azeitona;
    private boolean produzindo;
    private boolean abastecendoCaminhao;
    private Integer distanciaAteLagar;
    private Caminhao caminhao;
    private Integer codigo;

    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();

    private AtomicReference<ConcurrentLinkedQueue<Caminhao>> fila = new AtomicReference<ConcurrentLinkedQueue<Caminhao>>(
            FilaDeCaminhoes.getInstance().getFila());

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

    public Runnable produzirTask(Integer limiteTempoProducaoMinutos) {
        return () -> {
            final LocalDateTime inicioOperacao = LocalDateTime.now();
           
            while (LocalDateTime.now().isBefore(inicioOperacao.plusMinutes(limiteTempoProducaoMinutos))) {
                
                if (this.produzindo) {
                    this.abastecerCaminhao().despacharCaminhao();
                }

                if (fila.get().size() >= 12) {
                    try {
                        this.suspenderProducao();
                        System.out.println("Produção suspensa!" + " O tamanho da fila é " + fila.get().size() + ".");
                        Thread.sleep(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (!this.produzindo && fila.get().size() <= 4) {
                    this.retomarProducao();
                    System.out.println("Plantação " + this.codigo + " retomando produção!");
                }
            }

                
        };
    }

    
    public void suspenderProducao() {
        pauseLock.lock();
        this.produzindo = false;

        
    }

    public void retomarProducao() {
        this.produzindo = true;
        unpaused.signalAll();
      
        pauseLock.unlock();
      
    }

    public boolean isProduzindo() {
        return this.produzindo;
    }

    public Plantacao abastecerCaminhao() {
        if (this.produzindo && !this.abastecendoCaminhao) {
            this.caminhao = this.requisitarCaminhao();
            this.caminhao.getRelatorio().setCodigoPlantacao(this.codigo);
            this.caminhao.getRelatorio().setTipoAzeitona(this.getAzeitona().getVariedade());
            // System.out.println("Abastecendo caminhão " + this.caminhao);
            abastecendoCaminhao = true;
            try {
                Thread.sleep(this.caminhao.getTempoProcessamentoMillis());
                this.caminhao.avancaEstado();
                this.abastecendoCaminhao = false;
                // System.out.println(this.caminhao + " " + this.caminhao.getEstado());

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
