import java.util.function.Supplier;

import caminhao.Caminhao;

public abstract class FornecedorCaminhoes {
    private static Supplier <Caminhao> fornecerCaminhao = () -> 
        new Caminhao(4, 16, 2); 

    public static Caminhao enviarCaminhao() {
        //System.out.println("Um novo caminhão está a caminho da plantação!");
        return fornecerCaminhao.get();
    }

    
}
