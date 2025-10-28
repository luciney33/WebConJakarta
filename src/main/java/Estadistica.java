import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estadistica {
    private static Map<String, Estadistica> estadisticasUsuarios = new HashMap<>();
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

    public static Estadistica obtenerEstadistica(String usuario) {
        return estadisticasUsuarios.get(usuario);
    }

    public static void crearEstadistica(String usuario) {
        estadisticasUsuarios.putIfAbsent(usuario, new Estadistica());
    }

    public static Estadistica obtenerOCrearEstadistica(String usuario) {
        crearEstadistica(usuario);
        return obtenerEstadistica(usuario);
    }
}
