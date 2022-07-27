@FunctionalInterface
public interface TempoCargaDescarga {
    long getTempoProcessamento(int pesoConsiderado, int TempoCargaDescargaDoPeso, int capacidadeDoCaminhao);
}
