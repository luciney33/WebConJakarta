import java.util.ArrayList;
import java.util.List;

public class Estadistica {
    private List<Partida> partidas;

    public Estadistica() {
        this.partidas = new ArrayList<>();
    }

    public void agregarPartida(Partida partida) {
        this.partidas.add(partida);
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public int getTotalPartidas() {
        return partidas.size();
    }
}
