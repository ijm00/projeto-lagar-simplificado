package caminhao;

public class DescarregadoProcessadoEstado implements EstadosCaminhao{

    @Override
    public boolean proximo(Caminhao caminhao) {
        return false;
    }

    @Override
    public StatusCaminhao proximosEstados() {
        return StatusCaminhao.EXTINTO;
    }

    @Override
    public StatusCaminhao getStatus() {
        return StatusCaminhao.DESCARREGADO_PROCESSADO;
    }
    
}
