package caminhao;

import java.util.Random;

import caminhao.EstadosCaminhao.StatusCaminhao;

public class Caminhao {
    private Integer capacidadeCaminhaoToneladas;

    private EstadosCaminhao estadoCaminhao = new VazioEstado();
    private Runnable descarregou;
      
    public Caminhao(int capacidadeMinimaCaminhao, int capacidadeMaximaCaminhao) {
        this.capacidadeCaminhaoToneladas = new Random()
                .nextInt(capacidadeMaximaCaminhao + 1 - capacidadeMinimaCaminhao) + capacidadeMinimaCaminhao;
    }

    public Integer getCapacidadeCaminhaoToneladas() {
        return capacidadeCaminhaoToneladas;
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
        if (this.getEstadoCaminhao().getStatusCaminhao() == StatusCaminhao.CARREGADO) {
            try {
                Thread.sleep(distanciaAteLagar*1_000);
                this.avancaEstado();
                System.out.println(this.getEstadoCaminhao().getStatusCaminhao());
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void entrarNaFilaRecepcao() {
        if (this.getEstadoCaminhao() instanceof TransportandoEstado) {
            this.avancaEstado();
            System.out.println(this.getEstadoCaminhao().getStatusCaminhao());
        }
    }


    public void avancaEstado() {
        estadoCaminhao.proximo(this);
    }


    protected void setEstadoCaminhao(EstadosCaminhao estadoCaminhao) {
        this.estadoCaminhao = estadoCaminhao;
    }

    public EstadosCaminhao getEstadoCaminhao() {
        return estadoCaminhao;
    }



}
