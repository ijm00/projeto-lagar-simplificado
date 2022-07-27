package caminhao;

public class VazioEstado implements EstadosCaminhao{

    @Override
    public boolean proximo(Caminhao caminhao) {
        caminhao.setEstado(new CarregadoEstado());
        return true;
    }

    @Override
    public StatusCaminhao proximosEstados() {
        return StatusCaminhao.CARREGADO;
    }

    @Override
    public StatusCaminhao getStatus() {
        return StatusCaminhao.VAZIO;
    }
    
}
