package caminhao;
import java.util.LinkedList;
import java.util.Queue;

public class FilaDeCaminhoes {
    private static FilaDeCaminhoes filaDeCaminhoes;

    private Queue<Caminhao> fila = new LinkedList<>();
    
    private FilaDeCaminhoes() {

    };

    public static FilaDeCaminhoes getInstance() {
        if (filaDeCaminhoes == null) {
            filaDeCaminhoes = new FilaDeCaminhoes();
        }
        return filaDeCaminhoes;
    }

    public void adicionar(Caminhao caminhao) {
        this.fila.add(caminhao);
    }

    public void processar() {
        this.fila.poll();
    }
    
    public Queue<Caminhao> getFila() {
        return fila;
    }

}
