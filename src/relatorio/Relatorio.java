package relatorio;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Relatorio {
    private Integer codigoPlantacao;
    private String codigoRecepcao;
    private LocalDateTime inicioViagemCaminhao;
    private LocalDateTime fimViagemCaminhao;    
    private Integer capacidadeCaminhao;
    private String tipoAzeitona;
    private static Integer contadorToneladasProcessadas = 0;

    public Integer getCodigoPlantacao() {
        return codigoPlantacao;
    }

    public void setCodigoPlantacao(Integer codigoPlantacao) {
        this.codigoPlantacao = codigoPlantacao;
    }

    public String getCodigoRecepcao() {
        return codigoRecepcao;
    }

    public void setCodigoRecepcao(String codigoRecepcao) {
        this.codigoRecepcao = codigoRecepcao;
    }

    public LocalDateTime getInicioViagemCaminhao() {
        return inicioViagemCaminhao;
    }

    public void setInicioViagemCaminhao(LocalDateTime inicioViagemCaminhao) {
        this.inicioViagemCaminhao = inicioViagemCaminhao;
    }

    public LocalDateTime getFimViagemCaminhao() {
        return fimViagemCaminhao;
    }

    public void setFimViagemCaminhao(LocalDateTime fimViagemCaminhao) {
        this.fimViagemCaminhao = fimViagemCaminhao;
    }

    public Integer getCapacidadeCaminhao() {
        return capacidadeCaminhao;
    }

    public void setCapacidadeCaminhao(Integer capacidadeCaminhao) {
        this.capacidadeCaminhao = capacidadeCaminhao;
    }

    public String getTipoAzeitona() {
        return tipoAzeitona;
    }

    public void setTipoAzeitona(String tipoAzeitona) {
        this.tipoAzeitona = tipoAzeitona;
    }

    public static Integer getContadorToneladasProcessadas() {
        return contadorToneladasProcessadas;
    }

    public static void incrementarToneladasProcessadas(Integer toneladasProcessadas) {
        Relatorio.contadorToneladasProcessadas += toneladasProcessadas;
    }
    
    @Override
    public String toString() {
        return String.format(
            "%s - %04d >> %d toneladas de %s na recepcao %s de origem da plantação %d com tempo total de %d segundos.", 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
            contadorToneladasProcessadas,
            this.capacidadeCaminhao,
            this.tipoAzeitona,
            this.codigoRecepcao,
            this.codigoPlantacao,
            ChronoUnit.SECONDS.between(this.inicioViagemCaminhao, this.fimViagemCaminhao)
            );
    }
    
}
