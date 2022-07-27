package caminhao;

public class TransportandoEstado implements EstadosCaminhao {

    @Override
    public boolean proximo(Caminhao caminhao) {
        caminhao.setEstado(new AguardandoRecepcaoEstado());
        return true;
    }

    @Override
    public StatusCaminhao proximosEstados() {
        return StatusCaminhao.AGUARDANDO_NA_RECEPCAO;
    }

    @Override
    public StatusCaminhao getStatus() {
        return StatusCaminhao.TRANSPORTADO;
    }
    
}
