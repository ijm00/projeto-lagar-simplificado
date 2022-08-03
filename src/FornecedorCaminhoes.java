import java.util.function.Supplier;

import caminhao.Caminhao;

public abstract class FornecedorCaminhoes {
    private static Supplier <Caminhao> fornecerCaminhao = () -> 
        new Caminhao(4, 16, 2); 

    public static Caminhao enviarCaminhao() {
        return fornecerCaminhao.get();
    }

    
}
