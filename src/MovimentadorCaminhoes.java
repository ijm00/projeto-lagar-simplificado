import java.util.LinkedList;
import java.util.Queue;

import caminhao.Caminhao;

public abstract class MovimentadorCaminhoes {

    private static Queue<Caminhao> caminhoesEmEsperaNaRecepcaoLagar = new LinkedList<>();

    public static void encaminharParaLagar(Plantacao origem, Caminhao caminhao) {
        caminhao.transportarAzeitonas(origem.getDistanciaAteLagar());
    }

    public static void alocarNaFilaLagar(Caminhao caminhao) {
        caminhoesEmEsperaNaRecepcaoLagar.add(caminhao);
    }
}
