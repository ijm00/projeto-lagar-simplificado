package caminhao;

public class CarregadoEstado implements EstadosCaminhao {

    @Override
    public boolean proximo(Caminhao caminhao) {
        caminhao.setEstadoCaminhao(new TransportandoEstado());
        return true;
    }

    @Override
    public StatusCaminhao proximosEstados() {
        return StatusCaminhao.TRANSPORTADO;
    }

    @Override
    public StatusCaminhao getStatusCaminhao() {
        return StatusCaminhao.CARREGADO;
    }
    
}
