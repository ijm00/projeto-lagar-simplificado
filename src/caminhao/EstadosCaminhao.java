package caminhao;
public interface EstadosCaminhao {
    enum StatusCaminhao {
        VAZIO,
        CARREGADO,
        TRANSPORTADO,
        AGUARDANDO_NA_RECEPCAO,
        DESCARREGADO_PROCESSADO, //estado final
        EXTINTO;
    }

    boolean proximo(Caminhao caminhao);

    StatusCaminhao proximosEstados();

    StatusCaminhao getStatus();

}
