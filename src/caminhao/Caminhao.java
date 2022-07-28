package caminhao;

import java.util.Random;
import caminhao.EstadosCaminhao.StatusCaminhao;

public class Caminhao {
    private Integer capacidadeToneladas;
    private EstadosCaminhao estado = new VazioEstado();
    private Runnable descarregou;
    
      
    public Caminhao(int capacidadeMinima, int capacidadeMaxima) {
        this.capacidadeToneladas = new Random()
                .nextInt(capacidadeMaxima + 1 - capacidadeMinima) + capacidadeMinima;
    }

    public Integer getCapacidadeToneladas() {
        return capacidadeToneladas;
    }

    public void quandoDescarregou(Runnable descarregou) {
        this.descarregou = descarregou;
    }

    public void descarrega() {
        avancaEstado();
        descarregou.run();
    }

    public void transportarAzeitonas(int distanciaAteLagar) {
        System.out.println("Transportando azeitonas");
        if (this.getEstado() == StatusCaminhao.CARREGADO) {
            try {
                Thread.sleep(distanciaAteLagar*1_000);
                this.avancaEstado();
                System.out.println(this.getEstado());
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void entrarNaFilaRecepcao() {
        if (this.getEstado() == StatusCaminhao.TRANSPORTADO) {
            this.avancaEstado();
            FilaDeCaminhoes.getInstance().adicionar(this);
            System.out.println(this.getEstado());
        }
    }


    public void avancaEstado() {
        estado.proximo(this);
    }


    protected void setEstado(EstadosCaminhao estado) {
        this.estado = estado;
    }

    public StatusCaminhao getEstado() {
        return estado.getStatus();
    }



}
