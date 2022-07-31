package caminhao;

import java.util.concurrent.ConcurrentLinkedQueue;

public class FilaDeCaminhoes {
    private static FilaDeCaminhoes filaDeCaminhoes;
    private ConcurrentLinkedQueue<Caminhao> filaConcorrente = new ConcurrentLinkedQueue<>();
    
    private FilaDeCaminhoes() {

    };

    public static FilaDeCaminhoes getInstance() {
        if (filaDeCaminhoes == null) {
            filaDeCaminhoes = new FilaDeCaminhoes();
        }
        return filaDeCaminhoes;
    }

    public void adicionar(Caminhao caminhao) {
        this.filaConcorrente.add(caminhao);
    }

    public Caminhao processar() {
            return this.filaConcorrente.poll();
    }
    
    public ConcurrentLinkedQueue<Caminhao> getFila() {
        return filaConcorrente;
    }

}
