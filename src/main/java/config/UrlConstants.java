package config;

public class UrlConstants {
    // Constructor privado para prevenir instanciación
    private UrlConstants() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad");
    }

    // URLs de los servlets
    public static final String URL_FINISH = "/finish";
    public static final String URL_LOGIN = "/login";
    public static final String URL_JUEGO = "/juego";

    public static final String PAGE_LOGIN_HTML = "login.html";
    public static final String PAGE_LOGIN_ERROR = "login.html?error=true";

    // Rutas de plantillas Thymeleaf
    public static final String TEMPLATE_PREFIX = "/WEB-INF/templates/";
    public static final String TEMPLATE_SUFFIX = ".html";

    // Nombres de plantillas
    public static final String TEMPLATE_FINISH = "finish";
    public static final String TEMPLATE_LOGIN = "login";
    public static final String TEMPLATE_JUEGO = "juego";

}
