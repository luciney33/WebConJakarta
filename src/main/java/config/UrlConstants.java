package config;

public class UrlConstants {
    // Constructor privado para prevenir instanciación
    private UrlConstants() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad");
    }

    // URLs de los servlets
    public static final String URL_HOLA = "/servlet";
    public static final String URL_ADIVINA = "/adivina";

    // Rutas de plantillas Thymeleaf
    public static final String TEMPLATE_PREFIX = "/WEB-INF/templates/";
    public static final String TEMPLATE_SUFFIX = ".html";

    // Nombres de plantillas
    public static final String TEMPLATE_HOLA = "servlet";
    public static final String TEMPLATE_ADIVINA = "adivina";
}
