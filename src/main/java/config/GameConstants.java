package config;

public class GameConstants {
    // Constructor privado para prevenir instanciación
    private GameConstants() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad");
    }

    // Configuración del juego
    public static final int MAX_INTENTOS = 10;
    public static final int MIN_NUMERO = 1;
    public static final int MAX_NUMERO = 100;

    // Claves de sesión
    public static final String NUMERO_SECRETO = "numeroSecreto";
    public static final String INTENTOS_RESTANTES = "intentosRestantes";
    public static final String GAME_OVER = "gameOver";

    // Parámetros HTTP
    public static final String PARAM_NUMERO = "numero";
    public static final String PARAM_REINICIAR = "reiniciar";

    // Variables de contexto Thymeleaf
    public static final String VAR_INTENTOS = "intentos";
}
