public class Partida {
    private int puntuacion;
    private int intentos;

    public Partida(int puntuacion, int intentos) {
        this.puntuacion = puntuacion;
        this.intentos = intentos;
    }

    public int getPuntuacion() { return puntuacion; }
    public int getIntentos() { return intentos; }
}
