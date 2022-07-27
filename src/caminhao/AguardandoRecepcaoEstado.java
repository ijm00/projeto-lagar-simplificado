package caminhao;

public class AguardandoRecepcaoEstado implements EstadosCaminhao {

    @Override
    public boolean proximo(Caminhao caminhao) {
        caminhao.setEstadoCaminhao(new DescarregadoProcessadoEstado());
        return true;
    }

    @Override
    public StatusCaminhao proximosEstados() {
        return StatusCaminhao.DESCARREGADO_PROCESSADO;
    }

    @Override
    public StatusCaminhao getStatusCaminhao() {
        return StatusCaminhao.AGUARDANDO_NA_RECEPCAO;
    }
    
}
